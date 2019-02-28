package com.app.recommender.foodrecommender;

import com.app.recommender.Model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IFoodRepository extends MongoRepository<Food, String> {

    Food findFoodByName(String name);
}
