package com.app.recommender.physicalactivities.GoalServer;

import com.app.recommender.diet.IDietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class DietGoalReceiver {
    @Autowired
    IDietService dietService;
    @JmsListener(destination = "diet-updates-goal")
    public void updateDietCurrentPhysicalActivity(DietUpdateGoalMessage dietUpdatePaMessage){


        dietService.updateDietCurrentGoal(dietUpdatePaMessage);
    }
}
