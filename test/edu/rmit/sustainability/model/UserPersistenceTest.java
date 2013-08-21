/**
 * 
 */
package edu.rmit.sustainability.model;


import com.clarkparsia.empire.Empire;
import com.clarkparsia.empire.impl.RdfQuery;
import com.clarkparsia.empire.jena.JenaEmpireModule;
import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author E65691
 * Tests use of the Empire framework
 */
public class UserPersistenceTest {

    EntityManager aManager = null;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
        aManager = EmpireSDB2ManagerFactory.createEmpireEntityManager("war/WEB-INF/examples.empire.config.properties");
	}

	@Test
	public void userPersistenceTest() {
        User user = new User();
        user.setUsername("Lida");
        user.setPassword("1234");

        // Save the user
        aManager.persist(user);

        User userCopy = aManager.find(User.class, user.getRdfId());

        assertEquals(user, userCopy);




        // Update and save the project
        user.setUsername("Guillaume");
        aManager.merge(user);

        userCopy = aManager.find(User.class, user.getRdfId());
        assertEquals(userCopy.getUsername(), "Guillaume");
        assertEquals(userCopy.getPassword(), "1234");

        String queryString = "WHERE { ?result a <http://circlesofsustainability.org/ontology#User> . " +
                " ?result <http://circlesofsustainability.org/ontology#hasUsername> '" + user.getUsername() + "' . " +
                " ?result <http://circlesofsustainability.org/ontology#hasPassword> '" + user.getPassword() + "' . " +
                        "}";
        System.out.println(queryString);
        Query query = aManager.createQuery(queryString);
        // this query should return instances of type Book
        query.setHint(RdfQuery.HINT_ENTITY_CLASS, User.class);

        userCopy = (User)query.getResultList().get(0);

        assertEquals(user, userCopy);

        // Remove the project
        aManager.remove(user);
    }




	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
