package com.gcit.lms.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.service.AdministratorService;

/**
 * Servlet implementation class AdministratorServlet
 */
@WebServlet({ "/saveBook", "/editBook", "/deleteBook", "/pageBook" })
public class AdminBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdministratorService service = new AdministratorService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminBookServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "", forwardUrl = "/administratorbook.jsp", reqUrl = request.getRequestURI()
				.substring(request.getContextPath().length(), request.getRequestURI().length());
		try {
			if (reqUrl.equals("/deleteBook")) {
				// delete
				service.deleteBook(Integer.parseInt(request.getParameter("bookId")));
			}
			if (reqUrl.equals("/pageBook")) {
				// page
				Integer pageNo = Integer.parseInt(request.getParameter("sendPageNoBk"));
				request.setAttribute("countPgNoBk", pageNo);
				request.setAttribute("getPageNoBk", service.readBooksLimitSearch(pageNo, null));
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
		String message = "", forwardUrl = "", title = request.getParameter("title");
		try {
			if (!request.getParameterMap().containsKey("bookId")) {
				// add book
				Integer publisherId = Integer.parseInt(request.getParameter("publisherId"));
				String[] authorId = request.getParameterValues("authorIds");
				String[] genreId = request.getParameterValues("genreIds");
				List<Integer> authorIds = new ArrayList<>(), genreIds = new ArrayList<>();

				for (String string : authorId) {
					authorIds.add(Integer.parseInt(string));
				}

				for (String string : genreId) {
					genreIds.add(Integer.parseInt(string));
				}
				service.addBookWithID(title, publisherId, authorIds, genreIds);
				forwardUrl = "/administratoradd.jsp";
			} else {
				// update book
				Integer bookId = Integer.parseInt(request.getParameter("bookId"));
				service.updateBookTitle(bookId, title);
				message = "book updated successfully!";
				forwardUrl = "/administratorbook.jsp";
			}
			message = "Operation success!";
		} catch (Exception e) {
			message = "Operation failed. Contact system administrator";
		}

		request.setAttribute("statusMessage", message);
		RequestDispatcher rd = request.getRequestDispatcher(forwardUrl);
		rd.forward(request, response);
	}
}
