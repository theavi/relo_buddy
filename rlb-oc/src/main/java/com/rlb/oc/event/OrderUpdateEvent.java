package com.rlb.oc.event;

import com.rlb.oc.OrderStatus;
import com.rlb.oc.dto.ProductDto;
import com.rlb.oc.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateEvent {

    private String id;
    private Integer custId;
    private Date orderDate;
    private Location pickupAddress;
    private Location deliveryAddress;
    private List<ProductDto> productList = new ArrayList<>();
    private OrderStatus status;
}
