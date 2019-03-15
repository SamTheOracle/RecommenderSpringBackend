package com.app.recommender.foodrecommender;

import com.app.recommender.Model.FoodRdfNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IFoodService {


    List<FoodRdf> recommendFood(String foodName) throws FileNotFoundException;

    FoodRdf getFoodByName(String foodName, String outputType) throws IOException;

    FoodRdf createNewRdfFood(FoodRdf foodRDF) throws IOException, FoodRdfAlreadyCreatedException;

    FoodRdf[] getAllFood() throws FileNotFoundException, FoodRdfNotFoundException;

    FoodRdf updateFood(FoodRdf foodRDF) throws IOException, FoodRdfNotFoundException;
}


