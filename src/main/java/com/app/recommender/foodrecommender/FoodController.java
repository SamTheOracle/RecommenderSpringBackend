package com.app.recommender.foodrecommender;

import com.app.recommender.Model.*;
import com.app.recommender.diet.IDietService;
import com.app.recommender.Model.DietUpdates.DietUpdateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController("foodController")
public class FoodController {
    @Autowired
    IDietService dietService;
    @Autowired
    private IFoodService service;
    @Autowired
    private JmsTemplate template;

    @GetMapping(value = "/testfood")
    public int test() {
//        messageProducer.sendUpdateDiet("ciao", "ciao", "ciao", "ciao", "ciao");

        return 12;
    }

    @GetMapping(value = "/{foodName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFood(@PathVariable String foodName,
                                  @RequestParam String outputType,
                                  @RequestParam(value = "userId") String userId) {

        try {

            FoodRdf foodRdf = service.getFoodByName(foodName, outputType, userId);
            System.out.println(foodRdf);
            return ResponseEntity.status(HttpStatus.OK).body(foodRdf);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error with server");
        }

    }

    @GetMapping(value = "/{foodId}/recommendations/goodwiths", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRecomendations(@PathVariable(value = "foodId") String foodId, @RequestParam(value = "userId") String userId) {
        List<FoodRdf> recommendedFood;
        try {
            recommendedFood = service.recommendFood(foodId, userId);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(recommendedFood);
    }


    @PostMapping(value = "/creations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createNewFood(@RequestBody FoodRdf foodRDF, @RequestParam(value = "userId") String userId) {
        FoodRdf outputRdf = null;
        try {
            outputRdf = service.createNewRdfFood(foodRDF, userId);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (FoodRdfAlreadyCreatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(outputRdf);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllFood(@RequestParam(value = "userId") String userId) {
        FoodRdf[] foodRDFS;
        try {
            foodRDFS = service.getAllFood(userId);

        } catch (FileNotFoundException | FoodRdfNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(foodRDFS);
    }

    @PutMapping(value = "/properties/updates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modifyFoodProperty(@RequestBody FoodRdf foodRDF, @RequestParam(value = "foodId") String foodId,
                                             @RequestParam(value = "userId") String userId) {

        FoodRdf foodToSendBack = null;
        try {
            foodToSendBack = service.updateFood(foodRDF, foodId, userId);
        } catch (IOException | FoodRdfNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        FoodRdf foodRdfToUpdate = new FoodRdf();
        foodRdfToUpdate.setName(foodToSendBack.getName());
        foodRdfToUpdate.setSaltsPer100(foodToSendBack.getSaltsPer100());
        foodRdfToUpdate.setVitaminsPer100(foodToSendBack.getVitaminsPer100());
        foodRdfToUpdate.setProteinsPer100(foodToSendBack.getProteinsPer100());
        foodRdfToUpdate.setFatsPer100(foodToSendBack.getFatsPer100());
        foodRdfToUpdate.setCaloriesPer100(foodToSendBack.getCaloriesPer100());
        foodRdfToUpdate.setCarbsPer100(foodToSendBack.getCarbsPer100());
        foodRdfToUpdate.setBestEatenAt(foodToSendBack.getBestEatenAt());
        foodRdfToUpdate.setType(foodToSendBack.getType());
        foodRdfToUpdate.setName(foodToSendBack.getName());
        foodRdfToUpdate.setRdfOutput("");
        foodRdfToUpdate.setImageUrl("");
        foodRdfToUpdate.setId(foodToSendBack.getId());
        DietUpdateMessage m = new DietUpdateMessage();
        m.setUserId(userId);
        m.setFoodToUpdate(foodRdfToUpdate);
        template.convertAndSend("diet-updates", m);

        return ResponseEntity.status(200).body(foodToSendBack);

    }

    @GetMapping(value = "/{dietName}/suggestions/FruitsAndVegetables")
    public ResponseEntity getGeneralSuggestions(@PathVariable(value = "dietName") String dietName,
                                                @RequestParam(value = "userId") String userId) {
        Diet diet;
        List<FoodRdf> foodRdfList;
        try {
            diet = dietService.getDietByDietName(dietName, userId);
        } catch (DietNotFoundException | NoDietHistoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        try {
            foodRdfList = service.getGeneralFoodRecommendation(diet, userId);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(foodRdfList);
    }

    @GetMapping(value = "/{dietName}/suggestions/MeatAndFish")
    public ResponseEntity getGeneralSuggestionsMeatAndFish(@PathVariable(value = "dietName") String dietName,
                                                           @RequestParam(value = "userId") String userId,
                                                           @RequestParam(value = "rightAmountOfProteins") Double rightAmountOfProteins) {
        Diet diet;
        List<FoodRdf> foodRdfList;
        try {
            diet = dietService.getDietByDietName(dietName, userId);
        } catch (DietNotFoundException | NoDietHistoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        try {
            foodRdfList = service.getGeneralFoodRecommendationForMeat(diet, userId, rightAmountOfProteins);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        foodRdfList.forEach(System.out::println);
        return ResponseEntity.status(HttpStatus.OK).body(foodRdfList);
    }


}
