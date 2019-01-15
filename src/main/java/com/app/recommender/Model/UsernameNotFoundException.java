package com.app.recommender.Model;

public class UsernameNotFoundException extends Exception {

    private String errorMessage;


    public UsernameNotFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public UsernameNotFoundException(Throwable cause, String errorMessage) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
