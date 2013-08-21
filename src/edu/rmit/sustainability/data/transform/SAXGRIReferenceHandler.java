package edu.rmit.sustainability.data.transform;

import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

/**
 * 
 * @author lida
 *
 */

public class SAXGRIReferenceHandler extends DefaultHandler 
{
	public static String baseDir = "war/";
	
	
	public void start() throws ParserConfigurationException, SAXException, IOException
	{
	
		//TODO extracting the data from reference file

	}

	public void startDocument() throws SAXException 
	{
		//TODO extracting the data from reference file
	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException 
	{

		//TODO extracting the data from reference file
	}

	public void characters(char ch[], int start, int length) 
	{

	   //TODO extracting the data from reference file
	}
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException 
	{

		//TODO extracting the data from reference file
	}
	
}
