package com.app.recommender.foodrecommender;

import com.app.recommender.Model.Diet;
import com.app.recommender.Model.FoodRdfNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IFoodService {
    List<FoodRdf> recommendFood(String selectedFood, String userId) throws FileNotFoundException;


    FoodRdf getFoodByName(String foodName, String outputType, String userId) throws IOException;

    FoodRdf createNewRdfFood(FoodRdf foodRDF, String userId) throws IOException, FoodRdfAlreadyCreatedException;

    FoodRdf[] getAllFood(String userId) throws FileNotFoundException, FoodRdfNotFoundException;

    FoodRdf updateFood(FoodRdf foodRDF, String foodId, String userId) throws IOException, FoodRdfNotFoundException;

    List<FoodRdf> getGeneralFoodRecommendation(Diet diet, String userId) throws FileNotFoundException;

    List<FoodRdf> getGeneralFoodRecommendationForMeat(Diet diet, String userId, double rightAmountOfProteins) throws FileNotFoundException;

    List<FoodRdf> getGeneralFoodRecommendationForCarbohydrates(Diet diet, String userId) throws FileNotFoundException;


//    List<FoodRdf> recommendFood(String foodName) throws FileNotFoundException;
//
//    FoodRdf getFoodByName(String foodName, String outputType) throws IOException;
//
//    FoodRdf createNewRdfFood(FoodRdf foodRDF) throws IOException, FoodRdfAlreadyCreatedException;
//
//    FoodRdf[] getAllFood() throws FileNotFoundException, FoodRdfNotFoundException;
//
//    FoodRdf updateFood(FoodRdf foodRDF, String previousName) throws IOException, FoodRdfNotFoundException;
}


