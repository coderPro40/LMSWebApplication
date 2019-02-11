/*
 *
 *2:35:45 AM
 *Dec 21, 2017
 */
package com.gcit.lms.practice;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.lms.service.ConnectionUtil;

/**
 * @author ThankGod4Life
 * @date Dec 21, 2017
 *
 */
public class Main {
	public static ConnectionUtil connUtil = new ConnectionUtil();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			TestDAOServices admin = new TestDAOServices();
			admin.readAllBooks(conn);
			System.out.println("\n-----------------------------\n");
			admin.readBorrowersByName(conn);
			System.out.println("\n-----------------------------\n");
			admin.readBorrowerByPK(conn);
			System.out.println("\n-----------------------------\n");
//			admin.deleteBorrower(conn);
			conn.commit();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
