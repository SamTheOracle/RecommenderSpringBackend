package com.app.recommender.goals;

import com.app.recommender.Model.Goal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GoalRepository extends MongoRepository<Goal, String> {

    List<Goal> findByDietId(String dietId);

}
