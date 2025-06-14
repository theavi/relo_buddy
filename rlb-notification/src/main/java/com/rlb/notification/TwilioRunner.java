package com.rlb.notification;

import com.rlb.notification.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class TwilioRunner implements CommandLineRunner {

    @Autowired
    private TwilioService twilioService;

    //private String order_place_msg;
    @Value("${order.placed}")
    private String order_place_msg;


    @Override
    @Order(50)
    public void run(String... args) throws Exception {
        System.out.println("Send message->      ->    ->");
        String date = "May 5, 2025";
        String time = "2:00 PM";
        String msg = MessageFormat.format(order_place_msg, date, time);
        twilioService.sendWhatsAppMessage("+917743908937", order_place_msg);
       // twilioService.sendWhatsAppMessage("+919063237318", msg);
    }
}
