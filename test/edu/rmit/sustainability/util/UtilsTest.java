package edu.rmit.sustainability.util;

import java.io.IOException;
import java.util.List;
import java.util.Queue;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import edu.rmit.sustainability.util.Utils;

public class UtilsTest extends TestCase {

	
	@Before
	public void setUp() throws Exception {
	}
	
	public void testGenerateStringUUID() {
		String uuid = Utils.generateStringUUID();
		System.out.println(uuid);
		assertNotNull(uuid);
	}
	
	public void testGenerateUniqueName() {
		String uniqueName = Utils.generateUniqueName();
		System.out.println(uniqueName);
		assertNotNull(uniqueName);
	}
	
	public void testGetKeywords() {
    	List<String> keywords;
    	String text = "My text is one of the best texts in the world";
		String[] arrExpected = {"my", "text", "one", "best", "texts", "world"};
		
		try {
			keywords = Utils.getKeywords(text);
			assertNotNull(keywords);
			assertFalse("The keyword list shouldn't be empty.", keywords.isEmpty());
			
			// Check that the keywords found match the expected array of keywords
			int i = 0;
			for (String kw : keywords) {
				assertEquals(arrExpected[i], kw);
				System.out.print(kw + " ");
				i++;
			}
			System.out.print("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testGetKeywordsEmptyText() {
		List<String> keywords;
		
		try {
			keywords = Utils.getKeywords("");
			assertNotNull(keywords);
			assertTrue("The keyword list should be empty.", keywords.isEmpty());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() throws Exception {
	}


}
