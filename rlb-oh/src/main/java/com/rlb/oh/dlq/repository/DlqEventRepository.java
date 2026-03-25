
public interface DlqEventRepository extends MongoRepository<DlqEvent, String> {

    List<DlqEvent> findByStatus(DlqEventStatus status);

    List<DlqEvent> findByOriginalTopicAndStatus(String originalTopic, DlqEventStatus status);

    long countByStatus(DlqEventStatus status);
}