package com.rlb.oc.event.dto;

import com.rlb.oc.OrderStatus;
import com.rlb.oc.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDto {
    private String id;
    private Integer custId;
    private Date orderDate;
    private Location pickupAddress;
    private Location deliveryAddress;
    private List<ProductDto> productList = new ArrayList<>();
    private OrderStatus status;
}
