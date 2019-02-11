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
 * Servlet implementation class AdminBookNoServlet
 */
@WebServlet({"/editNoCopies", "/pageNoCopies"})
public class AdminBookNoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdministratorService service = new AdministratorService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminBookNoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "", forwardUrl = "/librariannoofcopies.jsp", reqUrl = request.getRequestURI()
				.substring(request.getContextPath().length(), request.getRequestURI().length());
		try {
			if (reqUrl.equals("/pageNoCopies")) {
				// page
				Integer pageNo = Integer.parseInt(request.getParameter("sendPageNoBn"));
				request.setAttribute("countPgNoBn", pageNo);
				request.setAttribute("getPageNoBn", service.readBookCopiesLimitSearch(pageNo, null));
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "", forwardUrl = "/librariannoofcopies.jsp";
		System.out.println("Hello World");
		try {
			if (request.getParameterMap().containsKey("bookId")) {
				// edit
				Integer bookId = Integer.parseInt(request.getParameter("bookId"));
				Integer branchId = Integer.parseInt(request.getParameter("branchId"));
				Integer noOfCopies = Integer.parseInt(request.getParameter("noOfCopies"));
				service.updateBookCopiesByIds(bookId, branchId, noOfCopies);
			}
			message = "Operation success!";
		} catch (Exception e) {
			message = "operation failed. Contact system administrator";
		}

		request.setAttribute("statusMessage", message);
		RequestDispatcher rd = request.getRequestDispatcher(forwardUrl);
		rd.forward(request, response);
	}

}
