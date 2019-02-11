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
	List<Book> book = service.readAllBooks();
%>
<%@include file="template1.html"%>

<!-- Add Books -->
<section id="addBooks">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 text-center">
				<h2 class="section-heading text-uppercase">Update Book Record</h2>
				<br> <br>
			</div>
		</div>
		<div class="row text-center">
			<div class="col-md-7">
				<form action="editBook" method="post">
					<h4 class="service-heading">Enter Updated Title of the Book</h4>
					<input type="text" autocomplete="off" style="font-size:25px;" name="title"> <br>
					<br>
					<br>
					<input type="hidden" name="bookId" value="<%=request.getParameter("bookId") %>" />
					<button type="submit">Save</button>
				</form>
			</div>
			<div class="col-md-5">
				<h4 class="service-heading">Current Book Title</h4>
				<h5 style="font-style: italic"><%
				if(request.getParameterMap().containsKey("bookId")){
					Book aut = service.readBookByPK(Integer.parseInt(request.getParameter("bookId")));
					out.println(aut.getTitle());
				}
				%></h5>
				<h5>Changed your mind about this record?</h5>
				<br>
				<div style="width: 250px" class="btn-group-vertical">
					<a onclick="window.location='administratorbook.jsp'"
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

