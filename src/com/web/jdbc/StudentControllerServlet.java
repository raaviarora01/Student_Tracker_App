package com.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDBUtil studentDBUtil;

	@Resource(name = "jdbc/student_tracker")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			studentDBUtil = new StudentDBUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String command = request.getParameter("command");

			if (command == null) {
				command = "LIST";
			}

			switch (command) {
			case "LIST": {
				listStudents(request, response);
				break;
			}

			case "ADD": {
				addStudent(request, response);
				break;
			}

			case "LOAD": {
				loadStudent(request, response);
				break;
			}

			case "UPDATE": {
				updateStudent(request, response);
				break;
			}

			case "DELETE": {
				deleteStudent(request, response);
				break;
			}

			default:
				listStudents(request, response);

			}

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String studentId = request.getParameter("studentID");

		studentDBUtil.deleteStudent(studentId);

		listStudents(request, response);

	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		Student s = new Student(studentId, firstName, lastName, email);

		studentDBUtil.updateStudent(s);

		listStudents(request, response);

	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String studentId = request.getParameter("studentID");
		Student theStudent = studentDBUtil.getStudent(studentId);

		request.setAttribute("student", theStudent);

		RequestDispatcher rd = request.getRequestDispatcher("/update_student_form.jsp");
		rd.forward(request, response);

	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		Student s = new Student(firstName, lastName, email);

		studentDBUtil.addStudent(s);

		listStudents(request, response);

	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Student> students = studentDBUtil.getStudents();

		request.setAttribute("student_list", students);

		RequestDispatcher rd = request.getRequestDispatcher("/list_students.jsp");
		rd.forward(request, response);

	}
}
