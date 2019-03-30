package com.app.recommender.Model;

public class GoalNotFoundException extends Exception {
    private String message;

    public GoalNotFoundException(String s) {
        this.message = s;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
