package com.rlb.oc.mapper;

import com.rlb.oc.OrderStatus;
import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.event.dto.OrderCreateDto;
import com.rlb.oc.model.Order;

public class OrderMapper {

    public static Order toEntity(OrderCreateDto dto){
        Order order = new Order();
        //order.setId(dto.getId());
        order.setCustId(dto.getCustId());
        order.setOrderDate(dto.getOrderDate());
        order.setPickupAddress(dto.getPickupAddress());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setProductList(dto.getProductList());
        order.setStatus(dto.getStatus());
        return order;
    }
    public static OrderCreateDto toDto(Order order){
        OrderCreateDto dto = new OrderCreateDto();
        dto.setId(order.getId());
        dto.setCustId(order.getCustId());
        dto.setOrderDate(order.getOrderDate());
        dto.setPickupAddress(order.getPickupAddress());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setProductList(order.getProductList());
        dto.setStatus(order.getStatus());
        return dto;
    }
    public static OrderCreateEvent toEvent(Order order){
        OrderCreateEvent event = new OrderCreateEvent();
        event.setId(order.getId());
        event.setCustId(order.getCustId());
        event.setOrderDate(order.getOrderDate());
        event.setPickupAddress(order.getPickupAddress());
        event.setDeliveryAddress(order.getDeliveryAddress());
        event.setProductList(order.getProductList());
        event.setStatus(OrderStatus.PLACED);
        return event;
    }
}
