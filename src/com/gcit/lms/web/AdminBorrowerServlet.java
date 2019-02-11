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
 * Servlet implementation class AdminBorrowerServlet
 */
@WebServlet({ "/saveBorrower", "/editBorrower", "/deleteBorrower", "/pageBorrower" })
public class AdminBorrowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdministratorService service = new AdministratorService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminBorrowerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "", forwardUrl = "/administratorborrower.jsp", reqUrl = request.getRequestURI()
				.substring(request.getContextPath().length(), request.getRequestURI().length());
		try {
			if (reqUrl.equals("/deleteBorrower")) {
				// delete
				service.deleteBorrower(Integer.parseInt(request.getParameter("cardNo")));
			}
			if (reqUrl.equals("/pageBorrower")) {
				// page
				Integer pageNo = Integer.parseInt(request.getParameter("sendPageNoBor"));
				request.setAttribute("countPgNoBor", pageNo);
				request.setAttribute("getPageNoBor", service.readBorrowersLimitSearch(pageNo, null));
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
		String message = "", forwardUrl = "";
		System.out.println("Hello World");
		String name = request.getParameter("name"), address = request.getParameter("address"),
				phone = request.getParameter("phone");
		try {
			if (!request.getParameterMap().containsKey("cardNo")) {
				// add
				Integer borrowerId = service.addBorrowerWithID(name, address, phone);
				System.out.println(borrowerId);
				forwardUrl = "/administratoradd.jsp";
			} else {
				// update
				Integer borrowerId = Integer.parseInt(request.getParameter("cardNo"));
				service.updateBorrower(borrowerId, name, address, phone);
				forwardUrl = "/administratorborrower.jsp";
			}
			message = "Operation success!";
		} catch (Exception e) {
			message = "Operation failed. Contact system administrator";
			System.out.println(message);
		}

		request.setAttribute("statusMessage", message);
		RequestDispatcher rd = request.getRequestDispatcher(forwardUrl);
		rd.forward(request, response);
	}

}
