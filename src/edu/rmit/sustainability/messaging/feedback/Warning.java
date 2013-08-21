package edu.rmit.sustainability.messaging.feedback;

/**
 * Class Warning
 * This class is inherits from the abstract class Feedback. It is one of the 
 * classes which can be sent to the MessageBus. It just contains a message.
 * 
 * It differs from Notification in that it represent a critical Notification, 
 * to which to user should pay attention.
 *
 * @author Guillaume Prevost
 * @since 20/03/2011
 */
public class Warning extends Feedback {

	/**
	 * Constructor of Warning
	 * @param message
	 */
    public Warning(String message) {
        super(message);
    }

    /**
     * Test whether 2 warning are equal
     * @param Suggestion w: the Warning to test
     * @return Boolean: whether the 2 Warning are equal or not.
     */
    public Boolean equals(Warning w) {
    	return (this.getMessage().equals(w.getMessage()));
    }
}
