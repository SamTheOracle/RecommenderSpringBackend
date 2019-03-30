package com.app.recommender.physicalactivities.GoalServer;

import com.app.recommender.Model.Goal;
import com.app.recommender.Model.GoalNotFoundException;
import com.app.recommender.Model.NoGoalFoundException;
import com.app.recommender.Model.PhysicalActivityRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Override
    public Goal createNewGoal(Goal goal) {
        List<Goal> goals = this.goalRepository.findByDietId(goal.getDietId());
        Goal g;
        if (goals.isEmpty()) {
            g = this.goalRepository.insert(goal);
            return g;
        } else {
            g = this.goalRepository.save(goal);
        }
        return g;

    }

    @Override
    public Goal updateGoal(Goal goal) throws NoGoalFoundException, GoalNotFoundException {
        List<Goal> goals = this.goalRepository.findByDietId(goal.getDietId());
        if (goals.isEmpty()) {
            throw new NoGoalFoundException("Error: no goal for diet: " + goal.getDietId());
        }
        Optional<Goal> optionalGoal = goals.stream().filter(g -> g.getDietId().equalsIgnoreCase(goal.getDietId())
                && g.getUserId().equalsIgnoreCase(goal.getUserId())).findFirst();
        if (optionalGoal.isPresent()) {
            return this.goalRepository.save(goal);
        }
        throw new GoalNotFoundException("Error: goal for diet " + goal.getDietId() + " not found. Userid: " + goal.getUserId());
    }

    @Override
    public Goal getGoal(String dietId, String userId) throws GoalNotFoundException, NoGoalFoundException {
        List<Goal> goals = this.goalRepository.findByDietId(dietId);
        if (goals.isEmpty()) {
            throw new NoGoalFoundException("Error: no goal for diet: " + dietId);
        }
        Optional<Goal> optionalGoal = goals.stream().filter(g -> g.getDietId().equalsIgnoreCase(dietId)
                && g.getUserId().equalsIgnoreCase(userId)).findFirst();
        if (optionalGoal.isPresent()) {
            return optionalGoal.get();
        }
        throw new GoalNotFoundException("Error: goal for diet " + dietId + " not found. Userid: " + userId);
    }

    @Override
    public Goal updateGoalAdherence(String goalId, List<PhysicalActivityRecord> records) throws GoalNotFoundException {
        Optional<Goal> goal = this.goalRepository.findById(goalId);
        if (!goal.isPresent()) {
            throw new GoalNotFoundException("Goal with id: " + goalId + " not found");
        } else {
            Goal goalToUpdate = goal.get();
            goalToUpdate.computeAdherence(records);
            Goal toSendBack = this.goalRepository.save(goalToUpdate);
            return toSendBack;
        }

    }
}
