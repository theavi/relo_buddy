@Component
public class GlobalKafkaExceptionHandler implements KafkaListenerErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalKafkaExceptionHandler.class);

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException ex) {

        Throwable cause = ex.getCause();

        // ✅ Log typed payload — DefaultErrorHandler cannot do this
        if (message.getPayload() instanceof OrderCreateEvent event) {
            logger.error(
                "Listener failed. eventId='{}', custId='{}', pincode='{}', " +
                "exceptionClass='{}', exceptionMessage='{}'",
                event.getId(),
                event.getCustId(),
                event.getPincode(),
                cause != null ? cause.getClass().getSimpleName() : "unknown",
                cause != null ? cause.getMessage() : ex.getMessage(),
                ex
            );
        } else {
            // Payload could not be deserialized — log raw
            logger.error(
                "Listener failed. rawPayload='{}', exceptionMessage='{}'",
                message.getPayload(),
                ex.getMessage(),
                ex
            );
        }

        // Rethrow — DefaultErrorHandler takes over for retry/DLQ
        throw ex;
    }
}