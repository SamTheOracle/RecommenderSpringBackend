package com.app.recommender.user.registrationmicroservice;

import com.app.recommender.user.registrationmicroservice.Model.User;
import com.app.recommender.user.registrationmicroservice.Persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/testing")
    public int testing(@RequestParam(value = "name", defaultValue = "World") String name) {


        return new Random().nextInt();
    }

    @PostMapping("/signup")
    public User signUp(@RequestBody User user) {

        repository.insert(user);
        return user;
    }


//    @RequestMapping("/service-instances/{applicationName}")
//    public List<ServiceInstance> serviceInstancesByApplicationName(
//            @PathVariable String applicationName) {
//        return this.discoveryClient.getInstances(applicationName);
//    }
}