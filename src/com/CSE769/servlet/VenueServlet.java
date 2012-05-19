package com.CSE769.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cse769.EJB.Entity.Venue;
import com.cse769.EJB.Service.VenueService;

/**
 * Servlet implementation class VenueServlet
 */
@WebServlet("/VenueServlet")
public class VenueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VenueServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	static String PAGE_HEADER = "<html><head /><body>";

	static String PAGE_FOOTER = "</body></html>";

	@EJB
	private VenueService venueService;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		/*userService.createUser("Sandeep", "passw0rd", "crsandy@gmail.com",
				"1444 Worthington Street", "Columbus", "Ohio", "43201",
				"6145863484", null, null);
		venueService.createVenue("Independence day", 10,
				"Indian Independence day", "Oval OSU", "Columbus", "Ohio",
				"43210", null);*/
		List<Venue> venues = venueService.getAllVenues();
		List<Venue> venues2 = venueService.findVenuesByName("ABC");
		
		writer.println(PAGE_HEADER);
	       
		for (Venue venue : venues) {
			writer.println("<h1>" + venue.getName() +  "</h1>");
		}
		
		for (Venue venue : venues2) {
			writer.println("<h3>" + venue.getName() +  "</h3>");
		}
		writer.println(PAGE_FOOTER);
		writer.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("Name");
		String size = request.getParameter("Size");
		String description = request.getParameter("Description");
		String address = request.getParameter("Address");
		String city = request.getParameter("City");
		String state = request.getParameter("State");
		String zipcode = request.getParameter("Zipcode");
		
		venueService.createVenue(name, new Integer(size), description, address,
				city, state, zipcode);
		
		PrintWriter writer = response.getWriter();		
		
		writer.println("<h3> Venue Created Successfully </h3>");
		writer.println("<a href=\"/OSU-eTicket-EJB-Servlet/Admin.html\">Home Page</a>");	
	}
}
