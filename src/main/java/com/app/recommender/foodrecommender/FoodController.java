package com.app.recommender.foodrecommender;

import com.app.recommender.Model.FoodRdfNotFoundException;
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
    private IFoodService service;
    @Autowired
    private JmsTemplate template;

    @GetMapping(value = "/testfood")
    public int test() {
//        messageProducer.sendUpdateDiet("ciao", "ciao", "ciao", "ciao", "ciao");

        return 12;
    }

    @GetMapping(value = "/{foodName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFood(@PathVariable String foodName, @RequestParam String outputType, @RequestParam(value = "userId") String userId) {

        try {

            FoodRdf foodRdf = service.getFoodByName(foodName, outputType, userId);
            System.out.println(foodRdf);
            return ResponseEntity.status(HttpStatus.OK).body(foodRdf);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error with server");
        }

    }

    @GetMapping(value = "/{foodName}/recommendations/goodwiths", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRecomendations(@PathVariable(value = "foodName") String foodName, @RequestParam(value = "userId") String userId) {
        List<FoodRdf> recommendedFood;
        try {
            recommendedFood = service.recommendFood(foodName, userId);
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
    public ResponseEntity modifyFoodProperty(@RequestBody FoodRdf foodRDF, @RequestParam(value = "foodId") String foodId
            , @RequestParam(value = "userId") String userId) {

        FoodRdf foodToSendBack = null;
        try {
            foodToSendBack = service.updateFood(foodRDF, foodId, userId);
        } catch (IOException | FoodRdfNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        FoodRdf foodRdfToUpdate = new FoodRdf();
        foodRdfToUpdate.setName(foodToSendBack.getName());
        foodRdfToUpdate.setSalts(foodToSendBack.getSalts());
        foodRdfToUpdate.setVitamins(foodToSendBack.getVitamins());
        foodRdfToUpdate.setProteins(foodToSendBack.getProteins());
        foodRdfToUpdate.setFats(foodToSendBack.getFats());
        foodRdfToUpdate.setCaloriesPer100(foodToSendBack.getCaloriesPer100());
        foodRdfToUpdate.setCarbs(foodRdfToUpdate.getCarbs());
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


}
