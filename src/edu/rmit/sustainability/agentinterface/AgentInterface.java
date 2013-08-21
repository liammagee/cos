	package edu.rmit.sustainability.agentinterface;
/***********************************************************************
 * Module:  AgentInterface.java
 * Author:  Guillaume Prevost
 * Purpose: Defines the Class AgentInterface
 ***********************************************************************/

import com.clarkparsia.empire.SupportsRdfId;
import com.intendico.gorite.Data;
import com.intendico.gorite.Executor;
import edu.rmit.sustainability.agentsystem.AgentSystem;
import edu.rmit.sustainability.messaging.Message;
import edu.rmit.sustainability.messaging.MessageBusFactory;
import edu.rmit.sustainability.messaging.MessagingException;
import edu.rmit.sustainability.messaging.feedback.SimpleSuggestion;
import edu.rmit.sustainability.model.Indicator;
import edu.rmit.sustainability.model.IndicatorSuggestionRequest;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class AgentInterface
 * This class is an interface between the Web Server and the {@link AgentSystem}
 * part. It manages the different agent systems, instantiating them as needed,
 * and transmit the data sent by users to the right agent system.
 * <p/>
 * Each project has one associated {@link AgentSystem}, common for the different
 * users working on a same project.
 * <p/>
 * It implements the design pattern Singleton in order to use a common
 * {@link AgentInterface} object in the entire application.
 *
 * @author Guillaume Prevost
 * @since 10/11/2010
 */
public class AgentInterface {

    /**
     * Unique instance of the {@link AgentInterface}
     */
    private static AgentInterface _instance = null;

    /**
     * Keeps track of the projects for which the associated {@link AgentSystem} is 'awake'
     */
    private HashMap<SupportsRdfId.RdfKey, AgentSystem> _awakeAgents;

    /**
     * Keeps track of the users working on a project
     */
    private HashMap<SupportsRdfId.RdfKey, ArrayList<User>> _usersWorking;

    /**
     * Constructor of AgentInterface
     * The constructor is private, according to the implementation of the
     * Singleton design pattern.
     */
    private AgentInterface() {
        _awakeAgents = new HashMap<SupportsRdfId.RdfKey, AgentSystem>();
    }

    /**
     * Method getInstance()
     * Static method returning a unique instance of {@link AgentInterface}, part
     * of the Singleton design pattern implementation.
     *
     * @return {@link AgentInterface}: a unique instance of {@link AgentInterface}
     */
    public static AgentInterface getInstance() {
        if (_instance == null)
            _instance = new AgentInterface();
        return (_instance);
    }

    /**
     * Method getAwakeAgents
     * Return the HashMap associating projects to Agent Systems
     *
     * @return HashMap<Integer, {@link AgentSystem}> awakeAgents: the HashMap associating projects to Agent Systems
     */
    public HashMap<SupportsRdfId.RdfKey, AgentSystem> getAwakeAgents() {
        return (_awakeAgents);
    }

    /**
     * Method sendData
     * This method let the user generically send data (called percept in the
     * agent-oriented paradigm) to the {@link AgentSystem}. It wraps the
     * {@link Project}, the {@link User} who sent the percept, and the percept
     * itself in a {@link Data} object (defined in the Gorite Framework).
     * <p/>
     * If the {@link AgentSystem} couldn't handle the type of Object sent, an
     * exception is caught an an error message displayed.
     *
     * @param {@link Project} project: the project the data has to be sent to
     * @param {@link User} user: the user sending the data
     * @param Object data: any object needed to be perceived by the Agent System
     * @return boolean: whether the data had been successfully sent or not
     */
    public boolean sendData(Project project, User user, Object percept) {
        // Get the Agent system attached to the project
        AgentSystem agentSystem = awakeAgentSystem(project, user);

        Data data = new Data();
        data.setValue("project", project);
        data.setValue("user", user);
        data.setValue("percept", percept);

        try {
            agentSystem.perceive(data);
        } catch (InvalidObjectException e) {
            System.err.println("error: unknown percept '" + e.getMessage() + "'");
        }
        return (true);
    }


    /**
     * Method selectProjectRequest
     * This method let a user define its current working project, activating the associated {@link AgentSystem}.
     * It also notify all the other users currently working on the project that a new user started working on it.
     *
     * @param {@link Project} project: the project to select
     * @param {@link User} user: the user selecting the project
     * @return boolean success: whether the operation have succeeded or not
     */
    public boolean selectProjectRequest(Project project, User user) {
        // Get the Agent system attached to the project
        awakeAgentSystem(project, user);

        Indicator waterConsumption = new Indicator();
        waterConsumption.setIdentifyingCode("waterconsumption");
        waterConsumption.setName("Water Consumption");
        waterConsumption.setDescription("The total water a consumption this year (in m3)");
        waterConsumption.setDomain(DummyKB.economyDomain);
        waterConsumption.setSubdomain(DummyKB.consumptionLeisure);
        waterConsumption.setIndicatorSet(DummyKB.indSetGRI);
        AgentInterface.getInstance().sendData(project, user, new IndicatorSuggestionRequest(waterConsumption));

        // Send a notification to all the other users working on this project
        System.out.println("User '" + user.getUsername() + "' selecting project '" + project.getId() + "'");
        try {
            // Look for the project
            Iterator<SupportsRdfId.RdfKey> it = _usersWorking.keySet().iterator();
            while (it.hasNext()) {
                SupportsRdfId.RdfKey id = it.next();
                if (id.equals(project.getRdfId())) {
                    // Iterate all the users currently working on the project
                    Iterator<User> itUsers = _usersWorking.get(id).iterator();
                    while (itUsers.hasNext()) {
                        MessageBusFactory.createMessageBus().sendMessage(itUsers.next(), project, new Message(new SimpleSuggestion("Another user (" + user.getUsername() + ") is also working on the project !")));
                    }
                }
            }
        } catch (MessagingException me) {
            System.out.println(me.getMessage());
        }
        return (true);
    }

    /**
     * Method awakeAgentSystem
     * Check if the agent system related to given the project is 'awake'. Instantiate it if it's not.
     *
     * @param int projectID: the ID of the project
     * @param int userID: the ID of the user requesting
     * @return {@link AgentSystem} agent: the agent system responsible of the given project
     */
    private AgentSystem awakeAgentSystem(Project project, User user) {
        AgentSystem agentSystem = null;

        if (_awakeAgents.containsKey(project.getRdfId()))
            agentSystem = _awakeAgents.get(project.getRdfId());
        else {
            System.out.println("Agent System for project " + project.getProjectName() + " created");
            agentSystem = new AgentSystem(project, user);
            new Thread(Executor.getDefaultExecutor(), "GORITE-" + project.getRdfId()).start();
            // Notifying the Agent System that it is now enabled
            agentSystem.awake();
            _awakeAgents.put(project.getRdfId(), agentSystem);
        }

        // Reset the timer of this Agent System
        agentSystem.resetTimer(this);
        return (agentSystem);
    }
}
