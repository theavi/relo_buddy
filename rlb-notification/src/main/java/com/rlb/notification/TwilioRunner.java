package com.rlb.notification;

import com.rlb.notification.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class TwilioRunner implements CommandLineRunner {

    //@Autowired
   // private TwilioService twilioService;

    private String order_place_msg;
    @Value("${ORDER_CREATED_MSG}")
    private  String order_placed_msg;

    @Override
    @Order(50)
    public void run(String... args) throws Exception {
        System.out.println(order_placed_msg);
       // twilioService.sendWhatsAppMessage("+917507571993",order_place_msg);
    }
}
