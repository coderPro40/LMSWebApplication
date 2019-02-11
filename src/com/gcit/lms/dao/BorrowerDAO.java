/*
 *
 *2:31:13 PM
 *Dec 21, 2017
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Borrower;

/**
 * @borrower ThankGod4Life
 * @date Dec 21, 2017
 *
 */
public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO(Connection conn) {
		super(conn);
	}

	// front end users need to know if operation was successful or fail. So
	// exception throw to front end
	public Integer addBorrowerWithID(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return saveWithID("INSERT INTO tbl_borrower (name, address, phone) VALUES (?, ?, ?)",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone() });
	}

	public void updateBorrower(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("UPDATE tbl_borrower SET name =?, address =?, phone =? WHERE cardNo = ?",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo() });
	}

	public void deleteBorrower(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] { borrower.getCardNo() });
	}

	public List<Borrower> readAllBorrowers()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_borrower", null);
	}
	
	public List<Borrower> readAllBorrowersWithLimit(Integer pageNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		return readLimit("SELECT * FROM tbl_borrower", null);
	}
	
	public Integer getBorrowersCount()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return getCount("SELECT COUNT(*) AS COUNT FROM tbl_borrower", null);
	}

	public List<Borrower> readBorrowersByName(String name)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		name = "%" + name + "%";
		return read("SELECT * FROM tbl_borrower WHERE name LIKE ?", new Object[] { name });
	}

	public Borrower readBorrowerByPK(Integer cardNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Borrower> borrowers = read("SELECT * FROM tbl_borrower WHERE cardNo  = ?", new Object[] { cardNo });
		if (borrowers != null) {
			return borrowers.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Borrower> extractData(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		BookDAO bDao = new BookDAO(conn);
		BranchDAO brDao = new BranchDAO(conn);
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower a = new Borrower();
			a.setCardNo(rs.getInt("cardNo"));
			a.setName(rs.getString("name"));
			a.setAddress(rs.getString("address"));
			a.setPhone(rs.getString("phone"));
			a.setBook(bDao.readFirstLevel(
					"SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_loans WHERE cardNo = ?)",
					new Object[] { a.getCardNo() }));
			a.setBranch(brDao.readFirstLevel(
					"SELECT * FROM tbl_library_branch WHERE branchId IN (SELECT branchId FROM tbl_book_loans WHERE cardNo = ?)",
					new Object[] { a.getCardNo() }));
			borrowers.add(a);
		}
		return borrowers;
	}

	@Override
	public List<Borrower> extractDataFirstLevel(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower a = new Borrower();
			a.setCardNo(rs.getInt("cardNo"));
			a.setName(rs.getString("name"));
			a.setAddress(rs.getString("address"));
			a.setPhone(rs.getString("phone"));
			borrowers.add(a);
		}
		return borrowers;
	}

}
