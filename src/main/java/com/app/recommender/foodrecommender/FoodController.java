package com.app.recommender.foodrecommender;

import com.app.recommender.Model.FoodRdfNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController("foodController")
public class FoodController {
    @Autowired
    private IFoodService service;

    @GetMapping(value = "/testfood")
    public int test() {
        return 12;
    }

    @GetMapping(value = "/{foodName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFood(@PathVariable String foodName, @RequestParam String outputType) {

        try {

            FoodRdf foodRdf = service.getFoodByName(foodName, outputType);
            System.out.println(foodRdf);
            return ResponseEntity.status(HttpStatus.OK).body(foodRdf);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error with server");
        }

    }

    @GetMapping(value = "/recommendations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRecomendations(@RequestParam(value = "foodName") String foodName) {
        List<FoodRdf> recommendedFood;
        try {
            recommendedFood = service.recommendFood(foodName);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(recommendedFood);
    }


    @PostMapping(value = "/creations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createNewFood(@RequestBody FoodRdf foodRDF) {
        FoodRdf outputRdf = null;
        try {
            outputRdf = service.createNewRdfFood(foodRDF);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (FoodRdfAlreadyCreatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(outputRdf);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllFood() {
        FoodRdf[] foodRDFS;
        try {
            foodRDFS = service.getAllFood();

        } catch (FileNotFoundException | FoodRdfNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(foodRDFS);
    }

    @PutMapping(value = "/properties/updates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modifyFoodProperty(@RequestBody FoodRdf foodRDF) {

        FoodRdf foodToSendBack = null;
        try {
            foodToSendBack = service.updateFood(foodRDF);
        } catch (IOException | FoodRdfNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(200).body(foodToSendBack);

    }


}
