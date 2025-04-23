package com.rlb.oc;

public enum OrderStatus {
    PLACED("Order Placed"),
    DISPATCHED("Order Dispatched"),
    DELIVERED("Order delivered");
    private String status;

    OrderStatus(String status) {
        status = status;
    }
}
