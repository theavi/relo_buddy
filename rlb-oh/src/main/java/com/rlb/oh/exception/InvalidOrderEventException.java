@Getter
public class InvalidOrderEventException extends RuntimeException {

    private final String eventId;

    public InvalidOrderEventException(String message) {
        super(message);
        this.eventId = null;
    }

    public InvalidOrderEventException(String message, String eventId) {
        super(message);
        this.eventId = eventId;
    }
}