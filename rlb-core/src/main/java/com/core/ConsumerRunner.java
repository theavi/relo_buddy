package com.core;

import com.core.client.ProducerFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ConsumerRunner implements ApplicationRunner {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient ribbonClient;

    @Autowired
    private ProducerFeignClient feignClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        /*ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:9092/hello", String.class);
        System.out.println(String.format("RLB Common Response : {} and HTTP Status is : {}",response.getBody(),response.getStatusCode()));
        System.out.println("Response :  " + response.getBody());*/

        List<ServiceInstance> instances = discoveryClient.getInstances("RLB-COMMON");
        ResponseEntity<String> discoveryClientResponse = restTemplate.getForEntity(instances.get(0).getUri() + "/hello", String.class);
        System.out.println("Response from Discovery Client:  " + discoveryClientResponse.getBody());

        //Ribbon
        for (int count = 0; count < 10; count++) {
            ServiceInstance instance = ribbonClient.choose("RLB-COMMON");
            ResponseEntity<String> loadbalancerclientresponse = restTemplate.getForEntity(instance.getUri() + "/hello", String.class);
            System.out.println("Response from Load balancer Client:  " + loadbalancerclientresponse.getBody());
        }

        ResponseEntity<String> feignResponse = feignClient.hello();
        System.out.println(feignResponse.getBody());

    }
}
