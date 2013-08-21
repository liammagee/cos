package edu.rmit.sustainability.model;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liam Magee
 * @since 25/03/2011
 */
@Namespaces({"frbr", "http://vocab.org/frbr/core#",
        "dc", "http://purl.org/dc/terms/",
        "cos", "http://circlesofsustainability.org/ontology#",
        "foaf", "http://xmlns.com/foaf/0.1/"})
@RdfsClass("cos:CriticalIssue")
@Entity
@EntityListeners(CoSEntityListener.class)
public class CriticalIssue extends AbstractEmpireModel {

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

    /* The project related to this issue */
    @RdfProperty("cos:hasProject")
    private Project project;

    /* A list of indicators which may measure this issue */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @RdfProperty("cos:hasIndicators")
    private List<Indicator> indicators = new ArrayList<Indicator>();


    /* A list of component issues which may comprise this overall critical issue */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @RdfProperty("cos:hasComponents")
    private List<IssueComponent> componentIssues;


    /* An associated domain of the issue. */
    @RdfProperty("cos:hasDomain")
    private Domain domain;


    /* An associated subdomain of the issue. */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @RdfProperty("cos:hasSubdomain")
    private List<Subdomain> subdomains = new ArrayList<Subdomain>();


    public CriticalIssue() {
        super();
        associatedObjective = new Objective();
    }


    public CriticalIssue(String name, String description, int perceivedSignificance) {
        this();
        this.name = name;
        this.description = description;
        this.perceivedSignificance = perceivedSignificance;
    }


    public List<IssueComponent> getComponentIssues() {
        return componentIssues;
    }

    public void setComponentIssues(List<IssueComponent> componentIssues) {
        this.componentIssues = componentIssues;
    }

    public String toString() {
        String str = "***** CRITICAL ISSUE: " + getName() + " ******\n";
        str += super.toString();
        if (componentIssues != null)
            for (IssueComponent comp : componentIssues) {
                str += comp.toString();
            }
        return (str);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPerceivedSignificance() {
        return perceivedSignificance;
    }

    public void setPerceivedSignificance(int perceivedSignificance) {
        this.perceivedSignificance = perceivedSignificance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Objective getAssociatedObjective() {
        return associatedObjective;
    }

    public void setAssociatedObjective(Objective associatedObjective) {
        this.associatedObjective = associatedObjective;
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


    @Override
    public boolean equals(Object o) {
        // If we have a RDF ID property set, defer to the superclass implementation
        if (mIdSupport.getRdfId() != null)
            return super.equals(o);

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CriticalIssue that = (CriticalIssue) o;

        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        // If we have a RDF ID property set, defer to the superclass implementation
        if (mIdSupport.getRdfId() != null)
            return super.hashCode();

        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        return result;
    }

    public int compareTo(Object o) {
        // If we have a RDF ID property set, defer to the superclass implementation
        if (mIdSupport.getRdfId() != null)
            return super.compareTo(o);

        return (this.name.compareTo(((CriticalIssue) o).name));
    }
}
