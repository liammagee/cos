package edu.rmit.sustainability.model.qssi;

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
public class QSSITest {

    private QSSI qssi;
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

        qssi = new QSSI();

        // Need to persist it now, so proper keys are set
        aManager.persist(qssi);
	}

    @Test
    public void evaluateQSSITest() {
    }



    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        // Need to persist it now, so proper keys are set
        aManager.remove(qssi);
    }

}
