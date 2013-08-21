/**
 * 
 */
package edu.rmit.sustainability.agentsystem.mainissuemanageragent;


import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.intendico.gorite.Data;
import com.intendico.gorite.Goal;
import com.intendico.gorite.Goal.States;
import com.intendico.gorite.ParallelEndException;

import edu.rmit.sustainability.agentsystem.AgentTestUtil;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

/**
 * @author Ke Sun
 *
 */
public class TestHandleIssue {

	Project project;
	Project projectWithValue;
	
	Data dataWithValue;
	Data dataWithoutValue;
	Data dataWithProjectValue;
	
	Vector<Goal> goals = new Vector<Goal>();
	
	Goal storeProjectPlan;
	Goal handleGeneralIssue;
	Goal handleNormativeGoal;
	Goal handleProjectCheckGeneralIssue;
	Goal handleProjectCheckNormativeGoal;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		
		//Prepare basic data
		User user = new User("KeSun", "1234");
		
		project = new Project("Young wellbeing");
		projectWithValue = new Project("Young wellbeing");
		
		dataWithValue = new Data();
		dataWithValue.setValue("project", project);
		dataWithValue.setValue("user", user);
		
		dataWithProjectValue = new Data();
		projectWithValue.setGeneralIssue("water shortage");
		projectWithValue.setNormativeGoal("reduce water useage");
		dataWithProjectValue.setValue("project", projectWithValue);
		dataWithProjectValue.setValue("user",user);
		
		dataWithoutValue = new Data();
		
		
		
		//find target goal
		
		Vector<Goal> temp = AgentTestUtil.getAllGoalsFromMainIssueManagerAgent();
		
		for (Goal goal : temp)
		{
			if (goal.name.equals("StoreProjectPlan"))
				storeProjectPlan = goal;
//				goals.add(goal);
			else if (goal.name.equals("handleGeneralIssue"))
				handleGeneralIssue = goal;
			else if (goal.name.equals("handleNormativeGoal"))
				handleNormativeGoal = goal;
			else if (goal.name.equals("HandleProjectCheckGeneralIssue"))
				handleProjectCheckGeneralIssue = goal;
			else if (goal.name.equals("HandleProjectCheckNormativeGoal"))
				handleProjectCheckNormativeGoal = goal;
		}
		
	}
	
	@Test
	public void storeProject_Goal()
	{
		States value = null;
		try {
			
			//test with data value
			//expect the plan Success
			if (storeProjectPlan != null)
			{
				value = storeProjectPlan.execute(dataWithValue);
			}
			
			assertTrue(value.equals(States.PASSED));
			
			//test without data value
			//expect the plan Fail
			if (storeProjectPlan != null)
			{
				value = storeProjectPlan.execute(dataWithoutValue);
			}
			
			assertTrue(value.equals(States.FAILED));
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParallelEndException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			if (e instanceof SQLException)
				fail("Critical: Database error!");
			fail("Critical: Other error!");
		}
	}
	
	/**
	 * HandleGeneralIssue is part of the conditional goal
	 * HandleProjectGeneralIssue, it handles the alternative
	 * condition, so it always pass.
	 */
	@Test
	public void handleGeneralIssue_ConditionalGoal()
	{
		States value = null;
		try {
			//test with general issue
			//expect always be success goal
			
			project.setGeneralIssue("water");//apply general issue
			
			if (handleGeneralIssue != null)
			{
				value = handleGeneralIssue.execute(dataWithValue);
			}
			
			assertTrue(value.equals(States.PASSED));
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParallelEndException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			fail("Critical: Other exception detected!");
		}
	}
	
	@Test
	public void handleNormativeGoal_ConditionalGoal()
	{
		States value = null;
		try {

			
			//test with general issue
			//expect always be success goal
			
			project.setNormativeGoal("Save water");//apply normative goal
			
			if (storeProjectPlan != null)
			{
				value = handleNormativeGoal.execute(dataWithValue);
			}
			
			assertTrue(value.equals(States.PASSED));
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParallelEndException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
			fail("Critical: Other exception detected!");
		}
	}

	@Test
	public void handleProjectCheckGeneralIssue_ConditionalGoal()
	{
		States value = null;
		try {

			//test without general issue
			//expect the plan Success when no value supplied
			if (handleProjectCheckGeneralIssue != null)
			{
				value = handleProjectCheckGeneralIssue.execute(dataWithValue);
			}
			
			assertTrue(value.equals(States.PASSED));
			
			//test with general issue
			//expect the plan Fail when value present
			
			project.setGeneralIssue("water");
			dataWithValue.setValue("project", project);
			
			if (handleProjectCheckGeneralIssue != null)
			{
				value = handleProjectCheckGeneralIssue.execute(dataWithProjectValue);
			}
			
			assertTrue(value.equals(States.FAILED));
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParallelEndException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			fail("Critical: Other exception detected!");
		}
	}
	
	@Test
	public void handleProjectCheckNormativeGoal_ConditionalGoal()
	{
		States value = null;
		try {

			//test without general issue
			//expect the plan Success when no value supplied
			if (handleProjectCheckNormativeGoal != null)
			{
				value = handleProjectCheckNormativeGoal.execute(dataWithValue);
			}
			
			assertTrue(value.equals(States.PASSED));
			
			//test with general issue
			//expect the plan Success when value present
			
			project.setNormativeGoal("Save water");//apply normative goal
			
			if (handleProjectCheckNormativeGoal != null)
			{
				value = handleProjectCheckNormativeGoal.execute(dataWithProjectValue);
			}
			
			assertTrue(value.equals(States.FAILED));
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParallelEndException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			fail("Critical: Other exception detected!");
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
