package com.app.recommender.foodrecommender;

import com.app.recommender.Model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FoodRepository extends MongoRepository<Food,String> {

    Food findFoodByName(String name);



    List<Food> findByType(String type);
}
