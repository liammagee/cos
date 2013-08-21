package edu.rmit.sustainability.setup;

import com.clarkparsia.empire.Empire;
import com.clarkparsia.empire.impl.RdfQuery;
import com.clarkparsia.empire.jena.JenaEmpireModule;
import edu.rmit.sustainability.data.RDFDataStorage;
import edu.rmit.sustainability.data.transform.SAXGRIDriver;
import edu.rmit.sustainability.model.*;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: E65691
 * Date: 12/05/11
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Setup {

    static Domain economy = null;
    static Domain ecology = null;
    static Domain politics = null;
    static Domain culture = null;
    static IndicatorSet indSetGRI = null;
    static IndicatorSet indSetOther = null;
    public static User defaultUser = null;

    public static void main(String[] args) {

        // Add hooks for supplied args

        // Remove EVERYTHING from the triple store
        RDFDataStorage.baseDir = "war/";
        RDFDataStorage storage = new RDFDataStorage();
        storage.clearTriples();


        // Set up the empire configuration
        System.setProperty("empire.configuration.file", "war/WEB-INF/examples.empire.config.properties");
        System.setProperty("log4j.debug", "true");
        Empire.init(new JenaEmpireModule());
        EntityManager aManager = Persistence.createEntityManagerFactory("SDB2").createEntityManager();

        aManager.getTransaction().begin();
        addDomainsAndSubDomains(aManager);
        addUsers(aManager);
        addIndicatorSets(aManager);
        addIndicators(aManager);
        addProjectAndCriticalIssues(aManager);
        aManager.getTransaction().commit();
        aManager.close();
    }


    private static void addProjectAndCriticalIssues(EntityManager aManager) {
        Project project = new Project("Water Crisis");
        project.setCreator(defaultUser);
        project.setProjectDescription("Water crisis is a term used to refer to the world�s water resources relative to human demand. The term has been applied to the worldwide water situation by the United Nations and other world organizations.");
        project.setGeneralIssue("Overall scarcity of usable water and water pollution");
        project.setNormativeGoal("Have enough water for everybody and reduce water pollution");

        // Set up users and Permissions
        project.setCreator(defaultUser);
        Query query = aManager.createQuery("WHERE {?result a <http://circlesofsustainability.org/ontology#User>}");

        // this query should return instances of type Project
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, User.class);

        List aResults = query.getResultList();
        for (Iterator iterator = aResults.iterator(); iterator.hasNext(); ) {
            User user = (User) iterator.next();
            if (!user.equals(defaultUser))
                project.addCollaborator(user);
        }


        ArrayList<CriticalIssue> ciList = new ArrayList<CriticalIssue>();


        /* Critical Issue: Inadequate access to drinking water */
        CriticalIssue accessWaterDrinkingCI = new CriticalIssue();
        accessWaterDrinkingCI.setCreator(defaultUser);
        accessWaterDrinkingCI.setName("Inadequate access to drinking water");
        accessWaterDrinkingCI.setDescription("Inadequate access to safe drinking water for about 884 million people");
        accessWaterDrinkingCI.setDomain(economy);
        accessWaterDrinkingCI.addSubdomain(economy.getSubdomain("Exchange and Transfer"));
        ciList.add(accessWaterDrinkingCI);


        /* Critical Issue: Inadequate access to water for sanitation */
        CriticalIssue accessWaterSanitationCI = new CriticalIssue();
        accessWaterSanitationCI.setCreator(defaultUser);
        accessWaterSanitationCI.setName("Inadequate access to water for sanitation");
        accessWaterSanitationCI.setDescription("Inadequate access to water for sanitation and waste disposal for 2.5 billion people");
        accessWaterSanitationCI.setDomain(economy);
        accessWaterSanitationCI.addSubdomain(economy.getSubdomain("Exchange and Transfer"));
        ciList.add(accessWaterSanitationCI);


        /* Critical Issue: Groundwater overdrafting */
        CriticalIssue groundwaterOverdraftingCI = new CriticalIssue();
        groundwaterOverdraftingCI.setCreator(defaultUser);
        groundwaterOverdraftingCI.setName("Groundwater overdrafting");
        groundwaterOverdraftingCI.setDescription("Groundwater excessive use leading to diminished agricultural yields");
        groundwaterOverdraftingCI.setDomain(economy);
        groundwaterOverdraftingCI.addSubdomain(economy.getSubdomain("Consumption and Use"));
        ciList.add(groundwaterOverdraftingCI);


        /* Critical Issue: Water pollution */
        CriticalIssue waterPollutionCI = new CriticalIssue();
        waterPollutionCI.setCreator(defaultUser);
        waterPollutionCI.setName("Overuse and pollution of Water");
        waterPollutionCI.setDescription("Overuse and pollution of water resources harming biodiversity");
        waterPollutionCI.setDomain(ecology);
        waterPollutionCI.addSubdomain(ecology.getSubdomain("Emission and Waste"));

        ArrayList<IssueComponent> compList = new ArrayList<IssueComponent>();
        // Component
        IssueComponent waterOveruseComp = new IssueComponent();
        waterOveruseComp.setName("Water Overuse");
        waterOveruseComp.setDescription("Overuse of water ressources");
        waterOveruseComp.setDomain(economy);
        waterOveruseComp.addSubdomain(economy.getSubdomain("Consumption and Use"));
        waterOveruseComp.setParentIssue(waterPollutionCI);
        compList.add(waterOveruseComp);

        // Component
        IssueComponent waterPollutionComp = new IssueComponent();
        waterPollutionComp.setName("Water Pollution");
        waterPollutionComp.setDescription("Pollution of water ressources");
        waterPollutionComp.setDomain(ecology);
        waterPollutionComp.addSubdomain(ecology.getSubdomain("Emission and Waste"));
        waterPollutionComp.setParentIssue(waterPollutionCI);
        compList.add(waterPollutionComp);

        // Component
        IssueComponent harmingBiodiversityComp = new IssueComponent();
        harmingBiodiversityComp.setName("Harming biodiversity");
        harmingBiodiversityComp.setDescription("Biodiversity is affected by water use and pollution");
        harmingBiodiversityComp.setDomain(ecology);
        harmingBiodiversityComp.addSubdomain(ecology.getSubdomain("Flora and Fauna"));
        harmingBiodiversityComp.setParentIssue(waterPollutionCI);
        compList.add(harmingBiodiversityComp);

        waterPollutionCI.setComponentIssues(compList);
        ciList.add(waterPollutionCI);


        /* Critical Issue: Conflict for water */
        CriticalIssue conflictForWaterCI = new CriticalIssue();
        conflictForWaterCI.setCreator(defaultUser);
        conflictForWaterCI.setName("Conflict for water");
        conflictForWaterCI.setDescription("Regional conflicts over scarce water resources sometimes resulting in warfare");
        conflictForWaterCI.setDomain(politics);
        conflictForWaterCI.addSubdomain(politics.getSubdomain("Security and Conflict"));
        ciList.add(conflictForWaterCI);

        project.setCriticalIssues(ciList);

        aManager.persist(project);
    }

    private static void addUsers(EntityManager aManager) {
        // Add some users
        User guillaume = new User("Guillaume", "1234");
        User kesun = new User("Ke Sun", "1234");
        User lida = new User("Lida", "1234");
        User liam = new User("Liam", "1234");

        aManager.persist(guillaume);
        aManager.persist(kesun);
        aManager.persist(lida);
        aManager.persist(liam);

        defaultUser = liam;
    }

    private static void addDomainsAndSubDomains(EntityManager aManager) {
        // Add current domains and subdomains
        economy = new Domain("Economy");
        economy.setDescription("The economic domain is defined in terms of activities associated with the production, use, movement, and management of resources, where the concept of ‘resources’ is used in the broadest sense of that word.");
        economy.addSubdomain(new Subdomain(1, "Resourcing and Production"));
        economy.addSubdomain(new Subdomain(2, "Exchange and Transfer"));
        economy.addSubdomain(new Subdomain(3, "Regulation and Counting"));
        economy.addSubdomain(new Subdomain(4, "Consumption and Use"));
        economy.addSubdomain(new Subdomain(5, "Labour and Welfare"));
        economy.addSubdomain(new Subdomain(6, "Technology and Fabrication"));
        economy.addSubdomain(new Subdomain(7, "Wealth and Allocation"));

        ecology = new Domain("Ecology");
        ecology.setDescription("The ecological domain is defined in terms of the intersection between the social and the natural realms, focussing on the important dimension of human engagement with and within nature, but also including the build environment.");
        ecology.addSubdomain(new Subdomain(8, "Water and Air"));
        ecology.addSubdomain(new Subdomain(9, "Flora and Fauna"));
        ecology.addSubdomain(new Subdomain(10, "Habitat and Land"));
        ecology.addSubdomain(new Subdomain(11, "Place and Abode"));
        ecology.addSubdomain(new Subdomain(12, "Materials and Energy"));
        ecology.addSubdomain(new Subdomain(13, "Infrastructure and Constructions"));
        ecology.addSubdomain(new Subdomain(14, "Emission and Waste"));

        politics = new Domain("Politics");
        politics.setDescription("The political domain is defined in terms of practices of authorization, legitimation and regulation.");
        politics.addSubdomain(new Subdomain(15, "Organization and Governance"));
        politics.addSubdomain(new Subdomain(16, "Law and Justice"));
        politics.addSubdomain(new Subdomain(17, "Communication and Dissemination"));
        politics.addSubdomain(new Subdomain(18, "Representation and Negotiation"));
        politics.addSubdomain(new Subdomain(19, "Security and Conflict"));
        politics.addSubdomain(new Subdomain(20, "Dialogue and Reconciliation"));
        politics.addSubdomain(new Subdomain(21, "Ethics and Duty"));

        culture = new Domain("Culture");
        culture.setDescription("The cultural domain is defined in terms of practices, discourses, and material expressions, which, over time, express continuities and discontinuities of meaning.");
        culture.addSubdomain(new Subdomain(22, "Engagement and Affiliation"));
        culture.addSubdomain(new Subdomain(23, "Symbolism and Creativity"));
        culture.addSubdomain(new Subdomain(24, "Memory and Projection"));
        culture.addSubdomain(new Subdomain(25, "Belief and Faith"));
        culture.addSubdomain(new Subdomain(26, "Enquiry and Learning"));
        culture.addSubdomain(new Subdomain(27, "Health and Recreation"));
        culture.addSubdomain(new Subdomain(28, "Gender and Reproduction"));

        aManager.persist(ecology);
        aManager.persist(economy);
        aManager.persist(politics);
        aManager.persist(culture);
    }

    private static void addIndicatorSets(EntityManager aManager) {
        // INDICATOR SETS

        indSetOther = new IndicatorSet();
        indSetOther.setCreator(defaultUser);
        indSetOther.setSource("Other");
        indSetOther.setName("Other Indicator Set");
        indSetOther.setDescription("Another set of indicators");

        aManager.persist(indSetOther);

        // Load GRI using SAX Parser
        SAXGRIDriver saxgriDriver = null;
        saxgriDriver.baseDir = "war/";
        saxgriDriver = new SAXGRIDriver(aManager);
        try {
            saxgriDriver.parser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


    private static void addIndicators(EntityManager aManager) {
        Indicator waterConsumption = new Indicator();
        waterConsumption.setCreator(defaultUser);
        waterConsumption.setIdentifyingCode("waterconsumption");
        waterConsumption.setName("Water Consumption");
        waterConsumption.setDescription("The total water a consumption this year (in m3)");
        waterConsumption.setDomain(economy);
        waterConsumption.setSubdomain(economy.getSubdomain("Consumption and Use"));
        waterConsumption.setIndicatorSet(indSetGRI);

        /* CREATION OF AN INDICATOR: RAIN AMOUNT */
        Indicator rainAmountInd = new Indicator();
        rainAmountInd.setCreator(defaultUser);
        rainAmountInd.setIdentifyingCode("rainamount");
        rainAmountInd.setName("Rain Amount");
        rainAmountInd.setDescription("Average amount of rain (in mm) this year.");
        rainAmountInd.setDomain(ecology);
        rainAmountInd.setSubdomain(ecology.getSubdomain("Water and Air"));
        rainAmountInd.setIndicatorSet(indSetGRI);
        rainAmountInd.setSource("GRI");

        /* CREATION OF AN INDICATOR: BUSHFIRES */
        Indicator bushfireInd = new Indicator();
        bushfireInd.setCreator(defaultUser);
        bushfireInd.setIdentifyingCode("bushfires");
        bushfireInd.setName("Bushfires");
        bushfireInd.setDescription("Number of important bushfires (more than 1000ha burnt) this year.");
        bushfireInd.setDomain(ecology);
        bushfireInd.setSubdomain(ecology.getSubdomain("Materials and Energy"));
        bushfireInd.setIndicatorSet(indSetGRI);
        bushfireInd.setSource("GRI");
        Target target = new Target();
        target.setAssociatedIndicator(bushfireInd);
        target.setValue("1");

        /* CREATION OF THE INDICATOR: WATER SUPPLY */
        Indicator waterSupplyInd = new Indicator();
        waterSupplyInd.setCreator(defaultUser);
        waterSupplyInd.setIdentifyingCode("watersupply");
        waterSupplyInd.setName("Water Supply");
        waterSupplyInd.setDescription("The total water supply of the territory (in m3)");
        waterSupplyInd.setDomain(ecology);
        waterSupplyInd.setSubdomain(ecology.getSubdomain("Water and Air"));
        waterSupplyInd.setIndicatorSet(indSetGRI);
        waterSupplyInd.setSource("GRI");

        /* CREATION OF THE INDICATOR: WATER CONSUMPTION / PERSON */
        Indicator waterConsumptionPerPersonInd = new Indicator();
        waterConsumptionPerPersonInd.setCreator(defaultUser);
        waterConsumptionPerPersonInd.setIdentifyingCode("waterConsumptionPerPerson");
        waterConsumptionPerPersonInd.setName("Water Consumption / Person");
        waterConsumptionPerPersonInd.setDescription("The water consumption this year (in m3)");
        waterConsumptionPerPersonInd.setDomain(economy);
        waterConsumptionPerPersonInd.setSubdomain(economy.getSubdomain("Consumption and Use"));
        waterConsumptionPerPersonInd.setIndicatorSet(indSetOther);
        waterConsumptionPerPersonInd.setSource("City of Melbourne");

        /* CREATION OF THE INDICATOR: NUMBER OF DAMS */
        Indicator numberDamsInd = new Indicator();
        numberDamsInd.setCreator(defaultUser);
        numberDamsInd.setIdentifyingCode("numberDams");
        numberDamsInd.setName("Number of Dams");
        numberDamsInd.setDescription("The number of dams in the region");
        numberDamsInd.setDomain(ecology);
        numberDamsInd.setSubdomain(ecology.getSubdomain("Infrastructure and Constructions"));
        numberDamsInd.setIndicatorSet(indSetGRI);
        numberDamsInd.setSource("GRI");

        /* CREATION OF THE INDICATOR: NUMBER OF CRIMES */
        Indicator numberCrimesInd = new Indicator();
        numberCrimesInd.setCreator(defaultUser);
        numberCrimesInd.setIdentifyingCode("numberCrimes");
        numberCrimesInd.setName("Number of Crimes");
        numberCrimesInd.setDescription("The number of crimes recorded by police last year");
        numberCrimesInd.setDomain(politics);
        numberCrimesInd.setSubdomain(politics.getSubdomain("Security and Conflict"));
        numberCrimesInd.setIndicatorSet(indSetOther);
        numberCrimesInd.setSource("City of Melbourne");

//        aManager.persist(waterConsumption);
//        aManager.persist(rainAmountInd);
//        aManager.persist(bushfireInd);
//        aManager.persist(waterSupplyInd);
//        aManager.persist(waterConsumptionPerPersonInd);
//        aManager.persist(numberDamsInd);
//        aManager.persist(numberCrimesInd);

        List<Indicator> indicators = indSetOther.getIndicators();
        indicators.add(waterConsumption);
        indicators.add(rainAmountInd);
        indicators.add(bushfireInd);
        indicators.add(waterSupplyInd);
        indicators.add(waterConsumptionPerPersonInd);
        indicators.add(numberDamsInd);
        indicators.add(numberCrimesInd);
        indSetOther.setIndicators(indicators);
        aManager.merge(indSetOther);
    }
}
