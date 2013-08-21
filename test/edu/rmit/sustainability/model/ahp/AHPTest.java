package edu.rmit.sustainability.model.ahp;

import Jama.Matrix;
import com.clarkparsia.empire.Empire;
import com.clarkparsia.empire.jena.JenaEmpireModule;
import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.model.CriticalIssue;
import edu.rmit.sustainability.util.AHPUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import static org.junit.Assert.*;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Liam
 * Date: 17/05/11
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class AHPTest {

    private AHP ahp;
    EntityManager aManager = null;


	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
        aManager = EmpireSDB2ManagerFactory.createEmpireEntityManager("war/WEB-INF/examples.empire.config.properties");

        List<Criterion> criteria = new ArrayList<Criterion>();
        criteria.add(new Criterion("Control", "", 2));
        criteria.add(new Criterion("Measurability", "", 1));
        criteria.add(new Criterion("Sensitivity", "", 3));

        List<CriticalIssue> issues = new ArrayList<CriticalIssue>();
        issues.add(new CriticalIssue("Water capacity", "", 3));
        issues.add(new CriticalIssue("Rainfall", "", 2));
        issues.add(new CriticalIssue("Consumption", "", 1));
        issues.add(new CriticalIssue("Acidity", "", 4));

        ahp = new AHP(criteria, issues);

        // Need to persist it now, so proper keys are set
        aManager.persist(ahp);
	}

    @Test
    public void evaluateAHPTest() {
        Map<CriticalIssue,Double> aggregatePrincipalEigenvector = ahp.getAggregatePrincipalEigenvector();
        for (Iterator<Map.Entry<CriticalIssue, Double>> iterator = aggregatePrincipalEigenvector.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<CriticalIssue, Double> criticalIssueDoubleEntry = iterator.next();
            System.out.println(criticalIssueDoubleEntry.getKey().getName() + ": " + criticalIssueDoubleEntry.getValue());
        }

        assertEquals(aggregatePrincipalEigenvector.size(), 4);

        // Get the sum of initial issue weights (3 + 2 + 1
        int totalInitialWeights = ahp.getTotalInitialWeights();

        DecimalFormat df = new DecimalFormat("#.#####");

        // Test the values of the generated weights match those of the initial weights
        for (CriticalIssue criticalIssue : ahp.getIssues()) {
            double calculatedWeight = aggregatePrincipalEigenvector.get(criticalIssue);
            String fCalculatedWeight = df.format(calculatedWeight);
            double initialWeight = Double.valueOf((double) (criticalIssue.getPerceivedSignificance()) / (double) totalInitialWeights);
            String fInitialWeight = df.format(calculatedWeight);
            assertEquals(fCalculatedWeight, fInitialWeight);
        }
    }

    @Test
    public void evaluateAHPWithAdjustedPairwiseComparisonsTest() {
        // Get the first criterion matrix
        IssueMatrix controlIssueMatrix = ahp.getIssueMatrices().get(0);
        // Get the second cell of the first row [1,2]
        MatrixCell matrixCell = controlIssueMatrix.getMatrixRows().get(0).getCells().get(1);
        // Check the value
        System.out.println(matrixCell.getValue());
        assertEquals(((long)3 / 2), (long) matrixCell.getValue());
        matrixCell.setValue( 1 /9);
        Map<CriticalIssue,Double> aggregatePrincipalEigenvector = ahp.getAggregatePrincipalEigenvector();
        for (Iterator<Map.Entry<CriticalIssue, Double>> iterator = aggregatePrincipalEigenvector.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<CriticalIssue, Double> criticalIssueDoubleEntry = iterator.next();
            System.out.println(criticalIssueDoubleEntry.getKey().getName() + ": " + criticalIssueDoubleEntry.getValue());
        }

        Matrix matrix = controlIssueMatrix.getMatrixAsMatrix();
        System.out.println(AHPUtils.isConsistent(matrix));

/*
        for (Iterator<Map.Entry<CriticalIssue, Double>> iterator = aggregatePrincipalEigenvector.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<CriticalIssue, Double> criticalIssueDoubleEntry = iterator.next();
            System.out.println(criticalIssueDoubleEntry.getKey().getName() + ": " + criticalIssueDoubleEntry.getValue());
        }

        assertEquals(aggregatePrincipalEigenvector.size(), 4);

        // Get the sum of initial issue weights (3 + 2 + 1
        int totalInitialWeights = ahp.getTotalInitialWeights();

        DecimalFormat df = new DecimalFormat("#.#####");

        // Test the values of the generated weights match those of the initial weights
        for (CriticalIssue criticalIssue : ahp.getIssues()) {
            double calculatedWeight = aggregatePrincipalEigenvector.get(criticalIssue);
            String fCalculatedWeight = df.format(calculatedWeight);
            double initialWeight = Double.valueOf((double) (criticalIssue.getPerceivedSignificance()) / (double) totalInitialWeights);
            String fInitialWeight = df.format(calculatedWeight);
            assertEquals(fCalculatedWeight, fInitialWeight);
        }
*/
    }



    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        // Need to persist it now, so proper keys are set
        aManager.remove(ahp);
    }

}
