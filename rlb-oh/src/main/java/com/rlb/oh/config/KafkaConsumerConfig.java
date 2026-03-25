@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:rlbGroup}")
    private String groupId;

    @Value("${spring.kafka.topics.dlq:order.created.v1.DLQ}")
    private String dlqTopic;

    @Value("${spring.kafka.consumer.max-attempts:3}")
    private int maxAttempts;

    @Bean
    public ConsumerFactory<String, OrderCreateEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // manual commit via error handler
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OrderCreateEvent.class.getName());
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // ─────────────────────────────────────────────
    // 4. DeadLetterPublishingRecoverer
    // Called when all retries are exhausted
    // Publishes failed message to DLQ topic with
    // all exception headers attached automatically
    // ─────────────────────────────────────────────
    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer() {
        return new DeadLetterPublishingRecoverer(
                kafkaTemplate(),
                // Route ALL failures to our configured DLQ topic
                // -1 means let Kafka choose the partition
                (record, ex) -> {
                    logger.error(
                        "Routing to DLQ. topic='{}', partition={}, offset={}, exception='{}'",
                        record.topic(), record.partition(), record.offset(),
                        ex.getMessage()
                    );
                    return new TopicPartition(dlqTopic, -1);
                }
        );
    }

    // ─────────────────────────────────────────────
    // 5. DefaultErrorHandler
    // Handles ALL exceptions thrown by @KafkaListener
    // Retries with exponential backoff
    // After exhaustion → calls DeadLetterPublishingRecoverer
    // ─────────────────────────────────────────────
    @Bean
    public DefaultErrorHandler errorHandler() {
        // Exponential backoff: 1s → 2s → 4s then DLQ
        ExponentialBackOff backOff = new ExponentialBackOff(1000L, 2.0);
        backOff.setMaxAttempts(maxAttempts);

        DefaultErrorHandler handler = new DefaultErrorHandler(
                deadLetterPublishingRecoverer(), backOff
        );

        // ─────────────────────────────────────────
        // Non-retryable exceptions
        // These skip ALL retries and go straight to DLQ
        // ─────────────────────────────────────────
        handler.addNotRetryableExceptions(
                InvalidOrderEventException.class,   // null/blank eventId
                DeserializationException.class      // malformed JSON payload
        );

        // ─────────────────────────────────────────
        // Log every retry attempt
        // ─────────────────────────────────────────
        handler.setRetryListeners((record, ex, deliveryAttempt) ->
                logger.warn(
                    "Retry attempt {} for topic='{}', partition={}, offset={}, exception='{}'",
                    deliveryAttempt,
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    ex.getMessage()
                )
        );

        return handler;
    }

    // ─────────────────────────────────────────────
    // 6. KafkaListenerContainerFactory
    // Wires everything together:
    //   consumerFactory + errorHandler
    // Used by @KafkaListener automatically
    // ─────────────────────────────────────────────
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCreateEvent>
            kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, OrderCreateEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(errorHandler());

        // Manual offset commit — only commit after successful processing
        factory.getContainerProperties()
               .setAckMode(ContainerProperties.AckMode.RECORD);

        return factory;
    }
}