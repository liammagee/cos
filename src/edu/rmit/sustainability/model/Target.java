package edu.rmit.sustainability.model;


import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Namespaces({"frbr", "http://vocab.org/frbr/core#",
        "dc", "http://purl.org/dc/terms/",
        "cos", "http://circlesofsustainability.org/ontology#",
        "foaf", "http://xmlns.com/foaf/0.1/"})
@RdfsClass("cos:Target")
@Entity
@EntityListeners(CoSEntityListener.class)
public class Target extends AbstractEmpireModel {

    /* The indicator associated with this target */
    @RdfProperty("cos:hasAssociatedIndicator")
    private Indicator associatedIndicator;

    /* The value of the target */
    @RdfProperty("cos:hasValue")
    private String value;

    /* Describes whether the objective is to increase the quantity of the underlying issue */
    @RdfProperty("cos:desiredDirection")
    private int desiredDirection;


    public Target() {
        super();
        value = "";
        desiredDirection = 0;
    }

    public Indicator getAssociatedIndicator() {
        return associatedIndicator;
    }

    public void setAssociatedIndicator(Indicator associatedIndicator) {
        this.associatedIndicator = associatedIndicator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getDesiredDirection() {
        return desiredDirection;
    }

    public void setDesiredDirection(int desiredDirection) {
        this.desiredDirection = desiredDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Target target = (Target) o;

        if (value != null ? !value.equals(target.value) : target.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Target{" +
                "value='" + value + '\'' +
                '}';
    }
}
