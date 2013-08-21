package edu.rmit.sustainability.model;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class IssueComponent
 * This abstract class represents a CriticalIssue according to the model.
 *
 * @author Liam Magee
 * @since 25/03/2011
 */
@Namespaces({"frbr", "http://vocab.org/frbr/core#",
        "dc", "http://purl.org/dc/terms/",
        "cos", "http://circlesofsustainability.org/ontology#",
        "foaf", "http://xmlns.com/foaf/0.1/"})
@RdfsClass("cos:IssueComponent")
@Entity
@EntityListeners(CoSEntityListener.class)
public class IssueComponent extends AbstractEmpireModel {
    /* The name of the issue */
    @RdfProperty("cos:hasName")
    private String name;

    /* A description of the issue */
    @RdfProperty("cos:hasDescription")
    private String description;

    /* The perceived importance of the issue */
    @RdfProperty("cos:hasPerceivedSignificance")
    private int perceivedSignificance;


    /* The associated objective of the objective.  */
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @RdfProperty("cos:hasAssociatedObjective")
    private Objective associatedObjective;

    /* The author of the issue */
    @RdfProperty("cos:createdBy")
    private User creator;

    /* The (optional) project related to this indicator */
    @RdfProperty("cos:hasProject")
    private Project project;

    /* A list of indicators which may measure this issue */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @RdfProperty("cos:hasIndicators")
    private List<Indicator> indicators = new ArrayList<Indicator>();


    /* An associated domain of the issue. */
    @RdfProperty("cos:hasDomain")
    private Domain domain;


    /**
     * The parent {@link CriticalIssue} of this component
     */
    @RdfProperty("cos:hasParentIssue")
    private CriticalIssue parentIssue;


    /* An associated subdomain of the issue. */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @RdfProperty("cos:hasSubdomain")
    private List<Subdomain> subdomains = new ArrayList<Subdomain>();


    public IssueComponent() {}

    public CriticalIssue getParentIssue() {
        return parentIssue;
    }

    public void setParentIssue(CriticalIssue parentIssue) {
        this.parentIssue = parentIssue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPerceivedSignificance() {
        return perceivedSignificance;
    }

    public void setPerceivedSignificance(int perceivedSignificance) {
        this.perceivedSignificance = perceivedSignificance;
    }

    public Objective getAssociatedObjective() {
        return associatedObjective;
    }

    public void setAssociatedObjective(Objective associatedObjective) {
        this.associatedObjective = associatedObjective;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public List<Subdomain> getSubdomains() {
        return subdomains;
    }

    public void setSubdomains(List<Subdomain> subdomains) {
        this.subdomains = subdomains;
    }

    public void addSubdomain(Subdomain subdomain) {
        this.subdomains.add(subdomain);
    }

    public String toString() {
        String str = "***** ISSUE COMPONENT: " + getName() + " ******\n";
        str += super.toString();
        return (str);
    }
}
