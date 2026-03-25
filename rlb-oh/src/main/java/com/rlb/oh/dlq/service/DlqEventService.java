// No @Transactional — MongoDB single-document ops are atomic themselves
@Service
public class DlqEventService {

    private static final Logger logger = LoggerFactory.getLogger(DlqEventService.class);

    private final DlqEventRepository dlqEventRepository;
    private final DlqAlertService dlqAlertService;

    public DlqEventService(DlqEventRepository dlqEventRepository,
                            DlqAlertService dlqAlertService) {
        this.dlqEventRepository = dlqEventRepository;
        this.dlqAlertService    = dlqAlertService;
    }

    public void handle(DlqEventContext context) {
        DlqEvent saved = persistToDB(context);
        logger.info("DLQ event persisted. id='{}', originalTopic='{}', originalOffset='{}'",
                saved.getId(), context.getOriginalTopic(), context.getOriginalOffset());

        fireAlert(context, saved.getId());
    }

    private DlqEvent persistToDB(DlqEventContext context) {
        try {
            DlqEvent entity = DlqEvent.builder()
                    .payload(String.valueOf(context.getPayload()))
                    .originalTopic(context.getOriginalTopic())
                    .originalPartition(context.getOriginalPartition())
                    .originalOffset(context.getOriginalOffset())
                    .originalTimestamp(context.getOriginalTimestamp())
                    .exceptionClass(context.getExceptionClass())
                    .exceptionMessage(context.getExceptionMessage())
                    .stackTrace(context.getStackTrace())
                    .dlqReceivedAt(context.getDlqReceivedAt())
                    .status(DlqEventStatus.PENDING)
                    .build();

            return dlqEventRepository.save(entity);

        } catch (Exception ex) {
            logger.error("Failed to persist DLQ event. originalOffset='{}'",
                    context.getOriginalOffset(), ex);
            throw ex;
        }
    }

    private void fireAlert(DlqEventContext context, String dlqEventId) {
        try {
            dlqAlertService.send(context, dlqEventId);
        } catch (Exception ex) {
            // Must not crash DLQ consumer — message already persisted in MongoDB
            logger.error("Failed to send DLQ alert. dlqEventId='{}'. " +
                         "Investigate manually in dlq_events collection.", dlqEventId, ex);
        }
    }
}