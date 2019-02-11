<%@page import="com.gcit.lms.entity.BookCopies"%>
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
	List<BookCopies> bookC = new ArrayList<>();
	String[] bookColumns = { "Branch", "Title", "Book Copies", "Update" };
	Integer count = 1, pageLength = 5, bookId=0, branchId=0;

	// for if request gotten or first page
	if (request.getAttribute("getPageNoBc") != null) {
		bookC = (List<BookCopies>) request.getAttribute("getPageNoBc");
		count = ((((int) request.getAttribute("countPgNoBc")) - 1) * pageLength) + 1;
	} else {
		bookC = service.readBookCopiesLimitSearch(1, null);
	}

	Integer totalAuthors = service.getBookCopiesCount();
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
			<h4 class="service-heading">List of Book Copies</h4>
			<p id="demo"></p>
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
						href="pagerBorrower?sendPageNoBc=<%=i%>"><%=i%></a></li>
					<%
						}
					%>
					<li class="page-item"><a class="page-link" href="#">last</a></li>
				</ul>
			</nav>
		</div>
		<div class="col-lg-6 text-right" style="display:inline;">
			<button
				onclick="window.location='librarianbranch.jsp'"
				type="button" class="btn btn-secondary">Update Library Details</button>
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
				</tr>
				<%
					for (BookCopies b : bookC) {
				%>
				<tr>
					<td><%=count%></td>
					<%
						// use = or out.println() to print to html web page
					%>
					<td><%=b.getBranch().getBranchName()%></td>
					<td><%=b.getBook().getTitle()%></td>
					<td><%=b.getNoOfCopies()%></td>
					<td><button onclick="<%bookId = b.getBook().getBookId(); branchId = b.getBranch().getBranchId();%>'"
							type="button" data-toggle="modal" data-target="#exampleModal"
							class="btn btn-primary">Add Copies</button></td>
				</tr>
				<%
				count++;
					}
				%>
			</table>
			<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" style="text-align: center;"
								id="exampleModalLabel">Alert</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">You're about to add extra copies this book.
							Proceed?</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger"
								onclick="this.disabled=true; window.location='updatenoofcopies.jsp?bookId=<%=bookId%>&branchId=<%=branchId %>';"
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

