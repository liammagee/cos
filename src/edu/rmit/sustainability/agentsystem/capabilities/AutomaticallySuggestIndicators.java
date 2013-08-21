/**
 * ##############################################################
 * Don't edit this file! It is generated via the GORITE Model Tool,
 * and it is likely to be over-written again. Thus, you should
 * rather make any changes via the Tool, and re-generate.
 * ##############################################################
 */

package edu.rmit.sustainability.agentsystem.capabilities;


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
public class AutomaticallySuggestIndicators extends Capability {
    Plan searchIndicatorSuggestionsCIKnownPlan = null; //---- Plan SearchIndicatorSuggestionsCIKnown
    Plan searchIndicatorSuggestionsGIKnownPlan = null; //---- Plan SearchIndicatorSuggestionsGIKnown
    Plan searchIndicatorSuggestionsNoInfoPlan = null; //---- Plan SearchIndicatorSuggestionsNoInfo
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
        addGoal( searchIndicatorSuggestionsCIKnownPlan = new Plan( "SearchIndicatorSuggestionsCIKnown", new Goal [] {
            new Goal( "searchIndicatorSuggestionsCIKnown" ) {
                public States execute(Data data) {
                     System.out.println("PLAN: Search Indicator Suggestion CI known");
                    return PASSED;
                }
            //---- End of Goal searchIndicatorSuggestionsCIKnown
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
        addGoal( searchIndicatorSuggestionsGIKnownPlan = new Plan( "SearchIndicatorSuggestionsGIKnown", new Goal [] {
            new Goal( "searchIndicatorSuggestionsGIKnown" ) {
                public States execute(Data data) {
                     System.out.println("PLAN: Search Indicator Suggestion GI known");
                    return PASSED;
                }
            //---- End of Goal searchIndicatorSuggestionsGIKnown
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
        addGoal( searchIndicatorSuggestionsNoInfoPlan = new Plan( "SearchIndicatorSuggestionsNoInfo", new Goal [] {
            new Goal( "searchIndicatorSuggestionsNoInfo" ) {
                public States execute(Data data) {
                     System.out.println("PLAN: Search Indicator Suggestion nothing known");
                    return PASSED;
                }
            //---- End of Goal searchIndicatorSuggestionsNoInfo
            }
        } ) );
        
    }

}
