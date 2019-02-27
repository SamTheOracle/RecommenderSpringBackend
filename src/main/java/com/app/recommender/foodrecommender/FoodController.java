package com.app.recommender.foodrecommender;

import com.app.recommender.Model.Food;
import org.apache.commons.io.IOUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileReader;

@CrossOrigin
@RestController("foodController")
public class FoodController {
    @Autowired
    private IFoodService service;

    @GetMapping(value = "/testfood")
    public int test() {
        return 12;
    }

    @PostMapping(value = "/")
    public ResponseEntity createFood(@RequestBody Food food) {
//        Food f = new Food();
//        f.setHealthy("healthy");
        service.createFood(food);
        return ResponseEntity.status(HttpStatus.CREATED).body(food);
    }

    @GetMapping(value = "/{foodName}",produces = "text/plain")
    public ResponseEntity getFood(@PathVariable String foodName) {
        Model model = ModelFactory.createDefaultModel() ;
        model.read("food.rdf");
        try{

            File  file = new File("food.rdf");
            FileReader reader = new FileReader(file);

            model.read(reader,null);
            Resource r = model.getResource("http://temphost/ontologies/food#MilkandCoffe");
            model.write(System.out);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(IOUtils.toString(new FileReader("food.rdf")));


        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error with server");
        }

    }


}
