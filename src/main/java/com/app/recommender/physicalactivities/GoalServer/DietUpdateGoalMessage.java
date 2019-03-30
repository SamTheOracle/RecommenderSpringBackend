package com.app.recommender.physicalactivities.GoalServer;

import com.app.recommender.Model.Goal;
import com.app.recommender.Model.PhysicalActivityRdf;

public class DietUpdateGoalMessage {
    private Goal goal;

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}
