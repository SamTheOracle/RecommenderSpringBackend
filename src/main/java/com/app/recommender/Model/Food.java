package com.app.recommender.Model;

public class Food {

    private String type;

    private String name;

    private Number calories;

    private Number fat;

    private Number carbs;

    private String healthy;

    private String[] mealTypes;

    private Number quantity;

    private Number caloriesPer100;

    private Number proteins;

    private String[] goodWith;

    public Food(String type, String name, Number calories, Number fat, Number carbs, String healthy, String[] mealTypes, Number quantity, Number caloriesPer100, Number proteins, String[] goodWith) {
        this.type = type;
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.healthy = healthy;
        this.mealTypes = mealTypes;
        this.quantity = quantity;
        this.caloriesPer100 = caloriesPer100;
        this.proteins = proteins;
        this.goodWith = goodWith;
    }


    public Food(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getCalories() {
        return calories;
    }

    public void setCalories(Number calories) {
        this.calories = calories;
    }

    public Number getFat() {
        return fat;
    }

    public void setFat(Number fat) {
        this.fat = fat;
    }

    public Number getCarbs() {
        return carbs;
    }

    public void setCarbs(Number carbs) {
        this.carbs = carbs;
    }

    public String getHealthy() {
        return healthy;
    }

    public void setHealthy(String healthy) {
        this.healthy = healthy;
    }

    public String[] getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(String[] mealTypes) {
        this.mealTypes = mealTypes;
    }

    public Number getQuantity() {
        return quantity;
    }

    public void setQuantity(Number quantity) {
        this.quantity = quantity;
    }

    public Number getCaloriesPer100() {
        return caloriesPer100;
    }

    public void setCaloriesPer100(Number caloriesPer100) {
        this.caloriesPer100 = caloriesPer100;
    }

    public Number getProteins() {
        return proteins;
    }

    public void setProteins(Number proteins) {
        this.proteins = proteins;
    }

    public String[] getGoodWith() {
        return goodWith;
    }

    public void setGoodWith(String[] goodWith) {
        this.goodWith = goodWith;
    }
}
