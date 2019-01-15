package com.app.recommender.Model;

public class ServerErrorException extends Exception {


    private String message;

    public ServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
