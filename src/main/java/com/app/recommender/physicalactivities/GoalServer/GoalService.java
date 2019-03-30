package com.app.recommender.physicalactivities.GoalServer;

import com.app.recommender.Model.Goal;
import com.app.recommender.Model.GoalNotFoundException;
import com.app.recommender.Model.NoGoalFoundException;
import com.app.recommender.Model.PhysicalActivityRecord;

import java.util.List;

public interface GoalService {

    Goal createNewGoal(Goal goal);

    Goal updateGoal(Goal goal) throws NoGoalFoundException, GoalNotFoundException;

    Goal getGoal(String dietId, String userId) throws GoalNotFoundException, NoGoalFoundException;

    Goal updateGoalAdherence(String goalId, List<PhysicalActivityRecord> records) throws GoalNotFoundException;
}
