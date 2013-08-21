package edu.rmit.sustainability.agentsystem.mainissuemanageragent;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.intendico.gorite.Data;
import com.intendico.gorite.Goal;
import com.intendico.gorite.ParallelEndException;
import com.intendico.gorite.Goal.States;

import edu.rmit.sustainability.agentsystem.AgentTestUtil;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

/**
 * @author Ke Sun
 * This set of test is not defined yet, due to incomplete 
 * goal implementation
 */
public class TestProvideIssueSuggestion {

	Data dataWithProjectValue;
	
	Vector<Goal> goals = new Vector<Goal>();
	Goal lookForSuggestions;
	
	@Before
	public void setUp() throws Exception {
		
		//Prepare basic data
		User user = new User("Guillaume", "1234");
		
		Project project = new Project("Young wellbeing");
		project.setGeneralIssue("water shortage");
		project.setNormativeGoal("reduce water useage");
		
		dataWithProjectValue = new Data();
		dataWithProjectValue.setValue("project", project);
		dataWithProjectValue.setValue("user", user);
		
		//find target goal
		
		Vector<Goal> temp = AgentTestUtil.getAllGoalsFromMainIssueManagerAgent();
		
		for (Goal goal : temp)
		{
			if (goal.name.equals("LookForSuggestions"))
				lookForSuggestions = goal;
//				goals.add(goal);
		}
		
	}
	
	@Test
	public void lookForSugestion_Goal()
	{
		States value = null;
		try {
			
			//test with data value
			//expect the plan Success
			if (lookForSuggestions != null)
			{
				value = lookForSuggestions.execute(dataWithProjectValue);
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

	
	@After
	public void tearDown() throws Exception {
	}

}
