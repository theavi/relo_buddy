@Service
public class CreateOrderEventProcessor {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventProcessor.class);

    private final OrderHandleService orderHandleService;
    private final ProcessedEventRepository processedEventRepository;

    public OrderEventProcessor(OrderHandleService orderHandleService,
                                ProcessedEventRepository processedEventRepository) {
        this.orderHandleService = orderHandleService;
        this.processedEventRepository = processedEventRepository;
    }

    @Transactional
    public void processIdempotently(OrderCreateEvent event, String eventId,
                                     String topic, Integer partition, Long offset) {

        if (processedEventRepository.existsById(eventId)) {
            logger.info("Duplicate event skipped. eventId='{}', topic='{}', partition={}, offset={}",
                    eventId, topic, partition, offset);
            return;
        }

        try {
            logger.info("Processing OrderCreateEvent. eventId='{}', custId='{}', pincode='{}', topic='{}', partition={}, offset={}",
                    eventId, event.getCustId(), event.getPincode(), topic, partition, offset);

            orderHandleService.handleOrder(event);

            processedEventRepository.save(
                    new ProcessedEvent(eventId, Instant.now(), topic, partition, offset));

            logger.info("OrderCreateEvent processed successfully. eventId='{}'", eventId);

        } catch (DataIntegrityViolationException ex) {
            logger.warn("Concurrent duplicate detected (unique constraint). eventId='{}' — skipping.", eventId);
            // Swallowed intentionally — idempotency race condition, not a real failure
        } catch (Exception ex) {
            logger.error("OrderCreateEvent processing failed. eventId='{}', topic='{}', partition={}, offset={}",
                    eventId, topic, partition, offset, ex);
            throw ex; // Rethrow so DefaultErrorHandler can retry → DLQ
        }
    }
}