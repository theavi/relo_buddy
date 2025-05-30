package com.rlb.oc.event;

import com.rlb.oc.OrderStatus;
import com.rlb.oc.dto.ProductDto;
import com.rlb.oc.model.Location;
import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderCreateEvent {
    private String id;
    private Integer custId;
    private Date orderDate;
    private Location pickupAddress;
    private Location deliveryAddress;
    private List<ProductDto> productList = new ArrayList<>();
    private OrderStatus status;
}

