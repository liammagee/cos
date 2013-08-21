package edu.rmit.sustainability.model.assess;


import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;
import edu.rmit.sustainability.model.AbstractEmpireModel;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;
import edu.rmit.sustainability.model.ahp.MatrixCell;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Liam Magee
 * @since 25/03/2011
 */
@Namespaces({"frbr", "http://vocab.org/frbr/core#",
        "dc", "http://purl.org/dc/terms/",
        "cos", "http://circlesofsustainability.org/ontology#",
        "foaf", "http://xmlns.com/foaf/0.1/"})
@RdfsClass("cos:Assessment")
@Entity
@EntityListeners(CoSEntityListener.class)
public class Assessment extends AbstractEmpireModel {

    /* The date of the assessment */
    @RdfProperty("cos:createdAt")
    private Date createdAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @RdfProperty("cos:createdBy")
    public User creator;

    /* The project related to this assessment */
    @RdfProperty("cos:hasProject")
    private Project project;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @RdfProperty("cos:hasAssessmentValues")
    private List<AssessmentValue> values = new ArrayList<AssessmentValue>();


    public Assessment() {
        super();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public List<AssessmentValue> getValues() {
        return values;
    }

    public void setValues(List<AssessmentValue> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Assessment that = (Assessment) o;

        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        // If we have a RDF ID property set, defer to the superclass implementation
        if (mIdSupport.getRdfId() != null)
            return super.hashCode();

        int result = super.hashCode();
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "createdAt=" + createdAt +
                '}';
    }
}


