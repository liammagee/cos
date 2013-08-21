/**
 * 
 */
package edu.rmit.sustainability.data.query;


import com.clarkparsia.empire.impl.RdfQuery;
import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.model.Indicator;
import edu.rmit.sustainability.model.IndicatorSet;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author E65691
 * Tests use of the Empire framework
 */
public class PublicIndicatorSetQueryTest {

    EntityManager aManager = null;
    Project project = null;
    User user = null;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
        aManager = EmpireSDB2ManagerFactory.createEmpireEntityManager("war/WEB-INF/examples.empire.config.properties");
        project = new Project();
        project.setProjectName("Guillaume Test Project");
        project.setProjectDescription("A description of the project");
        project.setGeneralIssue("The general issue");
        project.setNormativeGoal("The normative goal");

        user = new User();
        user.setUsername("Lida");
        user.setPassword("1234");

	}

	@Test
	public void indicatorSetQueryTest() {

        // Just runs the indicators to System.out
        PublicIndicatorSetQuery query = new PublicIndicatorSetQuery(aManager);
        List<IndicatorSet> indicatorSets = query.doQuery(project, user);
        for (Iterator<IndicatorSet> iterator = indicatorSets.iterator(); iterator.hasNext(); ) {
            IndicatorSet indicatorSet = iterator.next();
            List<Indicator> indicators = indicatorSet.getIndicators();
            for (Iterator<Indicator> indicatorIterator = indicators.iterator(); indicatorIterator.hasNext(); ) {
                Indicator indicator = indicatorIterator.next();
                System.out.println(indicator.getName());
            }
        }
    }




	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
