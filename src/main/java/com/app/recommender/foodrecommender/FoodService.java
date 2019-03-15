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
    public List<FoodRdf> recommendFood(String selectedFood) throws FileNotFoundException {

        return rdfRepository.getRdfFoodForRecommendation(selectedFood);
    }

    @Override
    public FoodRdf getFoodByName(String foodName, String outputType) throws IOException {
        FoodRdf food = null;
        if (outputType.equalsIgnoreCase("RDF")) {
            food = this.rdfRepository.getFoodByName(foodName);
        } else {

        }
        return food;
    }

    @Override
    public FoodRdf createNewRdfFood(FoodRdf foodRDF) throws IOException, FoodRdfAlreadyCreatedException {
        FoodRdf food = rdfRepository.getFoodByName(foodRDF.getName());
        if (food != null) {
            throw new FoodRdfAlreadyCreatedException("Error: food " + foodRDF.getName() + " already created");
        }
        return rdfRepository.createRdfFood(foodRDF);
    }

    @Override
    public FoodRdf[] getAllFood() throws FileNotFoundException, FoodRdfNotFoundException {
        FoodRdf[] food = rdfRepository.getAllFoodFromOntology();
        if (food == null) {
            throw new FoodRdfNotFoundException("Error: food not found");
        }
        return food;
    }

    @Override
    public FoodRdf updateFood(FoodRdf foodRDF) throws IOException, FoodRdfNotFoundException {
        String foodName = foodRDF.getName();
        if (foodName == null) {
            throw new FoodRdfNotFoundException("Error, bad request: there is no name in food");
        }
        FoodRdf f = rdfRepository.getFoodByName(foodRDF.getName());
        if (f == null) {
            throw new FoodRdfNotFoundException("Error: " + foodName + " could not be found");
        }
        f = rdfRepository.update(foodRDF);
        return f;
    }

}
