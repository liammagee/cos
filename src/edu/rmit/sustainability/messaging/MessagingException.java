package edu.rmit.sustainability.messaging;

public class MessagingException extends Exception {

    private String message;

    public MessagingException() {
    }

    public MessagingException(String message) {
        this.message = message;
    }

}
