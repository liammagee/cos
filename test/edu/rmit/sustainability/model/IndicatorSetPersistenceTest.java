/**
 * 
 */
package edu.rmit.sustainability.model;


import com.clarkparsia.empire.Empire;
import com.clarkparsia.empire.jena.JenaEmpireModule;
import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.server.DomainExtractor;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author E65691
 * Tests use of the Empire framework
 */
public class IndicatorSetPersistenceTest {

    EntityManager aManager = null;
    User defaultUser = null;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
        aManager = EmpireSDB2ManagerFactory.createEmpireEntityManager("war/WEB-INF/examples.empire.config.properties");

        defaultUser = new User();
        defaultUser.setUsername("Default");
        defaultUser.setPassword("1234");
        aManager.persist(defaultUser);
        // Create a project
	}

	@Test
	public void indicatorSetPersistenceTest() {
        // Save the project
        IndicatorSet indSetGRI = new IndicatorSet();
        indSetGRI.setCreator(defaultUser);
        indSetGRI.setSource("GRI");
        indSetGRI.setName("GRI Indicator Set");
        indSetGRI.setDescription("The set of indicator defined by the GRI");

        IndicatorSet indSetOther = new IndicatorSet();
        indSetOther.setCreator(defaultUser);
        indSetOther.setSource("Other");
        indSetOther.setName("Other Indicator Set");
        indSetOther.setDescription("Another set of indicators");
        aManager.persist(indSetGRI);
        aManager.persist(indSetOther);

        IndicatorSet copy = aManager.find(IndicatorSet.class, indSetGRI.getRdfId());
        assertEquals(copy.getName(), "GRI Indicator Set");

        Domain economy = DomainExtractor.getDomains().get(0);

        Indicator waterConsumption = new Indicator();
        waterConsumption.setCreator(defaultUser);
        waterConsumption.setIdentifyingCode("waterconsumption");
        waterConsumption.setName("Water Consumption");
        waterConsumption.setDescription("The total water a consumption this year (in m3)");
        waterConsumption.setDomain(economy);
        waterConsumption.setSubdomain(economy.getSubdomain("Consumption and Use"));
        waterConsumption.setIndicatorSet(indSetGRI);
        indSetGRI.addIndicator(waterConsumption);

        aManager.merge(indSetGRI);

        // Test the new Indicator can be found
        Indicator indicatorCopy = aManager.find(Indicator.class, waterConsumption.getRdfId());
        assertEquals(waterConsumption.getName(), indicatorCopy.getName());


        // Remove the projects
        aManager.remove(indSetGRI);
        aManager.remove(indSetOther);

        // Check the Indicator Set cannot be found...
        copy = aManager.find(IndicatorSet.class, indSetGRI.getRdfId());
        assertNull(copy);

        // ... but since we don't cascade deletes (TODO: Check this behaviour), we should still find the indicator
        indicatorCopy = aManager.find(Indicator.class, waterConsumption.getRdfId());
        assertEquals(waterConsumption.getName(), indicatorCopy.getName());
    }




	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
