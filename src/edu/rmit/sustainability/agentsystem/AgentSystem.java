/**
 * ##############################################################
 * Don't edit this file! It is generated via the GORITE Model Tool,
 * and it is likely to be over-written again. Thus, you should
 * rather make any changes via the Tool, and re-generate.
 * ##############################################################
 */

package edu.rmit.sustainability.agentsystem;

import edu.rmit.sustainability.agentsystem.agents.*;
import edu.rmit.sustainability.model.*;
import java.util.Timer;
import edu.rmit.sustainability.agentinterface.*;
import java.util.HashMap;
import java.io.InvalidObjectException;

//---- The following are the default imports
import com.intendico.data.*;
import com.intendico.gorite.*;
import com.intendico.gorite.addon.*;
import static com.intendico.gorite.Goal.States.*;

/**
 * This is a Model Platform element. It represents the Agent system
 * corresponding to one project using circles of sustainability.
 */
public class AgentSystem {
    MainIssueManagerAgent mainIssueManagerAgent = null; //---- MainIssueManagerAgent mainIssueManager
    SystemStatusManagerAgent systemStatusManagerAgent = null; //---- SystemStatusManagerAgent systemStatusManager
    DetailsSpecificationsAgent detailsSpecificationsAgent = null; //---- DetailsSpecificationsAgent detailSpecificationsManager
    MonitoringAgent monitoringAgent = null; //---- MonitoringAgent monitoringAgent
    Perceptor projectPerceptor = null; //---- Perceptor projectPerceptor
    Perceptor sleepSignalPerceptor = null; //---- Perceptor sleepSignalPerceptor
    Perceptor criticalIssuePerceptor = null; //---- Perceptor criticalIssuePerceptor
    Perceptor objectivePerceptor = null; //---- Perceptor objectivePerceptor
    Perceptor indicatorPerceptor = null; //---- Perceptor indicatorPerceptor
    Perceptor indicatorQueryPerceptor = null; //---- Perceptor indicatorQueryPerceptor
    Perceptor indicatorSuggestionRequestPerceptor = null; //---- Perceptor indicatorSuggestionRequestPerceptor
    Perceptor stopSuggestionsSignalPerceptor = null; //---- Perceptor stopSuggestionsSignalPerceptor
    Perceptor indicatorLinkPerceptor = null; //---- Perceptor indicatorLinkPerceptor
    Perceptor ahpPerceptor = null; //---- Perceptor ahpPerceptor
    Perceptor progressMonitorRequestPerceptor = null; //---- Perceptor progressMonitorRequestPerceptor
    /**
     * int project: The project to which the Agent System is related
     */
     private HashMap<String, Perceptor> _perceptors;
    
    /**
     * int project: The project to which the Agent System is related
     */
    private Project _project;
    
    /**
     * int user: The creator of the project to which this Agent System is related
     */
    private User _user;
    
    /**
     * Timer timer: if this timer reach the end of ots time, the agent system will go asleep
     */
    private Timer _timer;

    /**
    * Constructor of AgentSystem
    * @author Guillaume Prevost
    * @param Project project: the project to which the agent system is linked
    * @param User user: the creator of the project
    */
    public AgentSystem(Project project, User user)
    {
       this._project = project;
       this._user = user;
        	
       /* TODO: find a way to make this generic, in function of the existing classes in the package 'model' */
       this._perceptors = new HashMap<String, Perceptor>();
       _perceptors.put("Project", projectPerceptor);
       _perceptors.put("CriticalIssue", criticalIssuePerceptor);
       _perceptors.put("Objective", objectivePerceptor);
       _perceptors.put("Indicator", indicatorPerceptor);
       _perceptors.put("IndicatorQuery", indicatorQueryPerceptor);
       _perceptors.put("IndicatorSuggestionRequest", indicatorSuggestionRequestPerceptor);
       _perceptors.put("IndicatorLink", indicatorLinkPerceptor);
       _perceptors.put("AHP", ahpPerceptor);
       _perceptors.put("ProgressMonitorRequest",progressMonitorRequestPerceptor);
    }

                /**
                * Method perceive: chooses which perceptor needs to be used in function of the 
                * class of the 'percept' Object. If the class is String, it will compare the 
                * content of the String as a class name instead of the class String itself.
                *  
                * This method is a generic way of making the Agent System perceiving the different 
                * type of percepts of the model. The allowed percepts are defined in the constructor
                * of AgentSystem.
                * 
                * Note: some percepts which have only an impact on the Agent System behavior (eg: 
                * sleepSignal) have some methods directly implemented (eg: for sleepSignal, the 
                * methods sleep() and awake()) to make the Agent System perceiving them.
                * 
                * @author Guillaume Prevost
                * @param Object percept: the data which should be perceived by the Agent System
                * @throws InvalidObjectException: raised when the type of percept cannot be handled
                *  by the Agent System
                */
        public void perceive(Data data)  throws InvalidObjectException {
            	Object percept = data.getValue("percept");
    	String perceptClassName = percept.getClass().getSimpleName();
            	
            	// If the percept received is a String
            	if (perceptClassName.equals("String"))
            		perceptClassName = (String)percept;
            	
            	// If this is a perceivable percept
            	if (_perceptors.containsKey(perceptClassName))
            	{
            		System.out.println("Agent System: perceiving a '" + perceptClassName + "'");
            		_perceptors.get(perceptClassName).perceive(data);
            	}
            	else
            		throw new InvalidObjectException(perceptClassName);
        }
    
        /**
        * Method sleep: send a percept through the 'sleepSignalPerceptor' making
        * the Agent System realize it will get asleep (eventually saving its 
        * current state).
        * @author Guillaume Prevost
        */
    public void sleep()
    {
    	Data data = new Data();
    	data.setValue("sleep", true);
    	this.sleepSignalPerceptor.perceive("percepts", data);
    }
    
        /**
         * Method awake: send a percept through the 'sleepSignalPerceptor' making
         * the Agent System realize it just got awake (eventually needs to restore
         * its previous state).
         * @author Guillaume Prevost
         */
    public void awake()
    {
    	Data data = new Data();
    	data.setValue("sleep", false);
    	this.sleepSignalPerceptor.perceive("percepts", data);
    }
    
        /**
        * Method stopSuggestions: send a percept through the 'stopSuggestionsSignalPerceptor' 
        * making the agent stop generating automatically suggestions to the user.
        * @author Guillaume Prevost
        */
    public void stopSuggestions()
    {
    	Data data = new Data();
    	data.setValue("stop", true);
    	this.stopSuggestionsSignalPerceptor.perceive("percepts", data);
    }
    
        /**
         * Method startSuggestions: send a percept through the 'stopSuggestionsSignalPerceptor' 
         * making the agent start generating automatically suggestions to the user.
         * @author Guillaume Prevost
         */
    public void startSuggestions()
    {
    	Data data = new Data();
    	data.setValue("stop", false);
    	this.stopSuggestionsSignalPerceptor.perceive("percepts", data);
    }

    /**
    * Method getUser
    * @author Guillaume Prevost
    * @return int user: The creator of the project to which this Agent System is related
    */
    public User getUser()
    {
       return (this._user);
    }
    
    /**
    * Method setUser
    * @author Guillaume Prevost
    */
    public void setUser(User user)
    {
       this._user = user;
    }
    
    /**
    * Method getProject
    * @author Guillaume Prevost
    * @return int project: The project to which the Agent System is related
    */
    public Project getProject()
    {
       return (this._project);
    }
    
    /**
    * Method setProject
    * @author Guillaume Prevost
    */
    public void setProject(Project project)
    {
       this._project = project;
    }
    
    /**
    * Method getTimer
    * @author Guillaume Prevost
    * @return Timer: the current Timer attached to the Agent System
    */
    public Timer getTimer()
    {
       return (this._timer);
    }

    /**
    * Method resetTimer: reset the timer attached to the Agent System
    * @author Guillaume Prevost
    * @param AgentInterface: the agent interface
    */
    public void resetTimer(AgentInterface agentInterface)
    {
    	if (this._timer != null)
    	this._timer.cancel();
    	this._timer = new Timer();
    	this._timer.scheduleAtFixedRate(new AgentSystemSleepTimerTask(this._timer, agentInterface, this), AGENT_SYSTEM_AWAKE_TIME, AGENT_SYSTEM_AWAKE_TIME); 
    }

    /**
     * The time (in milliseconds) an Agent System stays awake without receiving a user request.
     * Default: 5000 * 3600 ms = 5h
     */
    public static final int AGENT_SYSTEM_AWAKE_TIME = 5000 * 3600;

    { //---- GORITE Model Platform setup
        //---- Initialisations 
        /**
         * The Agent responsible of the management of the General Issue and
         * Normative Goal of the project.
         */
        mainIssueManagerAgent = new MainIssueManagerAgent( "mainIssueManager",  this );
        /**
         * This is a Performer Element. This node and its sub tree contain a
         * definition of a particular GORITE Performer instance, which
         * essentially is an instantiation of the Performer class or a class
         * extending it (e.g., a Performer Type).  The node label contains the
         * performer's name, which can be anything in a Java string constant (in
         * particular, any double-quote must be preceeded by a back-slash).  The
         * description text is available for element documentation.
         */
        systemStatusManagerAgent = new SystemStatusManagerAgent( "systemStatusManager" );
        /**
         * This is a Performer Element. This node and its sub tree contain a
         * definition of a particular GORITE Performer instance, which
         * essentially is an instantiation of the Performer class or a class
         * extending it (e.g., a Performer Type).  The node label contains the
         * performer's name, which can be anything in a Java string constant (in
         * particular, any double-quote must be preceeded by a back-slash).  The
         * description text is available for element documentation.
         */
        detailsSpecificationsAgent = new DetailsSpecificationsAgent( "detailSpecificationsManager" );
        /**
         * This is a Performer Element. This node and its sub tree contain a
         * definition of a particular GORITE Performer instance, which
         * essentially is an instantiation of the Performer class or a class
         * extending it (e.g., a Performer Type).  The node label contains the
         * performer's name, which can be anything in a Java string constant (in
         * particular, any double-quote must be preceeded by a back-slash).  The
         * description text is available for element documentation.
         */
        monitoringAgent = new MonitoringAgent( "monitoringAgent" );
        /**
         * This is a Perceptor Element. This node and its sub tree contain a
         * definition of a particular GORITE Perceptor instance, which
         * essentially is an instantiation of the Perceptor class or a class
         * extending it.  The node label is available for element documentation.
         * The description text is available for element documentation.
         */
        projectPerceptor = new Perceptor( mainIssueManagerAgent, "HandleProject", "percepts" );
        /**
         * This is a Perceptor Element. This node and its sub tree contain a
         * definition of a particular GORITE Perceptor instance, which
         * essentially is an instantiation of the Perceptor class or a class
         * extending it.  The node label is available for element documentation.
         * The description text is available for element documentation.
         */
        sleepSignalPerceptor = new Perceptor( systemStatusManagerAgent, "HandleSleepSignal", "percepts" );
        /**
         * This is a Perceptor Element. This node and its sub tree contain a
         * definition of a particular GORITE Perceptor instance, which
         * essentially is an instantiation of the Perceptor class or a class
         * extending it.  The node label is available for element documentation.
         * The description text is available for element documentation.
         */
        criticalIssuePerceptor = new Perceptor( detailsSpecificationsAgent, "HandleCriticalIssue", "percepts" );
        /**
         * This is a Perceptor Element. This node and its sub tree contain a
         * definition of a particular GORITE Perceptor instance, which
         * essentially is an instantiation of the Perceptor class or a class
         * extending it.  The node label is available for element documentation.
         * The description text is available for element documentation.
         */
        objectivePerceptor = new Perceptor( detailsSpecificationsAgent, "SetCriticalIssueObjective", "percepts" );
        /**
         * This is a Perceptor Element. This node and its sub tree contain a
         * definition of a particular GORITE Perceptor instance, which
         * essentially is an instantiation of the Perceptor class or a class
         * extending it.  The node label is available for element documentation.
         * The description text is available for element documentation.
         */
        indicatorPerceptor = new Perceptor( detailsSpecificationsAgent, "HandleIndicator", "percepts" );
        /**
         * This is a Perceptor Element. This node and its sub tree contain a
         * definition of a particular GORITE Perceptor instance, which
         * essentially is an instantiation of the Perceptor class or a class
         * extending it.  The node label is available for element documentation.
         * The description text is available for element documentation.
         */
        indicatorQueryPerceptor = new Perceptor( detailsSpecificationsAgent, "BrowseIndicatorKB", "percepts" );
        /**
         * This is a Perceptor Element. This node and its sub tree contain a
         * definition of a particular GORITE Perceptor instance, which
         * essentially is an instantiation of the Perceptor class or a class
         * extending it.  The node label is available for element documentation.
         * The description text is available for element documentation.
         */
        indicatorSuggestionRequestPerceptor = new Perceptor( detailsSpecificationsAgent, "HandleIndicatorSuggestionRequest", "percepts" );
        /**
         * This is a Perceptor Element. This node and its sub tree contain a
         * definition of a particular GORITE Perceptor instance, which
         * essentially is an instantiation of the Perceptor class or a class
         * extending it.  The node label is available for element documentation.
         * The description text is available for element documentation.
         */
        stopSuggestionsSignalPerceptor = new Perceptor( detailsSpecificationsAgent, "StopAutomatedSuggestions", "percepts" );
        /**
         * This is a Perceptor Element. This node and its sub tree contain a
         * definition of a particular GORITE Perceptor instance, which
         * essentially is an instantiation of the Perceptor class or a class
         * extending it.  The node label is available for element documentation.
         * The description text is available for element documentation.
         */
        indicatorLinkPerceptor = new Perceptor( detailsSpecificationsAgent, "HandleIndicatorLink", "percepts" );
        /**
         * This is a Perceptor Element. This node and its sub tree contain a
         * definition of a particular GORITE Perceptor instance, which
         * essentially is an instantiation of the Perceptor class or a class
         * extending it.  The node label is available for element documentation.
         * The description text is available for element documentation.
         */
        ahpPerceptor = new Perceptor( monitoringAgent, "HandleAHP", "percepts" );
        /**
         * This is a Perceptor Element. This node and its sub tree contain a
         * definition of a particular GORITE Perceptor instance, which
         * essentially is an instantiation of the Perceptor class or a class
         * extending it.  The node label is available for element documentation.
         * The description text is available for element documentation.
         */
        progressMonitorRequestPerceptor = new Perceptor( monitoringAgent, "HandleMonitorProjectProgressRequest", "percepts" );
    } //---- End of GORITE Model Platform setup
    

    /**
     * Application entry method (generated)
     */
    public static void main(String [] args) {
        AgentSystem model = new AgentSystem(new Project("projectTestName"), new User("userTestID", "userTestName"));;
        //---- Starting executor thread 
        new Thread( Executor.getDefaultExecutor(), "GORITE" ).start();
        //---- Triggers 
    }

}
