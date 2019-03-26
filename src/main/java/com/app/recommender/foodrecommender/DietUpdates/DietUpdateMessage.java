package com.app.recommender.foodrecommender.DietUpdates;

import com.app.recommender.Model.FoodRdf;

public class DietUpdateMessage {

    private FoodRdf foodToUpdate;
    private String userId;

    public DietUpdateMessage() {

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
