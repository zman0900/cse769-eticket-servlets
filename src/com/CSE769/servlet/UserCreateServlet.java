package com.CSE769.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cse769.EJB.Entity.User;
import com.cse769.EJB.Service.UserService;

/**
 * Servlet for creating {@link User}s
 * 
 * @author group3
 */
@WebServlet("/UserCreateServlet")
public class UserCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UserCreateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@EJB
	UserService userSevice = new UserService();

	static String PAGE_HEADER = "<html><head /><body>";

	static String PAGE_FOOTER = "</body></html>";

	/**
	 * Responds with a list of all {@link User}'s usernames followed by a link
	 * back to the Admin.html page
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<User> users = userSevice.getAllUsers();
		PrintWriter writer = response.getWriter();
		writer.println(PAGE_HEADER);
		for (User user : users) {
			writer.println(user.getUsername());
		}
		writer.println("<a href=\"/OSU-eTicket-EJB-Servlet/Admin.html\">Home Page</a>");
		writer.println(PAGE_FOOTER);
	}

	/**
	 * Creates and stores a new {@link User} with the parameters specified as
	 * "multipart/form-data" from an HTML form.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String address = request.getParameter("Address");
		String city = request.getParameter("City");
		String state = request.getParameter("State");
		String zipcode = request.getParameter("Zipcode");
		String phone = request.getParameter("phone");
		userSevice.createUser(username, password, email, address, city, state,
				zipcode, phone);

		PrintWriter writer = response.getWriter();
		writer.println(PAGE_HEADER);
		writer.println("<h3> User Created Successfully </h3>");
		writer.println("<a href=\"/OSU-eTicket-EJB-Servlet/Admin.html\">Home Page</a>");
		writer.println(PAGE_FOOTER);
	}
}
