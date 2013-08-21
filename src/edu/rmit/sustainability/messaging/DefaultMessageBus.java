package edu.rmit.sustainability.messaging;

import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultMessageBus implements MessageBus {

    private Map messageQueue;

    public DefaultMessageBus() {
        messageQueue = new ConcurrentHashMap<UserProjectKey, Queue>();
    }

    public void sendMessage(User user, Project project, Message message) {
        UserProjectKey key = UserProjectKey.makeKey(user, project);
        Queue messages = (Queue) messageQueue.get(key);
        if (messages == null)
            messages = new PriorityQueue();

        System.out.println("Adding message " + message.toString() + " for " + user.getUsername());
        messages.add(message);
        messageQueue.put(key, messages);
    }

    public Queue retrieveMessage(User user, Project project) {
        UserProjectKey key = UserProjectKey.makeKey(user, project);
        return (Queue) messageQueue.get(key);
    }

    public void clearMessages(User user, Project project) {
        UserProjectKey key = UserProjectKey.makeKey(user, project);
        messageQueue.remove(key);
    }

    public void clearMessage(User user, Project project, Message message) {
        UserProjectKey key = UserProjectKey.makeKey(user, project);
        Queue queue = (Queue)messageQueue.remove(key);
        if (queue.contains(message))
            queue.remove(message);
    }

    public void publishToMessageQueue(User user, Project project, Message message) {
        // TODO Auto-generated method stub
    }

    public void subscribeToMessageQueue(User user, Project project) {
        // TODO Auto-generated method stub
    }

}
