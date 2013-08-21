package edu.rmit.sustainability.messaging.feedback;


public abstract class Feedback {

    /**
     * 'User-friendly' feedback message
     */
    private String message;

    /**
     * Default constructor of feedback
     */
    public Feedback() {
    }

    /**
     * Constructor of feedback
     *
     * @param message: 'user-friendly' feedback message
     */
    public Feedback(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return (message);
    }
}
