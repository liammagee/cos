package edu.rmit.sustainability.model.ahp;

import Jama.Matrix;
import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.model.AbstractEmpireModel;
import edu.rmit.sustainability.util.AHPUtils;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Liam
 * Date: 17/05/11
 * Time: 11:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Namespaces({"frbr", "http://vocab.org/frbr/core#",
        "dc", "http://purl.org/dc/terms/",
        "cos", "http://circlesofsustainability.org/ontology#",
        "foaf", "http://xmlns.com/foaf/0.1/"})
@RdfsClass("cos:AbstractMatrix")
@Entity
public abstract class AbstractMatrix extends AbstractEmpireModel {


    public AbstractMatrix() {
    }


    public abstract List<MatrixRow> getMatrixRows();

    public abstract void setMatrixRows(List<MatrixRow> matrixRows);


    public double[][] getMatrixArray() {
        List<MatrixRow> matrixRows = getMatrixRows();
        double[][] cellValues = new double[matrixRows.size()][matrixRows.get(0).getCells().size()];
        int i = 0;
        for (Iterator<MatrixRow> iterator = matrixRows.iterator(); iterator.hasNext(); i++) {
            MatrixRow next = iterator.next();
            cellValues[i] = next.getCellsAsArray();
        }
        return cellValues;
    }


    public void setMatrixArray(double[][] matrixArray) {
        List<MatrixRow> matrixRows = new ArrayList<MatrixRow>();
        for (int i = 0; i < matrixArray.length; i++) {
            double[] doubles = matrixArray[i];
            MatrixRow row = new MatrixRow();
            for (int j = 0; j < doubles.length; j++) {
                double aDouble = doubles[j];
                MatrixCell cell = new MatrixCell();
                cell.setValue(aDouble);
                row.addCell(cell);
            }
            matrixRows.add(row);
        }
        setMatrixRows(matrixRows);
    }

    public Matrix getMatrixAsMatrix() {
        return new Matrix(getMatrixArray());
    }

    public double getPrincipalEigenvalue() {
        return AHPUtils.generatePrincipalEigenvalue(getMatrixAsMatrix());
    }

    public double[] getPrincipalEigenvector() {
        return AHPUtils.generateNormalisedPrincipalEigenvector(getMatrixAsMatrix());
    }

    public double getConsistencyIndex() {
        return AHPUtils.getConsistencyIndex(getMatrixAsMatrix());
    }

    public double getConsistencyRatio() {
        return AHPUtils.getConsistencyRatio(getMatrixAsMatrix());
    }

    public boolean isConsistent() {
        return AHPUtils.isConsistent(getMatrixAsMatrix());
    }

}
