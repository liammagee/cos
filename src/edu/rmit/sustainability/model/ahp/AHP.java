package edu.rmit.sustainability.model.ahp;

import Jama.Matrix;
import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;
import edu.rmit.sustainability.model.AbstractEmpireModel;
import edu.rmit.sustainability.model.CriticalIssue;
import edu.rmit.sustainability.util.AHPUtils;

import javax.persistence.*;
import java.util.*;

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
@RdfsClass("cos:AHP")
@Entity
@EntityListeners(CoSEntityListener.class)
public class AHP extends AbstractEmpireModel {

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @RdfProperty("cos:hasCriteria")
    private List<Criterion> criteria;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @RdfProperty("cos:hasCriteriaMatrix")
    private CriteriaMatrix criteriaMatrix;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @RdfProperty("cos:hasIssue")
    private List<CriticalIssue> issues;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @RdfProperty("cos:hasIssueMatrices")
    private List<IssueMatrix> issueMatrices;


    public AHP() {
    }

    public AHP(List<Criterion> criteria, List<CriticalIssue> issues) {
        this.criteria = criteria;
        this.issues = issues;
        initialiseMatrices();
    }

    /**
     * Builds the internal matrices if they don't already exist, or require re-initialisation
     */
    public void rebuildMatrices() {
        if (criteria != null && issues != null && (criteriaMatrix == null || issueMatrices == null || issueMatrices.contains(null)))
            initialiseMatrices();
    }

    public void initialiseMatrices() {
        issueMatrices = new ArrayList<IssueMatrix>();

        double[] criteriaWeights = new double[criteria.size()];
        double[] issueWeights = new double[issues.size()];
        for (int i = 0; i < criteriaWeights.length; i++) {
            int criteriaWeight = criteria.get(i).getInitialWeight();
            criteriaWeights[i] = (double) criteriaWeight;
        }
        for (int i = 0; i < issueWeights.length; i++) {
            int issueWeight = issues.get(i).getPerceivedSignificance();
            issueWeights[i] = (double) issueWeight;
        }
        Matrix cwMatrix = AHPUtils.generateReciprocalMatrix(criteriaWeights);
        Matrix iwMatrix = AHPUtils.generateReciprocalMatrix(issueWeights);
        criteriaMatrix = new CriteriaMatrix(criteria, cwMatrix.getArray());
        for (Criterion criterion : criteria) {
            issueMatrices.add(new IssueMatrix(criterion, issues, iwMatrix.getArray()));
        }
    }

    /**
     * Main method for computing the aggregate principal eigenvector of all issue matrices, weighted by the criteria matrix.
     *
     * @return
     */
    public Map<CriticalIssue, Double> getAggregatePrincipalEigenvector() {
        double[][] aggregateIssueEigenvectorsArray = new double[issueMatrices.size()][issueMatrices.size()];
        int counter = 0;
        for (IssueMatrix issueMatrix : issueMatrices) {
            aggregateIssueEigenvectorsArray[counter] = issueMatrix.getPrincipalEigenvector();
            counter++;
        }
        Matrix aggregateMatrix = new Matrix(aggregateIssueEigenvectorsArray).transpose();
        double[][] criteriaEigenvectorArray = new double[1][criteriaMatrix.getCriteria().size()];
        criteriaEigenvectorArray[0] = criteriaMatrix.getPrincipalEigenvector();
        Matrix criteriaEigenvectorMatrix = new Matrix(criteriaEigenvectorArray);
        Matrix aggregatePrincipalEigenvector = AHPUtils.computeWeightedVector(aggregateMatrix, criteriaEigenvectorMatrix);
        double[] issueWeights = aggregatePrincipalEigenvector.getColumnPackedCopy();
        Map<CriticalIssue, Double> issuesAndWeights = new TreeMap<CriticalIssue, Double>();
        counter = 0;
        for (CriticalIssue criticalIssue : issues) {
            issuesAndWeights.put(criticalIssue, issueWeights[counter]);
            counter++;
        }
        return issuesAndWeights;
    }


    public int getTotalInitialWeights() {
        int total = 0;
        for (CriticalIssue criticalIssue : issues) {
            total += criticalIssue.getPerceivedSignificance();
        }
        return total;
    }


    public List<Criterion> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<Criterion> criteria) {
        this.criteria = criteria;
    }

    public List<CriticalIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<CriticalIssue> issues) {
        this.issues = issues;
    }

    public CriteriaMatrix getCriteriaMatrix() {
        return criteriaMatrix;
    }

    public void setCriteriaMatrix(CriteriaMatrix criteriaMatrix) {
        this.criteriaMatrix = criteriaMatrix;
    }

    public List<IssueMatrix> getIssueMatrices() {
        return issueMatrices;
    }

    public void setIssueMatrices(List<IssueMatrix> issueMatrices) {
        this.issueMatrices = issueMatrices;
    }


    public IssueMatrix getIssueMatrix(String criterionID) {
        for (Iterator<IssueMatrix> iterator = issueMatrices.iterator(); iterator.hasNext(); ) {
            IssueMatrix issueMatrix = iterator.next();
            if (issueMatrix.getCriterion().getId().equals(criterionID))
                return issueMatrix;
        }
        return null;
    }

    public double getAverageConsistencyIndex() {
        int counter = 0;
        double aggregateConsistencyIndex = 0;
        for (IssueMatrix issueMatrix : issueMatrices) {
            aggregateConsistencyIndex += issueMatrix.getConsistencyIndex();
            counter++;
        }
        return aggregateConsistencyIndex / counter;
    }

    public double getAverageConsistencyRatio() {
        int counter = 0;
        double aggregateConsistencyRatio = 0;
        for (IssueMatrix issueMatrix : issueMatrices) {
            aggregateConsistencyRatio += issueMatrix.getConsistencyRatio();
            counter++;
        }
        return aggregateConsistencyRatio / counter;
    }

    public boolean isGenerallyConsistent() {
        return getAverageConsistencyRatio() < 0.1;
    }
}
