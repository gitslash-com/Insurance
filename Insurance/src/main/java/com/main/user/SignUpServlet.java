package com.main.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	PreparedStatement stmt;

	public void init() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/insurance", "root", "root");
			stmt = con.prepareStatement("insert into users(username,name,email,password) values(?,?,?,?);");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userName = request.getParameter("userName");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		PrintWriter out = response.getWriter();
		try {
			stmt.setString(1, userName);
			stmt.setString(2, name);
			stmt.setString(3, email);
			stmt.setString(4, password);

			int result = stmt.executeUpdate();
			if (result>0) {
				response.sendRedirect("login.html");
			} else {
				response.sendError(result, "Registration Fail");
				response.sendRedirect("signup.html");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
