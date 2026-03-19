package com.rlb.oc.outbox.model;

public enum OutboxEventStatus {
    NEW,
    PROCESSING,
    SENT,
    FAILED
}

