package com.app.recommender.Model;

public enum MealType {
    Breakfast("Breakfast"),
    MorningBreak("Morning Break"),
    Lunch("Lunch"),
    AfternoonBreak("Afternoon Break"),
    Dinner("Dinner");

    private String valueToDisplay;

    MealType(String name) {
        this.valueToDisplay = name;
    }

    public String getValueToDisplay() {
        return valueToDisplay;
    }
}
