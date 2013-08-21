package edu.rmit.sustainability.assessment;


import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Liam
 * Date: 28/01/2010
 * Time: 5:42:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class CoSSelfAssessmentUtils {
    public static CoSSelfAssessment insertNew(String organisationName) {
        CoSSelfAssessment entry = new CoSSelfAssessment(organisationName);
        String[] backgroundConsiderations = {"", "", "", ""};
        entry.setBackgroundConsiderations(backgroundConsiderations);

        PersistenceManager pm = PMF.get().getPersistenceManager();
        pm.makePersistent(entry);

        return entry;
    }

    public static List<CoSSelfAssessment> getPage(
            Long keyOffset, int indexOffset) {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        Query query = pm.newQuery(CoSSelfAssessment.class);
        query.declareParameters(
                "Long keyOffset");
        StringBuilder filter = new StringBuilder();
        String combine = "";
        if (keyOffset != null && !keyOffset.equals("")) {
            if (filter.length() != 0) {
                filter.append(" && ");
            }
            filter.append("id >= keyOffset");
        }
        System.out.println("Filter is: " + filter.toString());
        if (filter.length() > 0) {
            query.setFilter(filter.toString());
        }
        query.setRange(indexOffset, indexOffset + 4);
        return (List<CoSSelfAssessment>) query.execute(keyOffset);
    }


    public static void delete(String id) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        CoSSelfAssessment entry = (CoSSelfAssessment) pm.getObjectById(CoSSelfAssessment.class, Long.valueOf(id));
        try {
            pm.deletePersistent(entry);
        } finally {
            pm.close();
        }
    }

    public static CoSSelfAssessment retrieve(String id) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        CoSSelfAssessment entry = (CoSSelfAssessment) pm.getObjectById(CoSSelfAssessment.class, Long.valueOf(id));
        return entry;
    }

    public static void updateStageOneQuestions(String id, int q, String d1, String d2, String d3, String d4) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        CoSSelfAssessment entry = (CoSSelfAssessment) pm.getObjectById(CoSSelfAssessment.class, Long.valueOf(id));
        String[] responses = {d1, d2, d3, d4};
        switch (q) {
            case 4:
                entry.setLevelOneQuestion4Responses(responses);
                break;
            case 3:
                entry.setLevelOneQuestion3Responses(responses);
                break;
            case 2:
                entry.setLevelOneQuestion2Responses(responses);
                break;
            case 1:
            default:
                entry.setLevelOneQuestion1Responses(responses);
                break;
        }
        try {
            pm.makePersistent(entry);
        } finally {
            pm.close();
        }
    }

    public static CoSSelfAssessment updateName(String id, String organisationName) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        CoSSelfAssessment entry = (CoSSelfAssessment) pm.getObjectById(CoSSelfAssessment.class, Long.valueOf(id));
        entry.setOrganisationName(organisationName);
        try {
            pm.makePersistent(entry);
        } finally {
            pm.close();
        }
        return entry;
    }


    public static CoSSelfAssessment updateBackgroundConsiderations(String id, String q1, String q2, String q3, String q4) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        CoSSelfAssessment entry = (CoSSelfAssessment) pm.getObjectById(CoSSelfAssessment.class, Long.valueOf(id));
        String[] backgroundConsiderations = {q1, q2, q3, q4};
        entry.setBackgroundConsiderations(backgroundConsiderations);
        try {
            pm.makePersistent(entry);
        } finally {
            pm.close();
        }
        return entry;
    }
}