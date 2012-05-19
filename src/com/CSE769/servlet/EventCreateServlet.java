package com.CSE769.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cse769.EJB.Entity.Event;
import com.cse769.EJB.Entity.EventCategory;
import com.cse769.EJB.Entity.Venue;
import com.cse769.EJB.Service.EventCategoryService;
import com.cse769.EJB.Service.EventService;
import com.cse769.EJB.Service.VenueService;

/**
 * Servlet implementation class EventCreate
 */
@WebServlet("/EventCreateServlet")
public class EventCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @EJB
    EventService eventService = new EventService();
    
    @EJB
    EventCategoryService eventCategoryService = new EventCategoryService();
    
    @EJB
    VenueService venueService = new VenueService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String category = request.getParameter("category");
		String description = request.getParameter("description");
		String cost = request.getParameter("cost");
		String date = request.getParameter("date");
		String quantity = request.getParameter("quantity");
		String venue = request.getParameter("venue");
		
		List<EventCategory> eventCategory = eventCategoryService.findCategoryByName(category);
		
		EventCategory cat = null;
		for (EventCategory eventCategory2 : eventCategory) {
			cat = eventCategory2;
		}
		
		List<Venue> venueObject = venueService.findVenuesByName(venue);		
		
		Venue ven = null;
		for (Venue venue2 : venueObject) {
			ven = venue2;
		}
		
		eventService.createEvent(name, cat, description, new Double(cost), new Date(), new Integer(quantity), ven);
		
		PrintWriter writer = response.getWriter();		
		List<Event> events = eventService.getAllEvents();     
		for (Event event : events) {
			writer.println("<h1>" + event.getName() +  "</h1>");
		}		
	}

}
