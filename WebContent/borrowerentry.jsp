<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="template1.html"%>
	
<section id="enterPassword">
	<br><br><br>
	<div class="container">
		<div class="row">
			<div class="col-lg-12 text-center">
				<h2 class="section-heading text-uppercase">Enter your information</h2>
				<br> <br>
			</div>
		</div>
		<div class="row text-center">
			<div class="col-md-12">
				<form action="authenticateBorrower" method="post">
					<h4 class="service-heading">Full-Name</h4>
					<input type="text" autocomplete="off" style="font-size:25px;" class="text-muted" name="name" required> <br> <br>
					<h4 class="service-heading">6-digit card #</h4>
					<input type="text" autocomplete="off" style="font-size:25px;" class="text-muted" name="cardNo" required> <br> <br>
					<button style="font-size:20px;" type="submit">Enter</button>
				</form>
			</div>
		</div>
	</div>
</section>