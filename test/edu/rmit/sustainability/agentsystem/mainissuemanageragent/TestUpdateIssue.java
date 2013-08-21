/**
 * 
 */
package edu.rmit.sustainability.agentsystem.mainissuemanageragent;


import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.intendico.gorite.Goal;

import edu.rmit.sustainability.agentsystem.AgentTestUtil;

/**
 * @author Ke Sun
 * This set of test is not defined yet, due to incomplete 
 * goal implementation
 */
public class TestUpdateIssue {

	Goal modifyGeneralIssue;


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		Vector<Goal> temp = AgentTestUtil.getAllGoalsFromMainIssueManagerAgent();
		for (Goal goal : temp)
		{
			if (goal.name.equals("modifyGeneralIssue"))
				modifyGeneralIssue = goal;
		}
	}

	@Test
	public void modifyGeneralIssue_Goal()
	{
		if (modifyGeneralIssue != null)
		{
			
		}
	}
	
	@Test
	public void modifyNormativeGoal_Goal()
	{
		
	}
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
