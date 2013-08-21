package edu.rmit.sustainability.util;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Liam
 * Date: 16/05/11
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class AHPUtils {

    /**
     * Look-up values for consistency (following http://people.revoledu.com/kardi/tutorial/AHP/Consistency.htm)
     * TODO: Retrieve from better authority (Saaty)
     */
    static Map<Integer, Double> CONSISTENCY_LOOKUPS = new HashMap<Integer, Double>();

    static {
        CONSISTENCY_LOOKUPS.put(0, 0.);
        CONSISTENCY_LOOKUPS.put(1, 0.);
        CONSISTENCY_LOOKUPS.put(2, 0.);
        CONSISTENCY_LOOKUPS.put(3, 0.58);
        CONSISTENCY_LOOKUPS.put(4, 0.9);
        CONSISTENCY_LOOKUPS.put(5, 1.12);
        CONSISTENCY_LOOKUPS.put(6, 1.24);
        CONSISTENCY_LOOKUPS.put(7, 1.32);
        CONSISTENCY_LOOKUPS.put(8, 1.41);
        CONSISTENCY_LOOKUPS.put(9, 1.45);
        CONSISTENCY_LOOKUPS.put(10, 1.49);
    }

    /**
     * Generates a reciprocal matrix from an array of initial seed weights
     *
     * @param seedWeights
     * @return
     */
    public static Matrix generateReciprocalMatrix(double[] seedWeights) {
        Matrix criteriaMatrix = new Matrix(seedWeights.length, seedWeights.length, 1);
        for (int i = 0; i < seedWeights.length; i++) {
            double criteriaWeight = seedWeights[i];
            for (int j = i + 1; j < seedWeights.length; j++) {
                double weight = seedWeights[j];

                double v1 = criteriaWeight / weight;
                double v2 = weight / criteriaWeight;
                if (weight == 0 || criteriaWeight == 0) {
                    v1 = 1;
                    v2 = 1;
                }
                criteriaMatrix.set(i, j, v1);
                criteriaMatrix.set(j, i, v2);
            }
        }
        return criteriaMatrix;
    }

    /**
     * Sets the lower triangular part of a square matrix to be the reciprocal of the upper triangular part.
     *
     * @param matrix
     * @return
     */
    public static Matrix makeMatrixReciprocal(Matrix matrix) throws Exception {
        int rows = matrix.getRowDimension();
        int cols = matrix.getColumnDimension();

        // Not square? Throw an Exception
        if (rows != cols)
            throw new Exception("Must be a square matrix.");

        for (int i = 0; i < rows - 1; i++) {
            for (int j = i + 1; j < cols; j++) {
                double v = matrix.get(i, j);
                matrix.set(j, i, 1 / v);
            }
        }

        return matrix;
    }

    /**
     * Generates the principal eigenvalue from the matrix.
     *
     * @param reciprocalMatrix
     * @return
     */
    public static double generatePrincipalEigenvalue(Matrix reciprocalMatrix) {
        EigenvalueDecomposition ed = reciprocalMatrix.eig();
        double principalEigenvalue = 0;
        for (int i = 0; i < ed.getRealEigenvalues().length; i++) {
            double v = ed.getRealEigenvalues()[i];
            if (v > Math.abs(principalEigenvalue)) {
                principalEigenvalue = v;
            }
        }
        return principalEigenvalue;
    }

    /**
     * Generates the normalised principal eigenvector of the matrix
     *
     * @param reciprocalMatrix
     * @return
     */
    public static double[] generateNormalisedPrincipalEigenvector(Matrix reciprocalMatrix) {
        EigenvalueDecomposition ed = reciprocalMatrix.eig();
        double principalEigenvalue = 0;
        int principalEigenvectorColumnDimension = 0;
        Matrix eigenvectors = ed.getV();
        for (int i = 0; i < ed.getRealEigenvalues().length; i++) {
            double v = ed.getRealEigenvalues()[i];
            if (v > Math.abs(principalEigenvalue)) {
                principalEigenvalue = v;
                principalEigenvectorColumnDimension = i;
            }
        }
        int[] rowArray = new int[eigenvectors.getColumnDimension()];
        for (int i = 0; i < rowArray.length; i++) {
            rowArray[i] = i;
        }

        // Principal eigenvector
        int[] colArray = {principalEigenvectorColumnDimension};
        Matrix principalEigenvector = eigenvectors.getMatrix(rowArray, colArray);

        // Normalise the principal eigenvector
        double columnSum = principalEigenvector.norm1();
        Matrix normalisedPrincipalEigenvector = principalEigenvector.times(1 / columnSum);
        return normalisedPrincipalEigenvector.getColumnPackedCopy();
    }

    /**
     * Generates a consistency index for the matrix (following http://people.revoledu.com/kardi/tutorial/AHP/Consistency.htm).
     *
     * @param matrix
     * @return
     */
    public static double getConsistencyIndex(Matrix matrix) {
        double principalEigenvalue = generatePrincipalEigenvalue(matrix);
        int dimension = matrix.getRowDimension();
        return (principalEigenvalue - dimension) / (dimension - 1);
    }

    /**
     * Generates a consistency ratio for the matrix (following http://people.revoledu.com/kardi/tutorial/AHP/Consistency.htm).
     *
     * @param matrix
     * @return
     */
    public static double getConsistencyRatio(Matrix matrix) {
        double consistencyIndex = getConsistencyIndex(matrix);
        int dimensionLookupValue = matrix.getRowDimension();
        if (dimensionLookupValue > 10)
            dimensionLookupValue = 10;
        double randomConsistencyDenominator = CONSISTENCY_LOOKUPS.get(dimensionLookupValue);
        return consistencyIndex / randomConsistencyDenominator;
    }

    /**
     * Checks whether the matrix is consistent, below a threshold of 0.1
     *
     * @param matrix
     * @return
     */
    public static boolean isConsistent(Matrix matrix) {
        double consistencyRatio = getConsistencyRatio(matrix);
        return (consistencyRatio < 0.1);
    }

    /**
     * Generates an aggregated priority vector, by generating a series of dotproduct's
     *
     * @param aggregateMatrix
     * @param priorityVector
     * @return
     */
    public static Matrix computeWeightedVector(Matrix aggregateMatrix, Matrix priorityVector) {
        Matrix transposePriority = priorityVector.transpose();
        Matrix computedWeightedVector = new Matrix(aggregateMatrix.getRowDimension(), 1);
        int[] columns = new int[aggregateMatrix.getColumnDimension()];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = i;
        }
        int rows = aggregateMatrix.getRowDimension();
        for (int i = 0; i < rows; i++) {
            int[] rowArray = new int[]{i};
            Matrix rowMatrix = aggregateMatrix.getMatrix(rowArray, columns);
            double aggregate = getDotProduct(rowMatrix, transposePriority);
            computedWeightedVector.set(i, 0, aggregate);
        }
        return computedWeightedVector;
    }

    /**
     * Simple, unchecked method for generating a dot product
     *
     * @param rowMatrix
     * @param columnMatrix
     * @return
     */
    public static double getDotProduct(Matrix rowMatrix, Matrix columnMatrix) {
        double dotProduct = 0;
        for (int i = 0; i < rowMatrix.getColumnDimension(); i++) {
            double rowValue = rowMatrix.get(0, i);
            double columnValue = columnMatrix.get(i, 0);
            dotProduct += rowValue * columnValue;
        }
        return dotProduct;
    }
}
