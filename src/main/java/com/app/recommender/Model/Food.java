package com.app.recommender.Model;

public class Food {

    private String id;

    private String type;

    private String name;

    private Number calories;

    private Number fats;

    private Number vitamins;

    private Number salts;

    private Number carbs;


    private Number quantity;

    private Number caloriesPer100;

    private Number proteins;


    public Food(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Number getVitamins() {
        return vitamins;
    }

    public void setVitamins(Number vitamins) {
        this.vitamins = vitamins;
    }

    public Number getSalts() {
        return salts;
    }

    public void setSalts(Number salts) {
        this.salts = salts;
    }

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

    public Number getFats() {
        return fats;
    }

    public void setFats(Number fats) {
        this.fats = fats;
    }

    public Number getCarbs() {
        return carbs;
    }

    public void setCarbs(Number carbs) {
        this.carbs = carbs;
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


}
