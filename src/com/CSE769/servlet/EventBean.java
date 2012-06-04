package com.CSE769.servlet;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import com.cse769.EJB.Entity.Event;
import com.cse769.EJB.Entity.EventCategory;
import com.cse769.EJB.Entity.Venue;
import com.cse769.EJB.Service.EventCategoryService;
import com.cse769.EJB.Service.EventService;
import com.cse769.EJB.Service.VenueService;

@ManagedBean
public class EventBean {

	@EJB
	EventService eventService = new EventService();

	@EJB
	EventCategoryService eventCategoryService = new EventCategoryService();

	@EJB
	VenueService venueService = new VenueService();

	private String name;
	private String category;
	private String description;
	private String cost;
	private String date;
	private String quantity;
	private String venue;
	private Event event;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public Event getEvent() {
		return event;
	}

	public String setEvent() {
		List<EventCategory> eventCategory = eventCategoryService
				.findCategoryByName(category);
		EventCategory cat = null;
		for (EventCategory eventCategory2 : eventCategory) {
			cat = eventCategory2;
		}
		List<Venue> venueObject = venueService.findVenuesByName(venue);
		Venue ven = null;
		for (Venue venue2 : venueObject) {
			ven = venue2;
		}
		Double costd = new Double(cost);
		int costp = (int) (costd * 100.0f);
		eventService.createEvent(name, cat, description, costp, new Date(),
				new Integer(quantity), ven);
		return "Admin";
	}
}
