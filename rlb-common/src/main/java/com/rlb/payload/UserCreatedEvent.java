package com.rlb.payload;

import lombok.Data;

@Data
public class UserCreatedEvent {
    private Integer id;
    private String name;
}
