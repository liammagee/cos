/**
 * ##############################################################
 * Don't edit this file! It is generated via the GORITE Model Tool,
 * and it is likely to be over-written again. Thus, you should
 * rather make any changes via the Tool, and re-generate.
 * ##############################################################
 */

package edu.rmit.sustainability.agentsystem.capabilities;

import edu.rmit.sustainability.agentsystem.capabilities.AutomaticallySuggestIndicators;
import edu.rmit.sustainability.model.*;
import java.util.*;
import edu.rmit.sustainability.messaging.*;
import edu.rmit.sustainability.messaging.feedback.*;
import edu.rmit.sustainability.data.query.*;
import edu.rmit.sustainability.util.Utils;
import java.util.Map.Entry;

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
public class ProvideIndicatorsSuggestions extends Capability {
    Relation bel_candidateIndicatorSuggestions = null; //---- Relation CandidateIndicatorSuggestions
    Plan handleIndicatorSuggestionRequestPlan = null; //---- Plan HandleIndicatorSuggestionRequest
    AutomaticallySuggestIndicators automaticallySuggestIndicatorsCap = null; //---- Capability AutomaticallySuggestIndicators
    Plan sortFilterSuggestionsPlan = null; //---- Plan ESortFilterSuggestions
    private static final int MAX_INDICATOR_SUGGESTIONS = 5;
    { //---- qua Capability
        //---- Beliefs
        /**
         * This is a Relation Element. This node and its sub tree contain a
         * definition of a particular GORITE Relation instance, which essentially
         * is an instantiation of the Relation class or a class extending it.
         * The node label contains the relation name, which can be anything in a
         * Java string constant (in particular, any double-quote must be
         * preceeded by a back-slash).  The description text is available for
         * element documentation.
         */
        bel_candidateIndicatorSuggestions = new Relation( "CandidateIndicatorSuggestions", 1 );
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
        addGoal( handleIndicatorSuggestionRequestPlan = new Plan( "HandleIndicatorSuggestionRequest", new Goal [] {
            new ConditionGoal( "ExtractRequestInfo", new Goal [] {
                new Goal( "ExtractIndicatorToFocus" ) {
                    public States execute(Data data) {
                        try {
                          Data percept = (Data)data.getValue("percept");
                          Indicator ind = ((IndicatorSuggestionRequest)percept.getValue("percept")).getIndicator();
                          if (ind != null) {
                             data.setValue("focus", ind);
                             data.setValue("user", percept.getValue("user"));
                             data.setValue("project", percept.getValue("project"));
                              System.out.println("FOCUS ON INDICATOR");
                          return (PASSED);
                          }
                       }
                       catch (Exception e) {}
                       return (FAILED);
                    }
                //---- End of Goal ExtractIndicatorToFocus
                },
                new Goal( "ExtractCriticalIssueToFocus" ) {
                    public States execute(Data data) {
                        try {
                          Data percept = (Data)data.getValue("percept");
                          CriticalIssue ci = ((IndicatorSuggestionRequest)percept.getValue("percept")).getIssue();
                          if (ci != null) {
                             data.setValue("focus", ci);
                             data.setValue("user", percept.getValue("user"));
                             data.setValue("project", percept.getValue("project"));
                             System.out.println("FOCUS ON ISSUE");
                             return (PASSED);
                          }
                       }
                       catch (Exception e) {}
                       return (FAILED);
                    }
                //---- End of Goal ExtractCriticalIssueToFocus
                }
            } ),
            new BDIGoal( "EDecideResearchMethods" ),
            new BDIGoal( "EIndicatorSuggestionNeeded" ),
            new BDIGoal( "ESortFilterSuggestions" )
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
        addGoal( new Plan( "EDecideResearchMethods", new Goal [] {
            new Goal( "UseAllSearchMethods" ) {
                public States execute(Data data) {
                   System.out.println("DECIDE RESEARCH METHODS: All methods");
                   if (data.getValue("focus") != null && data.getValue("focus") instanceof Indicator) {
                      data.setValue("researchMethod", "keywords");
                      data.setValue("researchMethod", "links");
                      data.setValue("researchMethod", "set");
                   }
                  else if (data.getValue("focus") != null && data.getValue("focus") instanceof CriticalIssue) {
                      data.setValue("researchMethod", "keywords");
                   }
                   return (PASSED);
                }//---- End of Goal UseAllSearchMethods
            }
        } ) {
            public Query context(Data data) {
               return (Context.TRUE);
            }//---- End of Plan EDecideResearchMethods
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
        addGoal( new Plan( "EDecideResearchMethods", new Goal [] {
            new Goal( "UserDefinedSearchMethods" ) {
                public States execute(Data data) {
                    System.out.println("DECIDE RESEARCH METHODS: User Defined methods");
                    return PASSED;
                }
            //---- End of Goal UserDefinedSearchMethods
            }
        } ) {
            public Query context(Data data) {
               return (Context.FALSE);
            }//---- End of Plan EDecideResearchMethods
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
        addGoal( new Plan( "EDecideResearchMethods", new Goal [] {
            new Goal( "AgentDefinedSearchMethods" ) {
                public States execute(Data data) {
                    System.out.println("DECIDE RESEARCH METHODS: Agent defined methods");
                    return PASSED;
                }
            //---- End of Goal AgentDefinedSearchMethods
            }
        } ) {
            public Query context(Data data) {
               return (Context.FALSE);
            }//---- End of Plan EDecideResearchMethods
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
        addGoal( new Plan( "EIndicatorSuggestionNeeded", new Goal [] {
            new SequenceGoal( "searchIndicatorSuggestionsIndFocused", new Goal [] {
                new RepeatGoal( "searchIndicatorsSuggestionsOnCriteria", new Goal [] {
                    new ConditionGoal( "FindIndicatorsOnCriteria", new Goal [] {
                        new BDIGoal( "FindIndicatorSuggestionsIndFocused" ),
                        new Goal( "TRUE" ) {
                            public States execute(Data data) {
                            	return (PASSED);
                            }
                        //---- End of Goal TRUE
                        }
                    } )
                } ) {
                    { control = "researchMethod"; }
                //---- End of RepeatGoal searchIndicatorsSuggestionsOnCriteria
                }
            } )
        } ) {
            public Query context(Data data) {
               if (data.getValue("focus") != null && data.getValue("focus") instanceof Indicator)
                  return (Context.TRUE);
               return (Context.FALSE);
            }//---- End of Plan EIndicatorSuggestionNeeded
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
        addGoal( new Plan( "EIndicatorSuggestionNeeded", new Goal [] {
            new SequenceGoal( "searchIndicatorSuggestionsCIFocused", new Goal [] {
                new RepeatGoal( "searchIndicatorsSuggestionsOnCriteria", new Goal [] {
                    new ConditionGoal( "FindIndicatorsOnCriteria", new Goal [] {
                        new BDIGoal( "FindIndicatorSuggestionsCIFocused" ),
                        new Goal( "TRUE" ) {
                            public States execute(Data data) {
                            	return (PASSED);
                            }
                        //---- End of Goal TRUE
                        }
                    } )
                } ) {
                    { control = "researchMethod"; }
                //---- End of RepeatGoal searchIndicatorsSuggestionsOnCriteria
                }
            } )
        } ) {
            public Query context(Data data) {
               if (data.getValue("focus") != null && data.getValue("focus")  instanceof CriticalIssue)
                  return (Context.TRUE);
               return (Context.FALSE);
            }//---- End of Plan EIndicatorSuggestionNeeded
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
        addGoal( new Plan( "FindIndicatorSuggestionsIndFocused", new Goal [] {
            new Goal( "FindIndicatorsByLink" ) {
                public States execute(Data data) {
                   System.out.println("Search Indicator Suggestions using link to Focused Indicator");
                   // TODO: Implement Indicator suggestion search using link
                   System.out.println("\tSuggestion found: -");
                   data.setValue("searchResults", true);
                   return (PASSED);
                }//---- End of Goal FindIndicatorsByLink
            }
        } ) {
            public Query context(Data data) {
               if (data.getValue("researchMethod").equals("links"))
                  return (Context.TRUE);
               return (Context.FALSE);
            }//---- End of Plan FindIndicatorSuggestionsIndFocused
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
        addGoal( new Plan( "FindIndicatorSuggestionsIndFocused", new Goal [] {
            new Goal( "FindIndicatorsByKeyword" ) {
                public States execute(Data data) {
                   System.out.println("Search Indicators suggestions using keywords");
                                	
                   Indicator focusedInd = (Indicator)(data.getValue("focus"));
                   
                   // Get the indicators containing the keywords from the Knowledge Base
                   List<Indicator> results = new ArrayList<Indicator>();
                   try {
                	   List<String> keywords = Utils.getKeywords(focusedInd.getName());
                       results = (new GetIndicatorsByKeywordsQuery()).doQuery((Project)data.getValue("project"), (User)data.getValue("user"), keywords);
                
                     // Count the occurrence of each result and merge
                      Map<Indicator, Integer> stats = new HashMap<Indicator, Integer>();
                      for(Indicator indicator : results) {
                         Integer count = stats.get(indicator);          
                         stats.put(indicator, (count == null) ? 1 : count + 1);
                      }          			
                      // Create a suggestion for each different result, weighted using the number of occurrence
                      Iterator<Map.Entry<Indicator, Integer>> it = stats.entrySet().iterator();
                      if (it.hasNext()) {
                         while (it.hasNext()) {
                            Map.Entry<Indicator, Integer> pairs = (Map.Entry<Indicator, Integer>)it.next();
                            Indicator indToSuggest = (Indicator)pairs.getKey();
                            int weight = (Integer)pairs.getValue();
                            System.out.println("\tSuggestion found: " + indToSuggest.getName());
                            bel_candidateIndicatorSuggestions.add(new Suggestion("The indicator '" + indToSuggest.getName() + "' may be relevant for '" + focusedInd.getName() + "' since they have similar names.", weight, indToSuggest));
                         }
                         data.setValue("searchResults", true);
                      }
                   } catch (Exception e) {
                      e.printStackTrace();
                   }
                   return (PASSED);
                }
            //---- End of Goal FindIndicatorsByKeyword
            }
        } ) {
            public Query context(Data data) {
               if (data.getValue("researchMethod").equals("keywords"))
                  return (Context.TRUE);
               return (Context.FALSE);
            }//---- End of Plan FindIndicatorSuggestionsIndFocused
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
        addGoal( new Plan( "FindIndicatorSuggestionsIndFocused", new Goal [] {
            new Goal( "FindIndicatorsBySource" ) {
                public States execute(Data data) {
                   System.out.println("Search Indicator Suggestions using common Indicator Source");
                
                   try {
                       Indicator focusedInd = (Indicator)data.getValue("focus");
                       List<Indicator> results = (new GetIndicatorsBySourceQuery()).doQuery((Project)data.getValue("project"), (User)data.getValue("user"), focusedInd.getSource());
                       
                       for (Indicator indToSuggest : results) {
                    	   System.out.println("\tSuggestion found: " + indToSuggest.getName());
                    	   bel_candidateIndicatorSuggestions.add(new Suggestion("The indicator '" + indToSuggest.getName() + "' may be relevant for '" + focusedInd.getName() + "' since they come from the same source.", 1, indToSuggest));
                       }
                   }
                   catch (Exception e) {
                	   e.printStackTrace();
                   }
                   return (PASSED);
                }//---- End of Goal FindIndicatorsBySource
            }
        } ) {
            public Query context(Data data) {
               if (data.getValue("researchMethod").equals("set"))
                  return (Context.TRUE);
               return (Context.FALSE);
            }//---- End of Plan FindIndicatorSuggestionsIndFocused
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
        addGoal( new Plan( "FindIndicatorSuggestionsCIFocused", new Goal [] {
            new Goal( "FindIndicatorsByKeyword" ) {
                public States execute(Data data) {
                       System.out.println("Search Ind Sugg using CI keywords");
                       CriticalIssue focusedCI = (CriticalIssue)data.getValue("focus");
                       
                       // Get the indicators containing the keywords from the Knowledge Base
                       List<Indicator> results = new ArrayList<Indicator>();
                       try {
                    	 List<String> keywords = Utils.getKeywords(focusedCI.getName());
                         results = (new GetIndicatorsByKeywordsQuery()).doQuery((Project)data.getValue("project"), (User)data.getValue("user"), keywords);
                    
                         // Count the occurrence of each result and merge
                          Map<Indicator, Integer> stats = new HashMap<Indicator, Integer>();
                          for(Indicator indicator : results) {
                             Integer count = stats.get(indicator);          
                             stats.put(indicator, (count == null) ? 1 : count + 1);
                          }          			
                          // Create a suggestion for each different result, weighted using the number of occurrence
                          Iterator<Map.Entry<Indicator, Integer>> it = stats.entrySet().iterator();
                          if (it.hasNext()) {
                             while (it.hasNext()) {
                                Map.Entry<Indicator, Integer> pairs = (Map.Entry<Indicator, Integer>)it.next();
                                Indicator indToSuggest = (Indicator)pairs.getKey();
                                int weight = (Integer)pairs.getValue();
                                System.out.println("Suggestion found: " + indToSuggest.getName());
                                bel_candidateIndicatorSuggestions.add(new Suggestion("The indicator '" + indToSuggest.getName() + "' may be relevant for the critical issue '" + focusedCI.getName() + "' since they have similar names.", weight, indToSuggest));
                             }
                             data.setValue("searchResults", true);
                          }
                       } catch (Exception e) {
                          System.err.println("Agent System error: the search of indicators by keywords failed");
                          return(FAILED);
                       }
                       return (PASSED);
                }//---- End of Goal FindIndicatorsByKeyword
            }
        } ) {
            public Query context(Data data) {
               if (data.getValue("researchMethod").equals("keywords")) {
                  return (Context.TRUE);
               }
               return (Context.FALSE);
            }//---- End of Plan FindIndicatorSuggestionsCIFocused
        } );
        
        addCapability( automaticallySuggestIndicatorsCap = new AutomaticallySuggestIndicators() );
        /**
         * This is a Plan Element. This node and its sub tree contain a
         * definition of a particular GORITE Plan instance, which essentially is
         * an instantiation of the Plan class or a class extending it.  The node
         * label contains the goal name, which can be anything in a Java string
         * constant (in particular, any double-quote must be preceeded by a
         * back-slash).  The description text is available for element
         * documentation.
         */
        addGoal( sortFilterSuggestionsPlan = new Plan( "ESortFilterSuggestions", new Goal [] {
            new Goal( "sortFilterSuggestions" ) {
                public States execute(Data data) {
                   System.out.println("PLAN: Sort & Filter Suggestions");
                                                                	
                   // If there are search result, sort, filter & send the suggestions
                   Boolean isResult = (Boolean)data.getValue("searchResults");
                   if (isResult != null && isResult == true)
                   {
                      List<Suggestion> results = new ArrayList<Suggestion>();
                      try {
                         // Get the candidate indicator suggestions
                         Ref ref = new Ref("$IndicatorSuggestions");
                         Query q = new And(new Query[] {
                            bel_candidateIndicatorSuggestions.get(new Object[] {ref})
                         });
                
                         // Fill in a list of all the suggestions found
                         while (q.next()) {
                            if (ref.get() instanceof Suggestion)
                               results.add((Suggestion) ref.get());
                         }
                      } catch (Exception e) {
                         System.err.println("error while retrieving indicator suggestion list");
                         e.printStackTrace();
                      }
                
                      // If the focus is an indicator, removes the focused indicator from the suggestions
                      if (data.getValue("focus") instanceof Indicator)
                         for (int i = 0; i < results.size(); i++)
                            if (((Indicator)results.get(i).getContent()).getRdfId().equals(((Indicator)data.getValue("focus")).getRdfId()))
                               results.remove(i);
                
                      // Count how many times each indicator is suggested
                      Map<Indicator, Integer> stats = new HashMap<Indicator, Integer>();
                      for(Suggestion result: results) {
                         Indicator ind = (Indicator)result.getContent();
                         Integer count = stats.get(ind);          
                         stats.put(ind, (count == null) ? 1 : count + 1);
                      }
                
                      // Copy the suggestions containing indicator that aren't suggested more than once
                      List<Suggestion> resultsMerged = new ArrayList<Suggestion>();
                      for(Suggestion result: results)
                         if (stats.get((Indicator)result.getContent()) <= 1)
                            resultsMerged.add(result);
                
                      // Merge the suggestions about a same indicator with the sum of the weights multiplied by the number of times they were suggested 
                      Iterator<Entry<Indicator, Integer>> it = stats.entrySet().iterator();
                      while (it.hasNext()) {
                         Map.Entry pairs = (Map.Entry)it.next();
                         Indicator indToMerge = (Indicator)pairs.getKey();
                         int totalWeight = 0;
                         int nbFound = 0;
                         for(Suggestion result: results)
                            if (((Indicator)result.getContent()).equals(indToMerge)) {
                               totalWeight += result.getWeight();
                               nbFound++;
                            }
                            if ((Integer)pairs.getValue() > 1)
                               resultsMerged.add(new Suggestion("The indicator '" + indToMerge.getName() + "' may be relevant.", totalWeight * nbFound, indToMerge));
                      }
                
                      // Sort suggestions using their compareTo() method (ie: in function of their weight)
                      System.out.println("Sorting results");
                      Collections.sort(resultsMerged);
                      Collections.reverse(resultsMerged);
                                                                      	
                      // Send the suggestions
                      int i = 1;
                      for(Suggestion result : resultsMerged) {
                          if (i++ > MAX_INDICATOR_SUGGESTIONS)
                             break;
                          try {
                             MessageBusFactory.createMessageBus().sendMessage((User)data.getValue("user"), (Project)data.getValue("project"), new Message(result));
                          }
                          catch (MessagingException me) {
                             System.out.println(me.getMessage());
                          }
                      }
                      bel_candidateIndicatorSuggestions.clear();
                      return (PASSED);
                   }
                   // If there is no result, the plan fails
                   System.err.println("No result: nothing to suggest");
                   return (FAILED);
                }//---- End of Goal sortFilterSuggestions
            }
        } ) );
        
    }

}
