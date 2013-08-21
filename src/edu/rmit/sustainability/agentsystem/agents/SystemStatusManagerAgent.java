/**
 * ##############################################################
 * Don't edit this file! It is generated via the GORITE Model Tool,
 * and it is likely to be over-written again. Thus, you should
 * rather make any changes via the Tool, and re-generate.
 * ##############################################################
 */

package edu.rmit.sustainability.agentsystem.agents;


//---- The following are the default imports
import com.intendico.data.*;
import com.intendico.gorite.*;
import com.intendico.gorite.addon.*;
import static com.intendico.gorite.Goal.States.*;

/**
 * This is a Performer Type Element. This node and its sub tree contain a
 * definition of a GORITE Performer Type, which essentially is a class
 * extending the Performer class.  The node label contains the performer
 * type name, which also is used as the name of the generated class, and
 * therefore it needs to be a simple Java identifier (excluding Java
 * keywords).  The description text is available for element
 * documentation.
 */
public class SystemStatusManagerAgent extends Performer {
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
        addGoal( new Plan( "HandleSleepSignal", new Goal [] {
            new Goal( "HandleAwakeSignal" ) {
                public States execute(Data data) {
                   try{
                      if (((Boolean)data.getValue("sleep")) == true)
                         System.out.println("Agent System: Going to Sleep");
                      else
                         System.out.println("Agent System: Is now Awake");
                   }
                   catch (Exception e) { return (FAILED); }
                      return (PASSED);
                }
            //---- End of Goal HandleAwakeSignal
            }
        } ) );
        
    }

    public SystemStatusManagerAgent(String name) {
        super( name );
    }

}
