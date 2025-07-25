package com.rlb.oh.service.impl;

import com.core.dto.TeamDto;
import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oh.client.CoreFeignClient;
import com.rlb.oh.kafka.producer.OrderAssignmentPublisher;
import com.rlb.oh.mapper.OrderEventMapper;
import com.rlb.oh.service.OrderHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderHandleServiceImpl implements OrderHandleService {

    private static final Logger logger = LoggerFactory.getLogger(OrderHandleServiceImpl.class);

    @Autowired
    public CoreFeignClient coreFeignClient;

    @Autowired
    public OrderAssignmentPublisher orderAssignmentPublisher;

    @Override
    public ResponseEntity<String> handleOrder(OrderCreateEvent event) {
        TeamDto team = coreFeignClient.getTeam(event.getPincode());
        team.setOrderId(event.getId());
        //event.setStatus(OrderStatus.ASSIGNED);
        orderAssignmentPublisher.publish(OrderEventMapper.toOrderAssignedToTeamEvent(event));
        return new ResponseEntity<>("Order assigned to a team", HttpStatus.OK);
    }
}
