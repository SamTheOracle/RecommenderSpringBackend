package com.app.recommender.physicalactivities;

import com.app.recommender.diet.IDietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class DietPhysicalActvityReceiver {
    @Autowired
    IDietService dietService;
    @JmsListener(destination = "diet-updates-pa")
    public void updateDietCurrentPhysicalActivity(DietUpdatePaMessage dietUpdatePaMessage){


            dietService.updateDietCurrentPhysicalActivity(dietUpdatePaMessage);
    }
}
