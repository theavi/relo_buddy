package com.rlb.payload;

import com.rlb.dto.ProductDto;
import com.rlb.enums.OrderStatus;
import com.rlb.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    private String id;
    private Integer custId;
    private Date orderDate;
    private Location pickupAddress;
    private Location deliveryAddress;
    private List<ProductDto> productList = new ArrayList<>();
    private OrderStatus status;
}
