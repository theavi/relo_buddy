@Document(collection = "processed_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessedEvent {

    @Id
    private String eventId;

    @Field("processed_at")
    private Instant processedAt;

    @Field("topic")
    private String topic;

    @Field("partition")
    private Integer partition;

    @Field("offset")
    private Long offset;
}