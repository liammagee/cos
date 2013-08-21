/**
 * 
 */
package edu.rmit.sustainability.util;


import Jama.Matrix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author E65691
 * Tests use of the Empire framework
 */
public class AHPUtilsTest {

    EntityManager aManager = null;
    double[] arbitraryWeights;
    Matrix reciprocalMatrix;
    Matrix adjustedReciprocalMatrix;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
        arbitraryWeights = new double[]{ 2., 1., 3. };
        reciprocalMatrix = AHPUtils.generateReciprocalMatrix(arbitraryWeights);

        // Adjust upper triangle manually
        adjustedReciprocalMatrix = reciprocalMatrix.copy();
        adjustedReciprocalMatrix.set(0, 1, 3);
        adjustedReciprocalMatrix.set(0, 2, 1);
        adjustedReciprocalMatrix.set(1, 2, (double)1 / 4);
	}

	@Test
	public void generateMatrixTest() {
        // Should have a rank of 1
        assertEquals(reciprocalMatrix.rank(), 1);
        // Should have a zero determinant
        assertEquals((long)reciprocalMatrix.det(), 0);
        // Should have a trace of 3 (3 * 1)
        assertEquals((long)reciprocalMatrix.trace(), 3);

        // Should have 3 rows
        assertEquals(reciprocalMatrix.getRowDimension(), 3);
        // Should have 3 columns
        assertEquals(reciprocalMatrix.getColumnDimension(), 3);

        // Check values
        assertEquals((long)reciprocalMatrix.get(0, 0), 1);
        assertEquals((long)reciprocalMatrix.get(0, 1), 2);
        assertEquals((long)reciprocalMatrix.get(0, 2), 2 / 3);
        assertEquals((long)reciprocalMatrix.get(1, 0), (long)0.5);
        assertEquals((long)reciprocalMatrix.get(1, 1), 1);
        assertEquals((long)reciprocalMatrix.get(1, 2), 1 / 3);
        assertEquals((long)reciprocalMatrix.get(2, 0), (long)1.5);
        assertEquals((long)reciprocalMatrix.get(2, 1), 3);
        assertEquals((long)reciprocalMatrix.get(2, 2), 1);
    }


	@Test
	public void generateWeightsTest() {
        double principalEigenvalue = AHPUtils.generatePrincipalEigenvalue(reciprocalMatrix);
        // Should have a principal eigenvalue of 3
        assertEquals((long)principalEigenvalue, 3);

        double[] principalEigenvector = AHPUtils.generateNormalisedPrincipalEigenvector(reciprocalMatrix);
        // Should have normalised weights of 2 / 6, 1 / 6 and 3 / 6
        assertEquals((long)principalEigenvector[0], (long)arbitraryWeights[0] / 6);
        assertEquals((long)principalEigenvector[0], (long)arbitraryWeights[1] / 6);
        assertEquals((long)principalEigenvector[0], (long)arbitraryWeights[2] / 6);
    }

	@Test
	public void makeReciprocalMatrixTest() {
        try {
            Matrix newReciprocalMatrix = AHPUtils.makeMatrixReciprocal(adjustedReciprocalMatrix);

            // Check the reciprocals
            assertEquals((long) newReciprocalMatrix.get(1, 0), (long) 1 / 3);
            assertEquals((long)newReciprocalMatrix.get(2, 0), (long) 1);
            assertEquals((long)newReciprocalMatrix.get(2, 1), (long) 4);
        }
        catch (Exception e){}
    }

	@Test
	public void consistencyTest() {
        double consistencyIndex = AHPUtils.getConsistencyIndex(reciprocalMatrix);
        assertEquals((long)consistencyIndex, 0);
        double consistencyRatio = AHPUtils.getConsistencyRatio(reciprocalMatrix);
        assertEquals((long)consistencyRatio, 0);
        boolean isConsistent = AHPUtils.isConsistent(reciprocalMatrix);
        assertTrue(isConsistent);

        // Make all pairwise comparisons 9, making maximum a > b > c > a
        Matrix inconsistentMatrix = reciprocalMatrix.copy();
        inconsistentMatrix.set(0, 1, 9);
        inconsistentMatrix.set(0, 2, 9);
        inconsistentMatrix.set(2, 1, 9);

        try {
            Matrix reciprocalInconsistentMatrix = AHPUtils.makeMatrixReciprocal(inconsistentMatrix);
            isConsistent = AHPUtils.isConsistent(reciprocalInconsistentMatrix);
            assertTrue(! isConsistent);

        }
        catch (Exception e){}
    }

	@Test
	public void aggregationTest() {
        double[] issueWeights = new double[]{3., 2., 1.};
        Matrix criterion1IssueMatrix = AHPUtils.generateReciprocalMatrix(issueWeights);
        Matrix criterion2IssueMatrix = AHPUtils.generateReciprocalMatrix(issueWeights);
        Matrix criterion3IssueMatrix = AHPUtils.generateReciprocalMatrix(issueWeights);
        double[] criterion1IssuePriorityVector = AHPUtils.generateNormalisedPrincipalEigenvector(criterion1IssueMatrix);
        double[] criterion2IssuePriorityVector = AHPUtils.generateNormalisedPrincipalEigenvector(criterion2IssueMatrix);
        double[] criterion3IssuePriorityVector = AHPUtils.generateNormalisedPrincipalEigenvector(criterion3IssueMatrix);
        double[] criteriaPriorityVector = AHPUtils.generateNormalisedPrincipalEigenvector(reciprocalMatrix);
        double[][] combinedVectors = new double[issueWeights.length][issueWeights.length];
        combinedVectors[0] = criterion1IssuePriorityVector;
        combinedVectors[1] = criterion2IssuePriorityVector;
        combinedVectors[2] = criterion3IssuePriorityVector;
        Matrix aggregateMatrix = new Matrix(combinedVectors).transpose();
        double[][] criteriaPriorityVectorArray = new double[1][criteriaPriorityVector.length];
        criteriaPriorityVectorArray[0] = criteriaPriorityVector;
        Matrix priorityVectorMatrix = new Matrix(criteriaPriorityVectorArray);
        aggregateMatrix.print(2, 3);
        priorityVectorMatrix.print(2, 3);
        Matrix weightedVector = AHPUtils.computeWeightedVector(aggregateMatrix, priorityVectorMatrix);
        weightedVector.print(2, 3);

    }


	@Test
	public void validityTest() {
        // TODO: Need to test that a matrix is square, singular, that upper and lower triangles are reciprocal and all values on the diagonal equal 1.
    }




	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
