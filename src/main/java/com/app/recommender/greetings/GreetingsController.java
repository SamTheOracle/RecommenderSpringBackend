package com.app.recommender.greetings;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class GreetingsController {

    @GetMapping(value="/{userName}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity showUserName(@PathVariable(value = "userName") String userName){
        return ResponseEntity.ok("Hi "+userName +" good to have you here!");
    }
}
