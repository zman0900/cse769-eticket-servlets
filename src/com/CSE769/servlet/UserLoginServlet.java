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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		
		PrintWriter writer = response.getWriter();
		
		if (name != null && name.length() != 0 && password != null
				&& password.length() != 0) {
			if(name.equals("admin") && password.equals("admin")){
				response.sendRedirect("/OSU-eTicket-EJB-Servlet/Admin.html");
			}
			List<User> users = userService.findUsersByName(name);
			if (users == null) {
				writer.println("<h3> User not found </h3>");
			} else {
				for (User user : users) {
					if (user.getPassword().equals(password)) {
						response.sendRedirect("/OSU-eTicket-EJB-Servlet/Events.html");
					}
				}
				writer.println("<h3> Wrong password </h3>");
			}
		}
		else{
			writer.println("<h3> Either username or password is empty </h3>");
		}
	}

}
