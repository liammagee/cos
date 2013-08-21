/**
 * ##############################################################
 * Don't edit this file! It is generated via the GORITE Model Tool,
 * and it is likely to be over-written again. Thus, you should
 * rather make any changes via the Tool, and re-generate.
 * ##############################################################
 */

package edu.rmit.sustainability.agentsystem.agents;

import edu.rmit.sustainability.agentsystem.capabilities.ProvideGeneralIssueSuggestions;
import edu.rmit.sustainability.agentsystem.capabilities.ProvideNormativeGoalSuggestions;
import edu.rmit.sustainability.data.*;
import edu.rmit.sustainability.model.*;
import edu.rmit.sustainability.messaging.*;
import edu.rmit.sustainability.messaging.feedback.*;
import edu.rmit.sustainability.agentsystem.AgentSystem;

//---- The following are the default imports
import com.intendico.data.*;
import com.intendico.gorite.*;
import com.intendico.gorite.addon.*;
import static com.intendico.gorite.Goal.States.*;

/**
 * This is the Main Issue Manager Agent. It handles General Issue and
 * Normative Goal percepts, process them, and provide suggestions when it
 * finds out some of them are relevants. Inherits from Performer.
 */
public class MainIssueManagerAgent extends Performer {
    ProvideGeneralIssueSuggestions provideGeneralIssueSugestionsCap = null; //---- Capability ProvideGeneralIssueSuggestions
    ProvideNormativeGoalSuggestions provideNormativeGoalSuggestionsCap = null; //---- Capability ProvideNormativeGoalSuggestions
    Plan handleProjectPlan = null; //---- Plan HandleProject
    Plan handleGeneralIssuePlan = null; //---- Plan HandleGeneralIssue
    Plan handleNormativeGoalPlan = null; //---- Plan HandleNormativeGoal
    /**
     * AgentSystem _agentSystem: A reference to the Agent System containing this Agent
     */
    private AgentSystem _agentSystem;
    { //---- qua Capability
        //---- Capabilities
        addCapability( provideGeneralIssueSugestionsCap = new ProvideGeneralIssueSuggestions() );
        addCapability( provideNormativeGoalSuggestionsCap = new ProvideNormativeGoalSuggestions() );
        /**
         * This plans creates a new General Issue in the Database. It triggers
         * another plan responsible of finding suggestions using this new General
         * Issue.
         */
        addGoal( handleProjectPlan = new Plan( "HandleProject", new Goal [] {
            new Goal( "handleProject" ) {
                public States execute(Data data) {
                   Data percept = (Data)(data.getValue("percept"));
                   data.setValue("user", (User)(percept.getValue("user")));
                   data.setValue("project", (Project)(percept.getValue("project")));
                   data.setValue("percept", (Project)(percept.getValue("percept")));
                
                   // Test whether the action is post-persist or post-merge.
                   Integer action = (Integer)(((Project)data.getValue("percept")).getLastPerformedAction());
                   if (action != AbstractEmpireModel.ACT_POSTMERGE && action != AbstractEmpireModel.ACT_POSTPERSIST)
                	   return (FAILED);
                   
                   try {
                      MessageBusFactory.createMessageBus().sendMessage((User)data.getValue("user"), (Project)data.getValue("project"), new Message(new Notification("Project Recieved")));
                   }
                   catch (MessagingException me) {
                      System.err.println(me.getMessage());
                   }
                   return (PASSED);
                }//---- End of Goal handleProject
            },
            new ConditionGoal( "checkGeneralIssueMissing", new Goal [] {
                new Goal( "checkGeneralIssueMissing" ) {
                    public States execute(Data data) {
                       if (data.getValue("project") != null)
                       {
                          String gi = ((Project)data.getValue("project")).getGeneralIssue();
                          if (gi == null || gi.equals(""))
                             return (FAILED);
                       }
                       return (PASSED);
                    }
                //---- End of Goal checkGeneralIssueMissing
                },
                new BDIGoal( "EGeneralIssueMissing" )
            } ),
            new ConditionGoal( "checkGeneralIssueChanged", new Goal [] {
                new Goal( "checkGeneralIssueChanged" ) {
                    public States execute(Data data) {
                       System.out.println("OLD GI: " + _agentSystem.getProject().getGeneralIssue());
                       if (data.getValue("project") != null)
                       {
                          String gi = ((Project)data.getValue("project")).getGeneralIssue();
                          if (gi != null && !(gi.equals("")) && !(gi.equals(_agentSystem.getProject().getGeneralIssue())))
                             return (FAILED);
                       }
                       return (PASSED);
                    }
                //---- End of Goal checkGeneralIssueChanged
                },
                new BDIGoal( "EGeneralIssueChanged" )
            } ),
            new ConditionGoal( "checkNormativeGoalMissing", new Goal [] {
                new Goal( "checkNormativeGoalMissing" ) {
                    public States execute(Data data) {
                       if (data.getValue("project") != null)
                       {
                          String ng = ((Project)data.getValue("project")).getNormativeGoal();
                          if (ng == null || ng.equals(""))
                             return (FAILED);
                       }
                       return (PASSED);
                    }
                //---- End of Goal checkNormativeGoalMissing
                },
                new BDIGoal( "ENormativeGoalMissing" )
            } ),
            new ConditionGoal( "checkNormativeGoalChanged", new Goal [] {
                new Goal( "checkNormativeGoalChanged" ) {
                    public States execute(Data data) {
                       System.out.println("OLD NG: " + _agentSystem.getProject().getNormativeGoal());
                       if (data.getValue("project") != null)
                       {
                          String ng = ((Project)data.getValue("project")).getNormativeGoal();
                          if (ng != null && !(ng.equals("")) && !(ng.equals(_agentSystem.getProject().getNormativeGoal())))
                             return (FAILED);
                       }
                       return (PASSED);
                    }
                //---- End of Goal checkNormativeGoalChanged
                },
                new BDIGoal( "ENormativeGoalChanged" )
            } )
        } ) );
        
        /**
         * This plans creates a new General Issue in the Database. It triggers
         * another plan responsible of finding suggestions using this new General
         * Issue.
         */
        addGoal( handleGeneralIssuePlan = new Plan( "HandleGeneralIssue", new Goal [] {
            new Goal( "handleGeneralIssue" ) {
                public States execute(Data data) {
                   System.out.println("General Issue Recieved");
                   Project project = (Project)data.getValue("project");
                   try {
                      MessageBusFactory.createMessageBus().sendMessage((User)data.getValue("user"), project, new Message(new Notification("General Issue Recieved")));
                   }
                   catch (MessagingException me) {
                      System.err.println(me.getMessage());
                   }
                   return (PASSED);
                }//---- End of Goal handleGeneralIssue
            },
            new BDIGoal( "EGeneralIssueChanged" )
        } ) );
        
        /**
         * This plans creates a new General Issue in the Database. It triggers
         * another plan responsible of finding suggestions using this new General
         * Issue.
         */
        addGoal( handleNormativeGoalPlan = new Plan( "HandleNormativeGoal", new Goal [] {
            new Goal( "handleNormativeGoal" ) {
                public States execute(Data data) {
                   System.out.println("Normative Goal Recieved");
                   Project project = (Project)data.getValue("project");
                   try {
                      MessageBusFactory.createMessageBus().sendMessage((User)data.getValue("user"), project, new Message(new Notification("Normative Goal Recieved")));
                   }
                   catch (MessagingException me) {
                      System.err.println(me.getMessage());
                   }
                   return (PASSED);
                }//---- End of Goal handleNormativeGoal
            },
            new BDIGoal( "ENormativeGoalChanged" )
        } ) );
        
    }

    public MainIssueManagerAgent(String name) {
        super( name );
    }
    
    public MainIssueManagerAgent(String name, AgentSystem agentSystem) {
        super( name );
        this._agentSystem = agentSystem;
    }
}
