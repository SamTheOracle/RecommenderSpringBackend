package com.app.recommender.foodrecommender;

import com.app.recommender.Model.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class FoodController {
    @Autowired
    private FoodRepository repository;

    @GetMapping(value = "/testfood")
    public int test(){
        return 12;
    }
    @PostMapping (value = "/")
    public ResponseEntity createFood(@RequestBody Food food){
        repository.insert(food);
        return ResponseEntity.status(HttpStatus.CREATED).body(food);
    }
    @GetMapping(value = "/{foodName}")
    public ResponseEntity getFood(@PathVariable String foodName){
        Food food = repository.findFoodByName(foodName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(food);

    }



}
