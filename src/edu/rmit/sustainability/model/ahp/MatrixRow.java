package edu.rmit.sustainability.model.ahp;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;
import edu.rmit.sustainability.model.AbstractEmpireModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
@RdfsClass("cos:MatrixRow")
@Entity
@EntityListeners(CoSEntityListener.class)
public class MatrixRow extends AbstractEmpireModel {

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @RdfProperty("cos:hasMatrixCell")
    private List<MatrixCell> cells = new ArrayList<MatrixCell>();

    public MatrixRow() {
    }

    public List<MatrixCell> getCells() {
        return cells;
    }

    public void setCells(List<MatrixCell> cells) {
        this.cells = cells;
    }

    public void addCell(MatrixCell cell) {
        cells.add(cell);
    }

    public double[] getCellsAsArray() {
        double[] cellValues = new double[cells.size()];
        int i = 0;
        for (Iterator<MatrixCell> iterator = cells.iterator(); iterator.hasNext(); i++) {
            MatrixCell next = iterator.next();
            cellValues[i] = next.getValue();
        }
        return cellValues;
    }
}
