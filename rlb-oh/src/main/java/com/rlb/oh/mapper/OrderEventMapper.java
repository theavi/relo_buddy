package com.rlb.oh.mapper;

import com.rlb.oc.OrderStatus;
import com.rlb.oc.dto.ProductDto;
import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.model.Location;
import com.rlb.oh.event.OrderAssignedToTeamEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderEventMapper {

    public static OrderAssignedToTeamEvent toOrderAssignedToTeamEvent(OrderCreateEvent event){

        OrderAssignedToTeamEvent orderAssignedToTeamEvent = new OrderAssignedToTeamEvent();

        orderAssignedToTeamEvent.setId(event.getId());
        orderAssignedToTeamEvent.setCustId(event.getCustId());
        orderAssignedToTeamEvent.setOrderDate(event.getOrderDate());
        orderAssignedToTeamEvent.setPickupAddress(event.getPickupAddress());
        orderAssignedToTeamEvent.setDeliveryAddress(event.getDeliveryAddress());
        orderAssignedToTeamEvent.setPincode(event.getPincode());
        orderAssignedToTeamEvent.setProductList(event.getProductList());
        orderAssignedToTeamEvent.setStatus(event.getStatus());

        return orderAssignedToTeamEvent;
    }
}
