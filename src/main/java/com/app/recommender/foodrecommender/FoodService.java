package com.app.recommender.foodrecommender;

import com.app.recommender.Model.FoodRdfNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service("foodservice")
public class FoodService implements IFoodService {

    @Autowired
    IFoodRecommenderRepository rdfRepository;

    @Override
    public List<FoodRdf> recommendFood(String selectedFood, String userId) throws FileNotFoundException {

        return rdfRepository.getRdfFoodForRecommendation(selectedFood, userId);
    }

    @Override
    public FoodRdf getFoodByName(String foodName, String outputType, String userId) throws IOException {
        FoodRdf food = null;
        if (outputType.equalsIgnoreCase("RDF")) {
            food = this.rdfRepository.getFoodById(foodName, userId);
        } else {

        }
        return food;
    }

    @Override
    public FoodRdf createNewRdfFood(FoodRdf foodRDF, String userId) throws IOException, FoodRdfAlreadyCreatedException {
        FoodRdf food = rdfRepository.getFoodById(foodRDF.getId(), userId);
        if (food != null) {
            throw new FoodRdfAlreadyCreatedException("Error: food " + foodRDF.getName() + " already created");
        }
        return rdfRepository.createRdfFood(foodRDF, userId);
    }

    @Override
    public FoodRdf[] getAllFood(String userId) throws FileNotFoundException, FoodRdfNotFoundException {
        FoodRdf[] food = rdfRepository.getAllFoodFromOntology(userId);
        if (food == null) {
            throw new FoodRdfNotFoundException("Error: food not found");
        }
        return food;
    }

    @Override
    public FoodRdf updateFood(FoodRdf foodRDF, String foodId, String userId) throws IOException, FoodRdfNotFoundException {
        String foodName = foodRDF.getId();
        if (foodName == null) {
            throw new FoodRdfNotFoundException("Error, bad request: there is no name in food");
        }
        FoodRdf f = rdfRepository.getFoodById(foodId, userId);
        if (f == null) {
            throw new FoodRdfNotFoundException("Error: " + foodName + " could not be found");
        }
        f = rdfRepository.update(foodRDF, foodId, userId);
        return f;
    }

}
