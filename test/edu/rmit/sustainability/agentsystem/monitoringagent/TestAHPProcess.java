/**
 * 
 */
package edu.rmit.sustainability.agentsystem.monitoringagent;


import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.rmit.sustainability.agentinterface.AgentInterface;
import edu.rmit.sustainability.model.CriticalIssue;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;
import edu.rmit.sustainability.model.ahp.AHP;
import edu.rmit.sustainability.model.ahp.Criterion;

/**
 * @author Ke Sun
 *
 */
public class TestAHPProcess {

	AgentInterface agentSystem;
	Project p;
	User u;
	CriticalIssue ci;
	AHP ahp;
	
	ArrayList<Criterion> criterion = new ArrayList<Criterion>();
	ArrayList<CriticalIssue> cis = new ArrayList<CriticalIssue>();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		this.agentSystem = AgentInterface.getInstance();
		p = new Project("AHP Test");
		u = new User("KeSun","1234");
		ci = new CriticalIssue();
        ci.setName("CI Name");
        ci.setDescription("CI Description");
        
        p.addCriticalIssue(new CriticalIssue("CriticalIssue1","C1 Description in CriticalIssue",1));
        p.addCriticalIssue(new CriticalIssue("CriticalIssue1","C1 Description in CriticalIssue",2));
        p.addCriticalIssue(new CriticalIssue("CriticalIssue1","C1 Description in CriticalIssue",3));
        p.addCriticalIssue(new CriticalIssue("CriticalIssue1","C1 Description in CriticalIssue",4));
        p.addCriticalIssue(new CriticalIssue("CriticalIssue1","C1 Description in CriticalIssue",5));
        
        //populate the criterion and critical issues
        cis.addAll(p.getCriticalIssues());
        
        
        criterion.add(new Criterion("Criterion1","C1 Description in Criterion",2));
        criterion.add(new Criterion("Criterion2","C2 Description in Criterion",5));
        criterion.add(new Criterion("Criterion3","C3 Description in Criterion",7));
        criterion.add(new Criterion("Criterion4","C4 Description in Criterion",8));
        criterion.add(new Criterion("Criterion5","C5 Description in Criterion",1));
        
        ahp = new AHP(criterion,cis);
        
        
        
        
		
	}
	
	@Test
	public void testBasicAHP()
	{
		this.agentSystem.sendData(p, u, ahp);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
