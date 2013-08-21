package edu.rmit.sustainability.data.transform;

import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import java.util.HashMap;

/**
 * 
 * @author lida
 *
 */

public class SAXGRIPresentationHandler  extends DefaultHandler
{
	public static String baseDir = "war/";
	private HashMap indicatorList = new HashMap ();
	private String entity = null;
	private String subEnity = null;
	private static int index=0;
	
	public void start() throws ParserConfigurationException, SAXException, IOException
	{
	
		DefaultHandler handler = new SAXGRIReferenceHandler();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(new File(baseDir+"GRI-XBRL/g3-2006-12-05-presentation.xml"), this);
		handler.startDocument();

	}

	public void startDocument() throws SAXException 
	{

	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException 
	{
     
        if (qName.equals("loc") )
        {	
        	 entity= atts.getValue(2).toString();
       
        }
        

	}	

	public void endElement(String namespaceURI, String localName, String qName) throws SAXException 
	{
		//Extracting GRI indicators
		if (qName.equals("loc")) 
		{      
			subEnity=entity.substring(9,11);
	
			if(( entity.length()>12) && (subEnity.equals("EC") || subEnity.equals("EN") || subEnity.equals("LA") || subEnity.equals("HR") || subEnity.equals("SO") || subEnity.equals("PR")))
			{
		  	  indicatorList.put(index++, entity);		  	  	    	
			}  
		}
	}
	
	public HashMap getIndicatorList() 
	{
		return indicatorList;
	}

	public void setIndicatorList(HashMap indicatorList) 
	{
		this.indicatorList = indicatorList;
	}
}
