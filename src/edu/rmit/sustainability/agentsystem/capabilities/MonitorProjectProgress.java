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
import edu.rmit.sustainability.util.ProjectProgressUtil;

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
public class MonitorProjectProgress extends Capability {
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
        addGoal( new Plan( "HandleMonitorProjectProgressRequest", new Goal [] {
            new Goal( "HandleMonitorProjectProgress" ) {
                public States execute(Data data) {
                   Data d = (Data) data.getValue("percept");
                   Project p = (Project) d.getValue("project");
                   User u = (User) d.getValue("user");
                   System.out.println("Monitoring progress...");
                   System.out.println("is "+p.getProjectName()+" for "+u.getUsername());
                                   
//                   try {
//                           MessageBusFactory.createMessageBus().sendMessage(
//                            u,p, 
//                            new Message(new Notification("Monitoring...")));
                           //for temp monioring effect before the code gen done
                           
                           //check GI
                           if (ProjectProgressUtil.isGIDefined(p))
                           {
                        	   p.getProjectProgress().setGeneralIssueDefined(1);
                           }
                           else
                           {
                        	   p.getProjectProgress().setGeneralIssueDefined(0);
                           }
                           if (ProjectProgressUtil.isNGDefined(p))
                           {
                        	   p.getProjectProgress().setNormativeGoalDefined(1);
                           }
                           else
                           {
                        	   p.getProjectProgress().setNormativeGoalDefined(0);
                           }
                        if (ProjectProgressUtil.hasAssessment(p))
                        {
                            p.getProjectProgress().setHasInitialAssessmentBeenConducted(1);
                        }
                        else
                        {
                            p.getProjectProgress().setDoAtLeastFourIssuesExist(0);
                        }
                    if (ProjectProgressUtil.isSufficentIssues(p,4))
                           {
                        	   p.getProjectProgress().setDoAtLeastFourIssuesExist(1);
                           }
                           else
                           {
                        	   p.getProjectProgress().setDoAtLeastFourIssuesExist(0);
                           }

                           
//                          }
//                   catch (MessagingException me) {
//                          System.out.println(me.getMessage());
//                    }
                    return PASSED;
                }
            //---- End of Goal HandleMonitorProjectProgress
            },
            new BDIGoal( "EEvaluateDefineProjectProcess" ),
            new BDIGoal( "EEvaluateDefineIssueProcess" ),
            new BDIGoal( "EEvaluateDefineIndicatorProcess" )
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
        addGoal( new Plan( "EEvaluateDefineProjectProcess", new Goal [] {
            new Goal( "CheckGeneralIssue" ) {
                public States execute(Data data) {
                    System.out.println("Checking generalIssue...");
                    return PASSED;
                }
            //---- End of Goal CheckGeneralIssue
            }
        } ) {
            public Query context(Data data) {          
               return Context.TRUE;
            //   reutrn Context.FALSE;
            }//---- End of Plan EEvaluateDefineProjectProcess
        } );
        
        /**
         * This is a Plan Element. This node and its sub tree contain a
         * definition of a particular GORITE Plan instance, which essentially is
         * an instantiation of the Plan class or a class extending it.  The node
         * label contains the goal name, which can be anything in a Java string
         * constant (in particular, any double-quote must be preceeded by a
         * back-slash).  The description text is available for element
         * documentation.
         */
        addGoal( new Plan( "EEvaluateDefineProjectProcess", new Goal [] {
            new Goal( "CheckNormativeGoal" ) {
                public States execute(Data data) {
                    System.out.println("Checking normative goal...");
                    return PASSED;
                }
            //---- End of Goal CheckNormativeGoal
            }
        } ) {
            public Query context(Data data) {          
               return Context.TRUE;
             //  reutrn Context.FALSE;
            }//---- End of Plan EEvaluateDefineProjectProcess
        } );
        
    }

}
