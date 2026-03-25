@Document(collection = "dlq_events")
@Data
@NoArgsConstructor
@Builder
public class DlqEvent {

    @Id
    private String id;

    @Field("payload")
    private String payload;

    @Field("original_topic")
    private String originalTopic;

    @Field("original_partition")
    private Integer originalPartition;

    @Field("original_offset")
    private Long originalOffset;

    @Field("original_timestamp")
    private Instant originalTimestamp;

    @Field("exception_class")
    private String exceptionClass;

    @Field("exception_message")
    private String exceptionMessage;

    @Field("stack_trace")
    private String stackTrace;

    @Field("dlq_received_at")
    private Instant dlqReceivedAt;

    @Field("status")
    private DlqEventStatus status;

    @Field("replayed_at")
    private Instant replayedAt;

    @Field("discarded_at")
    private Instant discardedAt;

    @Field("remarks")
    private String remarks;

    public void markReplayed(String remarks) {
        this.status     = DlqEventStatus.REPLAYED;
        this.replayedAt = Instant.now();
        this.remarks    = remarks;
    }

    public void markDiscarded(String remarks) {
        this.status      = DlqEventStatus.DISCARDED;
        this.discardedAt = Instant.now();
        this.remarks     = remarks;
    }
}