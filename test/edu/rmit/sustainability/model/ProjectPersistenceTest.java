/**
 * 
 */
package edu.rmit.sustainability.model;


import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.impl.RdfQuery;
import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author E65691
 * Tests use of the Empire framework
 */
public class ProjectPersistenceTest {

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
	public void projectPersistenceTest() {

        Project projectCopy = aManager.find(Project.class, project.getRdfId());
        assertEquals(projectCopy.getProjectName(), project.getProjectName());
        assertEquals(projectCopy, project);


        Query query = aManager.createQuery("WHERE {?result a <http://circlesofsustainability.org/ontology#Project>}");

        // this query should return instances of type Project
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, Project.class);

        List aResults = query.getResultList();
        for (Iterator iterator = aResults.iterator(); iterator.hasNext(); ) {
            System.out.println(((Project) iterator.next()).getProjectName());
        }


        // Update and save the project
        project.setProjectName("Liam's Test Project");
        aManager.merge(project);
    }


	@Test
	public void rankIssuesTest() {
        CriticalIssue issue1 = new CriticalIssue("Water Consumption", "", 1);
        CriticalIssue issue2 = new CriticalIssue("Rainfall", "", 3);
        CriticalIssue issue3 = new CriticalIssue("Water Salination", "", 2);
        project.addCriticalIssue(issue1);
        project.addCriticalIssue(issue2);
        project.addCriticalIssue(issue3);


        // Save the project
        aManager.merge(project);


        Project projectCopy = aManager.find(Project.class, project.getRdfId());
        assertNotNull(projectCopy);
        assertEquals(projectCopy.getCriticalIssues().size(), 3);

        Map<Integer,List<CriticalIssue>> criticalIssueRanking = projectCopy.getSimpleCriticalIssueRanking();
        assertEquals(criticalIssueRanking.get(1).get(0), issue2);
        assertEquals(criticalIssueRanking.get(2).get(0), issue3);
        assertEquals(criticalIssueRanking.get(3).get(0), issue1);

        // Add another issue, with the same significance, and re-test the rankings
        CriticalIssue issue4 = new CriticalIssue("Water Acidity", "", 2);
        project.addCriticalIssue(issue4);
        aManager.merge(project);

        criticalIssueRanking = project.getSimpleCriticalIssueRanking();
        assertEquals(criticalIssueRanking.get(1).get(0), issue2);
        List<CriticalIssue> equivalentlyRankedIssues = criticalIssueRanking.get(2);
        assertTrue(equivalentlyRankedIssues.contains(issue3));
        assertTrue(equivalentlyRankedIssues.contains(issue4));
        // Last issue should now be ranked fourth
        assertEquals(criticalIssueRanking.get(4).get(0), issue1);


    }



	@Test
	public void creatorCollaboratorTest() {
        User liam = new User("liam", "1234");
        project.setCreator(liam);

        aManager.persist(liam);

        // Save the project
        aManager.merge(project);

        Query query = aManager.createQuery("WHERE {?result a <http://circlesofsustainability.org/ontology#Project> . " +
                " ?result <http://circlesofsustainability.org/ontology#createdBy> <" + liam.getId() + "> }");

        // this query should return instances of type Project
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, Project.class);

        Project projectCopy = (Project)query.getSingleResult();
        assertNotNull(projectCopy);
        assertEquals(projectCopy.getCreator(), liam);


        // Test collaboration
        User guillaume = new User("guillaume", "1234");
        project.addCollaborator(guillaume);

        aManager.persist(guillaume);
        aManager.merge(project);

        query = aManager.createQuery("WHERE {?result a <http://circlesofsustainability.org/ontology#Project> . " +
                " { ?result <http://circlesofsustainability.org/ontology#createdBy> <" + guillaume.getId() + ">  } " +
                " UNION " +
                " { ?result <http://circlesofsustainability.org/ontology#hasCollaborator> <" + guillaume.getId() + "> }  " +
            "}");

        // this query should return instances of type Project
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, Project.class);

        projectCopy = (Project)query.getSingleResult();
        assertNotNull(project);
        assertEquals(project.getCollaborators().size(), 1);
        assertEquals(project.getCollaborators().get(0), guillaume);

        // Remove the users
        aManager.remove(liam);
        aManager.remove(guillaume);
    }


    @Test
    public void defaultProjectProgressTest() {
        ProjectProgress projectProgress = project.getProjectProgress();
        assertTrue(projectProgress.isProjectCreated() == 1);
        assertTrue(projectProgress.isGeneralIssueDefined() == 0);
        assertTrue(projectProgress.isNormativeGoalDefined() == 0);
        assertTrue(projectProgress.isDoAtLeastFourIssuesExist() == 0);
        assertTrue(projectProgress.isAreIssuesRanked() == 0);
        assertTrue(projectProgress.isAreAllDomainsCoveredByIssues() == 0);
        assertTrue(projectProgress.isDoAtLeastFourIndicatorsExist() == 0);
        assertTrue(projectProgress.isAreAllIssuesMeasuredByAtLeastOneIndicator() == 0);
        assertTrue(projectProgress.isHaveImpactsBetweenIssuesBeenAnalysed() == 0);
        assertTrue(projectProgress.isReportCompiled() == 0);
        assertTrue(projectProgress.isResponseDevelopedAndMonitored() == 0);
        assertTrue(projectProgress.isModelReviewedAndAdapted() == 0);
    }




	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
        // Remove the project
        aManager.remove(project);
	}

}
