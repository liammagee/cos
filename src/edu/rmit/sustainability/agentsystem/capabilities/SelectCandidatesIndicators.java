/**
 * ##############################################################
 * Don't edit this file! It is generated via the GORITE Model Tool,
 * and it is likely to be over-written again. Thus, you should
 * rather make any changes via the Tool, and re-generate.
 * ##############################################################
 */

package edu.rmit.sustainability.agentsystem.capabilities;

import edu.rmit.sustainability.agentsystem.capabilities.ProvideIndicatorsSuggestions;
import edu.rmit.sustainability.agentinterface.AgentInterface;
import edu.rmit.sustainability.model.*;
import edu.rmit.sustainability.messaging.*;
import edu.rmit.sustainability.messaging.feedback.*;

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
public class SelectCandidatesIndicators extends Capability {
    Plan handleIndicatorPlan = null; //---- Plan HandleIndicator
    ProvideIndicatorsSuggestions provideIndicatorsSuggestionsCap = null; //---- Capability ProvideIndicatorsSuggestions
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
        addGoal( handleIndicatorPlan = new Plan( "HandleIndicator", new Goal [] {
            new Goal( "handleIndicator" ) {
                public States execute(Data data) {
                   Data percept = (Data)data.getValue("percept");
                
                   Project project = (Project)percept.getValue("project");
                   User user = (User)percept.getValue("user");
                   Indicator ind = (Indicator)percept.getValue("percept");
                   Integer action = (Integer)(((Indicator)percept.getValue("percept")).getLastPerformedAction());
                
                   data.setValue("project", project);
                   data.setValue("user", user);
                   data.setValue("percept", ind);
                
                   // If indicator just created or updated
                   if (action == AbstractEmpireModel.ACT_POSTPERSIST || action == AbstractEmpireModel.ACT_POSTMERGE)
                      AgentInterface.getInstance().sendData(project, user, new IndicatorSuggestionRequest(ind));
                
                   try {
                      MessageBusFactory.createMessageBus().sendMessage(
                         (User)data.getValue("user"), 
                         (Project)data.getValue("project"), 
                         new Message(new Notification("Indicator Recieved")));
                   }
                   catch (MessagingException me) {
                      System.err.println(me.getMessage());
                   }
                   return (FAILED);
                }
            //---- End of Goal handleIndicator
            }
        } ) );
        
        addCapability( provideIndicatorsSuggestionsCap = new ProvideIndicatorsSuggestions() );
    }

}
