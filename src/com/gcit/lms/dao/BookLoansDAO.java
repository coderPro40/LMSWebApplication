/*
 *
+ *2:39:15 PM
 *Dec 21, 2017
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.BookLoans;

/**
 * @author ThankGod4Life
 * @date Dec 21, 2017
 *
 */
public class BookLoansDAO extends BaseDAO<BookLoans> {

	public BookLoansDAO(Connection conn) {
		super(conn);
	}

	// front end users need to know if operation was successful or fail. So
	// exception throw to front end
	public void addBookLoans(BookLoans bookLoans)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) VALUES (?, ?, ?, ?, ?)",
				new Object[] { bookLoans.getBook().getBookId(), bookLoans.getBranch().getBranchId(),
						bookLoans.getBorrower().getCardNo(), bookLoans.getDateOut(), bookLoans.getDueDate() });
	}

	public void updateBookLoansByDueDate(BookLoans bookLoans)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("UPDATE tbl_book_loans SET dueDate =? WHERE bookId = ? and branchId = ? and cardNo = ?",
				new Object[] { bookLoans.getDueDate(), bookLoans.getBook().getBookId(),
						bookLoans.getBranch().getBranchId(), bookLoans.getBorrower().getCardNo() });
	}

	public void updateBookLoansByDateIn(BookLoans bookLoans)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("UPDATE tbl_book_loans SET dateIn =? WHERE bookId = ? and branchId = ? and cardNo = ?",
				new Object[] { bookLoans.getDateIn(), bookLoans.getBook().getBookId(),
						bookLoans.getBranch().getBranchId(), bookLoans.getBorrower().getCardNo() });
	}

	public void deleteBookLoans(BookLoans bookLoans)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("DELETE FROM tbl_book_loans WHERE bookId = ? and branchId = ? and cardNo = ?",
				new Object[] { bookLoans.getBook().getBookId(), bookLoans.getBranch().getBranchId(),
						bookLoans.getBorrower().getCardNo() });
	}

	public List<BookLoans> readAllBookLoans()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_loans", null);
	}
	
	public List<BookLoans> readAllBookLoansWithLimit(Integer pageNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		return readLimit("SELECT * FROM tbl_book_loans", null);
	}
	
	public Integer getBookLoansCount()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return getCount("SELECT COUNT(*) AS COUNT FROM tbl_book_loans", null);
	}

	public BookLoans readBookLoansByIds(Integer bookId, Integer branchId, Integer cardNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<BookLoans> bookLoans = read(
				"SELECT * FROM tbl_book_loans WHERE bookId  = ? and branchId = ? and cardNo = ?",
				new Object[] { bookId, branchId, cardNo });
		if (bookLoans != null) {
			return bookLoans.get(0);
		} else {
			return null;
		}
	}

	public List<BookLoans> readBookLoansByBookId(Integer bookId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<BookLoans> bookLoans = read("SELECT * FROM tbl_book_loans WHERE bookId  = ?", new Object[] { bookId });
		if (bookLoans != null) {
			return bookLoans;
		} else {
			return null;
		}
	}

	public List<BookLoans> readBookLoansByBranchId(Integer branchId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<BookLoans> bookLoans = read("SELECT * FROM tbl_book_loans WHERE branchId  = ?", new Object[] { branchId });
		if (bookLoans != null) {
			return bookLoans;
		} else {
			return null;
		}
	}

	public List<BookLoans> readBookLoansByCardNo(Integer cardNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<BookLoans> bookLoans = read("SELECT * FROM tbl_book_loans WHERE cardNo  = ?", new Object[] { cardNo });
		if (bookLoans != null) {
			return bookLoans;
		} else {
			return null;
		}
	}

	@Override
	public List<BookLoans> extractData(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		BookDAO bDao = new BookDAO(conn);
		BranchDAO brDao = new BranchDAO(conn);
		BorrowerDAO boDao = new BorrowerDAO(conn);
		List<BookLoans> bookLoans = new ArrayList<>();
		while (rs.next()) {
			BookLoans a = new BookLoans();
			a.setDateOut(rs.getString("dateOut"));
			a.setDueDate(rs.getString("dueDate"));
			a.setDateIn(rs.getString("dateIn"));
			a.setBook(bDao.readBookByPK(rs.getInt("bookId")));
			a.setBranch(brDao.readBranchByPK(rs.getInt("branchId")));
			a.setBorrower(boDao.readBorrowerByPK(rs.getInt("cardNo")));
			bookLoans.add(a);
		}
		return bookLoans;
	}

	@Override
	public List<BookLoans> extractDataFirstLevel(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		BookDAO bDao = new BookDAO(conn);
		BranchDAO brDao = new BranchDAO(conn);
		BorrowerDAO boDao = new BorrowerDAO(conn);
		List<BookLoans> bookLoans = new ArrayList<>();
		while (rs.next()) {
			BookLoans a = new BookLoans();
			a.setDateOut(rs.getString("DateOut"));
			a.setDueDate(rs.getString("dueDate"));
			a.setDateIn(rs.getString("dateIn"));
			a.setBook(bDao.readBookByPK(rs.getInt("bookId")));
			a.setBranch(brDao.readBranchByPK(rs.getInt("branchId")));
			a.setBorrower(boDao.readBorrowerByPK(rs.getInt("cardNo")));
			bookLoans.add(a);
		}
		return bookLoans;
	}

}
