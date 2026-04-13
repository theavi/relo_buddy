package com.rlb.oh.dlq.entity;

public enum DlqEventStatus {
    PENDING,    
    REPLAYED,   
    DISCARDED  
}