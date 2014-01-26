package ca.adamkennedy.codingsamples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;


import ca.adamkennedy.codingsamples.Person.Builder;

/**
 * Reads people from XML files.
 * @author akennedy
 *
 */
public class XmlFilePersonReader implements PersonReader {

	/**
	 * reads people file
	 * 
	 * @return A list of all people read, in an arbitrary order
	 * @param datafile, an XML file.
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public List<Person> read(File dataFile) throws IOException, NumberFormatException {
		
		List<Person> people = new ArrayList<Person>();

		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = null;
			try {
				docBuilder = docBuilderFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Read XML data file
			Document doc = docBuilder.parse(dataFile);
			doc.getDocumentElement().normalize();

			NodeList peopleList = doc.getElementsByTagName("person");

			//Iterate over people 
			for (int i = 0; i < peopleList.getLength(); i++) {
				Node personNode = peopleList.item(i);
				Builder builder = new Person.Builder();
				builder.setId(Integer.parseInt(this.getNodeAttribute(personNode, "id")));
				builder.setFirstname(this.getNodeValue(personNode, "firstname"));
				builder.setLastname(this.getNodeValue(personNode, "lastname"));
				builder.setAge(Double.parseDouble(this.getNodeValue(personNode, "age")));
				builder.setStringBalance(this.getNodeValue(personNode, "balance"));
				
				
				people.add(builder.build());
				
			}

		} catch (Exception e) {
			//Treat all exceptions as IO errors
			throw new IOException(e.getMessage());

		} 
		return people;
	}
	
	
	/*
	 * Returns element's value from node 
	 */
	private String getNodeValue(Node node, String elementName) {
		Element element = (Element)((Element)node).getElementsByTagName(elementName).item(0);
		NodeList nodeList = element.getChildNodes();
		return ((Node)nodeList.item(0)).getNodeValue().trim();
	}
	
	/*
	 * Returns attribute's value from node
	 */
	private String getNodeAttribute(Node node, String attributeName) {
		return ((Element)node).getAttribute(attributeName);
		
	}	

}
