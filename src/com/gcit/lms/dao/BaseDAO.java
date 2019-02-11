package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDAO<T> {

	public Connection conn = null;

	public BaseDAO(Connection conn) {
		this.conn = conn;
	}
	
	private Integer pageNo = 0;
	private Integer pageSize = 5;
	
	/**
	 * @return the pageNo
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public PreparedStatement setObjects(String sql, Object[] vals)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		if (vals != null) {
			Integer count = 1;
			for (Object o : vals) {
				pstmt.setObject(count, o);
				count++;
			}
		}
		return pstmt;
	}

	public void save(String sql, Object[] vals)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		PreparedStatement pstmt = setObjects(sql, vals);
		pstmt.executeUpdate();
	}
	
	public Integer getCount(String sql, Object[] vals) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if(vals!=null){
			Integer count = 1;
			for(Object o: vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			return rs.getInt("COUNT");
		}
		return null;
	}

	public Integer saveWithID(String sql, Object[] vals)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		PreparedStatement pstmt = setObjects(sql, vals);
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}

	public List<T> read(String sql, Object[] vals)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		PreparedStatement pstmt = setObjects(sql, vals);
		return extractData(pstmt.executeQuery());
	}
	
	public List<T> readLimit(String sql, Object[] vals) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		if(getPageNo() > 0){
			sql+= " LIMIT "+(getPageNo()-1)*getPageSize()+" ,"+getPageSize();
		}
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if(vals!=null){
			Integer count = 1;
			for(Object o: vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		return extractData(pstmt.executeQuery());
	}

	public abstract List<T> extractData(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	public List<T> readFirstLevel(String sql, Object[] vals)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		PreparedStatement pstmt = setObjects(sql, vals);
		return extractDataFirstLevel(pstmt.executeQuery());
	}

	public abstract List<T> extractDataFirstLevel(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;

}
