<%@page import="java.util.Collections"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
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
	String[] authorColumns = { "Name", "Books" };
	List<Author> authors = new ArrayList<>();
	int count = 1, pageLength = 6;

	// for if request gotten or first page
	if (request.getAttribute("getPageNo") != null) {
		authors = (List<Author>) request.getAttribute("getPageNo");
		count = ((((int) request.getAttribute("countPgNo")) - 1) * pageLength) + 1;
	} else {
		authors = service.readAuthorsLimitSearch(1, null);
	}

	Integer totalAuthors = service.getAuthorsCount();
	Integer totalPages = 1;

	// for display
	if (totalAuthors % pageLength > 0) {
		totalPages = (totalAuthors / pageLength) + 1;
	} else {
		totalPages = totalAuthors / pageLength;
	}
%>
<%@include file="template1.html"%>

<section id="addBooks">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 text-center">
				<h4 class="service-heading">List of all Author records</h4>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12 text-right">
				<select name="record" style="display: inline;"
					onchange="if(this.selectedIndex!=0) self.location=this.options[this.selectedIndex].value">
					<option value="">Choose different service</option>
					<option value="administratorbook.jsp">Books</option>
					<option value="administratorgenre.jsp">Genres</option>
					<option value="administratorpublisher.jsp">Publishers</option>
					<option value="administratorborrower.jsp">Borrowers</option>
					<option value="administratorbranch.jsp">Branches</option>
					<option value="administratorbookloans.jsp">Book Loans</option>
				</select>
			</div>
		</div>
		<nav aria-label="Page navigation example">
			<ul class="pagination">
				<li class="page-item"><a class="page-link" href="#">first</a></li>
				<%
					for (int i = 1; i <= totalPages; i++) {
				%>
				<li class="page-item"><a class="page-link"
					href="pageAuthor?sendPageNo=<%=i%>"><%=i%></a></li>
				<%
					}
				%>
				<li class="page-item"><a class="page-link" href="#">last</a></li>
			</ul>
		</nav>
		<div class="row text-center">
			<div class="col-md-12">
				<table class="table table-striped">
					<tr>
						<th>#</th>
						<%
							for (String col : authorColumns) {
						%>
						<th><%=col%></th>
						<%
							}
						%>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
					<%
						for (Author a : authors) {
					%>
					<tr>
						<td><%=count%></td>
						<%
							// use = or out.println() to print to html web page
						%>
						<td><%=a.getAuthorName()%></td>
						<td>
							<%
								for (Book b : a.getBook()) {
										out.println(b.getTitle() + " | ");
									}
									count++;
							%>
						</td>
						<td><button
								onclick="window.location='updateauthors.jsp?authorId=<%=a.getAuthorId()%>'"
								type="button" class="btn btn-success">Edit</button></td>
						<td><button
								onclick="window.location='deleteAuthor?authorId=<%=a.getAuthorId()%>'"
								type="button" class="btn btn-danger">Delete</button>
					</tr>
					<%
						}
					%>
				</table>
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

