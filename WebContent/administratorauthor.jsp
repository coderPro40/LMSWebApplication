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
	String[] authorColumns = {"Name", "Books"};
	List<Author> authors = new ArrayList<>();
	Integer count = 1, pageLength = 5, choice = 0;

	// for if request gotten or first page
	if (request.getAttribute("getPageNoAu") != null) {
		authors = (List<Author>) request.getAttribute("getPageNoAu");
		count = ((((int) request.getAttribute("countPgNoAu")) - 1) * pageLength) + 1;
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

<script>
	function searchAuthors(){
		$.ajax({
		  url: "searchAuthor",
		  data: { 
			  searchString: $('#searchString').val()
		  }
		}).done(function(data) {
		    $('#authorsTable').html(data);
		});
	}
</script>

<div class="container">
	${statusMessage}
	<div class="row">
		<div class="col-lg-12 text-center">
			<h4 class="service-heading">List of all Author records</h4>
			<p id="demo"></p>
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
						href="pageAuthor?sendPageNoAu=<%=i%>"><%=i%></a></li>
					<%
						}
					%>
					<li class="page-item"><a class="page-link" href="#">last</a></li>
				</ul>
			</nav>
		</div>
		<div class="col-lg-6 text-right" style="display: inline;">
			<button onclick="window.location='administratoradd.jsp#addAuthors'"
				type="button" class="btn btn-secondary">Add New Author</button>
		</div>
	</div>
	<input class="form-control mr-sm-2" type="text" placeholder="Search by Author Name" aria-label="Search" id="searchString" oninput="searchAuthors()">
	
	<div class="row text-center">
		<div class="col-md-12">
			<table class="table table-striped" id="authorsTable">
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
					<td><button onclick="<%choice = a.getAuthorId();%>'"
							type="button" data-toggle="modal" data-target="#exampleModal"
							class="btn btn-danger">Delete</button>
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
								onclick="window.location='deleteAuthor?authorId=<%=choice%>'"
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

