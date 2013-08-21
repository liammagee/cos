package edu.rmit.sustainability.agentsystem;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.rmit.sustainability.agentsystem.mainissuemanageragent.TestHandleIssue;
import edu.rmit.sustainability.agentsystem.mainissuemanageragent.TestProvideIssueSuggestion;
import edu.rmit.sustainability.agentsystem.mainissuemanageragent.TestUpdateIssue;
import edu.rmit.sustainability.agentsystem.systemstatusmanageragent.TestHandleSleepSignal;

	@RunWith(Suite.class)
	@Suite.SuiteClasses(
	{
		TestHandleIssue.class,
		TestProvideIssueSuggestion.class,
		TestUpdateIssue.class,
		TestHandleSleepSignal.class
	})

	public class AllTests {}
