package com.CSE769.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import com.cse769.EJB.Entity.EventCategory;
import com.cse769.EJB.Service.EventCategoryService;

/**
 * Managed bean to allow JSF pages to interact with the
 * {@link EventCategoryService}
 * 
 * @author group3
 */
@ManagedBean
public class CategoryBean {

	private String category;
	private ArrayList<String> categories = null;
	@EJB
	private EventCategoryService eventCategoryService = new EventCategoryService();

	/**
	 * Returns the name of the {@link EventCategory}
	 * 
	 * @return the name
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Set the name of the {@link EventCategory}
	 * 
	 * @param category
	 *            the name
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Returns an {@link ArrayList} of all {@link EventCategory}s
	 * 
	 * @return the {@link ArrayList} of all {@link EventCategory}s
	 */
	public ArrayList<String> getCategories() {
		List<EventCategory> catList = eventCategoryService.getAllCategories();
		categories = new ArrayList<String>();
		for (EventCategory eventCategory : catList) {
			categories.add(eventCategory.getCategory());
		}
		return categories;
	}

	/**
	 * Creates and stores a new {@link EventCategory}
	 * 
	 * @return the string "Admin"
	 */
	public String createCategory() {
		eventCategoryService.createEventCategory(category);
		return "Admin";
	}
}
