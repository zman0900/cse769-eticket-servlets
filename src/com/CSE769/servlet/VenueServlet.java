package com.CSE769.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cse769.EJB.Entity.Venue;
import com.cse769.EJB.Service.VenueService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class VenueServlet
 */
@WebServlet("/VenueServlet")
public class VenueServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@EJB
	private VenueService venueService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VenueServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private JsonObject venueToJson(Venue aVenue) {
		JsonObject jo = new JsonObject();
		jo.addProperty("id", aVenue.getVenueId());
		jo.addProperty("name", aVenue.getName());
		jo.addProperty("description", aVenue.getDescription());
		jo.addProperty("address", aVenue.getAddress());
		jo.addProperty("city", aVenue.getCity());
		jo.addProperty("state", aVenue.getState());
		jo.addProperty("zip", aVenue.getZipCode());
		jo.addProperty("size", aVenue.getSize());
		JsonObject oneJsonVenue = new JsonObject();
		oneJsonVenue.add("venue", jo);
		return oneJsonVenue;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		Map<String, String[]> params = request.getParameterMap();
		if (params.containsKey("id")) {
			Long id;
			try {
				id = Long.parseLong(params.get("id")[0]);
			} catch (NumberFormatException e) {
				return;
			}
			Venue v = venueService.getVenueById(id);
			if (v == null) {
				return;
			}
			writer.write(venueToJson(v).toString());
		} else {
			System.out.println("Load Venues");
			List<Venue> venues = venueService.getAllVenues();
			JsonArray json = new JsonArray();
			Iterator<Venue> venuesIterator = venues.iterator();
			while (venuesIterator.hasNext()) {
				json.add(venueToJson(venuesIterator.next()));
			}
			JsonObject result = new JsonObject();
			result.add("venues", json);
			writer.write(result.toString());
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

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
