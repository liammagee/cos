package edu.rmit.sustainability.data.query;

import com.clarkparsia.empire.impl.RdfQuery;
import edu.rmit.sustainability.model.IndicatorSet;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: E65691
 * Date: 27/05/11
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class PublicIndicatorSetQuery extends AbstractCollectionQuery {

    public PublicIndicatorSetQuery(EntityManager em) {
        super(em);
    }

    public List<IndicatorSet> doQuery(Project project, User user) {
        Query query = getEntityManager().createQuery(
                "WHERE { ?result a <http://circlesofsustainability.org/ontology#IndicatorSet> . " + "}");

        // this query should return instances of type Project
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, IndicatorSet.class);

        List aResults = query.getResultList();

        List<IndicatorSet> indicatorSets = new ArrayList<IndicatorSet>();
        for (Iterator iterator = aResults.iterator(); iterator.hasNext(); ) {
            indicatorSets.add((IndicatorSet) iterator.next());
        }
        return indicatorSets;
    }
}
