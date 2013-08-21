package edu.rmit.sustainability.messaging.feedback;

/**
 * Class Notification
 * This class is inherits from the abstract class Feedback. It is one of the 
 * classes which can be sent to the MessageBus. It just contains a message. 
 *
 * @author Guillaume Prevost
 * @since 20/03/2011
 */
public class Notification extends Feedback {

	/**
	 * Constructor of Notification
	 * @param String message 
	 */
    public Notification(String message) {
        super(message);
    }

    /**
     * Test whether 2 notification are equal
     * @param Suggestion s: the Notification to test
     * @return Boolean: whether the 2 Notification are equal or not.
     */
    public Boolean equals(Notification n) {
    	return (this.getMessage().equals(n.getMessage()));
    }
}
