package com.app.recommender.foodrecommender;

import com.app.recommender.Model.FoodRdfNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IFoodRecommenderRepository {


    FoodRdf createRdfFood(FoodRdf foodRDF) throws FileNotFoundException;


    List<FoodRdf> getRdfFoodForRecommendation(String name) throws FileNotFoundException;

    FoodRdf getFoodByName(String foodName) throws IOException;


    FoodRdf[] getAllFoodFromOntology() throws FileNotFoundException;

    FoodRdf update(FoodRdf foodRDF) throws FoodRdfNotFoundException, FileNotFoundException;
}
