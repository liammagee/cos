/**
 * ##############################################################
 * Don't edit this file! It is generated via the GORITE Model Tool,
 * and it is likely to be over-written again. Thus, you should
 * rather make any changes via the Tool, and re-generate.
 * ##############################################################
 */

package edu.rmit.sustainability.agentsystem.agents;

import edu.rmit.sustainability.messaging.*;
import edu.rmit.sustainability.messaging.feedback.*;
import edu.rmit.sustainability.agentsystem.capabilities.*;

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
public class MonitoringAgent extends Performer {
    { //---- qua Capability
        //---- Capabilities
        addCapability( new AHPProcess() );
        addCapability( new MonitorProjectProgress() );
    }

    public MonitoringAgent(String name) {
        super( name );
    }

}
