package com.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDBUtil {

	private DataSource dataSource;

	public StudentDBUtil(DataSource theDataSource) {
		this.dataSource = theDataSource;
	}

	public Student getStudent(String studentId) {
		Student s = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet rs = null;
		int s_Id;

		try {
			s_Id = Integer.parseInt(studentId);
			myConn = dataSource.getConnection();
			String sql = "select * from  Student where id=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, s_Id);
			rs = myStmt.executeQuery();
			if (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");

				s = new Student(s_Id, firstName, lastName, email);
			} else {
				throw new Exception("Could not find any student with id = " + s_Id + " in our records!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myConn, myStmt, rs);
		}

		return s;
	}

	public List<Student> getStudents() {
		List<Student> students = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet rs = null;

		try {
			myConn = dataSource.getConnection();
			String sql = "select * from  Student";
			myStmt = myConn.createStatement();
			rs = myStmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");

				Student tempStudent = new Student(id, firstName, lastName, email);

				students.add(tempStudent);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myConn, myStmt, rs);
		}

		return students;

	}

	public void addStudent(Student s) {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = dataSource.getConnection();
			String sql = "insert into student" + "(first_name, last_name, email)" + "values (?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, s.getFirstName());
			myStmt.setString(2, s.getLastName());
			myStmt.setString(3, s.getEmail());
			myStmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myConn, myStmt, null);
		}
	}

	public void updateStudent(Student s) {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = dataSource.getConnection();
			String sql = "update student " + "set first_name=?, last_name=?, email=? " + "where id=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, s.getFirstName());
			myStmt.setString(2, s.getLastName());
			myStmt.setString(3, s.getEmail());
			myStmt.setInt(4, s.getId());
			myStmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myConn, myStmt, null);
		}
	}

	public void deleteStudent(String studentId) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		int s_id;

		try {
			s_id = Integer.parseInt(studentId);
			myConn = dataSource.getConnection();
			String sql = "delete from student where id=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, s_id);
			myStmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myConn, myStmt, null);
		}
	}

	private void close(Connection myConn, Statement myStmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (myStmt != null) {
				myStmt.close();
			}
			if (myConn != null) {
				myConn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
