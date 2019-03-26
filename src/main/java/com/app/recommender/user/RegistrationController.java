package com.app.recommender.user;

import com.app.recommender.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity getUser(@PathVariable(value = "userId") String userId) {
        try {
            return ResponseEntity.status(200).body(userService.getUserById(userId));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
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

            userService.saveNewUser(user);
            return ResponseEntity.status(201).headers(responseHeaders).body(user);
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(400).body(e.getErrorMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @GetMapping(value = "nutritionists/{nutritionistId}/patients/all")
    public ResponseEntity getAllNutritionistsPatient(@PathVariable(value = "nutritionistId") String nutritionistId) {
        List<User> patients;
        try {
            patients = this.userService.getAllNutritionistPatients(nutritionistId);
        } catch (ServerErrorException | UserNotFoundException | PatientsNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(patients);

    }

    @GetMapping(value = "patients/all")
    public ResponseEntity getAllPatients() {
        List<User> patients;
        patients = this.userService.getAllPatients();
        if (patients == null || patients.size() == 0) {
            return ResponseEntity.status(400).body("No patient is present in the system");
        }

        return ResponseEntity.status(HttpStatus.OK).body(patients);
    }

    @GetMapping(value = "registrations/signin/{email}")
    public ResponseEntity logIn(@PathVariable("email") String email) {
        try {
            User user = userService.getUser(email);

            return ResponseEntity.status(200).body(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(422).body(e.getMessage());
        } catch (ServerErrorException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping(value = "registrations/update")
    public ResponseEntity addUserDetails(@RequestBody User user) {

        try {
            User updatedUser = userService.updateNewUser(user);
            return ResponseEntity.status(200).body(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(422).body(e.getMessage());
        } catch (ServerErrorException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

}