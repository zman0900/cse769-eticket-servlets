package com.CSE769.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import com.cse769.EJB.Entity.EventCategory;
import com.cse769.EJB.Service.EventCategoryService;

@ManagedBean
public class CategoryBean {

	private String category;
	private ArrayList<String> categories = null;
	
	@EJB
    private EventCategoryService eventCategoryService = new EventCategoryService();

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;			
	}

	public ArrayList<String> getCategories() {
		List<EventCategory> catList = eventCategoryService.getAllCategories();
		categories = new ArrayList<String>();
		for (EventCategory eventCategory : catList) {
			categories.add(eventCategory.getCategory());
		}						
		return categories;
	}
	
	public String createCategory(){
		eventCategoryService.createEventCategory(category);	
		return "Admin";
	}
}
