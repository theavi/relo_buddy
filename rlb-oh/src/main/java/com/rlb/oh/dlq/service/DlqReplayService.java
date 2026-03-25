@Service
public class DlqReplayService {

    private static final Logger logger = LoggerFactory.getLogger(DlqReplayService.class);

    private final DlqEventRepository dlqEventRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderEventProcessor orderEventProcessor;

    @Value("${spring.kafka.topics.orderCreated:order.created.v1}")
    private String orderCreatedTopic;

    public DlqReplayService(DlqEventRepository dlqEventRepository,
                             KafkaTemplate<String, Object> kafkaTemplate,
                             OrderEventProcessor orderEventProcessor) {
        this.dlqEventRepository  = dlqEventRepository;
        this.kafkaTemplate       = kafkaTemplate;
        this.orderEventProcessor = orderEventProcessor;
    }

    @Transactional
    public void replay(Long dlqEventId, String remarks) {
        DlqEvent dlqEvent = dlqEventRepository.findById(dlqEventId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "DLQ event not found. id=" + dlqEventId));

        if (dlqEvent.getStatus() != DlqEventStatus.PENDING) {
            throw new IllegalStateException(
                    "DLQ event is not in PENDING state. id=" + dlqEventId +
                    ", status=" + dlqEvent.getStatus());
        }

        // Republish to original topic — will be picked up by normal consumer
        kafkaTemplate.send(orderCreatedTopic, dlqEvent.getPayload());
        logger.info("DLQ event replayed to topic='{}'. dlqEventId='{}'",
                orderCreatedTopic, dlqEventId);

        dlqEvent.markReplayed(remarks);
        dlqEventRepository.save(dlqEvent);
    }

    /**
     * Discards a DLQ event — operator confirmed it can be ignored.
     */
    @Transactional
    public void discard(Long dlqEventId, String remarks) {
        DlqEvent dlqEvent = dlqEventRepository.findById(dlqEventId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "DLQ event not found. id=" + dlqEventId));

        if (dlqEvent.getStatus() != DlqEventStatus.PENDING) {
            throw new IllegalStateException(
                    "DLQ event is not in PENDING state. id=" + dlqEventId);
        }

        dlqEvent.markDiscarded(remarks);
        dlqEventRepository.save(dlqEvent);
        logger.info("DLQ event discarded. dlqEventId='{}', remarks='{}'", dlqEventId, remarks);
    }

    /**
     * Returns all PENDING DLQ events — for ops dashboard.
     */
    public List<DlqEvent> getPendingEvents() {
        return dlqEventRepository.findByStatus(DlqEventStatus.PENDING);
    }
}