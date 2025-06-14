package com.rlb.enums;

public enum OrderStatus {
    PLACED("Order Placed"),
    ASSIGNED("Order Assigned"),
    DISPATCHED("Order Dispatched"),
    DELIVERED("Order delivered");
    private String status;

    OrderStatus(String status) {
        status = status;
    }
}
