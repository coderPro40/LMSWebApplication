<%@page import="com.gcit.lms.entity.Borrower"%>
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
	List<Publisher> publishers = service.readAllPublishers();
%>
<%@include file="template1.html"%>

<!-- Add Books -->
<section id="addBooks">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 text-center">
				<h2 class="section-heading text-uppercase">Update Publisher
					Record</h2>
				<br> <br>
			</div>
		</div>
		<div class="row text-center">
			<div class="col-md-7">
				<form action="editPublisher" method="post">
					<h4 class="service-heading">Enter Updated Name of the Publisher</h4>
					<input type="text" autocomplete="off" style="font-size:25px;" name="name"> <br>
					<br> Enter the Updated Address of the Publisher <br> <input
						type="text" autocomplete="off" style="font-size:25px;" name="address"> <br>
					<br> Enter the Updated Phone # of the Publisher <br> <input
						type="text" autocomplete="off" style="font-size:25px;" name="phone"> <br>
					<br>
					<input type="hidden" name="publisherId" value="<%=request.getParameter("publisherId") %>" />
					<button type="submit">Save</button>
				</form>
			</div>
			<div class="col-md-5">
				<h4 class="service-heading">Current Publisher Name</h4>
				<h5 style="font-style: italic">
					<%
						if (request.getParameterMap().containsKey("publisherId")) {
							Publisher aut = service.readPublisherByPK(Integer.parseInt(request.getParameter("publisherId")));
							out.println(aut.getPublisherName());
						}
					%>
				</h5>
				<br>
				<h4 class="service-heading">Current Publisher Address</h4>
				<h5 style="font-style: italic">
					<%
						if (request.getParameterMap().containsKey("publisherId")) {
							Publisher aut = service.readPublisherByPK(Integer.parseInt(request.getParameter("publisherId")));
							out.println(aut.getPublisherAddress());
						}
					%>
				</h5>
				<br>
				<h4 class="service-heading">Current Publisher Phone #</h4>
				<h5 style="font-style: italic">
					<%
						if (request.getParameterMap().containsKey("publisherId")) {
							Publisher aut = service.readPublisherByPK(Integer.parseInt(request.getParameter("publisherId")));
							out.println(aut.getPublisherPhone());
						}
					%>
				</h5>
				<br>
				<h5>Changed your mind about this record?</h5>
				<br>
				<div style="width: 250px" class="btn-group-vertical">
					<a onclick="window.location='administratorpublisher.jsp'"
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

