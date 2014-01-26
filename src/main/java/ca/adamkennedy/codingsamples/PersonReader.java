package ca.adamkennedy.codingsamples;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Interface for people reading
 * @author akennedy
 *
 */
public interface PersonReader {

	/**
	 * Reads people from datafile
	 * @param dataFile
	 * @return list of people
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	List<Person> read(File dataFile) throws IOException, NumberFormatException;
}
