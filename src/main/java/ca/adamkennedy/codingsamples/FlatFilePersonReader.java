package ca.adamkennedy.codingsamples;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.adamkennedy.codingsamples.Person.Builder;

/**
 * Reads people from flat, delimited files.
 * First line must be the field headers
 * @author akennedy
 *
 */
public class FlatFilePersonReader implements PersonReader {

	private String delimiter;
	
	public static final String PLACEHOLDER = "!AK::!";

	
	/**
	 * reads people file
	 * 
	 * @return A list of all people read, in an arbitrary order
	 * @param datafile, a flat, delimited file.
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public List<Person> read(File dataFile) throws IOException, NumberFormatException {
		
		List<Person> people = new ArrayList<Person>();
		BufferedReader br = null;
		try {
			//Open Input File
			FileInputStream fstream = new FileInputStream(dataFile);
			DataInputStream in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			
			//Read Headers (first line) and remove quotes
			String[] rawfields = br.readLine().split(this.getDelimiter());
			List<String> fieldList = new ArrayList<String>();		
			for(String field : rawfields) {
				fieldList.add(field.replace("\"", ""));
			}


			//Read remaining lines as data, creating people as we go
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				
				//Remove any delimiters surrounded by quotes
				String cleanedLine = this.cleanLine(strLine);
				
				String[] tokens = cleanedLine.split(this.getDelimiter());
				List<String> dataList = new ArrayList<String>();
				for(String token : tokens) {
					//Replace any delimiters found before the split.
					token = token.replaceAll(FlatFilePersonReader.PLACEHOLDER, this.delimiter);
					
					token = token.replaceAll("\"", "");
					dataList.add(token);
				}
				
				//Create the new person and fill the data
				Builder builder = new Person.Builder();
				builder.setFirstname(dataList.get(fieldList.indexOf("firstname")));
				builder.setLastname(dataList.get(fieldList.indexOf("lastname")));
				builder.setAge(Double.parseDouble(dataList.get(fieldList.indexOf("age"))));
				builder.setStringBalance(dataList.get(fieldList.indexOf("balance")));
				
				people.add(builder.build());
			}
		}
		finally {
			try {
				br.close();
			}
			catch(Exception e) {
				//Swallow this exception
			}
		}

		return people;

	}
	
	/*
	 * Cleans string by placing a placeholder for every 
	 * delimiter found which is surrounded by double quotes
	 */
    private String cleanLine(String line) {
    	
       Pattern p = Pattern.compile("\"[a-zA-Z0-9 ]+[" + this.delimiter + "][ a-zA-Z0-9]+\"");
       Matcher matcher = p.matcher(line);
       
       while(matcher.find()){
           String replacement = matcher.group().replaceAll(this.delimiter, FlatFilePersonReader.PLACEHOLDER);
           line = line.replaceAll(matcher.group(), replacement);
       }
       
       return line;
   }	

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
}
