@Service
public class CreateOrderEventProcessor {

   private static final Logger logger = LoggerFactory.getLogger(OrderEventProcessor.class);

    private final OrderHandleService orderHandleService;
    private final IdempotencyGuard idempotencyGuard;

    public OrderEventProcessor(OrderHandleService orderHandleService,
                                IdempotencyGuard idempotencyGuard) {
        this.orderHandleService = orderHandleService;
        this.idempotencyGuard = idempotencyGuard;
    }

    public void processIdempotently(OrderCreateEvent event, String eventId,
                                     String topic, Integer partition, Long offset) {

        boolean claimed = idempotencyGuard.tryClaimEvent(eventId, topic, partition, offset);

        if (!claimed) {
            return; 
        }

        try {
            logger.info("Processing OrderCreateEvent. eventId='{}', custId='{}', pincode='{}', " +
                        "topic='{}', partition={}, offset={}",
                    eventId, event.getCustId(), event.getPincode(), topic, partition, offset);

            orderHandleService.handleOrder(event);

            logger.info("OrderCreateEvent processed successfully. eventId='{}'", eventId);

        } catch (Exception ex) {
            logger.error("handleOrder failed. eventId='{}', topic='{}', partition={}, offset={}. " +
                         "Releasing claim for retry.",
                    eventId, topic, partition, offset, ex);

            idempotencyGuard.releaseClaimEvent(eventId);

            throw ex;
        }
    }
}