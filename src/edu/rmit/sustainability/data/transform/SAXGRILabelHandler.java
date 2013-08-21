package edu.rmit.sustainability.data.transform;

import java.io.*;

import com.google.inject.internal.cglib.core.CollectionUtils;
import edu.rmit.sustainability.setup.Setup;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import com.clarkparsia.empire.Empire;
import com.clarkparsia.empire.jena.JenaEmpireModule;
import com.sun.org.apache.bcel.internal.generic.NEW;

import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.model.*;

import javax.persistence.EntityManager;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author lida
 */

public class SAXGRILabelHandler extends DefaultHandler {

    // Variables for parsing the XBRL file
    private StringBuffer indicatorID = new StringBuffer();
    private StringBuffer indicatorTitle = new StringBuffer();
    private StringBuffer indicatorDescription = new StringBuffer();
    private StringBuffer GRIAspect = new StringBuffer();


    private int NO_FIELD = 0;
    private final int FIELD_ID = 1;
    private final int FIELD_TITLE = 2;
    private final int FIELD_DESCRIPTION = 3;
    private final int FIELD_ASPECT = 4;
    private int currentField = NO_FIELD;

    private int NO_DESCRIPTION = 1;
    private final int FIELD_RELEVANCE = 1;
    private final int FIELD_COMPILATION = 2;
    private final int FIELD_DEFINITION = 3;
    private final int FIELD_DOCUMENTATION = 4;
    private final int FIELD_REFERENCES = 5;

    private static boolean flag = false;

    private static int NO_GRI_ASPECT = 1;

    private int CURRENT_ASPECT = NO_GRI_ASPECT;

    private static int index = 0;


    private int currentDescriptionField = NO_DESCRIPTION;


    private char startRelTag[] = {'<', 'd', 'i', 'v', ' ', 'i', 'd', ' ', '=', ' ', 'R', 'e', 'l', 'e', 'v', 'a', 'n', 'c', 'e', '>'};
    private char startComTag[] = {'<', 'd', 'i', 'v', ' ', 'i', 'd', ' ', '=', ' ', 'C', 'o', 'm', 'p', 'i', 'l', 'a', 't', 'i', 'o', 'n', '>'};
    private char endTag[] = {'<', '/', 'd', 'i', 'v', '>'};

    private boolean capturedID = false;
    private boolean capturedTitle = false;
    private boolean capturedDescription = false;
    private boolean capturedAspect = false;
    private HashMap indicatorList = new HashMap();
    private HashMap GRIcategory = new HashMap();

    private HashMap test = new HashMap();

    public static String baseDir = "war/";

    // Output File
    private Writer output = null;
    private File file = new File("write.txt");

    // Empire model
    EntityManager aManager;
    User GRIIndCreator = new User();
    static IndicatorSet indSetGRI = null;

    public SAXGRILabelHandler() {}

    public SAXGRILabelHandler(EntityManager aManager) {

        try {
            output = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Initialising the GRI indicator Set
        indSetGRI = new IndicatorSet();
        indSetGRI.setCreator(Setup.defaultUser);
        indSetGRI.setSource("GRI");
        indSetGRI.setName("GRI Indicator Set");
        indSetGRI.setDescription("The set of indicator defined by the GRI");

        // Set up the empire configuration

        if (aManager == null) {
            try {
                aManager = EmpireSDB2ManagerFactory.createEmpireEntityManager((new File("./war/WEB-INF").getCanonicalPath())
                        + "//examples.empire.config.properties");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.setProperty("empire.configuration.file",
                    "war/WEB-INF/examples.empire.config.properties");
            System.setProperty("log4j.debug", "true");
            Empire.init(new JenaEmpireModule());
        }
        else {
            this.aManager = aManager;
        }
        aManager.persist(indSetGRI);


    }

    public void start(HashMap list, HashMap category) throws ParserConfigurationException, SAXException, IOException {

        for (int i = 0; i < list.size(); i++) {
            indicatorList.put(i, list.get(i));
        }

        for (int i = 0; i < category.size(); i++) {
            GRIcategory.put(i, category.get(i));
        }


        DefaultHandler handler = new SAXGRIReferenceHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(new File(baseDir + "GRI-XBRL/g3-2006-12-05-label.xml"), this);

    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {

        if (qName.equalsIgnoreCase("label")) {

            String firstAttributeValue = atts.getValue(1);
            System.out.println(firstAttributeValue);

            // Label processing
            if (atts.getValue(2).equals("http://www.xbrl.org/2003/role/label")) {
                boolean foundGRICategory = false;
                Collection griValues = GRIcategory.values();
                for (Iterator iterator = griValues.iterator(); iterator.hasNext(); ) {
                    String griCategoryValue = iterator.next().toString();
                    if (firstAttributeValue.startsWith(griCategoryValue)) {
                        foundGRICategory = true;
                        break;
                    }
                }
                if (foundGRICategory) {
                    currentField = FIELD_ASPECT;
                    capturedAspect = true;
                    flag = true;
                    GRIAspect.setLength(0);
                    return;
                }

                boolean foundIndicator = false;
                Collection indicatorListValues = indicatorList.values();
                for (Iterator iterator = indicatorListValues.iterator(); iterator.hasNext(); ) {
                    String indicatorValue = iterator.next().toString();
                    if (firstAttributeValue.startsWith(indicatorValue)) {
                        foundIndicator = true;
                        break;
                    }
                }

                if (foundIndicator) {
                    currentField = FIELD_ID;
                    capturedID = true;
                    flag = false;
                    indicatorID.setLength(0);
                }

            } else if (atts.getValue(2).equals("http://www.globalreporting.org/2006/G3/guidelineDefinition")) {

                boolean foundIndicator = false;
                Collection indicatorListValues = indicatorList.values();
                for (Iterator iterator = indicatorListValues.iterator(); iterator.hasNext(); ) {
                    String indicatorValue = iterator.next().toString();
                    if (firstAttributeValue.startsWith(indicatorValue)) {
                        foundIndicator = true;
                        break;
                    }
                }

                if (foundIndicator) {
                    currentField = FIELD_TITLE;
                    capturedTitle = true;
                    indicatorTitle.setLength(0);
                }

            } else if (atts.getValue(2).equals("http://www.globalreporting.org/2006/G3/protocolRelevance")) {

                currentField = FIELD_DESCRIPTION;
                currentDescriptionField = FIELD_RELEVANCE;
                capturedDescription = true;
                indicatorDescription.setLength(0);

            } else if (atts.getValue(2).equals("http://www.globalreporting.org/2006/G3/protocolCompilation")) {

                currentField = FIELD_DESCRIPTION;
                currentDescriptionField = FIELD_COMPILATION;
                capturedDescription = true;

            }


        }


    }


    public void characters(char ch[], int start, int length) {
        switch (currentField) {
            case FIELD_ASPECT:
                if (flag)
                    GRIAspect.append(ch, start, length);

                break;
            case FIELD_ID:
                indicatorID.append(ch, start, length);
                break;
            case FIELD_TITLE:
                indicatorTitle.append(ch, start, length);
                break;
            case FIELD_DESCRIPTION:
                if (currentDescriptionField == FIELD_RELEVANCE) {
                    indicatorDescription.append("<div id=\"relevance\">");
                    indicatorDescription.append(ch, start, length);
                    indicatorDescription.append("</div>");
                }
                if (currentDescriptionField == FIELD_COMPILATION) {
//                     indicatorDescription.append("<div id=\"compilation\">");
//		 			indicatorDescription.append(ch, index, length);
//                     indicatorDescription.append("</div>");
                } else
                    indicatorDescription.append(ch, start, length);
                break;
        }
    }


    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        // Reset the current field
        currentField = NO_FIELD;

        if (qName.equalsIgnoreCase("label")) {

            if (capturedID && capturedDescription && capturedTitle) {
                capturedID = false;
                capturedTitle = false;
                capturedDescription = false;
                capturedAspect = false;

                index = 0;


                // Empire Test

                try {
                    //Lida's note: TODO capturing the complete description divided by <div> tags
                    Indicator newIndicator = new Indicator();
                    //Lida's note: TODO changing the SPARQL query for the web interface and the properties here into GRI's ones
                    newIndicator.setIdentifyingCode(indicatorID.toString());
                    newIndicator.setName(indicatorTitle.toString());
                    newIndicator.setDescription(indicatorDescription.toString());
                    System.out.println(indicatorID.toString());
                    System.out.println(GRIAspect.toString());
                    newIndicator.setGRIAspect(GRIAspect.toString());


                    //Loading the data into the file for testing only
                    output.write(newIndicator.getIdentifyingCode() + "\n");
                    output.write(newIndicator.getName() + "\n");
                    output.write(newIndicator.getDescription() + "\n");
                    //Lida's note: TODO Manipulating the string
                    output.write(GRIAspect.toString() + "\n");

                    // Quick hack, to get most of the description in
                    // TODO: Use SDB layout2/index or hash instead
                    String indicatorDescriptionString =
                            indicatorDescription.toString();
                    if (indicatorDescriptionString.length() > 1000)
                        indicatorDescriptionString = indicatorDescriptionString.substring(0, 1000);
                    newIndicator.setDescription(indicatorDescriptionString);
                    newIndicator.setIndicatorSet(indSetGRI);
                    newIndicator.setSource("GRI");
                    newIndicator.setCreator(new User("Lida", "1234"));
//
//					// Liam's note: don't do this now
//					// aManager.persist(newIndicator);
//
//					// Add the indicator to the indicator set, then save the set
                    indSetGRI.addIndicator(newIndicator);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void endDocument() throws SAXException {

        aManager.merge(indSetGRI);
        try {
            output.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
