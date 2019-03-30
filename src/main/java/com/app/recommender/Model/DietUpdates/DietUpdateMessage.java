package com.app.recommender.Model.DietUpdates;

import com.app.recommender.Model.FoodRdf;
import com.app.recommender.Model.PhysicalActivityRdf;

public class DietUpdateMessage {

    private FoodRdf foodToUpdate;
    private String userId;
    private PhysicalActivityRdf physicalActivityRdf;

    public DietUpdateMessage() {

    }

    public PhysicalActivityRdf getPhysicalActivityRdf() {
        return physicalActivityRdf;
    }

    public void setPhysicalActivityRdf(PhysicalActivityRdf physicalActivityRdf) {
        this.physicalActivityRdf = physicalActivityRdf;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FoodRdf getFoodToUpdate() {
        return foodToUpdate;
    }

    public void setFoodToUpdate(FoodRdf foodToUpdate) {
        this.foodToUpdate = foodToUpdate;
    }
}
