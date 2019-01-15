package com.app.recommender.diet.Persistence;

import com.app.recommender.Model.Diet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DietRepository extends MongoRepository<Diet,String> {

    Diet findDietByName(String name);

    List<Diet> findByUserId(String userId);


}
