package com.CSE769.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
 * Servlet for {@link Venue}s
 * 
 * @author group3
 */
@WebServlet("/VenueServlet")
public class VenueServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@EJB
	private VenueService venueService;

	static String PAGE_HEADER = "<html><head /><body>";

	static String PAGE_FOOTER = "</body></html>";

	/**
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public VenueServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Converts the specified {@link Venue} to json
	 * 
	 * @param aVenue
	 *            the {@link Venue}
	 * @return a {@link Venue} as a {@link JsonObject}
	 */
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

	/**
	 * With no parameters specified, responds with an array of all {@link Venue}
	 * s in json format. <br />
	 * <br />
	 * If parameter "id=num" is specified, responds with the {@link Venue} in
	 * json format which has the specified id.
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		Map<String, String[]> params = request.getParameterMap();
		Iterator<Entry<String, String[]>> paramIterator = params.entrySet()
				.iterator();
		System.out.println("VenueServlet doGet");
		while (paramIterator.hasNext()) {
			Entry<String, String[]> p = paramIterator.next();
			System.out.println("\t" + p.getKey() + " = " + p.getValue()[0]);
		}
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

	/**
	 * Creates and stores a new {@link Venue} with the parameters specified as
	 * "multipart/form-data" from an HTML form.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
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
		writer.println(PAGE_HEADER);
		writer.println("<h3> Venue Created Successfully </h3>");
		writer.println("<a href=\"/OSU-eTicket-EJB-Servlet/Admin.html\">Home Page</a>");
		writer.println(PAGE_FOOTER);
	}
}
