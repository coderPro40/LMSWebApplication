package com.gcit.lms.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.service.AdministratorService;

/**
 * Servlet implementation class AdminBookLoansServlet
 */
@WebServlet({"/editBookLoans", "/pageBookLoans"})
public class AdminBookLoansServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdministratorService service = new AdministratorService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminBookLoansServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "", forwardUrl = "/administratorbookloans.jsp", reqUrl = request.getRequestURI()
				.substring(request.getContextPath().length(), request.getRequestURI().length());
		try {
			if (reqUrl.equals("/pageBookLoans")) {
				// page
				Integer pageNo = Integer.parseInt(request.getParameter("sendPageNoBl"));
				request.setAttribute("countPgNoBl", pageNo);
				request.setAttribute("getPageNoBl", service.readBookLoansLimitSearch(pageNo, null));
			}
			message = "Operation success!";
		} catch (Exception e) {
			message = "Operation failed. Contact system administrator";
		}

		request.setAttribute("statusMessage", message);
		RequestDispatcher rd = request.getRequestDispatcher(forwardUrl);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "", forwardUrl = "/administratorbookloans.jsp";
		System.out.println("Hello World");
		try {
			if (request.getParameterMap().containsKey("bookId")) {
				// delete
				Integer bookId = Integer.parseInt(request.getParameter("bookId"));
				Integer branchId = Integer.parseInt(request.getParameter("branchId"));
				Integer cardNo = Integer.parseInt(request.getParameter("cardNo"));
				String dueDate = request.getParameter("dueDate");
				service.updateBookLoansByDueDate(bookId, branchId, cardNo, dueDate);
			}
			message = "Operation success!";
		} catch (Exception e) {
			message = "Book operation failed. Contact system administrator";
		}

		request.setAttribute("statusMessage", message);
		RequestDispatcher rd = request.getRequestDispatcher(forwardUrl);
		rd.forward(request, response);
	}

}
