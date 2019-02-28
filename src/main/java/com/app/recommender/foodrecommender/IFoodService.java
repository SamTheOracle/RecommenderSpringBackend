package com.app.recommender.foodrecommender;

import com.app.recommender.Model.Food;

import java.io.FileNotFoundException;
import java.util.List;

public interface IFoodService {

    void createFood(Food food) throws FileNotFoundException;

    String exposeFood(Food food);

    List<Food> recommendFood(String foodName);

}
