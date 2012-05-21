package com.CSE769.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cse769.EJB.Entity.EventCategory;
import com.cse769.EJB.Service.EventCategoryService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	@EJB
	private EventCategoryService ecs = new EventCategoryService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		System.out.println("Load Categories");
		List<EventCategory> categories =  ecs.getAllCategories();
		JsonArray json = new JsonArray();
		Iterator<EventCategory> categoriesIterator = categories.iterator();
		while (categoriesIterator.hasNext()) {
			EventCategory aCategory = categoriesIterator.next();
			JsonObject jo = new JsonObject();
			jo.addProperty("id", aCategory.getCategoryId());
			jo.addProperty("name", aCategory.getCategory());
			JsonObject oneJsonCategory = new JsonObject();
			oneJsonCategory.add("category", jo);
			json.add(oneJsonCategory);
		}
		JsonObject result = new JsonObject();
		result.add("categories", json);
		writer.write(result.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
