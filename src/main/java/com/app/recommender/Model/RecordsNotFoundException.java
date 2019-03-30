package com.app.recommender.Model;

public class RecordsNotFoundException extends Exception {
    private String message;

    public RecordsNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
