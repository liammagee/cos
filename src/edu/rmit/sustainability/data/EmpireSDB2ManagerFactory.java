package edu.rmit.sustainability.data;

import com.clarkparsia.empire.Empire;
import com.clarkparsia.empire.jena.JenaEmpireModule;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EmpireSDB2ManagerFactory {
    private static EntityManager em;

    public static EntityManager createEmpireEntityManager(String propertyPath) {
        if (em == null) {
            try {
                // TODO: Needs to be externalised into configuration
                System.setProperty("empire.configuration.file", propertyPath);
                Empire.init(new JenaEmpireModule());
                em = Persistence.createEntityManagerFactory("SDB2")
                        .createEntityManager();
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return em;
    }
}
