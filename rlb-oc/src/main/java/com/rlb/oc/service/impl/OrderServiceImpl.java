package com.rlb.oc.service.impl;

import com.rlb.common.exception.RecordNotFound;
import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.event.OrderUpdateEvent;
import com.rlb.oc.dto.OrderCreateDto;
import com.rlb.oc.kafka.producer.OrderPublisher;
import com.rlb.oc.kafka.producer.OrderUpdatePublisher;
import com.rlb.oc.outbox.model.OutboxEvent;
import com.rlb.oc.outbox.service.OutboxService;
import com.rlb.oc.service.OrderService;
import com.rlb.oc.mapper.OrderMapper;
import com.rlb.oc.model.Order;
import com.rlb.oc.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final OrderPublisher orderPublisher;
    private final OrderUpdatePublisher orderUpdatePublisher;
    private final OutboxService outboxService;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderPublisher orderPublisher,
            OrderUpdatePublisher orderUpdatePublisher,
            OutboxService outboxService
    ) {
        this.orderRepository = Objects.requireNonNull(orderRepository, "orderRepository must not be null");
        this.orderPublisher = Objects.requireNonNull(orderPublisher, "orderPublisher must not be null");
        this.orderUpdatePublisher = Objects.requireNonNull(orderUpdatePublisher, "orderUpdatePublisher must not be null");
        this.outboxService = Objects.requireNonNull(outboxService, "outboxService must not be null");
    }

    @Override
    public String placeOrder(OrderCreateDto dto) {
        requireNonNull(dto, "OrderCreateDto must not be null");
        try {
            Order savedOrder = saveNewOrder(dto);
            OrderCreateEvent event = toOrderCreateEvent(savedOrder);

            OutboxEvent outboxEvent = enqueueCreatedOutbox(savedOrder, event);
            publishCreatedBestEffort(savedOrder, outboxEvent, event);

            logger.info(
                    "Order created successfully. orderId='{}', custId='{}'",
                    savedOrder.getId(),
                    savedOrder.getCustId()
            );
            return savedOrder.getId();
        } catch (RuntimeException ex) {
            logger.error("placeOrder failed. custId='{}'", dto.getCustId(), ex);
            throw ex;
        }
    }

    @Override
    public String getOrderStatus(String id) {
        final String orderId = requireNonBlank(id, "Order id must not be blank");
        if (orderId == null) {
            throw new IllegalArgumentException("Order id must not be blank");
        }
        try {
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isEmpty()) {
                logger.warn("Order not found. orderId='{}'", orderId);
                throw new RecordNotFound("Order not found");
            }

            if (order.get().getStatus() == null) {
                logger.error("Order found but status is null. orderId='{}'", orderId);
                throw new IllegalStateException("Order status unavailable");
            }

            return order.get().getStatus().toString();
        } catch (RuntimeException ex) {
            logger.error("getOrderStatus failed. orderId='{}'", orderId, ex);
            throw ex;
        }
    }

    @Override
    public String updateOrder(OrderCreateDto orderDto) {
        requireNonNull(orderDto, "OrderCreateDto must not be null");
        final String orderId = requireNonBlank(orderDto.getId(), "Order id must not be blank");
        if (orderId == null) {
            throw new IllegalArgumentException("Order id must not be blank");
        }
        if (orderDto.getDeliveryAddress() == null) {
            throw new IllegalArgumentException("Delivery address must not be null");
        }
        try {
            Order order = loadExistingOrder(orderId);
            order.setDeliveryAddress(orderDto.getDeliveryAddress());

            Order updatedOrder = orderRepository.save(order);

            OrderUpdateEvent event = toOrderUpdateEvent(updatedOrder);
            OutboxEvent outboxEvent = enqueueUpdatedOutbox(orderId, event);
            publishUpdatedBestEffort(orderId, outboxEvent, event);

            logger.info("Order updated successfully. orderId='{}'", orderId);
            return "Order updated successfully";
        } catch (RuntimeException ex) {
            logger.error("updateOrder failed. orderId='{}'", orderId, ex);
            throw ex;
        }
    }

    @Override
    public String deleteOrder(String id) {
        return "";
    }

    private Order saveNewOrder(OrderCreateDto dto) {
        Order entity = requireNonNull(OrderMapper.toEntity(dto), "OrderMapper.toEntity returned null");
        Order savedOrder;
        try {
            savedOrder = orderRepository.save(entity);
        } catch (RuntimeException ex) {
            logger.error("OrderRepository.save failed while placing order. custId='{}'", dto.getCustId(), ex);
            throw ex;
        }
        if (!StringUtils.hasText(savedOrder.getId())) {
            throw new IllegalStateException("Order was saved but no id was generated");
        }
        return savedOrder;
    }

    private Order loadExistingOrder(String orderId) {
        final String nonNullOrderId = Objects.requireNonNull(orderId, "orderId must not be null");
        return orderRepository.findById(nonNullOrderId)
                .orElseThrow(() -> new RecordNotFound("Record not found"));
    }

    private OrderCreateEvent toOrderCreateEvent(Order savedOrder) {
        OrderCreateEvent event = OrderMapper.toOrderCreateEvent(savedOrder);
        if (event == null || !StringUtils.hasText(event.getId())) {
            throw new IllegalStateException("OrderCreateEvent could not be created (missing id)");
        }
        return event;
    }

    private OrderUpdateEvent toOrderUpdateEvent(Order updatedOrder) {
        OrderUpdateEvent event = OrderMapper.toOrderUpdateEvent(updatedOrder);
        if (event == null || !StringUtils.hasText(event.getId())) {
            throw new IllegalStateException("OrderUpdateEvent could not be created (missing id)");
        }
        return event;
    }

    private OutboxEvent enqueueCreatedOutbox(Order savedOrder, OrderCreateEvent event) {
        try {
            return outboxService.enqueueOrderCreated(event);
        } catch (Exception ex) {
            logger.error(
                    "Failed to enqueue outbox event after saving order. orderId='{}', custId='{}'",
                    savedOrder.getId(),
                    savedOrder.getCustId(),
                    ex
            );
            throw ex;
        }
    }

    private OutboxEvent enqueueUpdatedOutbox(String orderId, OrderUpdateEvent event) {
        try {
            return outboxService.enqueueOrderUpdated(event);
        } catch (Exception ex) {
            logger.error("Failed to enqueue outbox event after updating order. orderId='{}'", orderId, ex);
            throw ex;
        }
    }

    private void publishCreatedBestEffort(Order savedOrder, OutboxEvent outboxEvent, OrderCreateEvent event) {
        try {
            orderPublisher.publishOrderPlaceEvent(event);
        } catch (Exception ex) {
            logger.error(
                    "Immediate publish failed; will retry via outbox. outboxId='{}', orderId='{}', custId='{}'",
                    outboxEvent.getId(),
                    savedOrder.getId(),
                    savedOrder.getCustId(),
                    ex
            );
        }
    }

    private void publishUpdatedBestEffort(String orderId, OutboxEvent outboxEvent, OrderUpdateEvent event) {
        try {
            orderUpdatePublisher.publishOrderUpdateEvent(event);
        } catch (Exception ex) {
            logger.error(
                    "Immediate publish failed; will retry via outbox. outboxId='{}', orderId='{}'",
                    outboxEvent.getId(),
                    orderId,
                    ex
            );
        }
    }

    private static <T> T requireNonNull(T value, String message) {
        if (value == null) throw new IllegalArgumentException(message);
        return value;
    }

    private static String requireNonBlank(String value, String message) {
        if (!StringUtils.hasText(value)) return null;
        return value.trim();
    }

}
