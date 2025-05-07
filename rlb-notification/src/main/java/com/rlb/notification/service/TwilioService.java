package com.rlb.notification.service;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//@Service
public class TwilioService {

    @Value("{twilio.accountSid}")
    private String accountSid;
    @Value("{twilio.authToken}")
    private String authToken;
    @Value("{twilio.phoneNumber")
    private String whatsAppNumber;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public String sendWhatsAppMessage(String to, String payload) {
        Message message = Message.creator(new com.twilio.type.PhoneNumber("+919063237318"),
                        new com.twilio.type.PhoneNumber("+18563863439"),
                        "Where's Aniket?")
                .create();

        System.out.println(message.getBody());
        return message.getSid();

    }
}
