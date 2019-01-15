package com.app.recommender.Model;


import java.util.List;


public class Meal {

    private String mealType;

    private List<Food> allFoodEntries;


    public Meal(){
    }

    public  String getMealType(){
        return this.mealType;
    }

    public  void setMealType(String mealType){
        this.mealType = mealType;
    }

    public  List<Food> getAllFoodEntries(){
        return this.allFoodEntries;
    }

    public  void setAllFoodEntries(List<Food> allFoodEntries){
        this.allFoodEntries = allFoodEntries;
    }
}
