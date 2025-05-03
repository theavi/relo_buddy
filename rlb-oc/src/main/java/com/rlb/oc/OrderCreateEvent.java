package com.rlb.oc;

import com.rlb.oc.dto.ProductDto;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderCreateEvent {
    private String id;
    private Integer custId;
    private Date orderDate;
    private List<ProductDto> productList = new ArrayList<>();
    private OrderStatus status;
}
