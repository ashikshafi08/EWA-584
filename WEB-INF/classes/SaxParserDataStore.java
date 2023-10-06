// [ ... the initial imports ... ]

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParserDataStore extends DefaultHandler {

    SmartDoorLock smartDoorLock;
    SmartDoorLockList smartDoorBell;
    SmartSpeaker smartSpeaker;
    SmartLight smartLight;
    SmartThermostat smartThermostat;
    
    static HashMap<String, SmartDoorLock> smartDoorLocks = new HashMap<>();
    static HashMap<String, SmartDoorLockList> smartDoorBells = new HashMap<>();
    static HashMap<String, SmartSpeaker> smartSpeakers = new HashMap<>();
    static HashMap<String, SmartLight> smartLights = new HashMap<>();
    static HashMap<String, SmartThermostat> smartThermostats = new HashMap<>();
    
    String consoleXmlFileName;
    String elementValueRead;
    String currentElement = "";

    public SaxParserDataStore(String consoleXmlFileName) {
        this.consoleXmlFileName = consoleXmlFileName;
        parseDocument();
    }

    //parse the xml using sax parser to get the data
    private void parseDocument() 
	{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try 
		{
	    SAXParser parser = factory.newSAXParser();
	    parser.parse(consoleXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
	}

    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {
        switch (elementName.toLowerCase()) {
            case "smartdoorlock":
                currentElement = "smartdoorlock";
                smartDoorLock = new SmartDoorLock();
                smartDoorLock.setId(attributes.getValue("id"));
                break;
            case "smartdoorbell":
                currentElement = "smartdoorbell";
                smartDoorBell = new SmartDoorLockList();
                smartDoorBell.setId(attributes.getValue("id"));
                break;
            case "smartspeaker":
                currentElement = "smartspeaker";
                smartSpeaker = new SmartSpeaker();
                smartSpeaker.setId(attributes.getValue("id"));
                break;
            case "smartlight":
                currentElement = "smartlight";
                smartLight = new SmartLight();
                smartLight.setId(attributes.getValue("id"));
                break;
            case "smartthermostat":
                currentElement = "smartthermostat";
                smartThermostat = new SmartThermostat();
                smartThermostat.setId(attributes.getValue("id"));
                break;
        }
    }

    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {

        switch (element.toLowerCase()) {
            case "smartdoorlock":
                smartDoorLocks.put(smartDoorLock.getId(), smartDoorLock);
                break;
            case "smartdoorbell":
                smartDoorBells.put(smartDoorBell.getId(), smartDoorBell);
                break;
            case "smartspeaker":
                smartSpeakers.put(smartSpeaker.getId(), smartSpeaker);
                break;
            case "smartlight":
                smartLights.put(smartLight.getId(), smartLight);
                break;
            case "smartthermostat":
                smartThermostats.put(smartThermostat.getId(), smartThermostat);
                break;
            case "name":
                setCurrentProductName(elementValueRead);
                break;
            case "price":
                setCurrentProductPrice(Double.parseDouble(elementValueRead));
                break;
            case "image":
                setCurrentProductImage(elementValueRead);
                break;
            case "manufacturer":
                setCurrentProductManufacturer(elementValueRead);
                break;
            case "discount":
                setCurrentProductDiscount(Double.parseDouble(elementValueRead));
                break;
        }
    }

    private void setCurrentProductName(String name) {
        switch (currentElement) {
            case "smartdoorlock":
                smartDoorLock.setName(name);
                break;
            case "smartdoorbell":
                smartDoorBell.setName(name);
                break;
            case "smartspeaker":
                smartSpeaker.setName(name);
                break;
            case "smartlight":
                smartLight.setName(name);
                break;
            case "smartthermostat":
                smartThermostat.setName(name);
                break;
        }
    }

    private void setCurrentProductPrice(Double price) {
        switch (currentElement) {
            case "smartdoorlock":
                smartDoorLock.setPrice(price);
                break;
            case "smartdoorbell":
                smartDoorBell.setPrice(price);
                break;
            case "smartspeaker":
                smartSpeaker.setPrice(price);
                break;
            case "smartlight":
                smartLight.setPrice(price);
                break;
            case "smartthermostat":
                smartThermostat.setPrice(price);
                break;
        }
    }

    private void setCurrentProductImage(String image) {
        switch (currentElement) {
            case "smartdoorlock":
                smartDoorLock.setImage(image);
                break;
            case "smartdoorbell":
                smartDoorBell.setImage(image);
                break;
            case "smartspeaker":
                smartSpeaker.setImage(image);
                break;
            case "smartlight":
                smartLight.setImage(image);
                break;
            case "smartthermostat":
                smartThermostat.setImage(image);
                break;
        }
    }

    private void setCurrentProductManufacturer(String manufacturer) {
        switch (currentElement) {
            case "smartdoorlock":
                smartDoorLock.setManufacturer(manufacturer);
                break;
            case "smartdoorbell":
                smartDoorBell.setManufacturer(manufacturer);
                break;
            case "smartspeaker":
                smartSpeaker.setManufacturer(manufacturer);
                break;
            case "smartlight":
                smartLight.setManufacturer(manufacturer);
                break;
            case "smartthermostat":
                smartThermostat.setManufacturer(manufacturer);
                break;
        }
    }

    
    private void setCurrentProductDiscount(Double discount) {
        switch (currentElement) {
            case "smartdoorlock":
                smartDoorLock.setDiscount(discount);
                break;
            case "smartdoorbell":
                smartDoorBell.setDiscount(discount);
                break;
            case "smartspeaker":
                smartSpeaker.setDiscount(discount);
                break;
            case "smartlight":
                smartLight.setDiscount(discount);
                break;
            case "smartthermostat":
                smartThermostat.setDiscount(discount);
                break;
        }
    }

   @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }



 public static void addHashmap() {
		String TOMCAT_HOME = System.getProperty("catalina.home");	
		new SaxParserDataStore(TOMCAT_HOME+"//webapps//assignNew//ProductCatalog.xml");
    } 
}