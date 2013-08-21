/**
 * ##############################################################
 * Don't edit this file! It is generated via the GORITE Model Tool,
 * and it is likely to be over-written again. Thus, you should
 * rather make any changes via the Tool, and re-generate.
 * ##############################################################
 */

package edu.rmit.sustainability.agentsystem.capabilities;

import edu.rmit.sustainability.messaging.*;
import edu.rmit.sustainability.messaging.feedback.*;
import edu.rmit.sustainability.model.*;
import edu.rmit.sustainability.agentinterface.AgentInterface;

//---- The following are the default imports
import com.intendico.data.*;
import com.intendico.gorite.*;
import com.intendico.gorite.addon.*;
import static com.intendico.gorite.Goal.States.*;

/**
 * This is a Capability Type Element. This node and its sub tree contain
 * a definition of a GORITE Capability Type, which essentially is a class
 * extending the Capability class.  The node label contains the
 * capability type name, which also is used as the name of the generated
 * class, and therefore it needs to be a simple Java identifier
 * (excluding Java keywords).  The description text is available for
 * element documentation.
 */
public class DefineCriticalIssues extends Capability {
    Plan handleCriticalIssuePlan = null; //---- Plan HandleCriticalIssue
    Plan setCriticalIssueObjectivePlan = null; //---- Plan SetCriticalIssueObjective
    { //---- qua Capability
        //---- Capabilities
        /**
         * This is a Plan Element. This node and its sub tree contain a
         * definition of a particular GORITE Plan instance, which essentially is
         * an instantiation of the Plan class or a class extending it.  The node
         * label contains the goal name, which can be anything in a Java string
         * constant (in particular, any double-quote must be preceeded by a
         * back-slash).  The description text is available for element
         * documentation.
         */
        addGoal( handleCriticalIssuePlan = new Plan( "HandleCriticalIssue", new Goal [] {
            new Goal( "handleCriticalIssue" ) {
                public States execute(Data data) {
                   Data percept = (Data)data.getValue("percept");
                   User user = (User)percept.getValue("user");
                   Project project = (Project)percept.getValue("project");
                   CriticalIssue issue = (CriticalIssue)percept.getValue("percept");
                   Integer action = (Integer)(((CriticalIssue)percept.getValue("percept")).getLastPerformedAction());
                   
                   data.setValue("user", user);
                   data.setValue("project", project);
                   data.setValue("percept", issue);
                
                   // If the action is 'post-load', look for suggestion for this Issue
                   if (action == AbstractEmpireModel.ACT_POSTLOAD)
                      AgentInterface.getInstance().sendData(project, user, new IndicatorSuggestionRequest(issue));
                
                   // If the action isn't post-persist or post-merge, do nothing
                   if (action != AbstractEmpireModel.ACT_POSTMERGE && action != AbstractEmpireModel.ACT_POSTPERSIST)
                      return (FAILED);
                
                   try {
                      MessageBusFactory.createMessageBus().sendMessage(
                         (User)data.getValue("user"), 
                         (Project)data.getValue("project"), 
                         new Message(new Notification("Critical Issue Recieved")));
                   }
                   catch (MessagingException me) {
                      System.err.println(me.getMessage());
                   }
                   return (PASSED);
                }//---- End of Goal handleCriticalIssue
            },
            new BDIGoal( "ECriticalIssueChanged" ),
            new BDIGoal( "AHPProcess" )
        } ) );
        
        /**
         * This is a Plan Element. This node and its sub tree contain a
         * definition of a particular GORITE Plan instance, which essentially is
         * an instantiation of the Plan class or a class extending it.  The node
         * label contains the goal name, which can be anything in a Java string
         * constant (in particular, any double-quote must be preceeded by a
         * back-slash).  The description text is available for element
         * documentation.
         */
        addGoal( setCriticalIssueObjectivePlan = new Plan( "SetCriticalIssueObjective", new Goal [] {
            new Goal( "setCriticalIssueObjective" ) {
                public States execute(Data data) {
                   Data percept = (Data)data.getValue("percept");
                   data.setValue("user", (User)percept.getValue("user"));
                   data.setValue("project", (Project)percept.getValue("project"));
                   data.setValue("percept", (Objective)percept.getValue("percept"));
                
                   try {
                      MessageBusFactory.createMessageBus().sendMessage(
                         (User)data.getValue("user"), 
                         (Project)data.getValue("project"), 
                         new Message(new Notification("Objective Recieved")));
                   }
                   catch (MessagingException me) {
                      System.err.println(me.getMessage());
                   }
                   return (PASSED);
                }//---- End of Goal setCriticalIssueObjective
            },
            new BDIGoal( "ECriticalIssueChanged" )
        } ) );
        
        addCapability( new ProvideCriticalIssueSuggestions() );
    }

}
