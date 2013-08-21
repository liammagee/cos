package edu.rmit.sustainability.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;

import static org.apache.lucene.util.Version.*;

public class Utils {
    public static final String NAMESPACE = "http://circlesofsustainability.org/ontology#";

    public static synchronized String generateStringUUID() {
        return UUID.randomUUID().toString();
    }

    public static synchronized String generateUniqueName() {
        return NAMESPACE + generateStringUUID();
    }

    /**
     * Lida : To create a URI for an unique ID that has been stored before on KB
     *
     * @param uniqueID
     * @return
     */
    public static synchronized String generateUniqueURI(String uniqueID) {
        return NAMESPACE + uniqueID;
    }

    /**
     * Simple method for escaping a String for RDF usage
     *
     * @return String
     */
    public static synchronized String rdfWrapString(String value) {
        return "'" + value + "'";
    }

    /**
     * Simple method for escaping a Node (in stringified form) for RDF usage
     *
     * @return String
     */
    public static synchronized String rdfWrapNode(String value) {
        return "<" + value + ">";
    }

    /**
     * Method returning a list of keywords from a given String. It uses the standard analyzer from
     * the Lucene library, which:
     * 	- tokenize
     * 	- put in lower case
     * 	- remove stop words
     * 	- try stemming words 
     * @param String text: the text to analyze
     * @return List<String> keywords: the keywords found in the text
     * @throws IOException: the method incrementToken can throw an IOException
     */
	public static List<String> getKeywords(String text) throws IOException {
		List<String> keywords = new ArrayList<String>();
//		Analyzer analyzer = new StandardAnalyzer(LUCENE_31);
//		TokenStream tokenStream = analyzer.tokenStream("FIELDNAME", new StringReader(text)); // Using this method, you will get a tokenstream
//
//		OffsetAttribute offsetAttribute = tokenStream.getAttribute(OffsetAttribute.class);
//		TermAttribute termAttribute = tokenStream.getAttribute(TermAttribute.class);
//		while (tokenStream.incrementToken()) {
//		    int startOffset = offsetAttribute.startOffset();
//		    int endOffset = offsetAttribute.endOffset();
//		    keywords.add(termAttribute.term());
//		}
		return (keywords);
	}
}
