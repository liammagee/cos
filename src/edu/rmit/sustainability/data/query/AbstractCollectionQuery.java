package edu.rmit.sustainability.data.query;

import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: E65691
 * Date: 27/05/11
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class   AbstractCollectionQuery {
    private EntityManager entityManager;

    protected AbstractCollectionQuery(EntityManager em) {
        this.entityManager = em;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public abstract List<?> doQuery(Project project, User user);
}
