package edu.rmit.sustainability.messaging;

import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

import java.util.Queue;

public interface MessageBus {

    public void sendMessage(User user, Project project, Message message) throws MessagingException;

    public Queue retrieveMessage(User user, Project project) throws MessagingException;

    public void clearMessages(User user, Project project) throws MessagingException;

    public void clearMessage(User user, Project project, Message message) throws MessagingException;

    public void publishToMessageQueue(User user, Project project, Message message) throws MessagingException;

    public void subscribeToMessageQueue(User user, Project project) throws MessagingException;
}
