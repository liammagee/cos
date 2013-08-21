package edu.rmit.sustainability.agentinterface;
/***********************************************************************
 * Module:  AgentSystemSleepTimerTask.java
 * Author:  Guillaume Prevost
 * Purpose: Defines the Class AgentSystemSleepTimerTask, inherited from TimerTask
 * 			Make the associated Agent System going to sleep when triggered
 ***********************************************************************/

import edu.rmit.sustainability.agentsystem.AgentSystem;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class AgentInterface
 * This class extends the {@link TimerTask}. It allows add a Timer onto each
 * {@link AgentSystem}. When the timer reaches its end, the {@link AgentSystem}
 * is automatically put 'asleep'. After having notified it to let it eventually
 * save data, the thread is stopped.
 * <p/>
 * This way, when no one is working on a project, the related {@link AgentSystem}
 * is deleted.
 *
 * @author Guillaume Prevost
 * @since 10/11/2010
 */
public class AgentSystemSleepTimerTask extends TimerTask {

    /**
     * The {@link Timer} to which this {@link TimerTask} is related
     */
    private Timer _timer;

    /**
     * A reference to the {@link AgentInterface}, in order to delete agent
     * systems from the list of 'awake' agent systems.
     */
    private AgentInterface _agentInterface;

    /**
     * The {@link AgentSystem} to which this sleep timer task is related.
     */
    private AgentSystem _agentSystem;

    /**
     * Constructor of AgentSystemSleepTimerTask
     */
    public AgentSystemSleepTimerTask(Timer timer, AgentInterface agentInterface, AgentSystem agentSystem) {
        _timer = timer;
        _agentInterface = agentInterface;
        _agentSystem = agentSystem;
    }

    /**
     * Method run
     * Method triggered when a {@link Timer} reaches its end. Make the related
     * {@link AgentSystem} 'go to sleep': After having notified it to let it
     * eventually save data, the thread is stopped, and the {@link AgentSystem}
     * is removed from the list of awake agent systems in the {@link AgentInterface}.
     */
    @Override
    public void run() {
        System.out.println("Agent System for project " + _agentSystem.getProject().getProjectName() + " is inactive...");

        // Send a percept to save the current state
        _agentSystem.sleep();

        // Kill the Agent System thread (check with Ralph how do that) ?

        // Cancel the Timer for this Agent System
        _timer.cancel();

        // Remove from the list of awake agent systems
        _agentInterface.getAwakeAgents().remove(_agentSystem.getProject().getRdfId());
    }

}
