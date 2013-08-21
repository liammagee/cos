/**
 * 
 */
package edu.rmit.sustainability.model;


import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

/**
 * @author E65691
 * Tests use of the Empire framework
 */
public class ObjectivePersistenceTest {

    EntityManager aManager = null;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
        aManager = EmpireSDB2ManagerFactory.createEmpireEntityManager("war/WEB-INF/examples.empire.config.properties");
	}

	@Test
	public void objectivePersistenceTest() {
        // Create a critical issue
        Objective objective = new Objective();
        objective.setDescription("Increase water catchments");
        objective.setDesiredDirection(1);
        aManager.persist(objective);

        System.out.println(objective.getDescription());
        System.out.println(objective.getRdfId().value());
        Objective objectiveCopy = aManager.find(Objective.class, objective.getRdfId());
        assertEquals(objectiveCopy.getDescription(), "Increase water catchments");
        assertEquals(objectiveCopy.getDescription(), objective.getDescription());

    }







	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
