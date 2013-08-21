package edu.rmit.sustainability.messaging.feedback;

import edu.rmit.sustainability.model.Indicator;


public class SimpleSuggestion extends Feedback {

    Indicator content = null;

    public SimpleSuggestion() {
        super();
    }

    public SimpleSuggestion(String message) {
        super(message);
    }

    public SimpleSuggestion(String message, Indicator content) {
        super(message);
        this.content = content;
    }
}
