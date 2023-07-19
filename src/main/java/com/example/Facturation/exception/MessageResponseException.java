package com.example.Facturation.exception;

public class MessageResponseException {

    private String message;

    public MessageResponseException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
