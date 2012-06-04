package com.CSE769.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cse769.EJB.Entity.User;
import com.cse769.EJB.Service.UserService;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserLoginServlet() {
		super();

	}

	@EJB
	UserService userService = new UserService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("username");
		String password = request.getParameter("password");

		PrintWriter writer = response.getWriter();

		writer.write("<html><head>");

		// None of this is secure since a user can just go straight to
		// Admin.html or Events.html
		if (name != null && name.length() != 0 && password != null
				&& password.length() != 0) {
			if (name.equals("admin") && password.equals("admin")) {
				// Hack for admin account
				response.sendRedirect("/OSU-eTicket-EJB-Servlet/Admin.html");
			} else {
				User user = userService.findUserByName(name);
				if (user == null) {
					writer.write("<h3>User not found</h3>");
				} else {
					if (user.getPassword().equals(password)) {
						response.sendRedirect("/OSU-eTicket-EJB-Servlet/Events.html");
					}
					writer.write("<h3>Wrong password</h3>");
				}
			}
		} else {
			writer.write("<h3>Either username or password is empty</h3>");
		}

		writer.write("</body></html>");
	}

}
