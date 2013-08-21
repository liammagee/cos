/**
 * ##############################################################
 * Don't edit this file! It is generated via the GORITE Model Tool,
 * and it is likely to be over-written again. Thus, you should
 * rather make any changes via the Tool, and re-generate.
 * ##############################################################
 */

package edu.rmit.sustainability.agentsystem.capabilities;

import edu.rmit.sustainability.messaging.*;
import edu.rmit.sustainability.model.ahp.*;
import edu.rmit.sustainability.model.*;
import edu.rmit.sustainability.messaging.feedback.*;
import edu.rmit.sustainability.data.*;

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
public class AHPProcess extends Capability {
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
        addGoal( new Plan( "HandleAHP", new Goal [] {
            new Goal( "AHPProcess" ) {
                public States execute(Data data){
                   System.out.println("AHP Process starting");
                   return (PASSED);
                }
            //---- End of Goal AHPProcess
            },
            new BDIGoal( "EValidateAHPRequirement" ),
            new BDIGoal( "EAdjustPairwiseComparison" )
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
        addGoal( new Plan( "EValidateAHPRequirement", new Goal [] {
            new Goal( "CheckNumberOfIssues" ) {
                                public States execute(Data data) {
                                   System.out.println("Checking the number of issues...");
                                   Data d = (Data) data.getValue("percept");
                                   Project p = (Project)d.getValue("project");
                                   User u = (User)d.getValue("user");
                                   AHP ahp = (AHP) d.getValue("percept");
                                   
                                   int numberOfIssues = 0;
                                   numberOfIssues = p.getCriticalIssues().size();
                                   String msg = "number of issues: ";
                                   
                                   msg += numberOfIssues;
                                   if (numberOfIssues >= 4)
                                   {
                                	   System.out.println("The AHP process can start.");
                                	   System.out.println(msg);
                //                	   System.out.println(ahp.toString());
                                	   
                                	   System.out.println(ahp.getIssues().size());
                                	   System.out.println(ahp.getCriteria().size());
                                	   
                //                	   Map<CriticalIssue, Double> rate = ahp.getAggregatePrincipalEigenvector();
                //                	   System.out.println(rate);
                                	   
                                	   //presist the AHP data
                //                	   ModelProxy modelProxy = new ModelProxy();
                //                	   modelProxy.put(ahp);
                //                	   EntityManager entityManager = null;
                //                	   try {
                //               			entityManager = EmpireSDB2ManagerFactory.createEmpireEntityManager((new File("./war/WEB-INF").getCanonicalPath()) + "\\examples.empire.config.properties");
                //               		} catch (IOException e) {
                //               			e.printStackTrace();
                //               		}
                //               		   entityManager.persist(ahp);
                                	   System.out.println("AHP data model presisted");
                                   }
                                   else
                                   {
                                	   System.out.println("AHP process requires at least 4 issues been identified.");
                                   }
                //                   try {
                //                       MessageBusFactory.createMessageBus().sendMessage(
                //                          u, 
                //                          p, 
                //                          new Message(new Notification(msg)));
                //                    }
                //                    catch (MessagingException me) {
                //                       System.err.println(me.getMessage());
                //                    }
                //                    catch (Exception e)
                //                    {
                //                    	e.printStackTrace();
                //                    }
                                    
                                    return PASSED;
                                }//---- End of Goal CheckNumberOfIssues
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
        addGoal( new Plan( "EAdjustPairwiseComparison", new Goal [] {
            new Goal( "AdjustPairwiseComparisonMatrix" ) {
                                public States execute(Data data) {
                                   System.out.println("Adject the pariwise....");
                                   Boolean changed = false;
                                   //compare the old, checking if changed.
                                   
                                   
                                   if (changed)
                                   {
                                	   System.out.println("Apply changes to the pariwise matrix");
                                   }
                                   else
                                   {
                                	   System.out.println("Ranking remains the same.");
                                   }
                                   
                                   return PASSED;
                                }//---- End of Goal AdjustPairwiseComparisonMatrix
            }
        } ) {
            public Query context(Data data) {            
              //check number of the issues
              Data d = (Data) data.getValue("percept");
              Project p = (Project) d.getValue("project");
              System.out.println(p.projectName);
                        	
               if (p.getCriticalIssues().size() < 4)
               {
                    return (Context.FALSE);
               }
                return Context.TRUE;
            }//---- End of Plan EAdjustPairwiseComparison
        } );
        
    }

}
