package com.app.recommender.user;

import com.app.recommender.Model.ServerErrorException;
import com.app.recommender.Model.User;
import com.app.recommender.Model.UserAlreadyExistException;
import com.app.recommender.Model.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@CrossOrigin
@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/testing")
    public int testing(@RequestParam(value = "nameRdf", defaultValue = "World") String name) {


        return new Random().nextInt();
    }

    @GetMapping(value = "registrations/{userId}")
    public ResponseEntity getUser(@PathVariable(value = "userId") String userId){
        try {
            return ResponseEntity.status(200).body(userService.getUserById(userId));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(400).body(e.getErrorMessage());
        } catch (ServerErrorException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    @PostMapping(value = "/registrations/signup")
    @ResponseBody
    public ResponseEntity signUp(@RequestBody User user) {
        System.out.println("USER POSTED: " + user);

        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.add();

        try {
            user.setCurrentGoal(null);
            user.setOlderPhysicalActivities(null);
            userService.saveNewUser(user);
            return ResponseEntity.status(201).headers(responseHeaders).body(user);
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(400).body(e.getErrorMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @GetMapping(value = "registrations/signin/{email}")
    public ResponseEntity logIn(@PathVariable("email") String email) {
        System.out.println("FETCHING FOR USER AT ID: " + email);
        try {
            User user = userService.getUser(email);
            System.out.println("Sending User: " + user);

            return ResponseEntity.status(200).body(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(422).body(e.getErrorMessage());
        } catch (ServerErrorException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping(value = "registrations/update")
    public ResponseEntity addUserDetails(@RequestBody User user) {

        try {
            User updatedUser = userService.updateNewUser(user);
            System.out.println(user.getBirthDate());
            System.out.println(updatedUser.getBirthDate());
            return ResponseEntity.status(200).body(updatedUser);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(422).body(e.getErrorMessage());
        } catch (ServerErrorException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

}