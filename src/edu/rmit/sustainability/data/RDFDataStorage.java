package edu.rmit.sustainability.data;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.sdb.store.DatasetStore;
import com.hp.hpl.jena.update.GraphStore;
import com.hp.hpl.jena.update.GraphStoreFactory;
import com.hp.hpl.jena.update.UpdateAction;
import edu.rmit.sustainability.model.*;
import edu.rmit.sustainability.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RDFDataStorage {

    static Utils codeUtility = new Utils();
    // TODO: Bad practice - should be some other mechanism for setting this.
    public static String baseDir = "war/";

    /* The list of prefixes required to work with triples */
    String project_ns = "prefix project:<http://circlesofsustainability.org/ontology/project#>";
    String rdf_ns = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    String ontology_ns = "prefix cos:<http://circlesofsustainability.org/ontology#>";


    /**
     * Loading the configuration for postgres database
     */
    Store store = SDBFactory.connectStore(baseDir + "sdb.ttl");

    /**
     * Dataset can be loaded from the file or the updated graphstore
     */
    Dataset dataset = DatasetStore.create(store);

    /**
     * Jena model to update the data into DB
     */
    Model model = SDBFactory.connectDefaultModel(store);


    /* Start raw data methods */


    /*
      * Generic RDF update. Requires the subject, predicate and old and new
      * objects of the update
      *
      * @param subject
      *
      * @param predicate
      *
      * @param oldObject
      *
      * @param newObject
      *
      * @return whether the insert is successful
      */
    public boolean dataUpdate(String subject, String predicate,
                              String oldObject, String newObject) {
        try {
            boolean deleteResult = dataDelete(subject, predicate, oldObject);
            if (!deleteResult)
                return deleteResult;
            boolean insertResult = dataInsert(subject, predicate, newObject);
            if (!insertResult)
                return insertResult;
        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
        return (true);

    }

    /**
     * Generic RDF insert. Requires the subject, predicate and object of the
     * insert
     *
     * @param subject
     * @param predicate
     * @param object
     * @return whether the insert is successful
     */
    public boolean dataInsert(String subject, String predicate, String object) {
        try {

            Model model = SDBFactory.connectDefaultModel(store);
            GraphStore graphStore = GraphStoreFactory.create(model);
            String queryString = ontology_ns + "\n" + rdf_ns + "\n" + project_ns + "\n" + " INSERT DATA { " + subject
                    + " " + predicate + " " + object + " .}";

            System.out.println(queryString);
            /**
             * Parsing the query string to update the graph
             */
            UpdateAction.parseExecute(queryString, graphStore);
            System.out.println("Got here - inserted triple");
        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
        return (true);

    }

    /**
     * Generic RDF data delete. Requires a predicate and object ID to identify
     * the triple to delete, and the subject of the delete
     *
     * @param subject
     * @param predicate
     * @param object
     * @return whether the delete is successful
     */
    public boolean dataDelete(String subject, String predicate, String object) {
        try {
            Model model = SDBFactory.connectDefaultModel(store);
            GraphStore graphStore = GraphStoreFactory.create(model);
            String queryString = ontology_ns + "\n" + rdf_ns + "\n" + project_ns + "\n" + " DELETE DATA {  " + subject
                    + " " + predicate + " " + object + " }";
            System.out.println(queryString);
            /**
             * Parsing the query string to update the graph
             */
            UpdateAction.parseExecute(queryString, graphStore);
            System.out.println("Got here - deleted triple");
        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
        return (true);
    }

    /**
     * Generic RDF data query. Retrieves a Map of all predicates and objects
     * associated with a given subject URI.
     *
     * @param subject
     * @return whether the delete is successful
     */
    public Map dataQuery(String subject) {
        Map map = new HashMap();
        try {
            Model model = SDBFactory.connectDefaultModel(store);
            String queryString = ontology_ns + "\n" + rdf_ns + "\n" + project_ns + "\n" + " SELECT ?p ?o WHERE { "
                    + subject + " ?p ?o . }";
            System.out.println(queryString);

            Query query = QueryFactory.create(queryString);
            QueryExecution queryExect = QueryExecutionFactory.create(query, model);
            try {
                ResultSet result = queryExect.execSelect();
                //ResultSetFormatter.out(result) ;

                while (result.hasNext()) {

                    QuerySolution soln = result.nextSolution();

                    RDFNode pNode = soln.get("p");
                    RDFNode oNode = soln.get("o");

                    System.out.println(pNode.toString() + " " + oNode.toString());

                    map.put(pNode.toString(), oNode.toString());
                }
            } finally {
                queryExect.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }


    /* End raw data methods */


    /* Start positioned data methods */


    /**
     * Generic RDF positioned delete. Deletes all predicates for a given subject.
     *
     * @param subject
     * @return whether the delete is successful
     */
    public boolean positionedDeleteAllPredicates(String subject) {
        try {
            Model model = SDBFactory.connectDefaultModel(store);
            GraphStore graphStore = GraphStoreFactory.create(model);
            String queryString = ontology_ns + "\n" + rdf_ns + "\n" + project_ns + "\n" +
                    " DELETE {" + subject + " ?p ?o} " +
                    " WHERE  {  " + subject + " ?p ?o . }";
            /**
             * Parsing the query string to update the graph
             */
            UpdateAction.parseExecute(queryString, graphStore);
            System.out.println("Got here - deleted triple");
        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
        return (true);
    }


    /**
     * Generic RDF positioned delete. Deletes a single given predicate for a given subject.
     *
     * @param subject
     * @param predicate
     * @return whether the delete is successful
     */
    public boolean positionedDeleteSinglePredicate(String subject, String predicate) {
        try {
            Model model = SDBFactory.connectDefaultModel(store);
            GraphStore graphStore = GraphStoreFactory.create(model);
            String queryString = ontology_ns + "\n" + rdf_ns + "\n" + project_ns + "\n" +
                    " DELETE { ?s ?p ?o } " +
                    " WHERE  {  " + subject + " " + predicate + " ?o . ?s ?p ?o }";
            /**
             * Parsing the query string to update the graph
             */
            UpdateAction.parseExecute(queryString, graphStore);
            System.out.println("Got here - deleted triple");
        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
        return (true);
    }


    /**
     * Generic RDF positioned update. Deletes and inserts a single given predicate and object for a given subject,
     * without requiring the old value to be known.
     *
     * @param subject
     * @param predicate
     * @param object
     * @return whether the delete is successful
     */
    public boolean positionedUpdateSinglePredicate(String subject, String predicate, String object) {
        try {
            System.out.println("-----positionedUpdateSinglePredicate-------");
            Model model = SDBFactory.connectDefaultModel(store);
            GraphStore graphStore = GraphStoreFactory.create(model);
            String queryString = ontology_ns + "\n" + rdf_ns + "\n" + project_ns + "\n" +
                    //" DELETE {" + subject + " " + predicate + " ?o} " +
                    " DELETE { ?s ?p ?o }" +
                    " WHERE  { " + subject + " " + predicate + " ?o . ?s ?p ?o  }" +
                    " INSERT {" + subject + " " + predicate + " " + object + " } ";


            System.out.println(queryString);
            /**
             * Parsing the query string to update the graph
             */
            UpdateAction.parseExecute(queryString, graphStore);
            System.out.println("Got here - deleted triple");
        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
        return (true);
    }


    /* End positioned data methods */

    /**
     * Clears all triples from the default store. USE ADVISEDLY!
     *
     * @return whether the clear operation is successful
     */
    public boolean clearTriples() {
        try {
            Model model = SDBFactory.connectDefaultModel(store);
            GraphStore graphStore = GraphStoreFactory.create(model);

            // For some reason not implemented in ARQ
            //String queryString = "CLEAR";


            String queryString = " DELETE { ?s ?p ?o } WHERE { ?s ?p ?o } ";

            /**
             * Parsing the query string to update the graph
             */
            System.out.println(queryString);
            UpdateAction.parseExecute(queryString, graphStore);
            System.out.println("Got here - cleared all triples");
        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
        return (true);
    }


    /* ++++++++++ Create, Update, Retrieve Methods for  Project Object +++++++++++++ */


    /**
     * Lida : This method create uniqueID for each entity and links them with "rdf:type".
     *  uniqueID is then shared for other triples as "subject" with different "predicates" and "objects" for specific entity (individual)
     *  This method creates new Project entity, other methods will be added to create other entities from ontology, such as, CriticalIssue,
     * User, Component, Objective and etc.
     * @param project
     * @param user
     * @return
     */

    /**
     *
     */
    public boolean createProject(Project project, User user) {

        try {

            String uniqueID = codeUtility.generateStringUUID();
            // TODO: See if this is necessary. If so, find another mechanism for setting the ID, compatible with the Empire framework.
//            project.setProjectId(uniqueID);

            /*
                      * Creating a uniqueURI for a specific uniqueID
                      */
            String uniqueURI = Utils.generateUniqueURI(uniqueID);

            dataInsert(Utils.rdfWrapNode(uniqueURI), Utils.rdfWrapNode("rdf:type"), Utils.rdfWrapString("cos:Project"));
            dataInsert(Utils.rdfWrapNode(uniqueURI), Utils.rdfWrapNode(Utils.generateUniqueURI("hasName")), Utils.rdfWrapString(project.getProjectName()));
            dataInsert(Utils.rdfWrapNode(uniqueURI), Utils.rdfWrapNode(Utils.generateUniqueURI("hasDescription")), Utils.rdfWrapString(project.getProjectDescription()));
            dataInsert(Utils.rdfWrapNode(uniqueURI), Utils.rdfWrapNode(Utils.generateUniqueURI("hasGeneralIssue")), Utils.rdfWrapString(project.getGeneralIssue()));
            dataInsert(Utils.rdfWrapNode(uniqueURI), Utils.rdfWrapNode(Utils.generateUniqueURI("hasGoal")), Utils.rdfWrapString(project.getNormativeGoal()));


        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
        return (true);
    }


    /**
     * Lida: Update project with new data
     *
     * @param project
     * @param user
     * @return
     */


    public boolean storeProject(Project project, User user) {
        /**
         * Getting uniqueID from project ID without retrieving from knowledge base
         */
        try {
            String uniqueID = project.getId();


            String uniqueURI = Utils.generateUniqueURI(uniqueID);

            positionedUpdateSinglePredicate(Utils.rdfWrapNode(uniqueURI), Utils.rdfWrapNode(Utils.generateUniqueURI("hasName")), Utils.rdfWrapString(project.getProjectName()));
            positionedUpdateSinglePredicate(Utils.rdfWrapNode(uniqueURI), Utils.rdfWrapNode(Utils.generateUniqueURI("hasDescription")), Utils.rdfWrapString(project.getProjectDescription()));
            positionedUpdateSinglePredicate(Utils.rdfWrapNode(uniqueURI), Utils.rdfWrapNode(Utils.generateUniqueURI("hasGeneralIssue")), Utils.rdfWrapString(project.getGeneralIssue()));
            positionedUpdateSinglePredicate(Utils.rdfWrapNode(uniqueURI), Utils.rdfWrapNode(Utils.generateUniqueURI("hasGoal")), Utils.rdfWrapString(project.getNormativeGoal()));


        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }

        return (true);
    }

    /**
     * Lida: Retrieving the project to
     *
     * @param projectName
     * @return
     */
    public Project retrieveProject(String projectName) {

        Project newproject = new Project();

        String queryString = ontology_ns + "\n" + rdf_ns + "\n" + project_ns + "\n"
                + "SELECT ?x ?issue ?goal ?description WHERE { ?x " + Utils.rdfWrapNode("rdf:type") + " '" + "cos:Project" + "'. ?x cos:hasName '" + projectName + "' . ?x cos:hasGeneralIssue ?issue . ?x cos:hasGoal ?goal . ?x cos:hasDescription ?description .}";

        Query query = QueryFactory.create(queryString);

        QueryExecution queryExect = QueryExecutionFactory
                .create(query, dataset);

        System.out.println(queryString);

        try {
            ResultSet result = queryExect.execSelect();
            //ResultSetFormatter.out(result);

            /**
             * Printing the result in the graph format (testing)
             */


            /**
             * Converting the information related to each project from a RDF row
             * into the Project object properties Note: project ID is assumed to
             * be the user name at the initial step of the implementation
             */

            while (result.hasNext()) {

                QuerySolution soln = result.nextSolution();


                RDFNode IDNode = soln.get("x");
                RDFNode normativeGoalNode = soln.get("goal");
                RDFNode issueNode = soln.get("issue");
                RDFNode descriptionNode = soln.get("description");


                newproject.setProjectName(projectName);
                // TODO: See if this is necessary. If so, find another mechanism for setting the ID, compatible with the Empire framework.
//                newproject.setProjectId(IDNode.toString().substring(44));
                newproject.setProjectDescription(descriptionNode.toString());
                newproject.setGeneralIssue(issueNode.toString());
                newproject.setNormativeGoal(normativeGoalNode.toString());

            }
        } finally {
            queryExect.close();
        }
        return newproject;
    }

    public boolean deleteProject(Project project) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean projectExists(Project project) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean projectExists(String projectid) {
        // TODO Auto-generated method stub
        return false;
    }


    /* ++++++++++ Create, Update, Retrieve Methods for  Critical Issue Object +++++++++++++ */


    /**
     * Lida : creating a Critical Issue
     *
     * @param criticalIssue
     * @return
     */


    public boolean createCriticalIssue(CriticalIssue criticalIssue, User user) {


        try {
            String uniqueID = codeUtility.generateStringUUID();

            // TODO: See if this is necessary. If so, find another mechanism for setting the ID, compatible with the Empire framework.
//			  criticalIssue.setId(uniqueID);

            /*
                  * Creating a uniqueURI for a specific uniqueID
                  */
            String uniqueURI = Utils.rdfWrapNode(Utils.generateUniqueURI(uniqueID));

            dataInsert(uniqueURI, "rdf:type", "cos:CriticalIssue");
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasName")), Utils.rdfWrapString(criticalIssue.getName()));
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasDescription")), Utils.rdfWrapString(criticalIssue.getDescription()));
//      	     dataInsert(uniqueURI,Utils.rdfWrapNode(Utils.generateUniqueURI("hasObjective")),Utils.generateUniqueURI(criticalIssue.getAssociatedObjective().getId()));
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasPerceive")), Utils.rdfWrapString(String.valueOf(criticalIssue.getPerceivedSignificance())));
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasAuthor")), Utils.generateUniqueURI(criticalIssue.getCreator().getUsername()));

            // If there is more than one component, I need to know how agents provide the information
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasComponent")), Utils.generateUniqueURI(criticalIssue.getComponentIssues().toString()));

            /**
             * Lida : Relating an issue to domains and subdomains
             * Question?:  If an issue can related to more than one domains we need a repeat these triples?
             */

            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasDomain")), Utils.generateUniqueURI(criticalIssue.getDomain().getName()));
            for (Subdomain subdomain : criticalIssue.getSubdomains()) {
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasSubDomain")), Utils.generateUniqueURI(subdomain.getName()));
            }


        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
        return (true);
    }


    /**
     * Lida : Addnig a critical Issue to the given project
     *
     * @param project
     * @param criticalIssue
     * @return
     */

    public boolean addCriticalIssueToProject(Project project, CriticalIssue criticalIssue) {
        try {
            positionedUpdateSinglePredicate(Utils.generateUniqueURI(project.getId()), Utils.generateUniqueURI("hasCriticalIssue"), Utils.generateUniqueURI(criticalIssue.getId()));

        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
        return (true);

    }

    /**
     * Lida : Updating the criticalIssue for the user
     *
     * @param criticalIssue
     * @param user
     * @return
     */

    public boolean updateCriticalIssue(CriticalIssue criticalIssue, User user) {
        /**
         * Getting uniqueID from criticalIssue ID without retrieving from knowledge base
         */
        try {

            String uniqueID = criticalIssue.getId();
            String uniqueURI = Utils.generateUniqueURI(uniqueID);


            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasName")), Utils.rdfWrapString(criticalIssue.getName()));
            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasDescription")), Utils.rdfWrapString(criticalIssue.getDescription()));
//		 positionedUpdateSinglePredicate(uniqueURI,Utils.rdfWrapNode(Utils.generateUniqueURI("hasObjective")),Utils.generateUniqueURI(criticalIssue.getAssociatedObjective().getId()));
            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasPerceive")), Utils.rdfWrapString(String.valueOf(criticalIssue.getPerceivedSignificance())));
            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasAuthor")), Utils.generateUniqueURI(criticalIssue.getCreator().getUsername()));


            /**
             * Lida : Relating an issue to domains and subdomains
             * Question?:  If an issue can related to more than one domains we need a repeat these triples?
             */

            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasDomain")), Utils.generateUniqueURI(criticalIssue.getDomain().getName()));
            for (Subdomain subdomain : criticalIssue.getSubdomains()) {
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasSubDomain")), Utils.generateUniqueURI(subdomain.getName()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }

        return (true);
    }

    /**
     * Lida : Retrieving all the critical issue for the given project
     *
     * @param project
     * @return
     */

    public ArrayList<CriticalIssue> retrieveAllCriticalIssues(Project project) {
        // Getting the given project URI as subject and creating the required predicate

        String subject = Utils.generateUniqueURI(project.getId());
        String predicate = Utils.generateUniqueURI("hasCriticalIssue");

        System.out.println("AllIssue: " + subject + " " + predicate);

        ArrayList<CriticalIssue> criticalIssueList = new ArrayList<CriticalIssue>();
        CriticalIssue newElement = new CriticalIssue();
        String uniqueURI = null;


        try {

            Map results = dataQuery(Utils.rdfWrapNode(subject));
            for (Iterator it = results.entrySet().iterator(); it.hasNext(); ) {

                Map.Entry<String, String> pairs1 = (Map.Entry<String, String>) it.next();
                if (pairs1.getValue().equals(predicate)) {
                    // Retrieving the properties for a selected critical issues
                    uniqueURI = pairs1.getKey();
                    //ID
                    // TODO: See if this is necessary. If so, find another mechanism for setting the ID, compatible with the Empire framework.
//	          newElement.setId(uniqueURI.substring(44));
                    //System.out.println(pairs1.getKey());
                    Map issues = dataQuery(Utils.rdfWrapNode(uniqueURI));

                    for (Iterator that = issues.entrySet().iterator(); that.hasNext(); ) {
                        Map.Entry<String, String> pairs2 = (Map.Entry<String, String>) that.next();
                        //Name
                        if (pairs2.getValue().equals("http://circlesofsustainability.org/ontology#hasName")) {
                            newElement.setName(pairs2.getKey());
                        }
                        //Description
                        else if (pairs2.getValue().equals("http://circlesofsustainability.org/ontology#hasDescription")) {
                            newElement.setName(pairs2.getKey());
                        }
                        //Objective
                        else if (pairs2.getValue().equals("http://circlesofsustainability.org/ontology#hasObjective")) {
                            newElement.setName(pairs2.getKey());
                        }
                        //Perceive
                        else if (pairs2.getValue().equals("http://circlesofsustainability.org/ontology#hasPerceive")) {
                            newElement.setName(pairs2.getKey());
                        }
                        //Author
                        else if (pairs2.getValue().equals("http://circlesofsustainability.org/ontology#hasAuthor")) {
                            newElement.setName(pairs2.getKey());
                        }
                        //Domain
                        else if (pairs2.getValue().equals("http://circlesofsustainability.org/ontology#hasDomain")) {
                            newElement.setName(pairs2.getKey());
                        }
                        //Subdomain
                        else if (pairs2.getValue().equals("http://circlesofsustainability.org/ontology#hasSubDomain")) {
                            newElement.setName(pairs2.getKey());
                        }
                    }
                    // Adding new CriticalIssue Element to the list
                    criticalIssueList.add(newElement);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println("Got here -- Retrieving all critical issues successful! ");

        return criticalIssueList;
    }


    /* ++++++++++ Create, Update, Retrieve Methods for  Indicator Object +++++++++++++ */

    /**
     * Lida: Creating a new Indicator
     */
    public boolean createIndicator(Indicator indicator, User user) {
        try {
            String uniqueID = codeUtility.generateStringUUID();

            // TODO: See if this is necessary. If so, find another mechanism for setting the ID, compatible with the Empire framework.
//			  indicator.setId(uniqueID);
            /*
                  * Creating a uniqueURI for a specific uniqueID
                  */
            String uniqueURI = Utils.rdfWrapNode(Utils.generateUniqueURI(uniqueID));

            dataInsert(uniqueURI, "rdf:type", "cos:Indicator");
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasName")), Utils.rdfWrapString(indicator.getName()));
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasDescription")), Utils.rdfWrapString(indicator.getDescription()));
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasSource")), Utils.rdfWrapString(indicator.getSource()));
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasidentifyingCode")), Utils.rdfWrapString(indicator.getIdentifyingCode()));
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasIndicatorSet")), Utils.generateUniqueURI(indicator.getIndicatorSet().getId()));
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasUnitOfMeasurement")), Utils.rdfWrapString(indicator.getUnitOfMeasure()));
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasParent")), Utils.generateUniqueURI(indicator.getParentIndicator().getId()));
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasDomain")), Utils.generateUniqueURI(indicator.getDomain().getName()));
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasSubDomain")), Utils.generateUniqueURI(indicator.getSubdomain().getName()));
            dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasTarget")), Utils.generateUniqueURI(indicator.getTarget().getValue()));
            //List of flags

            if (indicator.isSocial())
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isSocial")), Utils.rdfWrapString("true"));
            else
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isSocial")), Utils.rdfWrapString("false"));

            if (indicator.isNatural())
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isNatural")), Utils.rdfWrapString("true"));
            else
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isNatural")), Utils.rdfWrapString("fasle"));


            if (indicator.isProcess())
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isProcess")), Utils.rdfWrapString("true"));
            else
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isProcess")), Utils.rdfWrapString("false"));

            if (indicator.isObject())
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isObject")), Utils.rdfWrapString("true"));
            else
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isObject")), Utils.rdfWrapString("false"));

            if (indicator.isAggregate())
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isAggregate")), Utils.rdfWrapString("true"));
            else
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isAggregate")), Utils.rdfWrapString("false"));

            if (indicator.isSingular())
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isSingular")), Utils.rdfWrapString("true"));
            else
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isSingular")), Utils.rdfWrapString("false"));

            if (indicator.isSpatial())
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isSpatial")), Utils.rdfWrapString("true"));
            else
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isSpatial")), Utils.rdfWrapString("false"));

            if (indicator.isTemporal())
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isTemporal")), Utils.rdfWrapString("true"));
            else
                dataInsert(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isTemporal")), Utils.rdfWrapString("false"));
            /**
             * Lida: QUESTION? I am not sure how agents would provide KB the following information
             */

            // private List<Indicator> subordinateIndicators
            // dataInsert(uniqueURI,Utils.rdfWrapNode(Utils.generateUniqueURI("hasSubordinate")),Utils.generateUniqueURI(?));


            //	private List<AbstractIssue> associatedIssues
            // dataInsert(uniqueURI,Utils.rdfWrapNode(Utils.generateUniqueURI("hasAssociateIssues")),Utils.generateUniqueURI(?));


        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
        return (true);
    }


    /**
     * Lida: Updating an indicator
     *
     * @param indicator
     * @param user
     * @return
     */

    public boolean updateIndicator(Indicator indicator, User user) {
        try {

            String uniqueID = indicator.getId();
            String uniqueURI = Utils.generateUniqueURI(uniqueID);


            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasName")), Utils.rdfWrapString(indicator.getName()));
            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasDescription")), Utils.rdfWrapString(indicator.getDescription()));
            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasSource")), Utils.rdfWrapString(indicator.getSource()));
            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasidentifyingCode")), Utils.rdfWrapString(indicator.getIdentifyingCode()));
            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasIndicatorSet")), Utils.generateUniqueURI(indicator.getIndicatorSet().getId()));
            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasUnitOfMeasurement")), Utils.rdfWrapString(indicator.getUnitOfMeasure()));
            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasParent")), Utils.generateUniqueURI(indicator.getParentIndicator().getId()));
            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasDomain")), Utils.generateUniqueURI(indicator.getDomain().getName()));
            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasSubDomain")), Utils.generateUniqueURI(indicator.getSubdomain().getName()));
            positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("hasTarget")), Utils.generateUniqueURI(indicator.getTarget().getValue()));

            //List of flags

            if (indicator.isSocial())
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isSocial")), Utils.rdfWrapString("true"));
            else
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isSocial")), Utils.rdfWrapString("false"));

            if (indicator.isNatural())
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isNatural")), Utils.rdfWrapString("true"));
            else
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isNatural")), Utils.rdfWrapString("fasle"));


            if (indicator.isProcess())
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isProcess")), Utils.rdfWrapString("true"));
            else
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isProcess")), Utils.rdfWrapString("false"));

            if (indicator.isObject())
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isObject")), Utils.rdfWrapString("true"));
            else
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isObject")), Utils.rdfWrapString("false"));

            if (indicator.isAggregate())
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isAggregate")), Utils.rdfWrapString("true"));
            else
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isAggregate")), Utils.rdfWrapString("false"));

            if (indicator.isSingular())
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isSingular")), Utils.rdfWrapString("true"));
            else
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isSingular")), Utils.rdfWrapString("false"));

            if (indicator.isSpatial())
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isSpatial")), Utils.rdfWrapString("true"));
            else
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isSpatial")), Utils.rdfWrapString("false"));

            if (indicator.isTemporal())
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isTemporal")), Utils.rdfWrapString("true"));
            else
                positionedUpdateSinglePredicate(uniqueURI, Utils.rdfWrapNode(Utils.generateUniqueURI("isTemporal")), Utils.rdfWrapString("false"));
            /**
             * Lida: QUESTION? I am not sure how agents would provide KB the following information
             */

            // private List<Indicator> subordinateIndicators
            //  positionedUpdateSinglePredicate(uniqueURI,Utils.rdfWrapNode(Utils.generateUniqueURI("hasSubordinate")),Utils.generateUniqueURI(?));


            //	private List<AbstractIssue> associatedIssues
            //  positionedUpdateSinglePredicate(uniqueURI,Utils.rdfWrapNode(Utils.generateUniqueURI("hasAssociateIssues")),Utils.generateUniqueURI(?));

        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }

        return (true);
    }


    public boolean storeGeneralIssue(Project project, String generalIssue,
                                     ArrayList<String> relatedIssues) {
        // TODO Auto-generated method stub
        return false;
    }

    public String getGeneralIssue(Project project) {
        // TODO Auto-generated method stub
        return null;
    }

    public Project getProjectByID(String projectID) {
        return null;
    }


    /**
     * Generic RDF data query. Retrieves a Map of all predicates and objects
     * associated with a given subject URI.
     *
     * @param queryString
     * @return whether the delete is successful
     */
    public void genericQuery(String queryString) {
        try {
            Model model = SDBFactory.connectDefaultModel(store);
            Query query = QueryFactory.create(queryString);
            QueryExecution queryExect = QueryExecutionFactory.create(query,
                    model);
            try {
                ResultSet result = queryExect.execSelect();
                ResultSetFormatter.out(result);
            } finally {
                queryExect.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
