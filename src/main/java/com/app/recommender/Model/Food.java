package com.app.recommender.Model;

public class Food {

    private String id;

    private String type;

    private String name;

    private Number calories;

    private Number fats, fatsPer100;

    private Number vitamins, vitaminsPer100;

    private Number salts, saltsPer100;

    private Number carbs, carbsPer100;

    private Number quantity;

    private Number caloriesPer100;

    private Number proteins, proteinsPer100;


    public Food() {
    }

    public Number getFatsPer100() {
        return fatsPer100;
    }

    public void setFatsPer100(Number fatsPer100) {
        this.fatsPer100 = fatsPer100;
    }

    public Number getVitaminsPer100() {
        return vitaminsPer100;
    }

    public void setVitaminsPer100(Number vitaminsPer100) {
        this.vitaminsPer100 = vitaminsPer100;
    }

    public Number getSaltsPer100() {
        return saltsPer100;
    }

    public void setSaltsPer100(Number saltsPer100) {
        this.saltsPer100 = saltsPer100;
    }

    public Number getCarbsPer100() {
        return carbsPer100;
    }

    public void setCarbsPer100(Number carbsPer100) {
        this.carbsPer100 = carbsPer100;
    }

    public Number getProteinsPer100() {
        return proteinsPer100;
    }

    public void setProteinsPer100(Number proteinsPer100) {
        this.proteinsPer100 = proteinsPer100;
    }

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

    @Override
    public boolean equals(Object obj) {
        Food toCompare = (Food)obj;
        toCompare.id = ((Food) obj).id;
        return this.id.equalsIgnoreCase(toCompare.getId());
    }
}
