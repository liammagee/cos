package edu.rmit.sustainability.model.ahp;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;
import edu.rmit.sustainability.model.AbstractEmpireModel;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

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
public class MatrixCell extends AbstractEmpireModel {
    @RdfProperty("cos:hasValue")
    protected double value;

    public MatrixCell() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
