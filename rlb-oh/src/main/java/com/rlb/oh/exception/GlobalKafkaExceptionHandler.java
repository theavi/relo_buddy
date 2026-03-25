@Component
public class GlobalKafkaExceptionHandler implements KafkaListenerErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalKafkaExceptionHandler.class);

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException ex) {
        logger.error(
            "KafkaListenerErrorHandler caught exception. payload='{}', exception='{}'",
            message.getPayload(),
            ex.getMessage(),
            ex
        );
        // Rethrow so DefaultErrorHandler can retry → DLQ
        throw ex;
    }
}