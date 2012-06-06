package com.CSE769.servlet;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import com.cse769.EJB.Entity.Venue;
import com.cse769.EJB.Service.VenueService;

/**
 * Managed bean to allow JSF pages to interact with the {@link VenueService}.
 * This is currently unused, so the rest in not documented.
 * 
 * @author group3
 */
@ManagedBean
public class VenueBean {

	@EJB
	private VenueService venueService;
	private String name;
	private String size;
	private String description;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private List<String> venues;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public List<String> getVenues() {
		List<Venue> ven = venueService.getAllVenues();
		venues = new ArrayList<String>();
		for (Venue venue : ven) {
			venues.add(venue.getName());
		}
		return venues;
	}

	public void setVenues(List<String> venues) {
		this.venues = venues;
	}

}
