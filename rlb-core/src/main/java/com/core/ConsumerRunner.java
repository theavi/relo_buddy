package com.core;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConsumerRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {

        /*RestTemplate restTemplate=new RestTemplate();
      ResponseEntity<String> response= restTemplate.getForEntity("http://localhost:9092/hello",String.class);
        System.out.println(String.format("RLB Common Response : {} and HTTP Status is : {}",response.getBody(),response.getStatusCode()));
        System.out.println("Response :  "+response.getBody());*/
    }
}
