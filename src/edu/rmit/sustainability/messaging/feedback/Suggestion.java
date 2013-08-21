package edu.rmit.sustainability.messaging.feedback;

import edu.rmit.sustainability.model.AbstractEmpireModel;

/**
 * Class Suggestion
 * This class is inherits from the abstract class Feedback. It is one of the 
 * classes which can be sent to the MessageBus. This type of feedback has a 
 * message like the other Feedback classes, but also has a weight defining its 
 * importance, and eventually has a content which correspond to an object that 
 * is suggested. 
 *
 * @author Guillaume Prevost
 * @since 20/03/2011
 */
public class Suggestion extends Feedback implements Comparable<Suggestion> {

    /**
     * An eventual object that is suggested
     */
    Object content = null;

    /**
     * The importance of the Suggestion. The higher it is, the more important 
     * the Suggestion is.
     */
    int weight = 0;

    // Test fields
    AbstractEmpireModel sourceOfSuggestion = null;
    AbstractEmpireModel targetForSuggestion = null;
    String explanationOfSuggestion = null;

    /**
     * Default Constructor of Suggestion
     */
    public Suggestion() {
        super();
    }

    /**
     * Constructor of Suggestion
     *
     * @param message: the 'user-friendly' message of the suggestion
     */
    public Suggestion(String message) {
        super(message);
    }

    /**
     * Constructor of Suggestion
     *
     * @param message: the 'user-friendly' message of the suggestion
     * @param weight:  the weight of the suggestion
     */
    public Suggestion(String message, int weight) {
        super(message);
        this.weight = weight;
    }

    /**
     * Constructor of Suggestion
     *
     * @param message: the 'user-friendly' message of the suggestion
     * @param weight:  the weight of the suggestion
     * @param content: the object suggested
     */
    public Suggestion(String message, int weight, Object content) {
        super(message);
        this.weight = weight;
        this.content = content;
    }

    /**
     * Method setWeight
     * Set the weight of the suggestion to the given weight
     *
     * @param int weight: The new weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Method getWeight
     *
     * @return int: The weight of the suggestion
     */
    public int getWeight() {
        return (this.weight);
    }

    /**
     * Method getContent
     *
     * @return Indicator: The object contained in the suggestion
     */
    public Object getContent() {
        return (this.content);
    }

    /**
     * Method compareTo
     * Compare 2 suggestions in function of their weight.
     *
     * @param Suggestion s: the other suggestion to compare with this one
     * @return int: Returns a negative integer, zero, or a positive integer as this suggestion's weight is less than, equal to, or greater than the second's
     */
    public int compareTo(Suggestion s) {
        return (this.getWeight() - s.getWeight());
    }

    /**
     * Test whether 2 suggestions are equal
     * @param Suggestion s: the Suggestion to test
     * @return Boolean: whether the 2 Suggestion are equal or not.
     */
    public Boolean equals(Suggestion s) {
    	return (this.getMessage().equals(s.getMessage()));
    }
    
    /**
     * Method toString
     *
     * @return String
     */
    public String toString() {
        return (super.toString() + " Weight: " + weight);
    }
}
