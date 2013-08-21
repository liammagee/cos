/**
 *
 */
package edu.rmit.sustainability.model;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;
import edu.rmit.sustainability.model.ahp.AHP;
import edu.rmit.sustainability.model.ahp.Criterion;
import edu.rmit.sustainability.model.assess.Assessment;
import edu.rmit.sustainability.model.qssi.QSSI;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.*;

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
public class Project extends AbstractEmpireModel implements JSONAware {


    @RdfProperty("cos:hasName")
    public String projectName;

    @RdfProperty("cos:hasDescription")
    public String projectDescription;

    @RdfProperty("cos:hasGeneralDescription")
    public String generalIssue;

    @RdfProperty("cos:hasNormativeGoal")
    public String normativeGoal;

    @RdfProperty("cos:createdOn")
    public Date createdOn;


    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @RdfProperty("cos:createdBy")
    public User creator;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @RdfProperty("cos:projectProgress")
    public ProjectProgress projectProgress;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @RdfProperty("cos:hasAssessment")
    private List<Assessment> assessments = new ArrayList<Assessment>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @RdfProperty("cos:hasCollaborator")
    private List<User> collaborators = new ArrayList<User>();

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @RdfProperty("cos:hasCriticalIssue")
    private List<CriticalIssue> criticalIssues = new ArrayList<CriticalIssue>();


    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @RdfProperty("cos:hasAHP")
    public AHP ahp;


    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @RdfProperty("cos:hasQSSI")
    public QSSI qssi;


    public Project() {
        this.projectName = projectName;
        this.projectDescription = "";
        this.generalIssue = "";
        this.normativeGoal = "";
        this.projectProgress = new ProjectProgress();
    }


    public Project(String projectName, String projectDescription, String generalIssue, String normativeGoal) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.generalIssue = generalIssue;
        this.normativeGoal = normativeGoal;
        this.projectProgress = new ProjectProgress();
    }

    public Project(String projectName) {
        this.projectName = projectName;
        this.projectDescription = "";
        this.generalIssue = "";
        this.normativeGoal = "";
        this.projectProgress = new ProjectProgress();
    }



    public String getProjectName() {
        return projectName;
    }


    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public String getProjectDescription() {
        return projectDescription;
    }


    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }


    public String getGeneralIssue() {
        return generalIssue;
    }


    public void setGeneralIssue(String generalIssue) {
        this.generalIssue = generalIssue;
    }


    public String getNormativeGoal() {
        return normativeGoal;
    }


    public void setNormativeGoal(String normativeGoal) {
        this.normativeGoal = normativeGoal;
    }


    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }



    public void setCriticalIssues(List<CriticalIssue> ciList) {
        criticalIssues = ciList;
    }


    public List<CriticalIssue> getCriticalIssues() {
        return (criticalIssues);
    }



    /**
     * Generates a map of ranks and their associated issues, sorted from highest to lowest ranked.
     * @return a simple, score-based ranking of issues
     */
    public Map<Integer, List<CriticalIssue>> getSimpleCriticalIssueRanking() {
        Comparator<CriticalIssue> comparator = new Comparator<CriticalIssue>() {
            public int compare(CriticalIssue criticalIssue1, CriticalIssue criticalIssue2) {
                return criticalIssue2.getPerceivedSignificance() - criticalIssue1.getPerceivedSignificance();
            }
        };
        Collections.sort(criticalIssues, comparator);
        Map<Integer, List<CriticalIssue>> ranking = new TreeMap<Integer, List<CriticalIssue>>();
        if (criticalIssues.size() > 0) {
            int previousSignificance = criticalIssues.get(0).getPerceivedSignificance();
            int currentSignificance = 0;
            int rank = 1;
            int counter = 0;
            List<CriticalIssue> equivalentlyRankedIssues = new ArrayList<CriticalIssue>();

            // Rank issues: from those with highest perceived significance down
            for (CriticalIssue criticalIssue : criticalIssues) {
                currentSignificance = criticalIssue.getPerceivedSignificance();
                if (currentSignificance < previousSignificance) {
                    ranking.put(rank, equivalentlyRankedIssues);
                    equivalentlyRankedIssues = new ArrayList<CriticalIssue>();
                    rank += counter;
                    counter = 1;
                }
                else
                    counter++;
                equivalentlyRankedIssues.add(criticalIssue);
                previousSignificance = currentSignificance;
            }
            // Put last entry in now
            ranking.put(rank, equivalentlyRankedIssues);

        }
        return ranking;
    }

    /**
     * Generates a map of ranks and their associated issues, sorted from highest to lowest ranked.
     * @return a simple, score-based ranking of issues
     */
    public Map<Integer, List<CriticalIssue>> getCriteriaBasedCriticalIssueRanking() {
        // TODO: Implement with correct AHP ranking procedure
        Map<CriticalIssue, Double> aggregatePrincipalEigenvector = getAhp().getAggregatePrincipalEigenvector();
        Map<Double, List<CriticalIssue>> sortedWeights = new TreeMap<Double, List<CriticalIssue>>(new Comparator<Double>() {
            public int compare(Double o1, Double o2) {
                return (int)(o1.doubleValue() - o2.doubleValue());
            }
        });
        for (Iterator<Map.Entry<CriticalIssue, Double>> iterator = aggregatePrincipalEigenvector.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<CriticalIssue, Double> criticalIssueDoubleEntry = iterator.next();
            CriticalIssue issue = criticalIssueDoubleEntry.getKey();
            Double value = criticalIssueDoubleEntry.getValue();
            List<CriticalIssue> issues = new ArrayList<CriticalIssue>();
            if (sortedWeights.containsKey(value)) {
                issues = sortedWeights.get(value);
                issues.add(issue);
            }
            else {
                issues.add(issue);
                sortedWeights.put(value, issues);
            }
        }


        List<Double> keys = new ArrayList<Double>(sortedWeights.keySet());
        Map<Integer, List<CriticalIssue>> ranking = new TreeMap<Integer, List<CriticalIssue>>();
        if (keys.size() > 0) {

            double previousSignificance = keys.get(0);
            double currentSignificance = 0;
            int rank = 1;
            int counter = 0;
            List<CriticalIssue> equivalentlyRankedIssues = new ArrayList<CriticalIssue>();

            // Rank issues: from those with highest perceived significance down
            for (Iterator<Map.Entry<Double, List<CriticalIssue>>> iterator = sortedWeights.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<Double, List<CriticalIssue>> doubleCriticalIssueEntry = iterator.next();
                currentSignificance = doubleCriticalIssueEntry.getKey();
                List<CriticalIssue> criticalIssues = doubleCriticalIssueEntry.getValue();
                if (currentSignificance < previousSignificance) {
                    ranking.put(rank, equivalentlyRankedIssues);
                    equivalentlyRankedIssues = new ArrayList<CriticalIssue>();
                    rank += counter;
                    counter = 1;
                }
                else
                    counter++;
                equivalentlyRankedIssues.addAll(criticalIssues);
                previousSignificance = currentSignificance;
            }
            // Put last entry in now
            ranking.put(rank, equivalentlyRankedIssues);

        }
        return ranking;
    }

    /**
     * Adds an assessment to the current collection
     * @param assessment
     */
    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }


    /**
     * Adds a critical issue to the current collection
     * @param criticalIssue
     */
    public void addCriticalIssue(CriticalIssue criticalIssue) {
        criticalIssues.add(criticalIssue);
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<User> collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * Adds a collaborator to the current list of collaborators.
     * @param collaborator
     */
    public void addCollaborator(User collaborator) {
        if (this.collaborators == null)
            this.collaborators = new ArrayList<User>();
        this.collaborators.add(collaborator);
    }

    public AHP getAhp() {
        return ahp;
    }

    public void setAhp(AHP ahp) {
        this.ahp = ahp;
    }

    /**
     * Initialises a new AHP object, with some sample criteria and the current critical issues
     */
    public void initialiseAHP() {
        List<Criterion> criteria = new ArrayList<Criterion>();
        criteria.add(new Criterion("Degree of Control", "How much control do you have over this issue?", 1));
        criteria.add(new Criterion("Level of Concern", "How concerned are you about this issue?", 1));
        criteria.add(new Criterion("Able to Measure", "How much are you able to measure this issue, using available indicators and data?", 1));
        AHP ahp = new AHP(criteria, getCriticalIssues());
        setAhp(ahp);
    }

    public QSSI getQssi() {
        return qssi;
    }

    public void setQssi(QSSI qssi) {
        this.qssi = qssi;
    }

    public ProjectProgress getProjectProgress() {
        return projectProgress;
    }

    public void setProjectProgress(ProjectProgress projectProgress) {
        this.projectProgress = projectProgress;
    }

    /**
     * Initialises a new QSSI object
     */
    public void initialiseQSSI() {
        setQssi(new QSSI());
    }


    public String toString() {
        String str = "********* PROJECT " + getProjectName() + " **********\n";
        str += "Description: " + getProjectDescription() + "\n";
        str += "General Issue: " + getGeneralIssue() + "\n";
        str += "Normative Goal: " + getNormativeGoal() + "\n";
        str += "--------------------------------------------------\n";

        String ciNames = "";
        String ciFull = "";

        List<CriticalIssue> ciList = getCriticalIssues();
        if (ciList != null)
            for (CriticalIssue ci : ciList) {
                ciNames += "- " + ci.getName() + "\n";
                ciFull += ci.toString();
            }
        str += "Summary of Critical Issues:\n";
        str += ciNames;
        str += "--------------------------------------------------\n";
        str += ciFull;
        str += "--------------------------------------------------\n";

        return (str);
    }

    public String toJSONString(){
        JSONObject obj = new JSONObject();
        obj.put("projectName", projectName);
        obj.put("projectDescription", projectDescription);
        if (createdOn != null)
            obj.put("createdOn", createdOn.toString());
        if (creator != null)
            obj.put("creator", creator.getUsername());
        obj.put("projectProgress", projectProgress);
        return obj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (generalIssue != null ? !generalIssue.equals(project.generalIssue) : project.generalIssue != null)
            return false;
        if (normativeGoal != null ? !normativeGoal.equals(project.normativeGoal) : project.normativeGoal != null)
            return false;
        if (projectDescription != null ? !projectDescription.equals(project.projectDescription) : project.projectDescription != null)
            return false;
        if (projectName != null ? !projectName.equals(project.projectName) : project.projectName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        result = 31 * result + (projectDescription != null ? projectDescription.hashCode() : 0);
        result = 31 * result + (generalIssue != null ? generalIssue.hashCode() : 0);
        result = 31 * result + (normativeGoal != null ? normativeGoal.hashCode() : 0);
        return result;
    }

}

