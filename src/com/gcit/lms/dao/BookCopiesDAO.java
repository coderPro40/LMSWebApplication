/*
 *
 *2:36:49 PM
 *Dec 21, 2017
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.BookCopies;

/**
 * @author ThankGod4Life
 * @date Dec 21, 2017
 *
 */
public class BookCopiesDAO extends BaseDAO<BookCopies> {

	public BookCopiesDAO(Connection conn) {
		super(conn);
	}

	// front end users need to know if operation was successful or fail. So
	// exception throw to front end
	public void addBookCopies(BookCopies bookCopies)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES (?, ?, ?)", new Object[] {
				bookCopies.getBook().getBookId(), bookCopies.getBranch().getBranchId(), bookCopies.getNoOfCopies() });
	}

	public void updateBookCopies(BookCopies bookCopies)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("UPDATE tbl_book_copies SET noOfCopies =? WHERE bookId = ? and branchId = ?", new Object[] {
				bookCopies.getNoOfCopies(), bookCopies.getBook().getBookId(), bookCopies.getBranch().getBranchId() });
	}

	public void deleteBookCopies(BookCopies bookCopies)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("DELETE FROM tbl_book_copies WHERE bookId = ? and branchId = ?",
				new Object[] { bookCopies.getBook().getBookId(), bookCopies.getBranch().getBranchId() });
	}

	public List<BookCopies> readAllBookCopies()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_copies", null);
	}
	
	public Integer getBookCopiesCount()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return getCount("SELECT COUNT(*) AS COUNT FROM tbl_book_copies", null);
	}
	
	public Integer getBookCopiesCountOverZero()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return getCount("SELECT COUNT(*) AS COUNT FROM tbl_book_copies WHERE noOfCopies > 0", null);
	}
	
	public List<BookCopies> readAllBookCopiesWithLimit(Integer pageNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		return readLimit("SELECT * FROM tbl_book_copies", null);
	}
	
	public List<BookCopies> readAllBookCopiesWithLimitOverZero(Integer pageNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		return readLimit("SELECT * FROM tbl_book_copies WHERE noOfCopies > 0", null);
	}
	
	public List<BookCopies> readAllBookCopiesOverZero()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_copies WHERE noOfCopies > 0", null);
	}
	
	public BookCopies readBookCopiesByIds(Integer bookId, Integer branchId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<BookCopies> bookCopies = read("SELECT * FROM tbl_book_copies WHERE bookId =? and branchId  = ?",
				new Object[] { bookId, branchId });
		if (bookCopies != null) {
			return bookCopies.get(0);
		} else {
			return null;
		}
	}

	public List<BookCopies> readBookCopiesByBookId(Integer bookId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<BookCopies> bookCopies = read("SELECT * FROM tbl_book_copies WHERE bookId  = ?", new Object[] { bookId });
		if (bookCopies != null) {
			return bookCopies;
		} else {
			return null;
		}
	}

	public List<BookCopies> readBookCopiesByBranchId(Integer branchId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<BookCopies> bookCopies = read("SELECT * FROM tbl_book_copies WHERE branchId  = ?",
				new Object[] { branchId });
		if (bookCopies != null) {
			return bookCopies;
		} else {
			return null;
		}
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		BookDAO bDao = new BookDAO(conn);
		BranchDAO brDao = new BranchDAO(conn);
		List<BookCopies> bookCopies = new ArrayList<>();
		while (rs.next()) {
			BookCopies a = new BookCopies();
			a.setNoOfCopies(rs.getInt("noOfCopies"));
			a.setBook(bDao.readBookByPK(rs.getInt("bookId")));
			a.setBranch(brDao.readBranchByPK(rs.getInt("branchId")));
			bookCopies.add(a);
		}
		return bookCopies;
	}

	@Override
	public List<BookCopies> extractDataFirstLevel(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		BookDAO bDao = new BookDAO(conn);
		BranchDAO brDao = new BranchDAO(conn);
		List<BookCopies> bookCopies = new ArrayList<>();
		while (rs.next()) {
			BookCopies a = new BookCopies();
			a.setNoOfCopies(rs.getInt("noOfCopies"));
			a.setBook(bDao.readBookByPK(rs.getInt("bookId")));
			a.setBranch(brDao.readBranchByPK(rs.getInt("branchId")));
			bookCopies.add(a);
		}
		return bookCopies;
	}

}
