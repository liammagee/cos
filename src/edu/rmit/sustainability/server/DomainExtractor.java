package edu.rmit.sustainability.server;

import com.clarkparsia.empire.impl.RdfQuery;
import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.model.Domain;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DomainExtractor implements Serializable {
    public DomainExtractor() {
    }


    public static List<Domain> getDomains() {
        EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
        List<Domain> domains = new ArrayList<Domain>();
        if (em != null) {
            Query query = em.createQuery(
                    "WHERE { ?result a <http://circlesofsustainability.org/ontology#Domain> . " +
                            "}");

            // this query should return instances of type Book
            query.setHint(RdfQuery.HINT_ENTITY_CLASS, Domain.class);

            List aResults = query.getResultList();
            for (Iterator iterator = aResults.iterator(); iterator.hasNext(); ) {
                domains.add((Domain) iterator.next());
            }
        }
        return domains;
    }
}