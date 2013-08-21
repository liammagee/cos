package edu.rmit.sustainability.messaging;

import edu.rmit.sustainability.messaging.feedback.Feedback;
import edu.rmit.sustainability.messaging.feedback.SimpleSuggestion;
import edu.rmit.sustainability.messaging.feedback.Suggestion;

import java.util.Calendar;

public class Message implements Comparable<Message> {

    private Object messageContents = null;
    private Calendar messagePosted = null;
    private int timesRetrieved = 0;

    public Message(Suggestion suggestion) {
        this.messagePosted = Calendar.getInstance();
        this.messageContents = suggestion;
    }

    public Message(SimpleSuggestion simpleSuggestion) {
        this.messagePosted = Calendar.getInstance();
        this.messageContents = simpleSuggestion;
    }
    
    public Message(Feedback feedback) {
        super();
        this.messagePosted = Calendar.getInstance();
        this.messageContents = feedback;
    }

    public Message(String string) {
        // TODO Auto-generated constructor stub
    }

    public int compareTo(Message anotherMessage) {
        if (anotherMessage == null || anotherMessage.getMessagePosted() == null)
            return 0;
        if (messagePosted == null)
            return 0;
        return messagePosted.compareTo(anotherMessage.getMessagePosted());
    }


    @Override
    public String toString() {
    	return (messageContents != null ? messageContents.toString() : null);
    }


    public Object getMessageContents() {
        return messageContents;
    }


    public Calendar getMessagePosted() {
        return messagePosted;
    }

    public int getTimesRetrieved() {
        return timesRetrieved;
    }

    public void setTimesRetrieved(int timesRetrieved) {
        this.timesRetrieved = timesRetrieved;
    }

    public void incrementNumberOfTimesRetrieved() {
        setTimesRetrieved(getTimesRetrieved() + 1);
    }
}
