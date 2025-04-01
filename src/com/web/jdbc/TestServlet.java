package com.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Define datasource/Connection Pool for Resource Injection
	@Resource(name = "jdbc/student_tracker")
	private DataSource dataSource;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		Step 1 : Setup PrintWriter
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

//		Step 2 : Get connection to the database
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();

//			Step 3 : Create SQL Statement
			String sql = "select * from student";
			stmt = conn.createStatement();

//			Step 4 : Execute SQL query
			rs = stmt.executeQuery(sql);

//			Step 5 : Process the result set
			while (rs.next()) {
				String email = rs.getString("email");
				out.println(email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
