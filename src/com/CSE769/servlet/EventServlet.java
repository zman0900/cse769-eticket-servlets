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

import com.cse769.EJB.Entity.Event;
import com.cse769.EJB.Entity.EventCategory;
import com.cse769.EJB.Service.EventService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet for {@link Event}s
 * 
 * @author group3
 */
@WebServlet("/EventServlet")
public class EventServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@EJB
	private EventService eventService = new EventService();

	/**
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public EventServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Converts the specified {@link Event} to json, including the number of
	 * unsold {@link Ticket}s if withAvailableTickets is true
	 * 
	 * @param e
	 *            the {@link Event}
	 * @param withAvailableTickets
	 *            include the number of unsold {@link Ticket}s if true
	 * @return the {@link Event} as a {@link JsonObject}
	 */
	private JsonObject eventToJson(Event e, Boolean withAvailableTickets) {
		JsonObject jo = new JsonObject();
		jo.addProperty("id", e.getEventId());
		jo.addProperty("name", e.getName());
		jo.addProperty("description", e.getDescription());
		jo.addProperty("cost", (float) e.getCost() / 100);
		jo.addProperty("quantity", e.getQuantity());
		jo.addProperty("venue", e.getVenue().getName());
		jo.addProperty("venue_id", e.getVenue().getVenueId());
		jo.addProperty("date", e.getDate().getTime());
		jo.addProperty("category", e.getCategory().getCategory());
		jo.addProperty("category_id", e.getCategory().getCategoryId());
		if (withAvailableTickets) {
			Long availableTickets = eventService.getNumOfAvailableTickets(e
					.getEventId());
			jo.addProperty("available", availableTickets);
		}
		JsonObject oneJsonEvent = new JsonObject();
		oneJsonEvent.add("event", jo);
		return oneJsonEvent;
	}

	/**
	 * With no parameters specified, responds with an array of all {@link Event}
	 * s in json format. <br />
	 * <br />
	 * If parameter "demo" is specified, creates demo events and responds with
	 * "success=true/false" in json format. <br />
	 * <br />
	 * If parameter "id=num" is specified, responds with the {@link Event} in
	 * json format which has the specified id. <br />
	 * <br />
	 * If parameter "categoryid=num" is specified, responds with an array of
	 * {@link Event}s in json format which have an {@link EventCategory} with
	 * the specified id. <br />
	 * <br />
	 * If parameter "venueid=num" is specified, responds with an array of
	 * {@link Event}s in json format which have a {@link Venue} with the
	 * specified id. <br />
	 * <br />
	 * If parameter "search=string" is specified, responds with an array of
	 * {@link Event}s in json format whose names contain the specified string. <br />
	 * <br />
	 * If multiple parameters are specified, only the first one listed above is
	 * performed.
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		Map<String, String[]> params = request.getParameterMap();
		Iterator<Entry<String, String[]>> paramIterator = params.entrySet()
				.iterator();
		System.out.println("EventServlet doGet");
		while (paramIterator.hasNext()) {
			Entry<String, String[]> p = paramIterator.next();
			System.out.println("\t" + p.getKey() + " = " + p.getValue()[0]);
		}
		if (params.containsKey("demo")) {
			boolean result = eventService.createDemoEvents();
			JsonObject jo = new JsonObject();
			if (result)
				jo.addProperty("result", "success");
			else
				jo.addProperty("result", "fail");
			writer.write(jo.toString());
		} else if (params.containsKey("id")) {
			Long id;
			try {
				id = Long.parseLong(params.get("id")[0]);
			} catch (NumberFormatException e) {
				return;
			}
			Event e = eventService.getEventById(id);
			if (e == null) {
				return;
			}
			writer.write(eventToJson(e, true).toString());
		} else {
			List<Event> events;
			if (params.containsKey("categoryid")) {
				Long categoryId;
				try {
					categoryId = Long.parseLong(params.get("categoryid")[0]);
				} catch (NumberFormatException e) {
					return;
				}
				events = eventService.findEventsByCategoryId(categoryId);
			} else if (params.containsKey("venueid")) {
				Long venueId;
				try {
					venueId = Long.parseLong(params.get("venueid")[0]);
				} catch (NumberFormatException e) {
					return;
				}
				events = eventService.findEventsByVenueId(venueId);
			} else if (params.containsKey("search")) {
				String search = params.get("search")[0];
				events = eventService.searchEventsByName(search);
			} else {
				events = eventService.getAllEvents();
			}
			JsonArray json = new JsonArray();
			Iterator<Event> eventsIterator = events.iterator();
			while (eventsIterator.hasNext()) {
				json.add(eventToJson(eventsIterator.next(), false));
			}

			JsonObject result = new JsonObject();
			result.add("events", json);
			writer.write(result.toString());
		}
	}

}
