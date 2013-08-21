/**
 * ##############################################################
 * Don't edit this file! It is generated via the GORITE Model Tool,
 * and it is likely to be over-written again. Thus, you should
 * rather make any changes via the Tool, and re-generate.
 * ##############################################################
 */

package edu.rmit.sustainability.agentsystem.agents;

import edu.rmit.sustainability.agentsystem.capabilities.ManageDomains;
import edu.rmit.sustainability.agentsystem.capabilities.DefineCriticalIssues;
import edu.rmit.sustainability.agentsystem.capabilities.SelectCandidatesIndicators;
import edu.rmit.sustainability.agentsystem.capabilities.LinkIndicators;
import edu.rmit.sustainability.messaging.*;
import edu.rmit.sustainability.messaging.feedback.*;

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
public class DetailsSpecificationsAgent extends Performer {
    Plan browseIndicatorKBPlan = null; //---- Plan BrowseIndicatorKB
    Plan relateIndicatorToObjectivePlan = null; //---- Plan RelateIndicatorToObjective
    ManageDomains manageDomainsCap = null; //---- Capability ManageDomains
    DefineCriticalIssues defineCriticalIssuesCap = null; //---- Capability DefineCriticalIssues
    SelectCandidatesIndicators selectCandidatesIndicatorsCap = null; //---- Capability SelectCandidatesIndicators
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
        addGoal( browseIndicatorKBPlan = new Plan( "BrowseIndicatorKB", new Goal [] {
            new Goal( "browseIndicatorKB" ) {
                public States execute(Data data) {
                     System.out.println("PLAN: Browse Knowledge Base");
                     return (PASSED);
                }
            //---- End of Goal browseIndicatorKB
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
        addGoal( relateIndicatorToObjectivePlan = new Plan( "RelateIndicatorToObjective", new Goal [] {
            new Goal( "relateIndicatorToObjective" ) {
                public States execute(Data data) {
                     System.out.println("PLAN: Relate Indicator to Objectives");
                    return PASSED;
                }
            //---- End of Goal relateIndicatorToObjective
            }
        } ) );
        
        addCapability( manageDomainsCap = new ManageDomains() );
        addCapability( defineCriticalIssuesCap = new DefineCriticalIssues() );
        addCapability( selectCandidatesIndicatorsCap = new SelectCandidatesIndicators() );
    }

    public DetailsSpecificationsAgent(String name) {
        super( name );
    }

}
