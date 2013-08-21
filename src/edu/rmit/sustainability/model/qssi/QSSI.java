package edu.rmit.sustainability.model.qssi;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.model.AbstractEmpireModel;
import edu.rmit.sustainability.model.CriticalIssue;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: E65691
 * Date: 17/05/11
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Namespaces({"frbr", "http://vocab.org/frbr/core#",
        "dc", "http://purl.org/dc/terms/",
        "cos", "http://circlesofsustainability.org/ontology#",
        "foaf", "http://xmlns.com/foaf/0.1/"})
@RdfsClass("cos:QSSI")
@Entity
public class QSSI extends AbstractEmpireModel {

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @RdfProperty("cos:hasIssue")
    private List<CriticalIssue> issues;

    /* The name of the issue */
    @RdfProperty("cos:hasIssueMatrix")
    private double[][] issueMatrix;

    public QSSI() {
    }

    public QSSI(List<CriticalIssue> issues, double[][] issueMatrix) {
        this.issues = issues;
        this.issueMatrix = issueMatrix;
    }

    public List<CriticalIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<CriticalIssue> issues) {
        this.issues = issues;
    }

    public double[][] getIssueMatrix() {
        return issueMatrix;
    }

    public void setIssueMatrix(double[][] issueMatrix) {
        this.issueMatrix = issueMatrix;
    }


}
