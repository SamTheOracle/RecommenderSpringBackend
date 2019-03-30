package com.app.recommender.Model;

public class NoGoalFoundException extends Exception {
    private String message;

    public NoGoalFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
