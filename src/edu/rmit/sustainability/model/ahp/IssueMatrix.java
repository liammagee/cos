package edu.rmit.sustainability.model.ahp;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;
import edu.rmit.sustainability.model.CriticalIssue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
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
@RdfsClass("cos:IssueMatrix")
@Entity
@EntityListeners(CoSEntityListener.class)
public class IssueMatrix extends AbstractMatrix {


    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @RdfProperty("cos:hasCriterion")
    private Criterion criterion;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @RdfProperty("cos:hasCriticalIssue")
    private List<CriticalIssue> criticalIssues;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @RdfProperty("cos:hasMatrixRow")
    protected List<MatrixRow> matrixRows = new ArrayList<MatrixRow>();


    public IssueMatrix(){}

    public IssueMatrix(Criterion criterion, List<CriticalIssue> criticalIssues, double[][] matrixArray) {
        this.criterion = criterion;
        this.criticalIssues = criticalIssues;
        setMatrixArray(matrixArray);
    }

    public Criterion getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    public List<CriticalIssue> getCriticalIssues() {
        return criticalIssues;
    }

    public void setCriticalIssues(List<CriticalIssue> criticalIssues) {
        this.criticalIssues = criticalIssues;
    }

    public List<MatrixRow> getMatrixRows() {
        return matrixRows;
    }

    public void setMatrixRows(List<MatrixRow> matrixRows) {
        this.matrixRows = matrixRows;
    }
}
