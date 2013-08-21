package edu.rmit.sustainability.assessment;

/**
 * Created by IntelliJ IDEA.
 * User: Liam
 * Date: 28/01/2010
 * Time: 5:43:01 PM
 * To change this template use File | Settings | File Templates.
 */


import javax.jdo.annotations.*;
import java.util.Date;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class CoSSelfAssessment {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    public Long id;

    @Persistent
    public String organisationName;

    @Persistent
    public Date entryDate;

    @Persistent
    public String[] backgroundConsiderations;

    @Persistent
    public String[] levelOneQuestion1Responses;
    @Persistent
    public String[] levelOneQuestion2Responses;
    @Persistent
    public String[] levelOneQuestion3Responses;
    @Persistent
    public String[] levelOneQuestion4Responses;

    @Persistent
    public String[] levelTwoQuestions;

    public CoSSelfAssessment(String organisationName) {
        this.organisationName = organisationName;
        this.entryDate = new Date();
        this.backgroundConsiderations = new String[4];
        this.levelOneQuestion1Responses = new String[4];
        this.levelOneQuestion2Responses = new String[4];
        this.levelOneQuestion3Responses = new String[4];
        this.levelOneQuestion4Responses = new String[4];
        this.levelTwoQuestions = new String[4]; //[7];
    }

    public Long getId() {
        return id;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String[] getBackgroundConsiderations() {
        return backgroundConsiderations;
    }

    public void setBackgroundConsiderations(String[] backgroundConsiderations) {
        this.backgroundConsiderations = backgroundConsiderations;
    }

    public String[] getLevelOneQuestion1Responses() {
        return levelOneQuestion1Responses;
    }

    public void setLevelOneQuestion1Responses(String[] levelOneQuestion1Responses) {
        this.levelOneQuestion1Responses = levelOneQuestion1Responses;
    }

    public String[] getLevelOneQuestion2Responses() {
        return levelOneQuestion2Responses;
    }

    public void setLevelOneQuestion2Responses(String[] levelOneQuestion2Responses) {
        this.levelOneQuestion2Responses = levelOneQuestion2Responses;
    }

    public String[] getLevelOneQuestion3Responses() {
        return levelOneQuestion3Responses;
    }

    public void setLevelOneQuestion3Responses(String[] levelOneQuestion3Responses) {
        this.levelOneQuestion3Responses = levelOneQuestion3Responses;
    }

    public String[] getLevelOneQuestion4Responses() {
        return levelOneQuestion4Responses;
    }

    public void setLevelOneQuestion4Responses(String[] levelOneQuestion4Responses) {
        this.levelOneQuestion4Responses = levelOneQuestion4Responses;
    }
}