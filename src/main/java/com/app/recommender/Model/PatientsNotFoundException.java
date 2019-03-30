package com.app.recommender.Model;

public class PatientsNotFoundException extends Exception {
    private String message;

    public PatientsNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
