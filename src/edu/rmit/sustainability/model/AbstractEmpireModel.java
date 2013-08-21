package edu.rmit.sustainability.model;

import com.clarkparsia.empire.SupportsRdfId;
import com.clarkparsia.empire.annotation.SupportsRdfIdImpl;
import org.json.simple.JSONAware;

/**
 * Created by IntelliJ IDEA.
 * User: E65691
 * Date: 10/05/11
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractEmpireModel implements SupportsRdfId, Comparable {


	/**
	 * Possible actions that can be attached to any AbstractEmpireModel object,
	 * precising which action is performed on this object.
	 */
	public static final int ACT_PREUPDATE = 0;
	public static final int ACT_PREREMOVE = 1;
	public static final int ACT_PREPERSIST = 2;
	public static final int ACT_POSTLOAD = 3;
	public static final int ACT_POSTPERSIST = 4;
	public static final int ACT_POSTMERGE = 5;
	public static final int ACT_POSTREMOVING = 6;
	
    /**
     * Default support for the ID of an RDF concept
     */
    protected SupportsRdfId mIdSupport = new SupportsRdfIdImpl();

    /**
     * Provides context support, so the associated user
     */
    protected transient User currentUser;

    /**
     * Provides context support, so the associated project
     */
    protected transient Project currentProject;

    /**
     * Provides context support, so the action performed on this object (add, delete, etc...)
     */
    protected transient int lastPerformedAction;
    
    /**
     * @inheritDoc
     */
    public SupportsRdfId.RdfKey getRdfId() {
        return mIdSupport.getRdfId();
    }

    /**
     * @inheritDoc
     */
    public void setRdfId(final SupportsRdfId.RdfKey theId) {
        mIdSupport.setRdfId(theId);
    }


    public String getId() {
        return mIdSupport.getRdfId().value().toString();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }

    public int getLastPerformedAction() {
        return lastPerformedAction;
    }

    public void setLastPerformedAction(int lastPerformedAction) {
        this.lastPerformedAction = lastPerformedAction;
    }
    
    @Override
    public int hashCode() {
        return getRdfId() == null ? 0 : getRdfId().value().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEmpireModel that = (AbstractEmpireModel) o;

        if (mIdSupport != null ? !mIdSupport.equals(that.mIdSupport) : that.mIdSupport != null) return false;

        return true;
    }


    public int compareTo(Object o) {
        return (this.equals(o) || this.getRdfId() == null || ((AbstractEmpireModel) o).getRdfId() == null
                ? 0
                : this.getRdfId().value().toString().compareTo(((AbstractEmpireModel) o).getRdfId().value().toString()));
    }

}
