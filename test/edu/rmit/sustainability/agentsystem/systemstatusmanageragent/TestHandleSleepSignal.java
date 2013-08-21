/**
 * 
 */
package edu.rmit.sustainability.agentsystem.systemstatusmanageragent;


import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.intendico.gorite.Data;
import com.intendico.gorite.Goal;
import com.intendico.gorite.Goal.States;
import com.intendico.gorite.ParallelEndException;

import edu.rmit.sustainability.agentsystem.AgentTestUtil;

/**
 * @author Ke Sun
 *
 */
public class TestHandleSleepSignal {

	Goal handleSleepSignal;
	Data data;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		data = new Data();
		data.setValue("sleep", true);
		
		Vector<Goal> temp = AgentTestUtil.getAllGoalsFromSystemStatusManagerAgent();
		
		for (Goal goal : temp)
		{
			if (goal.name.equals("HandleSleepSignal"))
				handleSleepSignal = goal;
		}
		
	}

	
	/**
	 * Test HandleSleepSignal goal
	 */
	@Test
	public void handleSleepSignal()
	{
		States value = null;
		try {
			
			if (handleSleepSignal != null)
			{
				//ask agent to sleep
				value = handleSleepSignal.execute(data);
				assertTrue(value.equals(States.PASSED));
				
				//ask agent to wake up
				data.setValue("sleep", false);
				value = handleSleepSignal.execute(data);
				assertTrue(value.equals(States.PASSED));
			}
			else
				fail("Unexpected Problem!");
			
			
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParallelEndException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
