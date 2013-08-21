package edu.rmit.sustainability.data.transform;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * 
 * @author lida
 *
 */

public class SAXGRIDriver {

    public SAXGRIDriver(EntityManager aManager){
        presentationHandler = new SAXGRIPresentationHandler();
        labelHandler = new SAXGRILabelHandler(aManager);
        categoryList = new SAXGRICategoryList();
    }

    public SAXGRIDriver(){
    }

	SAXGRIPresentationHandler presentationHandler = new SAXGRIPresentationHandler();
	SAXGRILabelHandler labelHandler = new SAXGRILabelHandler();	
	SAXGRICategoryList categoryList = new SAXGRICategoryList();
	
	public static String baseDir = "war/";
	

	public void parser() throws ParserConfigurationException, SAXException, IOException
	{
        System.out.println("Starting the presentation handler");
	   presentationHandler.start();	  
        System.out.println("Starting the category handler");
	   categoryList.start();
        System.out.println("Starting the label handler");
        HashMap map = categoryList.getCatgoryList();
        Set set = map.entrySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
            Object next = iterator.next();
            System.out.println(next);
        }
	   labelHandler.start(presentationHandler.getIndicatorList(),categoryList.getCatgoryList());
	}

}
