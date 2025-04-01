<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>
<div id="wrapper">
	<div id="header">
		<h2>Student Tacker</h2>
	</div>
</div>

<div id="container">
	<div id="content">
	
		<input type="button" value="Add Student"
			onclick="window.location.href='add_student_form.jsp';"
			class="add-student-button">
		<table>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Action</th>
			</tr>
			
			<c:forEach var="tempStudent" items="${student_list }">
			
				<c:url var="updateLink" value="StudentControllerServlet">
					<c:param name="command" value="LOAD"></c:param>
					<c:param name="studentID" value="${tempStudent.id }"></c:param>
				</c:url>
				
				<c:url var="deleteLink" value="StudentControllerServlet">
					<c:param name="command" value="DELETE"></c:param>
					<c:param name="studentID" value="${tempStudent.id }"></c:param>
				</c:url>
				
				<tr>
					<td>${tempStudent.firstName }</td>
					<td>${tempStudent.lastName }</td>
					<td>${tempStudent.email }</td>
					<td>
					<a href="${updateLink }">Update</a>
					|
				<a href="${deleteLink }" onclick="if(!(confirm('Are you sure you wantto delete this student?'))) return false">Delete</a>
					</td>
				</tr>
				
			</c:forEach>
		</table>
	</div>
</div>
</body>
</html>