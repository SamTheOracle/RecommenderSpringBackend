package com.app.recommender.user.registrationmicroservice;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class RegistrationController {

    @Autowired
    private DiscoveryClient discoveryClient;
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET,
            value = "/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {


        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

//    @RequestMapping("/service-instances/{applicationName}")
//    public int serviceInstancesByApplicationName(
//            @PathVariable String applicationName) {
//        return this.discoveryClient.getInstances(applicationName).stream().findFirst().get().getPort();
//    }
}