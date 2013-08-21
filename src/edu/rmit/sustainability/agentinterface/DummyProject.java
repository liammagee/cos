package edu.rmit.sustainability.agentinterface;

import edu.rmit.sustainability.model.CriticalIssue;
import edu.rmit.sustainability.model.IssueComponent;
import edu.rmit.sustainability.model.Project;

import java.util.ArrayList;

/**
 * A dummy project about Water Crisis
 * Source: Wikipedia: 'Water Crisis'
 *
 * @see http://en.wikipedia.org/wiki/Water_crisis
 */
public class DummyProject extends Project {

    /**
     * Constructor of DummyProject
     * Creates a set of predefined Critical Issues and components
     */
    DummyProject() {
        super("Water Crisis");

        setProjectDescription("Water crisis is a term used to refer to the world�s water resources relative to human demand. The term has been applied to the worldwide water situation by the United Nations and other world organizations.");
        setGeneralIssue("Overall scarcity of usable water and water pollution");
        setNormativeGoal("Have enough water for everybody and reduce water pollution");

        ArrayList<CriticalIssue> ciList = new ArrayList<CriticalIssue>();


        /* Critical Issue: Inadequate access to drinking water */
        CriticalIssue accessWaterDrinkingCI = new CriticalIssue();
        accessWaterDrinkingCI.setName("Inadequate access to drinking water");
        accessWaterDrinkingCI.setDescription("Inadequate access to safe drinking water for about 884 million people");
        accessWaterDrinkingCI.setDomain(DummyKB.ecologyDomain);
        accessWaterDrinkingCI.addSubdomain(DummyKB.exchangeDistribution);
        ciList.add(accessWaterDrinkingCI);


        /* Critical Issue: Inadequate access to water for sanitation */
        CriticalIssue accessWaterSanitationCI = new CriticalIssue();
        accessWaterSanitationCI.setName("Inadequate access to water for sanitation");
        accessWaterSanitationCI.setDescription("Inadequate access to water for sanitation and waste disposal for 2.5 billion people");
        accessWaterSanitationCI.setDomain(DummyKB.ecologyDomain);
        accessWaterSanitationCI.addSubdomain(DummyKB.buildingInfrastructure);
        ciList.add(accessWaterSanitationCI);


        /* Critical Issue: Groundwater overdrafting */
        CriticalIssue groundwaterOverdraftingCI = new CriticalIssue();
        groundwaterOverdraftingCI.setName("Groundwater overdrafting");
        groundwaterOverdraftingCI.setDescription("Groundwater excessive use leading to diminished agricultural yields");
        groundwaterOverdraftingCI.setDomain(DummyKB.economyDomain);
        groundwaterOverdraftingCI.addSubdomain(DummyKB.consumptionLeisure);
        ciList.add(groundwaterOverdraftingCI);


        /* Critical Issue: Water pollution */
        CriticalIssue waterPollutionCI = new CriticalIssue();
//		waterPollutionCI.setId("waterPollution");
        waterPollutionCI.setName("Overuse and pollution of Water");
        waterPollutionCI.setDescription("Overuse and pollution of water resources harming biodiversity");
        waterPollutionCI.setDomain(DummyKB.ecologyDomain);
        waterPollutionCI.addSubdomain(DummyKB.emissionWaste);

        ArrayList<IssueComponent> compList = new ArrayList<IssueComponent>();
        // Component
        IssueComponent waterOveruseComp = new IssueComponent();
        waterOveruseComp.setName("Water Overuse");
        waterOveruseComp.setDescription("Overuse of water ressources");
        waterOveruseComp.setDomain(DummyKB.economyDomain);
        waterOveruseComp.addSubdomain(DummyKB.consumptionLeisure);
        waterOveruseComp.setParentIssue(waterPollutionCI);
        compList.add(waterOveruseComp);
        // Component
        IssueComponent waterPollutionComp = new IssueComponent();
        waterPollutionComp.setName("Water Pollution");
        waterPollutionComp.setDescription("Pollution of water ressources");
        waterPollutionComp.setDomain(DummyKB.ecologyDomain);
        waterPollutionComp.addSubdomain(DummyKB.emissionWaste);
        waterPollutionComp.setParentIssue(waterPollutionCI);
        compList.add(waterPollutionComp);
        // Component
        IssueComponent harmingBiodiversityComp = new IssueComponent();
        harmingBiodiversityComp.setName("Harming biodiversity");
        harmingBiodiversityComp.setDescription("Biodiversity is affected by water use and pollution");
        harmingBiodiversityComp.setDomain(DummyKB.ecologyDomain);
        harmingBiodiversityComp.addSubdomain(DummyKB.floraFauna);
        harmingBiodiversityComp.setParentIssue(waterPollutionCI);
        compList.add(harmingBiodiversityComp);

        waterPollutionCI.setComponentIssues(compList);
        ciList.add(waterPollutionCI);


        /* Critical Issue: Conflict for water */
        CriticalIssue conflictForWaterCI = new CriticalIssue();
        conflictForWaterCI.setName("Conflict for water");
        conflictForWaterCI.setDescription("Regional conflicts over scarce water resources sometimes resulting in warfare");
        conflictForWaterCI.setDomain(DummyKB.politicDomain);
        conflictForWaterCI.addSubdomain(DummyKB.conflictInsecurity);
        ciList.add(conflictForWaterCI);

        setCriticalIssues(ciList);
    }

}
