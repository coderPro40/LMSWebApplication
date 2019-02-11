<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	AdministratorService service = new AdministratorService();
	List<Author> authors = service.readAllAuthors();
%>
<%@include file="template1.html"%>

<!-- Add Books -->
<section id="addBooks">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 text-center">
				<h2 class="section-heading text-uppercase">Update Author Record</h2>
				<br> <br>
			</div>
		</div>
		<div class="row text-center">
			<div class="col-md-7">
				<form action="editAuthor" method="post">
					<h4 class="service-heading">Enter Updated Name of the Author</h4>
					<input type="text" autocomplete="off" style="font-size:25px;" name="name"> <br>
					<br>
					<br>
					<input type="hidden" name="authorId" value="<%=request.getParameter("authorId") %>" />
					<button type="submit">Save</button>
				</form>
			</div>
			<div class="col-md-5">
				<h4 class="service-heading">Current Author Name</h4>
				<h5 style="font-style: italic"><%
				if(request.getParameterMap().containsKey("authorId")){
					Author aut = service.readAuthorByPK(Integer.parseInt(request.getParameter("authorId")));
					out.println(aut.getAuthorName());
				}
				%></h5>
				<h5>Changed your mind about this record?</h5>
				<br>
				<div style="width: 250px" class="btn-group-vertical">
					<a onclick="window.location='administratorauthor.jsp'"
						type="button" class="btn btn-primary" data-toggle="button"
						aria-pressed="false">Return to Record list</a>
				</div>
				<br>
			</div>
		</div>
	</div>
</section>

<br>

<footer class="fixed-bottom" style="background-color: #212529;">
	<div class="container">
		<div class="row text-center">
			<div class="col-md-12">
				<p style="color: #ffffff;">Copyright © ThankGod Ofurum</p>
			</div>
		</div>
	</div>
</footer>

