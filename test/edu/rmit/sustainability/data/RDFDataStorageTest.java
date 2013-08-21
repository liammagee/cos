/**
 * 
 */
package edu.rmit.sustainability.data;


import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.rmit.sustainability.model.CriticalIssue;
import edu.rmit.sustainability.util.Utils;
import edu.rmit.sustainability.model.Project;

/**
 * @author E65691
 *
 */
public class RDFDataStorageTest {
	RDFDataStorage storage = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
        RDFDataStorage.baseDir = "war/";
		storage = new RDFDataStorage();
	}
	
	@Test 
	public void dataUpdateTest() {
		String subject = Utils.generateUniqueName();
		String predicate = Utils.generateUniqueName();
		String oldObject = "some old value";
		String newObject = "some new value";
		boolean insertResult = storage.dataInsert(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate), Utils.rdfWrapString(oldObject));
		boolean updateResult = storage.dataUpdate(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate), Utils.rdfWrapString(oldObject), Utils.rdfWrapString(newObject));
	    assertTrue(updateResult);
		

		// Check we have one result, as expected
		Map results = storage.dataQuery(Utils.rdfWrapNode(subject));
		assertFalse(results.get(predicate).equals(oldObject));
		assertTrue(results.get(predicate).equals(newObject));
	
		
		// Clean up		
    	boolean deleteResult = storage.dataDelete(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate), Utils.rdfWrapString(newObject));
    	assertTrue(deleteResult);    		
	}
	
	@Test 
	public void dataInsertTest() {
		String subject = Utils.generateUniqueName();
		String predicate = Utils.generateUniqueName();
		String object = "some value";
		boolean insertResult = storage.dataInsert(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate), Utils.rdfWrapString(object));
		assertTrue(insertResult);

		// Check we have one result, as expected
		Map results = storage.dataQuery(Utils.rdfWrapNode(subject));
		assertTrue(results.get(predicate).equals(object));
		
		// Clean up
    	boolean deleteResult = storage.dataDelete(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate), Utils.rdfWrapString(object));
     	assertTrue(deleteResult);
	}
	
	@Test 
	public void dataDeleteTest() {
		String subject = Utils.generateUniqueName();
		String predicate = Utils.generateUniqueName();
		String object = "some value";
		boolean insertResult = storage.dataInsert(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate), Utils.rdfWrapString(object));
		boolean deleteResult = storage.dataDelete(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate), Utils.rdfWrapString(object));
		assertTrue(deleteResult);

		// Check we have no results, as expected
		Map results = storage.dataQuery(Utils.rdfWrapNode(subject));
		assertNull(results.get(predicate));
	}

	@Test 
	public void dataQueryTest() {
		String subject = Utils.generateUniqueName();
		String predicate1 = Utils.generateUniqueName();
		String predicate2 = Utils.generateUniqueName();
		String object1 = "some value";
		String object2 = "another value";
		assertTrue(storage.dataInsert(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object1)));
		assertTrue(storage.dataInsert(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate2), Utils.rdfWrapString(object2)));
		
		Map results = storage.dataQuery(Utils.rdfWrapNode(subject));
		assertTrue(results.get(predicate1).equals(object1));
		assertTrue(results.get(predicate2).equals(object2));
		
		// Clean up
		assertTrue(storage.dataDelete(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object1)));
		assertTrue(storage.dataDelete(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate2), Utils.rdfWrapString(object2)));

		results = storage.dataQuery(Utils.rdfWrapNode(subject));
		assertNull(results.get(predicate1));
		assertNull(results.get(predicate2));
	}
	
	@Test 
	public void positionedDeleteAllPredicatesTest() {
		String subject = Utils.generateUniqueName();
		String predicate1 = Utils.generateUniqueName();
		String predicate2 = Utils.generateUniqueName();
		String object1 = "some value";
		String object2 = "another value";
		assertTrue(storage.dataInsert(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object1)));
		assertTrue(storage.dataInsert(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate2), Utils.rdfWrapString(object2)));
		
		// Clean up
		assertTrue(storage.positionedDeleteAllPredicates(Utils.rdfWrapNode(subject)));

		// Check we have no results, as expected
		Map results = storage.dataQuery(Utils.rdfWrapNode(subject));
		assertNull(results.get(predicate1));
		assertNull(results.get(predicate2));
	}
	
	@Test 
	public void positionedDeleteSinglePredicateTest() {
		String subject = Utils.generateUniqueName();
		String predicate1 = Utils.generateUniqueName();
		String predicate2 = Utils.generateUniqueName();
		String object1 = "some value";
		String object2 = "another value";
		assertTrue(storage.dataInsert(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object1)));
		assertTrue(storage.dataInsert(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate2), Utils.rdfWrapString(object2)));
		
		// Delete a single predicate
		assertTrue(storage.positionedDeleteSinglePredicate(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate2)));

		// Check we have only a single predicate, as expected
		Map results = storage.dataQuery(Utils.rdfWrapNode(subject));
		assertTrue(results.get(predicate1).equals(object1));
		assertNull(results.get(predicate2));

		// Clean up
	    assertTrue(storage.dataDelete(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object1)));
	}
	
	@Test 
	public void positionedUpdateSinglePredicateTest() {
		String subject = Utils.generateUniqueName();
		String predicate1 = Utils.generateUniqueName();
		String object1 = "some value";
		String object2 = "another value";
		assertTrue(storage.dataInsert(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object1)));
		
		// Update a single predicate
		assertTrue(storage.positionedUpdateSinglePredicate(Utils.rdfWrapNode(subject), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object2)));

		// Check we have the expected predicate value
		Map results = storage.dataQuery(Utils.rdfWrapNode(subject));
		assertTrue(results.get(predicate1).equals(object2));
		assertFalse(results.get(predicate1).equals(object1));
	}

	@Test
	public void retrieveAllCtriticalIssuesTest(){
		
		Project project = new  Project();
		String subject1 = Utils.generateUniqueURI(Utils.generateStringUUID());
		
		CriticalIssue issue1 = new CriticalIssue();
		String subject2 = Utils.generateUniqueURI(Utils.generateStringUUID());
		
	
		CriticalIssue issue2 = new CriticalIssue();
		String subject3 = Utils.generateUniqueURI(Utils.generateStringUUID());
		
		String object1 = "cos:Project";
		String object2 = "cos:CriticalIssue";
		String predicate1= "rdf:type";
		String predicate2= Utils.generateUniqueURI("hasCriticalIssue");
		String predicate3= Utils.generateUniqueURI("hasName");
		
		assertTrue(storage.dataInsert(Utils.rdfWrapNode(subject1), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object1)));
		assertTrue(storage.dataInsert(Utils.rdfWrapNode(subject1), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object1)));
		assertTrue(storage.dataInsert(Utils.rdfWrapNode(subject2), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object2)));
		assertTrue(storage.dataInsert(Utils.rdfWrapNode(subject3), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object2)));
		assertTrue(storage.dataInsert(Utils.rdfWrapNode(subject1), Utils.rdfWrapNode(predicate2), Utils.rdfWrapString(subject2)));
		assertTrue(storage.dataInsert(Utils.rdfWrapNode(subject1), Utils.rdfWrapNode(predicate2), Utils.rdfWrapString(subject3)));
		
		
		//Check the method retrieveSingleCriticalIssue
		assertNotNull(storage.retrieveAllCriticalIssues(project));
		
		// Clean up
	    assertTrue(storage.dataDelete(Utils.rdfWrapNode(subject1), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object1)));
	    assertTrue(storage.dataDelete(Utils.rdfWrapNode(subject1), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object1)));
		assertTrue(storage.dataDelete(Utils.rdfWrapNode(subject2), Utils.rdfWrapNode(predicate1), Utils.rdfWrapString(object2)));
		assertTrue(storage.dataDelete(Utils.rdfWrapNode(subject1), Utils.rdfWrapNode(predicate2), Utils.rdfWrapString(subject2)));
		assertTrue(storage.dataDelete(Utils.rdfWrapNode(subject1), Utils.rdfWrapNode(predicate2), Utils.rdfWrapString(subject2)));
		assertTrue(storage.dataDelete(Utils.rdfWrapNode(subject1), Utils.rdfWrapNode(predicate2), Utils.rdfWrapString(subject3)));
		

		
	}
	
	

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
//		storage.clearTriples();
	}

}
