package com.app.recommender.foodrecommender;

import com.app.recommender.Model.Food;
import com.app.recommender.Model.FoodRdfNotFoundException;
import org.apache.jena.rdf.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service("foodservice")
public class FoodService implements IFoodService {

    @Autowired
    IFoodRecommenderRepository rdfRepository;
    @Autowired
    private IFoodRepository mongoRepository;

    @Override
    public void createFood(Food food) throws FileNotFoundException {
        rdfRepository.createRdfFood(food);

    }

    @Override
    public void addStatementsGoodWith(String resourceSubjectName, List<String> resourcesObjectName) throws FoodRdfNotFoundException {
        resourcesObjectName.forEach(resourceName -> {
            try {
                rdfRepository.addStatementGoodWith(resourceSubjectName, resourceName);
            } catch (FoodRdfNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String exposeFood(Food food) {
        return null;
    }

    @Override
    public List<Food> recommendFood(String selectedFood) {
        List<Food> recommendedFood = new ArrayList<>();
        List<Resource> foodNames = rdfRepository.getRdfFoodForRecommendation(selectedFood);
        foodNames.forEach(r -> {
            System.out.println(r.getLocalName());
            Food f = mongoRepository.findFoodByName(r.getLocalName());
            recommendedFood.add(f);
        });
        return recommendedFood;
    }

}
