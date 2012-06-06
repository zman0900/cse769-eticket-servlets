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

/**
 * Managed bean to allow JSF pages to interact with the {@link EventService}
 * 
 * @author group3
 */
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

	/**
	 * Returns the name of the name of the {@link Event}
	 * 
	 * @return the event
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the {@link Event}
	 * 
	 * @param name
	 *            a name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the {@link EventCategory} of the {@link Event}
	 * 
	 * @return the {@link EventCategory}
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Set the {@link EventCategory} of the {@link Event}
	 * 
	 * @param category
	 *            an {@link EventCategory}
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Returns the description of the {@link Event}
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description of the {@link Event}
	 * 
	 * @param description
	 *            a description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the cost of the {@link Event} in cents
	 * 
	 * @return the cost in cents
	 */
	public String getCost() {
		return cost;
	}

	/**
	 * Set the cost of the event in cents
	 * 
	 * @param cost
	 *            a cost in cents
	 */
	public void setCost(String cost) {
		this.cost = cost;
	}

	/**
	 * Returns the date of the {@link Event}
	 * 
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Set the date of the {@link Event}
	 * 
	 * @param date
	 *            a date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Returns the total number of {@link Ticket}s offered for the {@link Event}
	 * 
	 * @return the total number of seats
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * Set the total number of {@link Ticket}s offered for the {@link Event}.
	 * Don't do this without also creating the appropriate number of
	 * {@link Ticket}s.
	 * 
	 * @param quantity
	 *            the total number of {@link Ticket}s offered for the
	 *            {@link Event}
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/**
	 * Returns the {@link Venue} of the {@link Event}
	 * 
	 * @return the {@link Venue}
	 */
	public String getVenue() {
		return venue;
	}

	/**
	 * Set the {@link Venue} of the {@link Event}
	 * 
	 * @param venue
	 *            the {@link Venue}
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	/**
	 * Returns the {@link Event}
	 * 
	 * @return the {@link Event}
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * Set the {@link Event}
	 * 
	 * @return the string "Admin"
	 */
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
