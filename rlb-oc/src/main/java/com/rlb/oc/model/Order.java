package com.rlb.oc.model;

import com.rlb.oc.OrderStatus;
import com.rlb.oc.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document(collection = "order")
@Data
@AllArgsConstructor
public class Order {
    @Id
    private String id;
    private Integer custId;
    private Date orderDate;
    private Location pickupAddress;
    private Location deliveryAddress;
    private List<ProductDto> productList = new ArrayList<>();
    private OrderStatus status;

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.orderDate = new Date();
    }
}
