/*
 *
 *11:29:38 PM
 *Dec 20, 2017
 */
package com.gcit.lms.practice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ThankGod4Life
 * @param <T>
 * @date Dec 20, 2017
 *
 */
public abstract class BaseService<T> {
	public void execute(Object daoEntity, Connection conn, String daoMethod, Object receivedObject)
			throws SQLException, ClassNotFoundException, InstantiationException {
		try {
			// https://stackoverflow.com/questions/160970/how-do-i-invoke-a-java-method-when-given-the-method-name-as-a-string
			Method setDaoMethod = daoEntity.getClass().getMethod(daoMethod, String.class);
			setDaoMethod.invoke(daoEntity, receivedObject);
			conn.commit();
		} catch (IllegalAccessException | SQLException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	@SuppressWarnings("unchecked") // lone test
	public static List<Object> read(Object daoEntity, Connection conn, String daoMethod) throws SQLException {
		try {
			// https://stackoverflow.com/questions/160970/how-do-i-invoke-a-java-method-when-given-the-method-name-as-a-string
			Method getDaoMethod = daoEntity.getClass().getMethod(daoMethod);
			List<Object> result = (List<Object>) getDaoMethod.invoke(daoEntity);
			return result;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked") // abstract method test
	public List<T> readdd(Object daoEntity, Connection conn, String daoMethod) throws SQLException {
		try {
			// override abstract parameters using T for abstract class
			// https://stackoverflow.com/questions/40314719/java-generic-abstract-method-parameters
			Method getDaoMethod = daoEntity.getClass().getMethod(daoMethod);
			List<T> result = (List<T>) getDaoMethod.invoke(daoEntity);
			return extractData(result);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return null;
	}
	
	public abstract List<T> extractData(List<T> result);
}
