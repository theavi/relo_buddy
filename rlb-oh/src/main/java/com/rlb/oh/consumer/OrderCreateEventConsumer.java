@Component
public class OrderCreateEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderCreateEventConsumer.class);

    private final OrderEventProcessor orderEventProcessor;

    public OrderCreateEventConsumer(OrderEventProcessor orderEventProcessor) {
        this.orderEventProcessor = orderEventProcessor;
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.orderCreated:order.created.v1}",
            groupId = "${spring.kafka.consumer.group-id:rlbGroup}",
            errorHandler = "globalKafkaExceptionHandler"  // ← wired here
    )
    public void consume(
            @Payload OrderCreateEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.OFFSET) Long offset
    ) {
        if (event == null) {
            logger.error("Null event received. topic='{}', partition={}, offset={}",
                    topic, partition, offset);
            throw new InvalidOrderEventException("Null event received");
        }

        final String rawId = event.getId();
        if (!StringUtils.hasText(rawId)) {
            logger.error("Blank eventId received. topic='{}', partition={}, offset={}",
                    topic, partition, offset);
            throw new InvalidOrderEventException("Event has blank/null id");
        }

        final String eventId = rawId.trim();
        logger.debug("Event received. eventId='{}', topic='{}', partition={}, offset={}",
                eventId, topic, partition, offset);

        orderEventProcessor.processIdempotently(event, eventId, topic, partition, offset);
    }
}