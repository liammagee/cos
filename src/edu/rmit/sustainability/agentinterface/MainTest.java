package edu.rmit.sustainability.agentinterface;

import edu.rmit.sustainability.data.DBDataStorage;
import edu.rmit.sustainability.model.*;
import edu.rmit.sustainability.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainTest {
    public static void main(final String[] args) {
        //testAgentInterface();
        //testLoginProcess();
        //testCreateProject();
        //testRetrieveUserProjects();
        //testProjectExistence();
        //testProjectsUsersAccess();
        //testPerceiveProject();
        //RDFDataStorage storage = new RDFDataStorage();
        //testIndicatorManagment();
        //testCriticalIssueCreation();
    	testindicatorSuggestionsRequest();
    	
    }
    
    // TEST OF CRITICAL ISSUE CREATION
    public static void testCriticalIssueCreation() {
        User user = new User("Liam", "1234");
        Project project = new Project("Test");
        project.setGeneralIssue("Old GI");
        project.setNormativeGoal("Old NG");

        CriticalIssue ci = new CriticalIssue();
        ci.setName("CI Name");
        ci.setDescription("CI Description");

        Objective obj = new Objective();
        obj.setDescription("bla");
        obj.setDesiredDirection(1);

        obj.setUnderlyingIssue(ci);
        ci.setAssociatedObjective(obj);

        AgentInterface.getInstance().sendData(project, user, project);
        AgentInterface.getInstance().sendData(project, user, ci);
        AgentInterface.getInstance().sendData(project, user, obj);
    }

    // TEST OF INDICATOR SUGGESTIONS
    public static void testindicatorSuggestionsRequest() {
        User user = new User("Guillaume", "1234");
        Project project = new Project("Test");

        /*
         * My Indicator: Total Water Consumption
         * This indicator is in the domain / subdomain: Economy / Consumption and Leisure
         * Its name contains the keywords: 'water' and 'supply'
         * Its description contains the keywords: 'total' 'water' 'consumption' and 'year'
         */
        Indicator ind = new Indicator();
        ind.setIdentifyingCode("waterconsumption");
        ind.setName("Water Consumption test");
        ind.setDescription("The total water a consumption this year (in m3)");
        ind.setSource("GRI");
        ind.setDomain(DummyKB.economyDomain);
        ind.setSubdomain(DummyKB.consumptionLeisure);
        ind.setIndicatorSet(DummyKB.indSetGRI);

        /*
         * My Critical Issue: Test Critical Issue
         */
        CriticalIssue ci = new CriticalIssue();
        ci.setName("Too much water consumed");
        ci.setDescription("CI Description");
        Objective obj = new Objective();
        obj.setDescription("bla");
        obj.setDesiredDirection(1);
        obj.setUnderlyingIssue(ci);
        ci.setAssociatedObjective(obj);
        
        /*
           * Request the Agent System to give some suggestions based on this indicator
           * Given the DummyKB and the indicator, the agent system should suggest:
           * 1. Water Consumption / Person: same domain and subdomain + many keywords in common
           * 2. Water Supply: same domain + keyword in common
           * The others aren't relevant enough
           */
        //AgentInterface.getInstance().sendData(project, user, new IndicatorSuggestionRequest(ind));
        AgentInterface.getInstance().sendData(project, user, new IndicatorSuggestionRequest(ci));
    }

    public static void populateDummyKB(User user, Project project) {

        /* CREATION OF AN INDICATOR: RAIN AMOUNT */
        Indicator rainAmountInd = new Indicator();
        rainAmountInd.setIdentifyingCode("rainamount");
        rainAmountInd.setName("Rain Amount");
        rainAmountInd.setDescription("Average amount of rain (in mm) this year.");
        rainAmountInd.setDomain(DummyKB.ecologyDomain);
        rainAmountInd.setSubdomain(DummyKB.earthWaterAir);
        rainAmountInd.setIndicatorSet(DummyKB.indSetGRI);
        rainAmountInd.setSource("GRI");

        /* CREATION OF AN INDICATOR: BUSHFIRES */
        Indicator bushfireInd = new Indicator();
        bushfireInd.setIdentifyingCode("bushfires");
        bushfireInd.setName("Bushfires");
        bushfireInd.setDescription("Number of important bushfires (more than 1000ha burnt) this year.");
        bushfireInd.setDomain(DummyKB.ecologyDomain);
        bushfireInd.setSubdomain(DummyKB.otherEcology);
        bushfireInd.setIndicatorSet(DummyKB.indSetGRI);
        bushfireInd.setSource("GRI");
        Target target = new Target();
        target.setAssociatedIndicator(bushfireInd);
        target.setValue("1");

        /* CREATION OF THE INDICATOR: WATER SUPPLY */
        Indicator waterSupplyInd = new Indicator();
        waterSupplyInd.setIdentifyingCode("watersupply");
        waterSupplyInd.setName("Water Supply");
        waterSupplyInd.setDescription("The total water supply of the territory (in m3)");
        waterSupplyInd.setDomain(DummyKB.ecologyDomain);
        waterSupplyInd.setSubdomain(DummyKB.earthWaterAir);
        waterSupplyInd.setIndicatorSet(DummyKB.indSetGRI);
        waterSupplyInd.setSource("GRI");

        /* CREATION OF THE INDICATOR: WATER CONSUMPTION / PERSON */
        Indicator waterConsumptionPerPersonInd = new Indicator();
        waterConsumptionPerPersonInd.setIdentifyingCode("waterConsumptionPerPerson");
        waterConsumptionPerPersonInd.setName("Water Consumption / Person");
        waterConsumptionPerPersonInd.setDescription("The water consumption this year (in m3)");
        waterConsumptionPerPersonInd.setDomain(DummyKB.economyDomain);
        waterConsumptionPerPersonInd.setSubdomain(DummyKB.consumptionLeisure);
        waterConsumptionPerPersonInd.setIndicatorSet(DummyKB.indSetOther);
        waterConsumptionPerPersonInd.setSource("City of Melbourne");

        /* CREATION OF THE INDICATOR: NUMBER OF DAMS */
        Indicator numberDamsInd = new Indicator();
        numberDamsInd.setIdentifyingCode("numberDams");
        numberDamsInd.setName("Number of Dams");
        numberDamsInd.setDescription("The number of dams in the region");
        numberDamsInd.setDomain(DummyKB.ecologyDomain);
        numberDamsInd.setSubdomain(DummyKB.buildingInfrastructure);
        numberDamsInd.setIndicatorSet(DummyKB.indSetGRI);
        numberDamsInd.setSource("GRI");

        /* CREATION OF THE INDICATOR: NUMBER OF CRIMES */
        Indicator numberCrimesInd = new Indicator();
        numberCrimesInd.setIdentifyingCode("numberCrimes");
        numberCrimesInd.setName("Number of Crimes");
        numberCrimesInd.setDescription("The number of crimes recorded by police last year");
        numberCrimesInd.setDomain(DummyKB.politicDomain);
        numberCrimesInd.setSubdomain(DummyKB.conflictInsecurity);
        numberCrimesInd.setIndicatorSet(DummyKB.indSetOther);
        numberCrimesInd.setSource("City of Melbourne");

        /* SENDING INDICATOR TO THE AGENT SYSTEM */
        try {
            /*AgentInterface.getInstance().sendData(project, user, bushfireInd);
               AgentInterface.getInstance().sendData(project, user, rainAmountInd);
               AgentInterface.getInstance().sendData(project, user, waterSupplyInd);
               AgentInterface.getInstance().sendData(project, user, waterConsumptionPerPersonInd);
               AgentInterface.getInstance().sendData(project, user, numberDamsInd);
               AgentInterface.getInstance().sendData(project, user, numberCrimesInd);*/
//			DummyKB.getInstance().addIndicator(bushfireInd);
//			DummyKB.getInstance().addIndicator(rainAmountInd);
//			DummyKB.getInstance().addIndicator(waterSupplyInd);
//			DummyKB.getInstance().addIndicator(waterConsumptionPerPersonInd);
//			DummyKB.getInstance().addIndicator(numberDamsInd);
//			DummyKB.getInstance().addIndicator(numberCrimesInd);
        } catch (Exception e) {
            System.err.println("error while populating the KB: " + e.getMessage());
        }
    }

    public static void testIndicatorManagment() {

        User user = new User("Guillaume", "1234");
        Project proj1 = new Project("Elderly wellbeing");

        /* CREATION OF AN INDICATOR: RAIN AMOUNT */
        Indicator rainAmountInd = new Indicator();
        rainAmountInd.setIdentifyingCode("rainamount");
        rainAmountInd.setName("Rain Amount");
        rainAmountInd.setDescription("Average amount of rain (in mm) this year.");
        rainAmountInd.setDomain(DummyKB.ecologyDomain);
        rainAmountInd.setIndicatorSet(DummyKB.indSetGRI);

        /* CREATION OF AN INDICATOR: BUSHFRIRES */
        Indicator bushfireInd = new Indicator();
        bushfireInd.setIdentifyingCode("bushfires");
        bushfireInd.setName("Bushfires");
        bushfireInd.setDescription("Number of important bushfires (more than 1000ha burnt) this year.");
        bushfireInd.setDomain(DummyKB.ecologyDomain);
        bushfireInd.setIndicatorSet(DummyKB.indSetGRI);
        Target target = new Target();
        target.setAssociatedIndicator(bushfireInd);
        target.setValue("1");

        /* SENDING INDICATOR TO THE AGENT SYSTEM */
        try {
            AgentInterface.getInstance().sendData(proj1, user, bushfireInd);
            AgentInterface.getInstance().sendData(proj1, user, rainAmountInd);

            Indicator indicator = DummyKB.getInstance().getIndicator("bushfires");
            System.out.println("Found: " + indicator.toString() + ": " + indicator.getName());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // TEST OF CREATING PROJECTS THROUGH THE AGENT SYSTEM
    public static void testPerceiveProject() {
        User user = new User("Guillaume", "1234");
        Project proj1 = new Project("Young wellbeing");
        Project proj2 = new Project("Young wellbeing", "Description", "GI", "");
        Project proj3 = new Project("Young wellbeing", "Description", "", "NG");
        Project proj4 = new Project("Young wellbeing", "Description", "GI", "NG");

        AgentInterface agentInterface = AgentInterface.getInstance();
        agentInterface.sendData(proj1, user, proj1);
        agentInterface.sendData(proj2, user, proj2);
        agentInterface.sendData(proj3, user, proj3);
        agentInterface.sendData(proj4, user, proj4);
    }

    // TEST OF SENDING DATA TO THE AGENT SYSTEM
    public static void testSendData() {

        User user = new User("Guillaume", "1234");
        Project proj1 = new Project("Elderly wellbeing");

        // Test: sending an invalid type of percept
        Object test = new Object();
        AgentInterface.getInstance().sendData(proj1, user, test); // Should cause an error message


    }

    // TEST WHETHER PROJECT BELONG TO A USER
    public static void testProjectsUsersAccess() {
        DBDataStorage storage = new DBDataStorage();

        // Should be true
        System.out.println("Guillaume can access project PCUL02: "
                + storage.projectBelongsTo("PCUL02", "Guillaume"));
        // Should be false
        System.out.println("Guillaume can access project PECO01: "
                + storage.projectBelongsTo("PECO01", "Guillaume"));

        Project project = new Project("Operating costs");
        User guillaume = new User("Guillaume", "1234");
        User lida = new User("Lida", "1234");

        // Should be true
        System.out.println("Lida can access project PECN01: "
                + storage.projectBelongsTo(project, lida));
        // Should be false
        System.out.println("Guillaume can access project PECN01: "
                + storage.projectBelongsTo(project, guillaume));
    }

    // TEST THE EXISTENCE OF PROJECTS
    public static void testProjectExistence() {
        DBDataStorage storage = new DBDataStorage();

        // Test an existing project ID
        if (storage.projectExists("PCUL02"))
            System.out.println("Project PCUL02 exists");
        else
            System.out.println("Project PCUL02 does not exists");

        // Test a non-existing project ID
        if (storage.projectExists("ASD"))
            System.out.println("Project ASD exists");
        else
            System.out.println("Project ASD does not exists");

        // Test using a project object
        Project project = new Project("Elderly wellbeing");
        if (storage.projectExists(project))
            System.out.println("Project PCUL01 exists");
        else
            System.out.println("Project PCUL01 does not exists");
    }

    // TEST OF RETRIEVAL OF A USER'S PROJECTS
    public static void testRetrieveUserProjects() {
        User user = new User("Guillaume", "1234");

        DBDataStorage storage = new DBDataStorage();
        ArrayList<Project> projectList = storage.retrieveProjects(user);
        Iterator<Project> it = projectList.iterator();
        Project project = null;
        while (it.hasNext()) {
            project = it.next();
            System.out.println("ID: " + project.getId());
            System.out.println("NAME: " + project.getProjectName());
        }
    }

    // TEST OF PROJECT DELETION AND CREATION
    public static void testCreateProject() {
        Project project = new Project("Young wellbeing");
        User user = new User("Guillaume", "1234");

        // Direct use of the database
        DBDataStorage storage = new DBDataStorage();
        storage.deleteProject(project);
        Boolean success = storage.storeProject(project, user);
        if (success)
            System.out.println("Project PCUL02(Young wellbeing) created");
        else
            System.out.println("Failed to create project PCUL02(Young wellbeing)");

        System.out.println("----------");

        // Store project using the agent System
        storage.deleteProject(project);
        AgentInterface agentInterface = AgentInterface.getInstance();
        agentInterface.sendData(project, user, project);
    }

    // TEST OF A LOGIN PROCESS (EMULATES WEB SERVER REQUESTS)
    public static void testLoginProcess() {
        User good = new User("Guillaume", "1234");
        User badLogin = new User("Pwet", "1234");
        User badPass = new User("Guillaume", "bla");

        DBDataStorage storage = new DBDataStorage();

        // Authentication tests
        boolean success = storage.authenticateUser(good); // Authorized user
        System.out.println("Guillaume(1234): " + success);
        success = storage.authenticateUser(badLogin); // Wrong login
        System.out.println("Pwet(1234): " + success);
        success = storage.authenticateUser(badPass); // Wrong password
        System.out.println("Guillaume(bla): " + success);

        // Login tests (authenticate + retrieve user's project)
        // Retrieve all user's project
        ArrayList<Project> projectList = storage.authenticateUserAndRetrieveProjects(good);
        Iterator<Project> it = null;
        Project project = null;
        if (projectList != null) {
            it = projectList.iterator();
            System.out.println("Guillaume(1234) project's:");
            while (it.hasNext()) {
                project = it.next();
                System.out.println("ID: " + project.getId());
                System.out.println("NAME: " + project.getProjectName());
            }
        }
    }

    // TEST OF AGENT INTERFACE, CALLING AGENT SYSTEM PERCEPTORS
    public static void testAgentInterface() {
        System.out.println("0");

        User user1 = new User("Guillaume", "1234");
        User user2 = new User("Lida", "1234");
        User user3 = new User("James", "1234");
        Project proj1 = new Project("Projet1", "Description1", "GI1", "NG1");
        Project proj2 = new Project("Projet2", "Description2", "GI2", "NG2");
        Project proj3 = new Project("Projet3", "Description3", "GI3", "NG3");
        Project proj4 = new Project("Projet4", "Description4", "GI4", "NG4");
        Project proj5 = new Project("Projet5", "Description5", "GI5", "NG5");

        AgentInterface agentInterface = AgentInterface.getInstance();

        // Sending an invalid type of percept
        agentInterface.sendData(proj1, user2, new Objective());
        agentInterface.sendData(proj1, user2, new Indicator());
        agentInterface.sendData(proj1, user2, new Object()); // This type isn't part of the model package, it should cause an error
        agentInterface.sendData(proj1, user2, new CriticalIssue());

        // Send valid 'Project' percepts
        agentInterface.sendData(proj1, user1, proj1);
        agentInterface.sendData(proj2, user2, proj2);
        agentInterface.sendData(proj3, user1, proj3);

        // Wait 2 seconds
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2");

        // Send a percept only for the project 1
        agentInterface.sendData(proj4, user3, proj4);

        // Wait 3 seconds
        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("5");

        // The project 2 should go asleep 2 seconds before the project 1

        // Wait 5 seconds
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("10");

        // Send new percepts to the project 2 (should re-enable the agent system)
        agentInterface.sendData(proj5, user2, proj1);
    }
}
