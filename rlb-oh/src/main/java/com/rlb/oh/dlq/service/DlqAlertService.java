// service/
@Service
public class DlqAlertService {

    private static final Logger logger = LoggerFactory.getLogger(DlqAlertService.class);

    @Value("${app.alerts.slack.webhook-url:#{null}}")
    private String slackWebhookUrl;

    @Value("${spring.application.name:order-service}")
    private String appName;

    private final RestTemplate restTemplate;

    public DlqAlertService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void send(DlqEventContext context, Long dlqEventId) {
        if (!StringUtils.hasText(slackWebhookUrl)) {
            logger.warn("Slack webhook not configured. Skipping alert for dlqEventId='{}'", dlqEventId);
            return;
        }

        String message = buildSlackMessage(context, dlqEventId);
        restTemplate.postForEntity(slackWebhookUrl,
                Map.of("text", message), String.class);

        logger.info("DLQ alert sent to Slack. dlqEventId='{}'", dlqEventId);
    }

    private String buildSlackMessage(DlqEventContext context, Long dlqEventId) {
        return String.format(
            """
            🚨 *DLQ Alert — %s*
            • *DLQ Event ID:*     %d
            • *Original Topic:*   %s
            • *Partition/Offset:* %s / %s
            • *Exception:*        %s
            • *Message:*          %s
            • *Received At:*      %s
            ➡️ Investigate DB table `dlq_events` where id = %d
            """,
            appName,
            dlqEventId,
            context.getOriginalTopic(),
            context.getOriginalPartition(),
            context.getOriginalOffset(),
            context.getExceptionClass(),
            context.getExceptionMessage(),
            context.getDlqReceivedAt(),
            dlqEventId
        );
    }
}