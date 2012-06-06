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

import com.cse769.EJB.Entity.EventCategory;
import com.cse769.EJB.Service.EventCategoryService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet for {@link EventCategory}s
 * 
 * @author group3
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@EJB
	private EventCategoryService ecs = new EventCategoryService();

	/**
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CategoryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Responds with an array of all {@link EventCategory}s in json format, or
	 * if the "id" parameter is specified in the url, responds with the
	 * {@link EventCategory} in json format with the specified id if it exists.
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
		System.out.println("CategoryServlet doGet");
		while (paramIterator.hasNext()) {
			Entry<String, String[]> p = paramIterator.next();
			System.out.println("\t" + p.getKey() + " = " + p.getValue()[0]);
		}
		List<EventCategory> categories = ecs.getAllCategories();
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

}
