package edu.rmit.sustainability.setup;

import com.clarkparsia.empire.Empire;
import com.clarkparsia.empire.impl.RdfQuery;
import com.clarkparsia.empire.jena.JenaEmpireModule;
import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.model.Indicator;
import edu.rmit.sustainability.model.IndicatorSet;
import edu.rmit.sustainability.model.Subdomain;
import edu.rmit.sustainability.server.LoginControllerServlet;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: E65691
 * Date: 30/06/11
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class AssignGRIIndicatorsToSubdomains {
    public static void main(String[] args) {
        System.setProperty("empire.configuration.file", "war/WEB-INF/examples.empire.config.properties");
        System.setProperty("log4j.debug", "true");
        Empire.init(new JenaEmpireModule());
        EntityManager aManager = Persistence.createEntityManagerFactory("SDB2").createEntityManager();


        // Step 1: Load subdomains
        Query query = aManager.createQuery("WHERE { " +
                        "?result a <http://circlesofsustainability.org/ontology#Subdomain>; <http://circlesofsustainability.org/ontology#subdomainID> ?sd . " +
                        "}" +
                        " ORDER BY ?sd "
        );

        // this query should return instances of type Project
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, Subdomain.class);

        List<Subdomain> subdomains = query.getResultList();
        for (Iterator<Subdomain> iterator = subdomains.iterator(); iterator.hasNext(); ) {
            Subdomain subdomain = iterator.next();
            System.out.println("Subdomain: " + subdomain.getName());
        }

        // Step 2: Create a new list for subdomains related to a given indicator
        List relatedSubdomains = new ArrayList();


        // Step 3: Retrieve an indicator (TODO: maybe need a list of codes first?)
        String identifyingCode = "EC1";
        query = aManager.createQuery("WHERE { " +
                        "?result a <http://circlesofsustainability.org/ontology#Indicator> . " +
                        "?result <http://circlesofsustainability.org/ontology#hasIdentifyingCode> 'EC1' . " +
                        "} "
        );
        // this query should return instances of type Project
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, Indicator.class);

        Indicator ec1 = (Indicator)query.getSingleResult();

        // Step 4: if the indicator is found, set the related subdomains, save the indicator, and clear the related subdomain list
        if (ec1 != null) {
            // Add subdomains
            relatedSubdomains.add(subdomains.get(4));
            relatedSubdomains.add(subdomains.get(6));
            ec1.setSubdomains(relatedSubdomains);
            aManager.merge(ec1);
            relatedSubdomains.clear();
        }

        // Step 5: Check the subdomains are in fact assigned to the indicator
        query = aManager.createQuery("WHERE { " +
                        "?result a <http://circlesofsustainability.org/ontology#Indicator> . " +
                        "?result <http://circlesofsustainability.org/ontology#hasIdentifyingCode> 'EC1' . " +
                        "}");
        // this query should return instances of type Project
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, Indicator.class);

        ec1 = (Indicator)query.getSingleResult();
        List<Subdomain> ec1Subdomains = ec1.getSubdomains();
        System.out.println("EC1 subdomains:");
        for (Iterator<Subdomain> iterator = ec1Subdomains.iterator(); iterator.hasNext(); ) {
            Subdomain subdomain = iterator.next();
            System.out.println(" - " + subdomain.getName());
        }


        /*
        // Load all indicators
        query = aManager.createQuery("WHERE { " +
                        "?result a <http://circlesofsustainability.org/ontology#Indicator> . " +
                        "?result <http://circlesofsustainability.org/ontology#belongsToIndicatorSet> ?gri . " +
                        "?gri <http://circlesofsustainability.org/ontology#hasName> 'GRI Indicator Set' . " +
                        "}");

        // this query should return instances of type Project
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, Indicator.class);

        List<Indicator> aResults = query.getResultList();
        for (Iterator<Indicator> iterator = aResults.iterator(); iterator.hasNext(); ) {
            Indicator indicator = iterator.next();
            System.out.println("GRI indicator: " + indicator.getIdentifyingCode());
        }
        */

        System.out.println("Do something...");
    }
}
