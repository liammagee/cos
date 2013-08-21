package edu.rmit.sustainability.model.empire;

import org.openrdf.model.Value;

/**
 * Created by IntelliJ IDEA.
 * User: Liam
 * Date: 24/05/11
 * Time: 9:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class MatrixModel implements Value {
    int[] matrixArray;

    public MatrixModel() {
    }

    public MatrixModel(int[] matrixArray) {
        this.matrixArray = matrixArray;
    }

    public int[] getMatrixArray() {
        return matrixArray;
    }

    public void setMatrixArray(int[] matrixArray) {
        this.matrixArray = matrixArray;
    }

    public String stringValue() {
        return matrixArray.toString();
    }
}
