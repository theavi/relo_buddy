package com.rlb.oc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document(collation = "order")
@Data
@AllArgsConstructor
public class Order {
    @Id
    private String id;// ObjectId-- String
    private Date orderDate;
    private String deliveryAddress;

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.orderDate = new Date();
    }
}
