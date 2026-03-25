
public class DlqEventContext {

    private final Object payload;
    private final String originalTopic;
    private final Integer originalPartition;
    private final Long originalOffset;
    private final Instant originalTimestamp;
    private final String exceptionClass;
    private final String exceptionMessage;
    private final String stackTrace;
    private final Instant dlqReceivedAt;

    private DlqEventContext(Builder builder) {
        this.payload          = builder.payload;
        this.originalTopic    = builder.originalTopic;
        this.originalPartition= builder.originalPartition;
        this.originalOffset   = builder.originalOffset;
        this.originalTimestamp= builder.originalTimestamp;
        this.exceptionClass   = builder.exceptionClass;
        this.exceptionMessage = builder.exceptionMessage;
        this.stackTrace       = builder.stackTrace;
        this.dlqReceivedAt    = builder.dlqReceivedAt;
    }

   
    public Object getPayload()            { return payload; }
    public String getOriginalTopic()      { return originalTopic; }
    public Integer getOriginalPartition() { return originalPartition; }
    public Long getOriginalOffset()       { return originalOffset; }
    public Instant getOriginalTimestamp() { return originalTimestamp; }
    public String getExceptionClass()     { return exceptionClass; }
    public String getExceptionMessage()   { return exceptionMessage; }
    public String getStackTrace()         { return stackTrace; }
    public Instant getDlqReceivedAt()     { return dlqReceivedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Object payload;
        private String originalTopic;
        private Integer originalPartition;
        private Long originalOffset;
        private Instant originalTimestamp;
        private String exceptionClass;
        private String exceptionMessage;
        private String stackTrace;
        private Instant dlqReceivedAt;

        public Builder payload(Object val)                { this.payload = val;           return this; }
        public Builder originalTopic(String val)          { this.originalTopic = val;     return this; }
        public Builder originalPartition(Integer val)     { this.originalPartition = val; return this; }
        public Builder originalOffset(Long val)           { this.originalOffset = val;    return this; }
        public Builder originalTimestamp(Instant val)     { this.originalTimestamp = val; return this; }
        public Builder exceptionClass(String val)         { this.exceptionClass = val;    return this; }
        public Builder exceptionMessage(String val)       { this.exceptionMessage = val;  return this; }
        public Builder stackTrace(String val)             { this.stackTrace = val;        return this; }
        public Builder dlqReceivedAt(Instant val)         { this.dlqReceivedAt = val;     return this; }

        public DlqEventContext build()                    { return new DlqEventContext(this); }
    }
}



// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class DlqEventContext {

//     private Object payload;
//     private String originalTopic;
//     private Integer originalPartition;
//     private Long originalOffset;
//     private Instant originalTimestamp;
//     private String exceptionClass;
//     private String exceptionMessage;
//     private String stackTrace;
//     private Instant dlqReceivedAt;
// }