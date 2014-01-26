package ca.adamkennedy.codingsamples;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for loading people from data files.
 * 
 * @author akennedy
 *
 */
public class DataHandler {


	/**
	 * loads people from the supplied data files.
	 * 
	 * @return List<Person> A list of all people read, in an arbitrary order
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public List<Person> loadPeople() throws IOException, NumberFormatException {

		ClassLoader cl = Thread.currentThread().getContextClassLoader();

		File xmlfile = new File(cl.getResource("data.xml").getFile());
		File data1file = new File(cl.getResource("data1.txt").getFile());
		File data2file = new File(cl.getResource("data2.txt").getFile());


		List<Person> people = new ArrayList<Person>();

		people.addAll(new XmlFilePersonReader().read(xmlfile));

		FlatFilePersonReader ffpr = new FlatFilePersonReader();
		ffpr.setDelimiter(","); //Comma delimited
		people.addAll(ffpr.read(data1file));

		ffpr.setDelimiter("\\s+"); //White space delimited
		people.addAll(ffpr.read(data2file));


		return people;		

	}
	

}
