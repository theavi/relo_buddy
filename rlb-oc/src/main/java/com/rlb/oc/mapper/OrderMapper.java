package com.rlb.oc.mapper;

import com.rlb.oc.OrderStatus;
import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.event.dto.ProductDto;
import com.rlb.oc.model.Location;
import com.rlb.oc.model.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderMapper {

    public static Order toEntity(OrderCreateEvent dto){
        Order order = new Order();
        order.setId(dto.getId);
        order.setCustId(dto.getCustId());
        order.setOrderDate(dto.getOrderDate());
        order.setPickUpAddress(dto.getPickUpAddress());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setProductList(dto.getProductList());
        order.setStatus(dto.getStatus());
        return order;
    }
    public static OrderCreateEvent toEntity(Order order){
        OrderCreateEvent orderCreateEvent = new OrderCreateEvent();
        orderCreateEvent.setId(order.getId);
        orderCreateEvent.setCustId(order.getCustId());
        orderCreateEvent.setOrderDate(order.getOrderDate());
        orderCreateEvent.setPickUpAddress(order.getPickUpAddress());
        orderCreateEvent.setDeliveryAddress(order.getDeliveryAddress());
        orderCreateEvent.setProductList(order.getProductList());
        orderCreateEvent.setStatus(order.getStatus());
        return orderCreateEvent;
    }
}
