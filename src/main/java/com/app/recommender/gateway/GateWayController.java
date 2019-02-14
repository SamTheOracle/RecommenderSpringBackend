package com.app.recommender.gateway;

import com.app.recommender.Model.Diet;
import com.app.recommender.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
public class GateWayController {

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping(value = "/test")
    public ResponseEntity testGateway() {
        return ResponseEntity.status(200).body("ciaone");
    }

    @GetMapping(value = "/physicalactivities/presentation",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPhysicalActivitiesPresentation(@RequestParam(value = "userId") String userId) throws ExecutionException, InterruptedException {

        Optional<ServiceInstance> dietMicroservice = discoveryClient.getInstances("diet-microservice").stream()
                .findAny();
        Optional<ServiceInstance> userMicroservice = discoveryClient.getInstances("registrations-microservice").stream()
                .findAny();
        CompletableFuture<Diet> dietCompletableFuture = new CompletableFuture<>();
        CompletableFuture<User> userCompletableFuture = new CompletableFuture<>();


        if (dietMicroservice.isPresent()) {

            ServiceInstance dietMicroserviceInstance = dietMicroservice.get();
            RestTemplate dietClient = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            String uri = "http://localhost:"+dietMicroserviceInstance.getPort()+"/current?userId=" + userId;

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            ResponseEntity<Diet> response = dietClient.exchange(uri, HttpMethod.GET, entity, Diet.class);

            dietCompletableFuture.complete(response.getBody());
        }
        if(userMicroservice.isPresent()){
            ServiceInstance  userMicroserviceInstance = userMicroservice.get();
            RestTemplate dietClient = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            String uri = "http://localhost:"+userMicroserviceInstance.getPort()+ "/registrations/"+userId;
            ResponseEntity<User> response = dietClient.exchange(uri, HttpMethod.GET, entity, User.class);


            userCompletableFuture.complete(response.getBody());
        }

        CompletableFuture<Map<String,Object>> result = dietCompletableFuture.thenCombine(userCompletableFuture,(diet,user)->{

            Map<String,Object> map = new HashMap<>();
            map.put("diet",diet);
            map.put("user",user);
            return map;
        });

        if(result.get().isEmpty()){
            return ResponseEntity.status(400).body("Error");

        }
        return ResponseEntity.status(200).body(result.get());

    }
    @GetMapping(value = "/services")
    public List<String> getServices(){

        return discoveryClient.getInstances("registrations-microservice").stream().map(serviceInstance -> serviceInstance.getUri()+"\n "+serviceInstance.getHost()).collect(Collectors.toList());
    }


}
