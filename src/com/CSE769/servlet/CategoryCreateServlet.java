package com.CSE769.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cse769.EJB.Service.EventCategoryService;

/**
 * Servlet implementation class CategoryCreateServlet
 */
@WebServlet("/CategoryCreateServlet")
public class CategoryCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @EJB
    EventCategoryService eventCategoryService = new EventCategoryService();

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
		String category = request.getParameter("category");
		eventCategoryService.createEventCategory(category);
		
		PrintWriter writer = response.getWriter();		
		
		writer.println("<h3> Category Created Successfully </h3>");
		writer.println("<a href=\"/OSU-eTicket-EJB-Servlet/Admin.html\">Home Page</a>");	
	}

}
