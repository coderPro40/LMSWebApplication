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
 * Servlet implementation class AdminPublisherServlet
 */
@WebServlet({ "/savePublisher", "/editPublisher", "/deletePublisher", "/pagePublisher" })
public class AdminPublisherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdministratorService service = new AdministratorService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminPublisherServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "", forwardUrl = "/administratorpublisher.jsp", reqUrl = request.getRequestURI()
				.substring(request.getContextPath().length(), request.getRequestURI().length());
		try {
			if (reqUrl.equals("/deletePublisher")) {
				// delete
				service.deletePublisher(Integer.parseInt(request.getParameter("publisherId")));
			}
			if (reqUrl.equals("/pagePublisher")) {
				// page
				Integer pageNo = Integer.parseInt(request.getParameter("sendPageNoPb"));
				request.setAttribute("countPgNoPb", pageNo);
				request.setAttribute("getPageNoPb", service.readPublishersLimitSearch(pageNo, null));
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "", forwardUrl = "";
		System.out.println("Hello World");
		String name = request.getParameter("name"), address = request.getParameter("address"),
				phone = request.getParameter("phone");
		try {
			if (!request.getParameterMap().containsKey("publisherId")) {
				// add

				String[] bookId = request.getParameterValues("bookIds");
				Integer publisherId = service.addPublisherWithID(name, address, phone);

				for (String string : bookId) {
					service.updateBookPublisher(Integer.parseInt(string), publisherId);
				}
				forwardUrl = "/administratoradd.jsp";
			} else {
				// update
				Integer publisherId = Integer.parseInt(request.getParameter("publisherId"));
				service.updatePublishersName(publisherId, name, address, phone);
				forwardUrl = "/administratorpublisher.jsp";
			}
			message = "Operation success!";
			System.out.println(message);
		} catch (Exception e) {
			message = "Operation failed. Contact system administrator";
			System.out.println(message);
		}

		request.setAttribute("statusMessage", message);
		RequestDispatcher rd = request.getRequestDispatcher(forwardUrl);
		rd.forward(request, response);
	}

}
