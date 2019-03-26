package com.app.recommender.Model;

public class UserNotFoundException extends Exception {

    private String errorMessage;


    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public UserNotFoundException(Throwable cause, String errorMessage) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
