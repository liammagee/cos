package edu.rmit.sustainability.messaging;

public class MessageBusFactory {

    // Singleton
    private static final DefaultMessageBus bus = new DefaultMessageBus();

    public static MessageBus createMessageBus() {
        return bus;
    }
}
