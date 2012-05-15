package com.CSE769.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cse769.EJB.Service.UserService;
import com.cse769.EJB.Service.VenueService;

/**
 * Servlet implementation class VenueServlet
 */
@WebServlet("/VenueServlet")
public class VenueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VenueServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@EJB
	private UserService userService;

	@EJB
	private VenueService venueService;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		userService.createUser("Sandeep", "passw0rd", "crsandy@gmail.com",
				"1444 Worthington Street", "Columbus", "Ohio", "43201",
				"6145863484");
		venueService.createVenue("Independence day", 10,
				"Indian Independence day", "Oval OSU", "Columbus", "Ohio",
				"43210");
		writer.close();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String name = req.getParameter("Name");
		String size = req.getParameter("Size");
		String description = req.getParameter("Description");
		String address = req.getParameter("Address");
		String city = req.getParameter("City");
		String state = req.getParameter("State");
		String zipcode = req.getParameter("Zipcode");
		
		venueService.createVenue(name, new Integer(size), description, address,
				city, state, zipcode);
	}
}
