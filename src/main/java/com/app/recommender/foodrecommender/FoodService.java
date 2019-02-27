package com.app.recommender.foodrecommender;

import com.app.recommender.Model.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("foodservice")
public class FoodService implements IFoodService {

    @Autowired
    IFoodRepository repository;

    @Override
    public void createFood(Food food) {
        repository.createRdfFood(food);

    }
}
