package com.app.recommender.Model;

public class UserAlreadyExistException extends Exception {


    private String errorMessage;


    public UserAlreadyExistException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public UserAlreadyExistException(Throwable cause, String errorMessage) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
