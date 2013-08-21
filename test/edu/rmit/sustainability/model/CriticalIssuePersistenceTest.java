/**
 * 
 */
package edu.rmit.sustainability.model;


import com.clarkparsia.empire.Empire;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author E65691
 * Tests use of the Empire framework
 */
public class CriticalIssuePersistenceTest {

    EntityManager aManager = null;
    Project project = null;

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
        // Save the project
        aManager.persist(project);
	}

	@Test
	public void criticalIssuePersistenceTest() {

        // Create a critical issue
        CriticalIssue issue = new CriticalIssue();
        issue.setName("Lack of water");
        issue.setDescription("Drought conditions (lack of rainfall, excessive consumption) have led to water scarcity.");

        // Add the issue to the project
        project.setCriticalIssues(Arrays.asList(issue));

        assertEquals(project.getCriticalIssues().size(), 1);

        // Now save the project
        aManager.merge(project);

        Project projectCopy = aManager.find(Project.class, project.getRdfId());
        assertEquals(projectCopy.getCriticalIssues().size(), 1);
        CriticalIssue copy = projectCopy.getCriticalIssues().get(0);
        assertEquals(copy, issue);
        assertEquals(copy.getName(), "Lack of water");
    }

    @Test
    public void projectSavesIssueDetailsTest() {
        CriticalIssue issue = new CriticalIssue();
        issue.setName("Lack of water");
        issue.setDescription("Drought conditions (lack of rainfall, excessive consumption) have led to water scarcity.");
        issue.setPerceivedSignificance(1);
        project.setCriticalIssues(Arrays.asList(issue));

        // Now save the project
        aManager.merge(project);

        CriticalIssue copy = project.getCriticalIssues().get(0);
        // Set the significance to something different
        copy.setPerceivedSignificance(5);
        aManager.merge(project);

        // Check the issue has been saved
        copy = aManager.find(CriticalIssue.class, copy.getRdfId());
        assertEquals(copy.getPerceivedSignificance(), 5);
    }


	@Test
	public void nestedPropertyTest() {
        // Create a critical issue
        CriticalIssue issue = new CriticalIssue();
        issue.setName("Lack of water");
        issue.setDescription("Drought conditions (lack of rainfall, excessive consumption) have led to water scarcity.");

        Map<String, String[]> parameters = new HashMap<String, String[]>();
        parameters.put("associatedObjective.description", new String[]{"Increase water catchments"});

        try {
            BeanUtils.populate(issue, parameters);
            aManager.persist(issue);

            System.out.println(issue.getRdfId().value());
            System.out.println(issue.getAssociatedObjective().getRdfId().value());

            System.out.println(issue.getAssociatedObjective().getDescription());
            assertEquals(issue.getAssociatedObjective().getDescription(), "Increase water catchments");

            Objective objective = aManager.find(Objective.class, issue.getAssociatedObjective().getRdfId());
            System.out.println(objective.getDescription());
            assertEquals(objective.getDescription(), "Increase water catchments");

            aManager.remove(issue);
            Objective objectiveCopy = aManager.find(Objective.class, objective.getRdfId());
            System.out.println(objectiveCopy);
            assertEquals(objectiveCopy, null);

        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }







	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {

        // Remove the projects
        aManager.remove(project);
	}

}
