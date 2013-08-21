package edu.rmit.sustainability.data.query;

import com.clarkparsia.empire.impl.RdfQuery;
import edu.rmit.sustainability.model.Indicator;
import edu.rmit.sustainability.model.IndicatorSet;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: E65691
 * Date: 27/05/11
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrivateIndicatorQuery extends AbstractCollectionQuery {

    public PrivateIndicatorQuery(EntityManager em) {
        super(em);
    }

    public List<Indicator> doQuery(Project project, User user) {
        Query query;
        List aResults;
        query = getEntityManager().createQuery(
                "WHERE { " +
                        " ?result a <http://circlesofsustainability.org/ontology#Indicator> . " +
                        " ?result <http://circlesofsustainability.org/ontology#hasProject> <" + project.getRdfId().value() + "> . " +
                        "OPTIONAL { ?result <http://circlesofsustainability.org/ontology#belongsToIndicatorSet> ?name } . " +
                        "FILTER (!BOUND(?name)) "
                        + "}");

        // this query should return instances of type Project
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, Indicator.class);

        aResults = query.getResultList();

        List<Indicator> indicators = new ArrayList<Indicator>();
        for (Iterator iterator = aResults.iterator(); iterator.hasNext(); ) {
            indicators.add((Indicator) iterator.next());
        }
        return indicators;
    }

}
