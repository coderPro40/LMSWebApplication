<%@page import="com.gcit.lms.entity.BookLoans"%>
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
	List<BookLoans> bookLoans = new ArrayList<>();
	String[] bookLoansColumns = { "BookName", "Branch Name", "Borrower", "Date Out", "Due Date" };
	int count = 1, pageLength = 5;

	// for if request gotten or first page
	if (request.getAttribute("getPageNoBl") != null) {
		bookLoans = (List<BookLoans>) request.getAttribute("getPageNoBl");
		count = ((((int) request.getAttribute("countPgNoBl")) - 1) * pageLength) + 1;
	} else {
		bookLoans = service.readBookLoansLimitSearch(1, null);
	}

	Integer totalAuthors = service.getBookLoansCount();
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
			<h4 class="service-heading">List of all Book-Loans records</h4>
			<p id="demo"></p>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12 text-right">
			<select name="record" style="display: inline;"
				onchange="if(this.selectedIndex!=0) self.location=this.options[this.selectedIndex].value">
				<option value="">Choose from list</option>
				<option value="administratorbook.jsp">Books</option>
				<option value="administratorauthor.jsp">Authors</option>
				<option value="administratorgenre.jsp">Genres</option>
				<option value="administratorpublisher.jsp">Publishers</option>
				<option value="administratorborrower.jsp">Borrowers</option>
				<option value="administratorbranch.jsp">Branches</option>
			</select>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<nav aria-label="Page navigation example" id="paginationBar">
				<ul class="pagination">
					<li class="page-item"><a class="page-link" href="#">first</a></li>
					<%
						for (int i = 1; i <= totalPages; i++) {
					%>
					<li class="page-item"><a class="page-link"
						href="pageBookLoans?sendPageNoBl=<%=i%>"><%=i%></a></li>
					<%
						}
					%>
					<li class="page-item"><a class="page-link" href="#">last</a></li>
				</ul>
			</nav>
		</div>
	</div>
	<div class="row text-center">
		<div class="col-md-12">
			<table class="table table-striped">
				<tr>
					<th>#</th>
					<%
						for (String col : bookLoansColumns) {
					%>
					<th><%=col%></th>
					<%
						}
					%>
					<th>Edit</th>
				</tr>
				<%
					for (BookLoans bk : bookLoans) {
				%>
				<tr>
					<td><%=count%></td>
					<%
						// use = or out.println() to print to html web page
					%>
					<td><%=bk.getBook().getTitle()%></td>
					<td><%=bk.getBranch().getBranchName()%></td>
					<td><%=bk.getBorrower().getName()%></td>
					<td><%=bk.getDateOut()%></td>
					<td><%=bk.getDueDate()%></td>
					<td><button type="button" class="btn btn-success"
							onclick="window.location.href='updatebookloans.jsp?bookId=<%=bk.getBook().getBookId()%>&branchId=<%=bk.getBranch().getBranchId()%>&cardNo=<%=bk.getBorrower().getCardNo()%>'">Edit</button></td>
				</tr>
				<!-- & symbol to separate multiple values -->
				<%
					count++;
					}
				%>
			</table>
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

