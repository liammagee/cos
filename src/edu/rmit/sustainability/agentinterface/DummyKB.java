package edu.rmit.sustainability.agentinterface;

import edu.rmit.sustainability.model.Domain;
import edu.rmit.sustainability.model.Indicator;
import edu.rmit.sustainability.model.IndicatorSet;
import edu.rmit.sustainability.model.Subdomain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DummyKB {

    private static DummyKB _instance = null;

    private ArrayList<Indicator> _indicators = new ArrayList<Indicator>();

    public static Domain ecologyDomain;
    public static Subdomain earthWaterAir = new Subdomain();
    public static Subdomain floraFauna = new Subdomain();
    public static Subdomain placeHabitat = new Subdomain();
    public static Subdomain materialEnergy = new Subdomain();
    public static Subdomain buildingInfrastructure = new Subdomain();
    public static Subdomain emissionWaste = new Subdomain();
    public static Subdomain otherEcology = new Subdomain();


    public static Domain economyDomain;
    public static Subdomain productionRessourcing = new Subdomain();
    public static Subdomain exchangeDistribution = new Subdomain();
    public static Subdomain consumptionLeisure = new Subdomain();
    public static Subdomain workWelfare = new Subdomain();
    public static Subdomain technologyFabrication = new Subdomain();
    public static Subdomain wealthAllocation = new Subdomain();
    public static Subdomain otherEconomy = new Subdomain();

    public static Domain cultureDomain;
    public static Subdomain engagementPlacement = new Subdomain();
    public static Subdomain symbolismAesthetics = new Subdomain();
    public static Subdomain memoryProjection = new Subdomain();
    public static Subdomain enquiryLearning = new Subdomain();
    public static Subdomain wellbeingResilience = new Subdomain();
    public static Subdomain reproductionAffiliation = new Subdomain();
    public static Subdomain otherCulture = new Subdomain();

    public static Domain politicDomain;
    public static Subdomain organizationGovernance = new Subdomain();
    public static Subdomain rightJustice = new Subdomain();
    public static Subdomain communicationDissemination = new Subdomain();
    public static Subdomain representationNegociation = new Subdomain();
    public static Subdomain conflictInsecurity = new Subdomain();
    public static Subdomain dialogReconciliation = new Subdomain();
    public static Subdomain otherPolitic = new Subdomain();

    public static IndicatorSet indSetGRI;
    public static IndicatorSet indSetOther;

    private DummyKB() {

        // ECOLOGY DOMAIN
        ecologyDomain = new Domain();

        List<Subdomain> ecologySubdomains = new ArrayList<Subdomain>();
        earthWaterAir.setName("Earth, Water and Air");
        earthWaterAir.setDescription("Earth, Water and Air description");
        ecologySubdomains.add(earthWaterAir);
        earthWaterAir.setParentDomain(ecologyDomain);
        floraFauna.setName("Flora and Fauna");
        floraFauna.setDescription("Flora and Fauna description");
        floraFauna.setParentDomain(ecologyDomain);
        ecologySubdomains.add(floraFauna);
        placeHabitat.setName("Place and Habitat");
        placeHabitat.setDescription("Place and Habitat description");
        placeHabitat.setParentDomain(ecologyDomain);
        ecologySubdomains.add(placeHabitat);
        materialEnergy.setName("Materials and Energy");
        materialEnergy.setDescription("Materials and Energy description");
        materialEnergy.setParentDomain(ecologyDomain);
        ecologySubdomains.add(materialEnergy);
        buildingInfrastructure.setName("Buildings and Infrastructure");
        buildingInfrastructure.setDescription("Buildings and Infrastructure description");
        buildingInfrastructure.setParentDomain(ecologyDomain);
        ecologySubdomains.add(buildingInfrastructure);
        emissionWaste.setName("Emission and Waste");
        emissionWaste.setDescription("Emission and Waste description");
        emissionWaste.setParentDomain(ecologyDomain);
        ecologySubdomains.add(emissionWaste);
        otherEcology.setName("Other");
        otherEcology.setDescription("Other description");
        otherEcology.setParentDomain(ecologyDomain);
        ecologySubdomains.add(otherEcology);

        ecologyDomain.setName("Ecology");
        ecologyDomain.setDescription("Ecology domain description");
        ecologyDomain.setSubdomains(ecologySubdomains);

        // ECONOMY DOMAIN
        economyDomain = new Domain();

        List<Subdomain> economySubdomains = new ArrayList<Subdomain>();
        productionRessourcing.setName("Production and Ressourcing");
        productionRessourcing.setDescription("Production and Ressourcing description");
        productionRessourcing.setParentDomain(economyDomain);
        economySubdomains.add(productionRessourcing);
        exchangeDistribution.setName("Exchange and Distribution");
        exchangeDistribution.setDescription("Exchange and Distribution description");
        exchangeDistribution.setParentDomain(economyDomain);
        economySubdomains.add(exchangeDistribution);
        consumptionLeisure.setName("Consumption and Leisure");
        consumptionLeisure.setDescription("Consumption and Leisure description");
        consumptionLeisure.setParentDomain(economyDomain);
        economySubdomains.add(consumptionLeisure);
        workWelfare.setName("Work and Welfare");
        workWelfare.setDescription("Work and Welfare description");
        workWelfare.setParentDomain(economyDomain);
        economySubdomains.add(workWelfare);
        technologyFabrication.setName("Technology and Fabrication");
        technologyFabrication.setDescription("Technology and Fabrication description");
        technologyFabrication.setParentDomain(economyDomain);
        economySubdomains.add(technologyFabrication);
        wealthAllocation.setName("Wealth and Allocation");
        wealthAllocation.setDescription("Wealth and Allocation description");
        wealthAllocation.setParentDomain(economyDomain);
        economySubdomains.add(wealthAllocation);
        otherEconomy.setName("Other");
        otherEconomy.setDescription("Other description");
        otherEconomy.setParentDomain(economyDomain);
        economySubdomains.add(otherEconomy);

        economyDomain.setName("Economy");
        economyDomain.setDescription("Economy domain description");
        economyDomain.setSubdomains(economySubdomains);


        // CULTURE DOMAIN
        cultureDomain = new Domain();

        List<Subdomain> cultureSubdomains = new ArrayList<Subdomain>();
        engagementPlacement.setName("Engagement and Placement");
        engagementPlacement.setDescription("Engagement and Placement description");
        engagementPlacement.setParentDomain(cultureDomain);
        cultureSubdomains.add(engagementPlacement);
        symbolismAesthetics.setName("Symbolism and Aesthetics");
        symbolismAesthetics.setDescription("Symbolism and Aesthetics description");
        symbolismAesthetics.setParentDomain(cultureDomain);
        cultureSubdomains.add(symbolismAesthetics);
        memoryProjection.setName("Memory and Projection");
        memoryProjection.setDescription("Memory and Projection description");
        memoryProjection.setParentDomain(cultureDomain);
        cultureSubdomains.add(memoryProjection);
        enquiryLearning.setName("Enquiry and Learning");
        enquiryLearning.setDescription("Enquiry and Learning description");
        enquiryLearning.setParentDomain(cultureDomain);
        cultureSubdomains.add(enquiryLearning);
        wellbeingResilience.setName("Wellbeing and Resilience");
        wellbeingResilience.setDescription("Wellbeing and Resilience description");
        wellbeingResilience.setParentDomain(cultureDomain);
        cultureSubdomains.add(wellbeingResilience);
        reproductionAffiliation.setName("Reproduction and Affiliation");
        reproductionAffiliation.setDescription("Reproduction and Affiliation description");
        reproductionAffiliation.setParentDomain(cultureDomain);
        cultureSubdomains.add(reproductionAffiliation);
        otherCulture.setName("Other");
        otherCulture.setDescription("Other description");
        otherCulture.setParentDomain(cultureDomain);
        cultureSubdomains.add(otherCulture);

        cultureDomain.setName("Culture");
        cultureDomain.setDescription("Culture domain description");
        cultureDomain.setSubdomains(cultureSubdomains);

        // CULTURE DOMAIN
        politicDomain = new Domain();

        List<Subdomain> politicSubdomains = new ArrayList<Subdomain>();
        organizationGovernance.setName("Organizationand and Governance");
        organizationGovernance.setDescription("Organization and Governance description");
        organizationGovernance.setParentDomain(politicDomain);
        politicSubdomains.add(organizationGovernance);
        rightJustice.setName("Rights and Justice");
        rightJustice.setDescription("Rights and Justice description");
        rightJustice.setParentDomain(politicDomain);
        politicSubdomains.add(rightJustice);
        communicationDissemination.setName("Communication and Dissemination");
        communicationDissemination.setDescription("Communication and Dissemination description");
        communicationDissemination.setParentDomain(politicDomain);
        politicSubdomains.add(communicationDissemination);
        representationNegociation.setName("Reprensentation and Negociation");
        representationNegociation.setDescription("Reprensentation and Negociation description");
        representationNegociation.setParentDomain(politicDomain);
        politicSubdomains.add(representationNegociation);
        conflictInsecurity.setName("Conflict and Insecurity");
        conflictInsecurity.setDescription("Conflict and Insecurity description");
        conflictInsecurity.setParentDomain(politicDomain);
        politicSubdomains.add(conflictInsecurity);
        dialogReconciliation.setName("Dialogue and Reconciliation");
        dialogReconciliation.setDescription("Dialogue and Reconciliation description");
        dialogReconciliation.setParentDomain(politicDomain);
        politicSubdomains.add(dialogReconciliation);
        otherPolitic.setName("Other");
        otherPolitic.setDescription("Other description");
        otherPolitic.setParentDomain(politicDomain);
        politicSubdomains.add(otherPolitic);

        politicDomain.setName("Politic");
        politicDomain.setDescription("Politic domain description");
        politicDomain.setSubdomains(politicSubdomains);

        // INDICATORS SET
        indSetGRI = new IndicatorSet();
        indSetGRI.setSource("GRI");
        indSetGRI.setName("GRI Indicator Set");
        indSetGRI.setDescription("The set of indicator defined by the GRI");

        indSetOther = new IndicatorSet();
        indSetOther.setSource("Other");
        indSetOther.setName("Other Indicator Set");
        indSetOther.setDescription("Another set of indicators");
    }

    public static DummyKB getInstance() {
        if (_instance == null)
            _instance = new DummyKB();
        return (_instance);
    }

    public void addIndicator(Indicator ind) {
        System.out.println("Adding indicator: '" + ind.getName() + "'");
        _indicators.add(ind);
    }

    public Indicator getIndicator(String id) throws Exception {
        Iterator<Indicator> it = _indicators.iterator();
        Indicator ind;

        while (it.hasNext()) {
            ind = it.next();
            if (ind.getId().equals(id))
                return (ind);
        }
        throw new Exception("No such Indicator in the KB");
    }

    public List<Indicator> getIndicatorsByKeywords(List<String> keywords) throws Exception {
        List<Indicator> results = new ArrayList<Indicator>();
        for (String keyword : keywords) {
            try {
                results.addAll(getIndicatorsByKeyword(keyword));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return (results);
    }

    public List<Indicator> getIndicatorsByKeyword(String keyword) throws Exception {
        Iterator<Indicator> it = _indicators.iterator();
        Indicator ind;
        List<Indicator> results = new ArrayList<Indicator>();

        if (it.hasNext()) {
            while (it.hasNext()) {
                ind = it.next();
                String[] wordsName = ind.getName().split(" ");
                String[] wordsDesc = ind.getDescription().split(" ");

                ArrayList<String> arrWords = (ArrayList<String>) Arrays.asList(wordsName);
                arrWords.addAll((ArrayList<String>) Arrays.asList(wordsDesc));
                String[] words = arrWords.toArray(new String[arrWords.size()]);

                for (String word : words)
                    if (word.equals(keyword))
                        results.add(ind);
            }
            return (results);
        }
        throw new Exception("No Indicator in the KB containing: '" + keyword + "'");
    }
}
