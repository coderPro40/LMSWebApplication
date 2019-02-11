package com.gcit.lms.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.service.AdministratorService;

/**
 * Servlet implementation class BorrowerAuthenticateServlet
 */
@WebServlet({ "/authenticateBorrower", "/checkInBorrower", "/checkOutBorrower", "/pagerBorrower" })
public class BorrowerAuthenticateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdministratorService service = new AdministratorService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BorrowerAuthenticateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "", forwardUrl = "/borrowercheck.jsp", reqUrl = request.getRequestURI()
				.substring(request.getContextPath().length(), request.getRequestURI().length());
		Integer cardNo = 0;
		try {
			if (reqUrl.equals("/checkInBorrower")) {
				// increase no Of Copies
				Integer bookId = Integer.parseInt(request.getParameter("bookId")),
						branchId = Integer.parseInt(request.getParameter("branchId"));
				cardNo = Integer.parseInt(request.getParameter("cardNo"));
				service.updateBookCopiesByIds(bookId, branchId, 1); // since check-in we set value to 1
				service.updateBookLoansByDateIn(bookId, branchId, cardNo, LocalDate.now().toString());
				request.setAttribute("cardNo", cardNo);
			}
			if (reqUrl.equals("/checkOutBorrower")) {
				// decrease no Of Copies
				Integer bookId = Integer.parseInt(request.getParameter("bookId")),
						branchId = Integer.parseInt(request.getParameter("branchId"));
				cardNo = Integer.parseInt(request.getParameter("cardNo"));
				service.updateBookCopiesByIds(bookId, branchId, -1); // since check-in we set value to 1
				Book book = service.readBookByPK(bookId);
				Branch branch = service.readBranchByPK(branchId);
				Borrower borrower = service.readBorrowerByPK(cardNo);
				service.addBookLoans(book, branch, borrower, LocalDate.now().toString(),
						LocalDate.now().plus(1, ChronoUnit.WEEKS).toString());
				request.setAttribute("cardNo", cardNo);
			}
			if (reqUrl.equals("/pagerBorrower")) {
				if(request.getParameterMap().containsKey("cardNo")) {
					request.setAttribute("cardNo", cardNo);
				}
				Integer pageNo = Integer.parseInt(request.getParameter("sendPageNoBc"));
				request.setAttribute("countPgNoBc", pageNo);
				request.setAttribute("getPageNoBc", service.readBookCopiesLimitSearch(pageNo, null));
			}
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
		String message = "",
				forwardUrl = "/borrowerentry.jsp", reqUrl = request.getRequestURI()
						.substring(request.getContextPath().length(), request.getRequestURI().length()),
				name = request.getParameter("name");
		try {
			if (reqUrl.equals("/authenticateBorrower")) {
				// read
				System.out.println(Integer.parseInt(request.getParameter("cardNo")));
				Borrower borrower = service.readBorrowerByCardNo(Integer.parseInt(request.getParameter("cardNo")));
				forwardUrl = ((borrower.getName()).equals(name)) ? "/borrowercheck.jsp" : "/borrowerentry.jsp";
				message = ((borrower.getName()).equals(name)) ? "Good entry, enjoy checking the books"
						: "Invalid entry! Try again";
				String cardNo = ((borrower.getName()).equals(name)) ? request.getParameter("cardNo") : "";
				request.setAttribute("cardNo", cardNo);
			}
		} catch (Exception e) {
			message = "Operation failed. Contact system administrator";
		}

		request.setAttribute("statusMessage", message);
		RequestDispatcher rd = request.getRequestDispatcher(forwardUrl);
		rd.forward(request, response);
	}

}
