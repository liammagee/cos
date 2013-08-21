/**
 * 
 */
package edu.rmit.sustainability.agentsystem;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.intendico.gorite.Goal;

import edu.rmit.sustainability.agentsystem.agents.DetailsSpecificationsAgent;
import edu.rmit.sustainability.agentsystem.agents.MainIssueManagerAgent;
import edu.rmit.sustainability.agentsystem.agents.SystemStatusManagerAgent;

/**
 * @author Ke Sun
 *
 */
public class AgentTestUtil {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector<Goal> getAllGoalsFromDetailsSpecificationsAgent()
	{
		Vector<Goal> rootGoals = new Vector<Goal>();
		Vector<Goal> goalList = new Vector<Goal>();
		
		
		DetailsSpecificationsAgent agent = new DetailsSpecificationsAgent("project name");
		
//		System.out.println("MainIssueManagerAgent agent has "+agent.goals.size() + " goals");
		Hashtable goals = agent.goals;
		Enumeration keys = agent.goals.keys();
		//process root goals;
		while (keys.hasMoreElements())
		{
			Vector<Goal> temp = (Vector<Goal>) (goals.get(keys.nextElement()));
			for (Goal g : temp)
			{
				rootGoals.add(g);
			}
		}
//		System.out.println("Root Goal"+rootGoals.size());
		
		
		for (Goal g : rootGoals)
		{
			processSubGoal(g, goalList);
		}
		
		return goalList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector<Goal> getAllGoalsFromSystemStatusManagerAgent()
	{
		Vector<Goal> rootGoals = new Vector<Goal>();
		Vector<Goal> goalList = new Vector<Goal>();
		
		
		SystemStatusManagerAgent agent = new SystemStatusManagerAgent("project name");
		
//		System.out.println("MainIssueManagerAgent agent has "+agent.goals.size() + " goals");
		Hashtable goals = agent.goals;
		Enumeration keys = agent.goals.keys();
		//process root goals;
		while (keys.hasMoreElements())
		{
			Vector<Goal> temp = (Vector<Goal>) (goals.get(keys.nextElement()));
			for (Goal g : temp)
			{
				rootGoals.add(g);
			}
		}
//		System.out.println("Root Goal"+rootGoals.size());
		
		
		for (Goal g : rootGoals)
		{
			processSubGoal(g, goalList);
		}
		
		return goalList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector<Goal> getAllGoalsFromMainIssueManagerAgent()
	{
		Vector<Goal> rootGoals = new Vector<Goal>();
		Vector<Goal> goalList = new Vector<Goal>();
		
		
		MainIssueManagerAgent agent = new MainIssueManagerAgent("project name");
		
//		System.out.println("MainIssueManagerAgent agent has "+agent.goals.size() + " goals");
		Hashtable goals = agent.goals;
		Enumeration keys = agent.goals.keys();
		//process root goals;
		while (keys.hasMoreElements())
		{
			Vector<Goal> temp = (Vector<Goal>) (goals.get(keys.nextElement()));
			for (Goal g : temp)
			{
				rootGoals.add(g);
			}
		}
//		System.out.println("Root Goal"+rootGoals.size());
		
		
		for (Goal g : rootGoals)
		{
			processSubGoal(g, goalList);
		}
		
		return goalList;
	}
	
	public static void processSubGoal(Goal g, Vector<Goal> goalList)
	{
		if (g.subgoals == null)
			goalList.add(g);
		else
			for (Goal g1 : g.subgoals)
				processSubGoal(g1,goalList);
	}
}
