package ca.adamkennedy.codingsamples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet")
public class PeopleServlet extends HttpServlet {


	private static final long serialVersionUID = 6185845693296451202L;

	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException
					{
		// Set response content type
		response.setContentType("text/html");
		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>Salesforce Coding Test</h1>");
		
		
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		if(sort == null || sort.trim().equals("")) {
			sort = "LASTNAME";
		}
		sort = sort.trim();
		
		if(order == null || order.trim().equals("")) {
			order = "A";
		}
		order = order.trim();
		if(!(order.equals("A") || order.equals("D"))) {
			out.println("<h1>INVALID SORT ORDER: " + order +"</h1>");
			order = "A";
		}

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
		
		try {
			PersonComparator.valueOf(sort);
		}
		catch(Exception e) {
			out.println("<h1>INVALID SORT FIELD: " + sort + "</h1>");
			sort = "LASTNAME";
		}
		
		

		String orderLabel = "";
		if(order.equals("A")) {
			orderLabel = "ascending";
			Collections.sort(people, PersonComparator.getComparator(PersonComparator.valueOf(sort)));
		}
		else {
			orderLabel = "descending";
			Collections.sort(people, Collections.reverseOrder(PersonComparator.getComparator(PersonComparator.valueOf(sort))));
		}
		out.println("<p>Data is sorted in " + orderLabel + " order by " + sort + "</p>");
		
		out.println("<table>");
		out.println("<tr><th>Lastname</th><th>Firstname</th><th width=100>Age</th><th>Balance</th></tr>");
		out.println("<tr>" +
				"<td><a href="+request.getRequestURL()+"?sort=LASTNAME&order=A>A</a>/<a href="+request.getRequestURL()+"?sort=LASTNAME&order=D>D</a></td>" +
				"<td><a href="+request.getRequestURL()+"?sort=FIRSTNAME&order=A>A</a>/<a href="+request.getRequestURL()+"?sort=FIRSTNAME&order=D>D</a></td>" +
				"<td><a href="+request.getRequestURL()+"?sort=AGE&order=A>A</a>/<a href="+request.getRequestURL()+"?sort=AGE&order=D>D</a></td>" +
				"<td><a href="+request.getRequestURL()+"?sort=BALANCE&order=A>A</a>/<a href="+request.getRequestURL()+"?sort=BALANCE&order=D>D</a></td>" +
				"</tr>");
		
		for (Person person : people) {
			out.println("<tr><td>" +
					person.getLastname() +
					"</td><td>" +
					person.getFirstname() +
					"</td><td>" +
					person.getAge() +
					"</td><td>$" +
					person.getBalance() +
					"</td></tr>");
		}
		out.println("</tr></table>");
					}

	public void destroy()
	{
		// do nothing.
	}

}
