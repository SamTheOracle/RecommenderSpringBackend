package com.app.recommender.Model;

public class FoodRdfAlreadyCreatedException extends Exception {
    private String message;

    public FoodRdfAlreadyCreatedException(String s) {
        super(s);
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
