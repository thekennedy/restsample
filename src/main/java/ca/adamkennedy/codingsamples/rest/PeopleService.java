package ca.adamkennedy.codingsamples.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import ca.adamkennedy.codingsamples.DataHandler;
import ca.adamkennedy.codingsamples.Person;
import ca.adamkennedy.codingsamples.PersonComparator;

@Path("/json/people")
public class PeopleService {	 


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{sortOrder}/{sortBy}")
    public List<Person> read(@PathParam("sortOrder") String sortOrder,@PathParam("sortBy") String sortBy) {
    	DataHandler dh = new DataHandler();
		List<Person> people = null;
		try {
			people = dh.loadPeople();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<PersonComparator> compares = new ArrayList<PersonComparator>();

		try {
			compares.add(PersonComparator.valueOf(sortBy));
		}
		catch(IllegalArgumentException e) {
			compares.add(PersonComparator.LASTNAME);
		}
		
		Collections.sort(people, PersonComparator.getComparator(compares.toArray(new PersonComparator[compares.size()])));
		
		return people;
    }

}

