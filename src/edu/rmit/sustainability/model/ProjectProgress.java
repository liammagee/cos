/**
 *
 */
package edu.rmit.sustainability.model;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * @author Liam
 */
@Namespaces({"frbr", "http://vocab.org/frbr/core#",
        "dc", "http://purl.org/dc/terms/",
        "cos", "http://circlesofsustainability.org/ontology#",
        "foaf", "http://xmlns.com/foaf/0.1/"})
@RdfsClass("cos:Project")
@Entity
@EntityListeners(CoSEntityListener.class)
public class ProjectProgress extends AbstractEmpireModel implements JSONAware {


    @RdfProperty("cos:isProjectCreated")
    public int isProjectCreated = 1;

    @RdfProperty("cos:hasInitialAssessmentBeenConducted")
    public int hasInitialAssessmentBeenConducted = 0;

    @RdfProperty("cos:isGeneralIssueDefined")
    public int isGeneralIssueDefined = 0;

    @RdfProperty("cos:isNormativeGoalDefined")
    public int isNormativeGoalDefined = 0;

    @RdfProperty("cos:doAtLeastFourIssuesExist")
    public int doAtLeastFourIssuesExist = 0;

    @RdfProperty("cos:areIssuesRanked")
    public int areIssuesRanked = 0;

    @RdfProperty("cos:areAllDomainsCoveredByIssues")
    public int areAllDomainsCoveredByIssues = 0;

    @RdfProperty("cos:doAtLeastFourIndicatorsExist")
    public int doAtLeastFourIndicatorsExist = 0;

    @RdfProperty("cos:areAllIssuesMeasuredByAtLeastOneIndicator")
    public int areAllIssuesMeasuredByAtLeastOneIndicator = 0;

    @RdfProperty("cos:haveImpactsBetweenIssuesBeenAnalysed")
    public int haveImpactsBetweenIssuesBeenAnalysed = 0;

    @RdfProperty("cos:isReportCompiled")
    public int isReportCompiled = 0;

    @RdfProperty("cos:isResponseDevelopedAndMonitored")
    public int isResponseDevelopedAndMonitored = 0;


    @RdfProperty("cos:isModelReviewedAndAdapted")
    public int isModelReviewedAndAdapted = 0;


    /* The (optional) project related to this indicator */
    @RdfProperty("cos:hasProject")
    private Project project;


    public ProjectProgress() {
    }

    public int isHasInitialAssessmentBeenConducted() {
        return hasInitialAssessmentBeenConducted;
    }

    public void setHasInitialAssessmentBeenConducted(int hasInitialAssessmentBeenConducted) {
        this.hasInitialAssessmentBeenConducted = hasInitialAssessmentBeenConducted;
    }

    public int isProjectCreated() {
        return isProjectCreated;
    }

    public void setProjectCreated(int projectCreated) {
        isProjectCreated = projectCreated;
    }

    public int isGeneralIssueDefined() {
        return isGeneralIssueDefined;
    }

    public void setGeneralIssueDefined(int generalIssueDefined) {
        isGeneralIssueDefined = generalIssueDefined;
    }

    public int isNormativeGoalDefined() {
        return isNormativeGoalDefined;
    }

    public void setNormativeGoalDefined(int normativeGoalDefined) {
        isNormativeGoalDefined = normativeGoalDefined;
    }

    public int isDoAtLeastFourIssuesExist() {
        return doAtLeastFourIssuesExist;
    }

    public void setDoAtLeastFourIssuesExist(int doAtLeastFourIssuesExist) {
        this.doAtLeastFourIssuesExist = doAtLeastFourIssuesExist;
    }

    public int isAreIssuesRanked() {
        return areIssuesRanked;
    }

    public void setAreIssuesRanked(int areIssuesRanked) {
        this.areIssuesRanked = areIssuesRanked;
    }

    public int isAreAllDomainsCoveredByIssues() {
        return areAllDomainsCoveredByIssues;
    }

    public void setAreAllDomainsCoveredByIssues(int areAllDomainsCoveredByIssues) {
        this.areAllDomainsCoveredByIssues = areAllDomainsCoveredByIssues;
    }

    public int isDoAtLeastFourIndicatorsExist() {
        return doAtLeastFourIndicatorsExist;
    }

    public void setDoAtLeastFourIndicatorsExist(int doAtLeastFourIndicatorsExist) {
        this.doAtLeastFourIndicatorsExist = doAtLeastFourIndicatorsExist;
    }

    public int isAreAllIssuesMeasuredByAtLeastOneIndicator() {
        return areAllIssuesMeasuredByAtLeastOneIndicator;
    }

    public void setAreAllIssuesMeasuredByAtLeastOneIndicator(int areAllIssuesMeasuredByAtLeastOneIndicator) {
        this.areAllIssuesMeasuredByAtLeastOneIndicator = areAllIssuesMeasuredByAtLeastOneIndicator;
    }

    public int isHaveImpactsBetweenIssuesBeenAnalysed() {
        return haveImpactsBetweenIssuesBeenAnalysed;
    }

    public void setHaveImpactsBetweenIssuesBeenAnalysed(int haveImpactsBetweenIssuesBeenAnalysed) {
        this.haveImpactsBetweenIssuesBeenAnalysed = haveImpactsBetweenIssuesBeenAnalysed;
    }

    public int isReportCompiled() {
        return isReportCompiled;
    }

    public void setReportCompiled(int reportCompiled) {
        isReportCompiled = reportCompiled;
    }

    public int isResponseDevelopedAndMonitored() {
        return isResponseDevelopedAndMonitored;
    }

    public void setResponseDevelopedAndMonitored(int responseDevelopedAndMonitored) {
        this.isResponseDevelopedAndMonitored = responseDevelopedAndMonitored;
    }

    public int isModelReviewedAndAdapted() {
        return isModelReviewedAndAdapted;
    }

    public void setModelReviewedAndAdapted(int modelReviewedAndAdapted) {
        isModelReviewedAndAdapted = modelReviewedAndAdapted;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String toJSONString(){
        JSONObject obj = new JSONObject();
        obj.put("isProjectCreated", isProjectCreated);
        obj.put("hasInitialAssessmentBeenConducted", hasInitialAssessmentBeenConducted);
        obj.put("isGeneralIssueDefined", isGeneralIssueDefined);
        obj.put("isNormativeGoalDefined", isNormativeGoalDefined);
        obj.put("doAtLeastFourIssuesExist", doAtLeastFourIssuesExist);
        obj.put("areIssuesRanked", areIssuesRanked);
        obj.put("areAllDomainsCoveredByIssues", areAllDomainsCoveredByIssues);
        obj.put("doAtLeastFourIndicatorsExist", doAtLeastFourIndicatorsExist);
        obj.put("areAllIssuesMeasuredByAtLeastOneIndicator", areAllIssuesMeasuredByAtLeastOneIndicator);
        obj.put("haveImpactsBetweenIssuesBeenAnalysed", haveImpactsBetweenIssuesBeenAnalysed);
        obj.put("isReportCompiled", isReportCompiled);
        obj.put("isResponseDevelopedAndMonitored", isResponseDevelopedAndMonitored);
        obj.put("isModelReviewedAndAdapted", isModelReviewedAndAdapted);
        return obj.toString();
    }
}

