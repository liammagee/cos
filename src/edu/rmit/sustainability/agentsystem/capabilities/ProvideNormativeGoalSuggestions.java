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

//---- The following are the default imports
import com.intendico.data.*;
import com.intendico.gorite.*;
import com.intendico.gorite.addon.*;
import static com.intendico.gorite.Goal.States.*;

/**
 * This capability is allows the main issue manager agent to provide
 * relevant suggestions about the general issue and/or normative goal.
 */
public class ProvideNormativeGoalSuggestions extends Capability {
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
        addGoal( new Plan( "ENormativeGoalChanged", new Goal [] {
            new Goal( "lookForNormativeGoalSuggestions" ) {
                public States execute(Data data) {
                   // TODO: Query the Database to find related Normative Goals to suggest
                   try {
                      MessageBusFactory.createMessageBus().sendMessage((User)data.getValue("user"), (Project)data.getValue("project"), new Message(new Notification("Looking for related Normative Goals to suggest")));
                   }
                   catch (MessagingException me) {
                      System.out.println(me.getMessage());
                   }
                   return (PASSED);
                }
            //---- End of Goal lookForNormativeGoalSuggestions
            }
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
        addGoal( new Plan( "ENormativeGoalMissing", new Goal [] {
            new Goal( "LookForExistingNormativeGoal" ) {
                public States execute(Data data) {
                   // TODO: Query the Database to find existing Normative Goals to suggest
                   try {
                      MessageBusFactory.createMessageBus().sendMessage((User)data.getValue("user"), (Project)data.getValue("project"), new Message(new Notification("Looking for existing Normative Goals to suggest")));
                   }
                   catch (MessagingException me) {
                      System.out.println(me.getMessage());
                   }
                   return (PASSED);
                }
            //---- End of Goal LookForExistingNormativeGoal
            }
        } ) );
        
    }

}
