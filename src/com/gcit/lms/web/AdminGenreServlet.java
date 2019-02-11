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
 * Servlet implementation class AdminGenreServlet
 */
@WebServlet({ "/saveGenre", "/editGenre", "/deleteGenre", "/pageGenre" })
public class AdminGenreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdministratorService service = new AdministratorService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminGenreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "", forwardUrl = "/administratorgenre.jsp", reqUrl = request.getRequestURI()
				.substring(request.getContextPath().length(), request.getRequestURI().length());
		try {
			if (reqUrl.equals("/deleteGenre")) {
				// delete
				service.deleteGenre(Integer.parseInt(request.getParameter("genreId")));
			}
			if (reqUrl.equals("/pageGenre")) {
				// page
				Integer pageNo = Integer.parseInt(request.getParameter("sendPageNoGr"));
				request.setAttribute("countPgNoGr", pageNo);
				request.setAttribute("getPageNoGr", service.readGenresLimitSearch(pageNo, null));
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
		String message = "", forwardUrl = "/administratoradd.jsp";
		System.out.println("Hello World");
		String name = request.getParameter("name");
		try {
			if (!request.getParameterMap().containsKey("genreId")) {
				// add

				String[] bookId = request.getParameterValues("bookIds");
				Integer genreId = service.addGenreWithID(name);

				for (String string : bookId) {
					service.addBookGenres(Integer.parseInt(string), genreId);
					forwardUrl = "/administratoradd.jsp";
				}
			} else {
				// update
				Integer genreId = Integer.parseInt(request.getParameter("genreId"));
				service.updateGenre(genreId, name);
				forwardUrl = "/administratorgenre.jsp";
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
