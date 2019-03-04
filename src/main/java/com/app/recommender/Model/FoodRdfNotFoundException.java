package com.app.recommender.Model;

public class FoodRdfNotFoundException extends Exception {
    private String message;

    public FoodRdfNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
