package com.rlb.oh.idempotency.service;

import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oh.service.OrderHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateOrderEventProcessor {

   private static final Logger logger = LoggerFactory.getLogger(CreateOrderEventProcessor.class);

    private final OrderHandleService orderHandleService;
    private final IdempotencyGuard idempotencyGuard;

    public CreateOrderEventProcessor(OrderHandleService orderHandleService,
                                IdempotencyGuard idempotencyGuard) {
        this.orderHandleService = orderHandleService;
        this.idempotencyGuard = idempotencyGuard;
    }

    public void processIdempotently(OrderCreateEvent event, String eventId,
                                    String topic, Integer partition, Long offset) {

        boolean claimed = idempotencyGuard.tryClaimEvent(eventId, topic, partition, offset);

        if (!claimed) {
            return; 
        }

        try {
            logger.info("Processing OrderCreateEvent. eventId='{}', custId='{}', pincode='{}', " +
                        "topic='{}', partition={}, offset={}",
                    eventId, event.getCustId(), event.getPincode(), topic, partition, offset);

            orderHandleService.handleOrder(event);

            logger.info("OrderCreateEvent processed successfully. eventId='{}'", eventId);

        } catch (Exception ex) {
            logger.error("handleOrder failed. eventId='{}', topic='{}', partition={}, offset={}. " +
                         "Releasing claim for retry.",
                    eventId, topic, partition, offset, ex);

            idempotencyGuard.releaseClaimEvent(eventId);

            throw ex;
        }
    }
}