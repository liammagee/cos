package edu.rmit.sustainability.model.empire;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;
import edu.rmit.sustainability.model.AbstractEmpireModel;
import edu.rmit.sustainability.model.ahp.AbstractMatrix;
import edu.rmit.sustainability.model.ahp.Criterion;

import javax.persistence.*;
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
@RdfsClass("cos:ArrayModel")
@Entity
@EntityListeners(CoSEntityListener.class)
public class ArrayModel extends AbstractEmpireModel {


    @RdfProperty("cos:hasMatrixArray")
    protected MatrixModel matrixArray;

    public ArrayModel() {}

    public ArrayModel(MatrixModel matrixArray) {
        setMatrixArray(matrixArray);
    }


    public MatrixModel getMatrixArray() {
        return matrixArray;
    }

    public void setMatrixArray(MatrixModel matrixArray) {
        this.matrixArray = matrixArray;
    }
}
