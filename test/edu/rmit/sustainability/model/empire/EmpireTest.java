package edu.rmit.sustainability.model.empire;

import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.model.CriticalIssue;
import edu.rmit.sustainability.model.ahp.AHP;
import edu.rmit.sustainability.model.ahp.Criterion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Liam
 * Date: 17/05/11
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmpireTest {

    private ArrayModel arrayModel;
    EntityManager aManager = null;


	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
        aManager = EmpireSDB2ManagerFactory.createEmpireEntityManager("war/WEB-INF/examples.empire.config.properties");
        int[] matrixArray = new int[1];
        arrayModel = new ArrayModel(new MatrixModel(matrixArray));
	}

    @Test
    public void arrayPersistenceTest() {
//        aManager.persist(arrayModel);
    }



    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        // Need to persist it now, so proper keys are set
//        aManager.remove(arrayModel);
    }

}
