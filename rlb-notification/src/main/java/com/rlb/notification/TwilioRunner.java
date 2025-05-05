package com.rlb.notification;

import com.rlb.notification.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class TwilioRunner implements CommandLineRunner {

    @Autowired
    private TwilioService twilioService;

    @Override
    @Order(50)
    public void run(String... args) throws Exception {
        twilioService.sendWhatsAppMessage("+917507571993","Hello Aniket");
    }
}
