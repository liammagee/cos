package edu.rmit.sustainability.model.assess;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;
import edu.rmit.sustainability.model.AbstractEmpireModel;
import edu.rmit.sustainability.model.Subdomain;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: E65691
 * Date: 24/05/11
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
@Namespaces({"frbr", "http://vocab.org/frbr/core#",
        "dc", "http://purl.org/dc/terms/",
        "cos", "http://circlesofsustainability.org/ontology#",
        "foaf", "http://xmlns.com/foaf/0.1/"})
@RdfsClass("cos:MatrixCell")
@Entity
@EntityListeners(CoSEntityListener.class)
public class AssessmentValue extends AbstractEmpireModel implements Comparable {

    @RdfProperty("cos:hasValue")
    protected double value;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @RdfProperty("cos:hasSubdomain")
    protected Subdomain subdomain;

    public AssessmentValue() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Subdomain getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(Subdomain subdomain) {
        this.subdomain = subdomain;
    }


    @Override
    public int compareTo(Object o) {
        AssessmentValue otherValue = (AssessmentValue)o;
        return this.getSubdomain().getParentDomain().getName().compareTo(otherValue.getSubdomain().getParentDomain().getName());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public int hashCode() {

        return super.hashCode();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
