/*
 *
 *2:31:45 PM
 *Dec 21, 2017
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Branch;

/**
 * @author ThankGod4Life
 * @date Dec 21, 2017
 *
 */
public class BranchDAO extends BaseDAO<Branch> {

	public BranchDAO(Connection conn) {
		super(conn);
	}

	// front end users need to know if operation was successful or fail. So
	// exception throw to front end
	public Integer addBranchWithID(Branch branch)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return saveWithID("INSERT INTO tbl_library_branch VALUES (?, ?, ?)",
				new Object[] { branch.getBranchId(), branch.getBranchName(), branch.getBranchAddress() });
	}

	public void updateBranch(Branch branch)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("UPDATE tbl_library_branch SET branchName =?, branchAddress =? WHERE branchId = ?",
				new Object[] { branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId() });
	}

	public void deleteBranch(Branch branch)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[] { branch.getBranchId() });
	}

	public List<Branch> readAllBranchs()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_library_branch", null);
	}

	public List<Branch> readBranchsByName(String branchName)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		branchName = "%" + branchName + "%";
		return read("SELECT * FROM tbl_library_branch WHERE branchName LIKE ?", new Object[] { branchName });
	}
	
	public List<Branch> readAllBranchesWithLimit(Integer pageNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		return readLimit("SELECT * FROM tbl_library_branch", null);
	}
	
	public Integer getBranchesCount()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return getCount("SELECT COUNT(*) AS COUNT FROM tbl_library_branch", null);
	}

	public Branch readBranchByPK(Integer branchId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Branch> branchs = read("SELECT * FROM tbl_library_branch WHERE branchId  = ?", new Object[] { branchId });
		if (branchs != null) {
			return branchs.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Branch> extractData(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		BookDAO bDao = new BookDAO(conn);
		BorrowerDAO brDao = new BorrowerDAO(conn);
		List<Branch> branchs = new ArrayList<>();
		while (rs.next()) {
			Branch a = new Branch();
			a.setBranchId(rs.getInt("branchId"));
			a.setBranchName(rs.getString("branchName"));
			a.setBranchAddress(rs.getString("branchAddress"));
			a.setBook(bDao.readFirstLevel(
					"SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_copies WHERE branchId = ?)",
					new Object[] { a.getBranchId() }));
			a.setBorrower(brDao.readFirstLevel(
					"SELECT * FROM tbl_borrower WHERE cardNo IN (SELECT cardNo FROM tbl_book_loans WHERE branchId = ?)",
					new Object[] { a.getBranchId() }));
			branchs.add(a);
		}
		return branchs;
	}

	@Override
	public List<Branch> extractDataFirstLevel(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Branch> branchs = new ArrayList<>();
		while (rs.next()) {
			Branch a = new Branch();
			a.setBranchId(rs.getInt("branchId"));
			a.setBranchName(rs.getString("branchName"));
			a.setBranchAddress(rs.getString("branchAddress"));
			branchs.add(a);
		}
		return branchs;
	}

}
