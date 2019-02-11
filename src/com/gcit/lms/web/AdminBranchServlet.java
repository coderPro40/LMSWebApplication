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
 * Servlet implementation class AdminBranchServlet
 */
@WebServlet({ "/saveBranch", "/editBranch", "/deleteBranch", "/pageBranch", "/editTwoBranch" })
public class AdminBranchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdministratorService service = new AdministratorService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminBranchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "", forwardUrl = "/administratorbranch.jsp", reqUrl = request.getRequestURI()
				.substring(request.getContextPath().length(), request.getRequestURI().length());
		try {
			if (reqUrl.equals("/deleteBranch")) {
				// delete
				service.deleteBranch(Integer.parseInt(request.getParameter("branchId")));
			}
			if (reqUrl.equals("/pageBranch")) {
				// page
				Integer pageNo = Integer.parseInt(request.getParameter("sendPageNoBr"));
				request.setAttribute("countPgNoBr", pageNo);
				request.setAttribute("getPageNoBr", service.readBranchsLimitSearch(pageNo, null));
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
		String message = "", forwardUrl = "", name = request.getParameter("name"),
				address = request.getParameter("address"), reqUrl = request.getRequestURI()
						.substring(request.getContextPath().length(), request.getRequestURI().length());
		try {
			if (reqUrl.equals("/saveBranch")) {
				// add
				Integer branchId = service.addBranchWithID(name, address);
				System.out.println(branchId);
				forwardUrl = "/administratoradd.jsp";
			} else {
				// update

				Integer branchId = Integer.parseInt(request.getParameter("branchId"));
				service.updateBranch(branchId, name, address);
				forwardUrl = "/administratorbranch.jsp";
				if (reqUrl.equals("/editTwoBranch")) {
					forwardUrl= "/librarianbranch.jsp";
				}
			}
			message = "Operation success!";
		} catch (Exception e) {
			message = "operation failed. Contact system administrator";
			System.out.println(message);
		}

		request.setAttribute("statusMessage", message);
		RequestDispatcher rd = request.getRequestDispatcher(forwardUrl);
		rd.forward(request, response);
	}

}
