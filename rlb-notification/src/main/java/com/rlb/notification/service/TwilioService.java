package com.rlb.notification.service;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String fromWhatsAppNumber;


    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public String sendWhatsAppMessage(String to, String messageBody) {
        Message message = Message.creator(
                        new PhoneNumber("whatsapp:" + to),
                        new PhoneNumber("whatsapp:" + fromWhatsAppNumber),
                        messageBody)
                .create();

        return message.getSid();

    }
}