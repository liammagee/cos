package edu.rmit.sustainability.setup;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;


import edu.rmit.sustainability.data.transform.SAXGRIDriver;
import org.xml.sax.SAXException;


public class SAXDriverTest {
	

    public static void main(String[] args) {
        SAXGRIDriver saxgriDriver = null;
        saxgriDriver.baseDir = "war/";
		saxgriDriver = new SAXGRIDriver();
        try {
            saxgriDriver.parser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
