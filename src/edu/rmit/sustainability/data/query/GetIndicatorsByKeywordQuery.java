package edu.rmit.sustainability.data.query;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.clarkparsia.empire.impl.RdfQuery;

import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.model.Indicator;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

public class GetIndicatorsByKeywordQuery {
	
	private EntityManager entityManager;
	
	public GetIndicatorsByKeywordQuery() {
		try {
			this.entityManager = EmpireSDB2ManagerFactory.createEmpireEntityManager((new File("./war/WEB-INF").getCanonicalPath()) + "\\examples.empire.config.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Indicator> doQuery(Project project, User user, String keyword) {
		
		// TODO: DO not select indicators which already belong to the project
		Query query = entityManager.createQuery("WHERE { ?result a <http://circlesofsustainability.org/ontology#Indicator> . "
        		+ " ?result <http://circlesofsustainability.org/ontology#hasName> ?name FILTER regex(?name, '" + keyword + "', 'i') "
        		+ "}");

        query.setHint(RdfQuery.HINT_ENTITY_CLASS, Indicator.class);

//        query.setFirstResult(1);
//        query.setMaxResults(5);

        List<Indicator> aResults = query.getResultList();

        List<Indicator> indicators = new ArrayList<Indicator>();
        for (Iterator iterator = aResults.iterator(); iterator.hasNext(); ) {
        	indicators.add((Indicator) iterator.next());
        }
        return(indicators);
	}
	
}
