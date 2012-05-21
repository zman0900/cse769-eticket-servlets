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

import com.cse769.EJB.Entity.Event;
import com.cse769.EJB.Service.EventService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/EventServlet")
public class EventServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@EJB
	private EventService eventService = new EventService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		Map<String, String[]> params = request.getParameterMap();
		if (params.containsKey("demo")) {
			boolean result = eventService.createDemoEvents();
			JsonObject jo = new JsonObject();
			if (result)
				jo.addProperty("result", "success");
			else
				jo.addProperty("result", "fail");
			writer.write(jo.toString());
		} else {
			System.out.println("Load Events");
			List<Event> events;
			if (params.containsKey("categoryid")) {
				Long categoryId;
				try {
				categoryId = Long.parseLong(params.get("categoryid")[0]);
				} catch (NumberFormatException e) {
					return;
				}
				events = eventService.findEventsByCategoryId(categoryId);
			} else {
				events = eventService.getAllEvents();
			}
			JsonArray json = new JsonArray();
			Iterator<Event> eventsIterator = events.iterator();
			while (eventsIterator.hasNext()) {
				Event e = eventsIterator.next();
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
				JsonObject oneJsonEvent = new JsonObject();
				oneJsonEvent.add("event", jo);
				json.add(oneJsonEvent);
			}

			JsonObject result = new JsonObject();
			result.add("events", json);
			writer.write(result.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
