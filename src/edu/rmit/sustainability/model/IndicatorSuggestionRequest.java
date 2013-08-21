package edu.rmit.sustainability.model;

import java.util.List;


/**
 * Class IndicatorSuggestionsRequest
 * Defines a percept that can be sent to the agent system, requiring
 * it to find and send back one or more suggestions of possible candidates
 * indicators, based on a given Indicator or Issue contained in the percept.
 *
 * @author Guillaume Prevost
 */
public class IndicatorSuggestionRequest {

    /**
     * The indicator the suggestions requested should focus about
     */
    private Indicator indicator = null;

    /**
     * The issue the suggestions requested should focus about
     */
    private CriticalIssue issue = null;

    /**
     * Eventual list of research methods the user specify to find suggestions
     * The agent system will try to find suggestion using these methods.
     */
    private List<String> searchMethods = null;


    /*
      * Constructors
      */

    public IndicatorSuggestionRequest(Indicator indicator) {
        setIndicator(indicator);
    }

    public IndicatorSuggestionRequest(CriticalIssue issue) {
        setIssue(issue);
    }

    public IndicatorSuggestionRequest(Indicator indicator, List<String> searchMethods) {
        setIndicator(indicator);
        setSearchMethods(searchMethods);
    }

    public IndicatorSuggestionRequest(CriticalIssue issue, List<String> searchMethods) {
        setIssue(issue);
        setSearchMethods(searchMethods);
    }

    /*
      * Getters and Setters
      */

    private void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public Indicator getIndicator() {
        return (this.indicator);
    }

    private void setIssue(CriticalIssue issue) {
        this.issue = issue;
    }

    public CriticalIssue getIssue() {
        return (this.issue);
    }

    private void setSearchMethods(List<String> searchMethods) {
        this.searchMethods = searchMethods;
    }

    public List<String> getSearchMethods() {
        return (this.searchMethods);
    }
}
