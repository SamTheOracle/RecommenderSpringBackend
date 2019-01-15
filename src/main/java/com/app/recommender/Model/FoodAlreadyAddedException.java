package com.app.recommender.Model;

public class FoodAlreadyAddedException extends Exception {

    private String message;

    public FoodAlreadyAddedException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
