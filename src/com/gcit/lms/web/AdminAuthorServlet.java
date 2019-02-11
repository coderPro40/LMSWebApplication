package com.gcit.lms.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.service.AdministratorService;

/**
 * Servlet implementation class AdminAuthorServlet
 */
@WebServlet({ "/saveAuthor", "/editAuthor", "/deleteAuthor", "/pageAuthor", "/searchAuthor" })
public class AdminAuthorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdministratorService service = new AdministratorService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminAuthorServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "", forwardUrl = "/administratorauthor.jsp", reqUrl = request.getRequestURI()
				.substring(request.getContextPath().length(), request.getRequestURI().length());
		Boolean isAjax = Boolean.FALSE;
		try {
			if (reqUrl.equals("/deleteAuthor")) {
				// delete
				service.deleteAuthor(Integer.parseInt(request.getParameter("authorId")));
			}
			if (reqUrl.equals("/pageAuthor")) {
				// page
				Integer pageNo = Integer.parseInt(request.getParameter("sendPageNoAu"));
				request.setAttribute("countPgNoAu", pageNo);
				request.setAttribute("getPageNoAu", service.readAuthorsLimitSearch(pageNo, null));
			}
			if (reqUrl.equals("/searchAuthor")) {
				String searchString = request.getParameter("searchString");
				List<Author> authors = service.readAuthorsLimitSearch(1, searchString);
				String[] authorColumns = { "Name", "Books" };
				StringBuffer strBuf = new StringBuffer();
				strBuf.append("<tr><th>#</th>");
				for (String string : authorColumns) {
					strBuf.append("<th>"+string+"</th>");
				}
				strBuf.append("<th>Edit</th><th>Delete</th></tr>");
				for (Author a : authors) {
					strBuf.append("<tr><td>"+authors.indexOf(a)+1+"</td><td>"+a.getAuthorName()+"</td><td>");
					for (Book b : a.getBook()) {
						strBuf.append(b.getTitle()+ " | ");
					}
					strBuf.append("</td><td><button type='button' class='btn btn-primary' onclick='window.location='updateauthors.jsp?authorId="+a.getAuthorId()+"''>Edit</button></td>");
					strBuf.append("<td><button type='button' class='btn btn-danger' onclick='window.location='deleteAuthor?authorId="+a.getAuthorId()+"''>Delete</button></td></tr>");
				}
				response.getWriter().append(strBuf.toString());
				// keep bottom in case of fail
				isAjax = Boolean.TRUE;
			}
			message = "Operation success!";
		} catch (Exception e) {
			message = "Operation failed. Contact system administrator";
		}

		if (!isAjax) {
			request.setAttribute("statusMessage", message);
			RequestDispatcher rd = request.getRequestDispatcher(forwardUrl);
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "", forwardUrl = "", reqUrl = request.getRequestURI()
				.substring(request.getContextPath().length(), request.getRequestURI().length()),
				name = request.getParameter("name");
		try {
			if (reqUrl.equals("/saveAuthor")) {
				// add
				String[] bookId = request.getParameterValues("bookIds");
				Integer authorId = service.addAuthorWithID(name);
				forwardUrl = "/administratoradd.jsp";

				for (String string : bookId) {
					service.addBookAuthors(Integer.parseInt(string), authorId);
				}
			}
			if (reqUrl.equals("/editAuthor")) {
				// update
				Integer authorId = Integer.parseInt(request.getParameter("authorId"));
				service.updateAuthor(authorId, name);
				forwardUrl = "/administratorauthor.jsp";
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
