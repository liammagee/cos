package edu.rmit.sustainability.model.ahp;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: E65691
 * Date: 17/05/11
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
@Namespaces({"frbr", "http://vocab.org/frbr/core#",
        "dc", "http://purl.org/dc/terms/",
        "cos", "http://circlesofsustainability.org/ontology#",
        "foaf", "http://xmlns.com/foaf/0.1/"})
@RdfsClass("cos:CriteriaMatrix")
@Entity
@EntityListeners(CoSEntityListener.class)
public class CriteriaMatrix extends AbstractMatrix {


    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @RdfProperty("cos:hasMatrixRow")
    protected List<MatrixRow> matrixRows = new ArrayList<MatrixRow>();

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @RdfProperty("cos:hasCriterion")
    private List<Criterion> criteria;

    public CriteriaMatrix() {}

    public CriteriaMatrix(List<Criterion> criteria, double[][] matrixArray) {
        this.criteria = criteria;
        setMatrixArray(matrixArray);
    }

    public List<Criterion> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<Criterion> criteria) {
        this.criteria = criteria;
    }

    public List<MatrixRow> getMatrixRows() {
        return matrixRows;
    }

    public void setMatrixRows(List<MatrixRow> matrixRows) {
        this.matrixRows = matrixRows;
    }
}
