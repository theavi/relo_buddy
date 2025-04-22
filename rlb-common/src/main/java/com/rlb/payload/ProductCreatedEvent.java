package com.rlb.payload;

import lombok.Data;

@Data
public class ProductCreatedEvent {
    private Integer productId;
    private String name;
}
