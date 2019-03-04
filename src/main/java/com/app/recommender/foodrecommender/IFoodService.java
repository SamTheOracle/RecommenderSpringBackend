package com.app.recommender.foodrecommender;

import com.app.recommender.Model.Food;
import com.app.recommender.Model.FoodRdfNotFoundException;

import java.io.FileNotFoundException;
import java.util.List;

public interface IFoodService {

    void createFood(Food food) throws FileNotFoundException;

    void addStatementsGoodWith(String resourceSubjectName, List<String> resourcesObjectName) throws FoodRdfNotFoundException;

    String exposeFood(Food food);

    List<Food> recommendFood(String foodName);

}


