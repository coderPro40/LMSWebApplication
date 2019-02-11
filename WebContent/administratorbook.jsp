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
	List<Book> book = new ArrayList<>();
	String[] bookColumns = { "Title", "Publisher", "Genre", "Author" };
	int count = 1, pageLength = 5, choice=0;

	// for if request gotten or first page
	if (request.getAttribute("getPageNoBk") != null) {
		book = (List<Book>) request.getAttribute("getPageNoBk");
		count = ((((int) request.getAttribute("countPgNoBk")) - 1) * pageLength) + 1;
	} else {
		book = service.readBooksLimitSearch(1, null);
	}

	Integer totalAuthors = service.getBooksCount();
	Integer totalPages = 1;

	// for display
	if (totalAuthors % pageLength > 0) {
		totalPages = (totalAuthors / pageLength) + 1;
	} else {
		totalPages = totalAuthors / pageLength;
	}
%>
<%@include file="template1.html"%>

<div class="container">
	<div class="row">
		<div class="col-lg-12 text-center">
			<h4 class="service-heading">List of all Book records</h4>
			<p id="demo"></p>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12 text-right">
			<select name="record" style="display: inline;"
				onchange="if(this.selectedIndex!=0) self.location=this.options[this.selectedIndex].value">
				<option value="">Choose from list</option>
				<option value="administratorauthor.jsp">Authors</option>
				<option value="administratorgenre.jsp">Genres</option>
				<option value="administratorpublisher.jsp">Publishers</option>
				<option value="administratorborrower.jsp">Borrowers</option>
				<option value="administratorbranch.jsp">Branches</option>
				<option value="administratorbookloans.jsp">Book Loans</option>
			</select>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="col-lg-6 text-left" style="display: inline;">
			<nav aria-label="Page navigation example" id="paginationBar">
				<ul class="pagination">
					<li class="page-item"><a class="page-link" href="#">first</a></li>
					<%
						for (int i = 1; i <= totalPages; i++) {
					%>
					<li class="page-item"><a class="page-link"
						href="pageBook?sendPageNoBk=<%=i%>"><%=i%></a></li>
					<%
						}
					%>
					<li class="page-item"><a class="page-link" href="#">last</a></li>
				</ul>
			</nav>
		</div>
		<div class="col-lg-6 text-right" style="display: inline;">
			<button onclick="window.location='administratoradd.jsp#addBooks'"
				type="button" class="btn btn-secondary">Add New Book</button>
		</div>
	</div>
	<div class="row text-center">
		<div class="col-md-12">
			<table class="table table-striped">
				<tr>
					<th>#</th>
					<%
						for (String col : bookColumns) {
					%>
					<th><%=col%></th>
					<%
						}
					%>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
				<%
					for (Book b : book) {
				%>
				<tr>
					<td><%=count%></td>
					<%
						// use = or out.println() to print to html web page
					%>
					<td><%=b.getTitle()%></td>
					<td>
						<%
							for (Author a : b.getAuthors()) {
									out.println(a.getAuthorName() + " | ");
								}
						%>
					</td>
					<td>
						<%
							for (Genre g : b.getGenres()) {
									out.println(g.get_Genre_Name() + " | ");
								}
						%>
					</td>
					<td>
						<%
							out.println(b.getPublisher().getPublisherName());
								count++;
						%>
					</td>
					<td><button
							onclick="window.location='updatebooks.jsp?bookId=<%=b.getBookId()%>'"
							type="button" class="btn btn-success">Edit</button></td>
					<td><button onclick="<%choice = b.getBookId();%>'"
							type="button" data-toggle="modal" data-target="#exampleModal"
							class="btn btn-danger">Delete</button></td>
				</tr>
				<%
					}
				%>
			</table>
			<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" style="text-align: center;"
								id="exampleModalLabel">Danger</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">You're about to delete this record.
							Proceed?</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger"
								onclick="window.location='deleteBook?bookId=<%=choice%>'"
								>Yes</button>
							<button type="button"
								class="btn btn-success" data-dismiss="modal">No</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

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

