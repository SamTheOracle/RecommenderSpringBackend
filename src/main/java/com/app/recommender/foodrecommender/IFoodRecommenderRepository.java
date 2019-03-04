package com.app.recommender.foodrecommender;

import com.app.recommender.Model.Food;
import com.app.recommender.Model.FoodRdfNotFoundException;
import org.apache.jena.rdf.model.Resource;

import java.io.FileNotFoundException;
import java.util.List;

public interface IFoodRecommenderRepository {

    void createRdfFood(Food f) throws FileNotFoundException;

    void addStatementGoodWith(String subjectName, String foodNameGoodWith) throws FoodRdfNotFoundException;

    List<Resource> getRdfFoodForRecommendation(String name);
}
