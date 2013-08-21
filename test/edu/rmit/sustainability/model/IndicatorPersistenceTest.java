/**
 * 
 */
package edu.rmit.sustainability.model;


import com.clarkparsia.empire.Empire;
import com.clarkparsia.empire.EmpireOptions;
import com.clarkparsia.empire.impl.RdfQuery;
import com.clarkparsia.empire.jena.JenaEmpireModule;
import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @author E65691
 * Tests use of the Empire framework
 */
public class IndicatorPersistenceTest {

    EntityManager aManager = null;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
        aManager = EmpireSDB2ManagerFactory.createEmpireEntityManager("war/WEB-INF/examples.empire.config.properties");
	}

	@Test
	public void indicatorPersistenceTest() {
        // Save the project
        Project project = new Project();
        project.setProjectName("Guillaume Test Project");
        project.setProjectDescription("A description of the project");
        project.setGeneralIssue("The general issue");
        project.setNormativeGoal("The normative goal");

        // Save the project
        aManager.persist(project);

        // Create a critical issue
        CriticalIssue issue = new CriticalIssue();
        issue.setName("Lack of water");
        issue.setDescription("Drought conditions (lack of rainfall, excessive consumption) have led to water scarcity.");

        // Add the issue to the project
        project.setCriticalIssues(Arrays.asList(issue));

        assertEquals(project.getCriticalIssues().size(), 1);

        // Now save the project
        aManager.merge(project);

        // Need to save the issue explicitly?
//        aManager.persist(issue);


        Project projectCopy = aManager.find(Project.class, project.getRdfId());
        assertEquals(projectCopy.getCriticalIssues().size(), 1);
        assertEquals(projectCopy.getCriticalIssues().get(0), issue);

        // Remove the projects
        aManager.remove(project);
    }

	@Test
	public void nestedPropertyTest() {
        // Create a indicator
        Indicator indicator = new Indicator();
        indicator.setName("Rainfall");
        indicator.setDescription("Annual rainfall in district");


        try {
            Map<String, String[]> parameters = new HashMap<String, String[]>();
            parameters.put("target.value", new String[]{"1.0"});
            BeanUtils.populate(indicator, parameters);
            aManager.persist(indicator);

            assertEquals(indicator.getTarget().getValue(), "1.0");

            Target target = aManager.find(Target.class, indicator.getTarget().getRdfId());
            assertEquals(target.getValue(), "1.0");

            // Test nested update
            parameters = new HashMap<String, String[]>();
            parameters.put("target.value", new String[]{"2.0"});
            BeanUtils.populate(indicator, parameters);
//            target.setValue("2.0");
//            indicator.setTarget(target);
            aManager.merge(indicator);
            assertEquals(indicator.getTarget().getValue(), "2.0");

            // Test finding an Indicator and checking its Target class
            System.out.println("TESTING NESTED PROPERTY");
//            EmpireOptions.ENFORCE_ENTITY_ANNOTATION = false;
            Indicator indicatorCopy = aManager.find(Indicator.class, indicator.getRdfId());
            assertEquals(indicatorCopy.getTarget().getValue(), "2.0");

            Target targetCopy = aManager.find(Target.class, target.getRdfId());
            assertEquals(targetCopy.getValue(), "2.0");

            // Test the target is also removed
            aManager.remove(indicator);
            targetCopy = aManager.find(Target.class, target.getRdfId());
            assertNull(targetCopy);

        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * Tests that indicators with no indicator sets only can be found
     */
	@Test
	public void findIndicatorsWithNoSetsTest() {
        // Create a indicator
        Indicator indicator = new Indicator();
        indicator.setName("My Own Indicator");
        indicator.setDescription("Annual rainfall in district");
        indicator.setIndicatorSet(null);
        aManager.persist(indicator);

        Indicator copy = aManager.find(Indicator.class, indicator.getRdfId());
        assertNotNull(copy);
        assertEquals(copy, indicator);

        // Now get all indicators belonging to indicator sets
        Query query = aManager.createQuery(
                "WHERE { ?result a <http://circlesofsustainability.org/ontology#IndicatorSet> . " + "}");

        // this query should return instances of type Project
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, IndicatorSet.class);

        List aResults = query.getResultList();

        List<IndicatorSet> indicatorSets = new ArrayList<IndicatorSet>();
        List<Indicator> indicatorsInSets = new ArrayList<Indicator>();
        for (Iterator iterator = aResults.iterator(); iterator.hasNext(); ) {
            indicatorSets.add((IndicatorSet) iterator.next());
        }
        for (Iterator<IndicatorSet> iterator = indicatorSets.iterator(); iterator.hasNext(); ) {
            IndicatorSet indicatorSet = iterator.next();
            for (Iterator<Indicator> indicatorIterator = indicatorSet.getIndicators().iterator(); indicatorIterator.hasNext(); ) {
                Indicator indicatorInSet =  indicatorIterator.next();
                indicatorsInSets.add(indicatorInSet);
            }
        }


        // Get all indicators NOT belonging to a set
        query = aManager.createQuery(
                "WHERE { ?result a <http://circlesofsustainability.org/ontology#Indicator> . " +
                        "OPTIONAL { ?result <http://circlesofsustainability.org/ontology#belongsToIndicatorSet> ?name } . " +
                        "FILTER (!BOUND(?name)) "
                        + "}");
//        " MINUS { ?result <http://circlesofsustainability.org/ontology#hasIndicatorSet> ?name } "

        // this query should return instances of type Project
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, Indicator.class);

        aResults = query.getResultList();

        List<Indicator> indicatorsNotInSets = new ArrayList<Indicator>();
        for (Iterator iterator = aResults.iterator(); iterator.hasNext(); ) {
            indicatorsNotInSets.add((Indicator) iterator.next());
        }

        // Check no indicators not in a set are not in the set of indicators...
        System.out.println("Check no indicators not in a set are not in the set of indicators...");
        for (Iterator<Indicator> iterator = indicatorsNotInSets.iterator(); iterator.hasNext(); ) {
            Indicator indicatorNotInSet = iterator.next();
            System.out.println(indicatorNotInSet.getRdfId().value());
            assertFalse(indicatorsInSets.contains(indicatorNotInSet));
        }

        // Check no indicators in a set are not in the set of indicators not in a set...
        System.out.println("Check no indicators in a set are not in the set of indicators not in a set...");
        for (Iterator<Indicator> iterator = indicatorsInSets.iterator(); iterator.hasNext(); ) {
            Indicator indicatorInSet = iterator.next();
            System.out.println(indicatorInSet.getRdfId().value());
            assertFalse(indicatorsNotInSets.contains(indicatorInSet));
        }

        aManager.remove(indicator);

    }








	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
