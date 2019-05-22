package com.app.recommender.greetings;

import com.app.recommender.Model.FoodRdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
public class GreetingsController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/{userName}")
    public ResponseEntity getStuffWithDiscovery(@PathVariable(value = "userName") String userName) {
        List<ServiceInstance> instances = discoveryClient.getInstances("food-microservice");
        Optional<ServiceInstance> instanceOptional = instances.stream().findAny();
        if (instanceOptional.isPresent()) {
            ServiceInstance instance = instanceOptional.get();
            RestTemplate restTemplate = new RestTemplate();
            int port = instance.getPort();
            String host = instance.getHost();
            String uri = "http://" + host + ":" + port + "/Dieta Aprile Ufficiale/suggestions/FruitsAndVegetables?userId=21bd08ee-8ee7-f1bd-d379-9800771f6f81";
            ResponseEntity<FoodRdf[]> response = restTemplate.getForEntity(uri, FoodRdf[].class);
            return ResponseEntity.ok(Objects.requireNonNull(response.getBody()));
        }

        return ResponseEntity.ok("Hi " + userName + " good to have you here!");
    }

    @GetMapping(value = "/instances")
    public ResponseEntity showUserName(@PathVariable(value = "userName") String userName) {


        return ResponseEntity.ok("Hi " + userName + " good to have you here!");
    }

}
