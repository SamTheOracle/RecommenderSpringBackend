package com.app.recommender.foodrecommender;


import com.app.recommender.Model.FoodRdf;
import org.apache.jena.rdf.model.Property;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IFoodRecommenderRepository {
    FoodRdf createRdfFood(FoodRdf foodRDF, String userId) throws FileNotFoundException;

    List<FoodRdf> getRdfFoodForRecommendation(String foodId, String userId) throws FileNotFoundException;

    FoodRdf getFoodById(String foodId, String userId) throws IOException;

    FoodRdf[] getAllFoodFromOntology(String userId) throws FileNotFoundException;

    FoodRdf update(FoodRdf foodRDF, String foodId, String userId) throws FileNotFoundException;

    List<FoodRdf> getRdfFoodByStatement(Property property, String object, String userId) throws FileNotFoundException;
}
