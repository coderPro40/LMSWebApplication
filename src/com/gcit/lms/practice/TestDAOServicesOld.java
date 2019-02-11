package com.gcit.lms.practice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.ConnectionUtil;

public class TestDAOServicesOld extends BaseService<Book> {

	public static ConnectionUtil connUtil = new ConnectionUtil();

	public static void readAllAuthors() // test with lone
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Connection conn = connUtil.getConnection();
		List<Object> author = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		Author athr = new Author();
		author = read(adao, conn, "readAllAuthors");
		Method getAuthorName = athr.getClass().getMethod("getAuthorName");

		for (Object object : author) {
			System.out.println(object);
			String authorName = (String) getAuthorName.invoke(object);
			System.out.println(authorName);
		}
	}

	public void readAllAuthorsssss(Connection conn) // test with abstract method
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		BookDAO adao = new BookDAO(conn);
		List<Book> athr = adao.readAllBooks();
		
		for (Book book : athr) {
			System.out.println(book.getTitle());
		}
//		readdd(adao, conn, "readAllBooks");
	}

	@Override
	public List<Book> extractData(List<Book> daoEntity) { // abstract method practice
		for (Book object : daoEntity) {
			System.out.println(object.getTitle());
			for (Author authors : object.getAuthors()) {
				System.out.println(authors.getAuthorName());
			}
			Publisher publisher = object.getPublisher();
			System.out.println(publisher.getPublisherName());
		}
		return null;
	}

	// public void addAuthor(Author author)
	// throws InstantiationException, IllegalAccessException,
	// ClassNotFoundException, SQLException {
	// Connection conn = connUtil.getConnection();
	// AuthorDAO adao = new AuthorDAO(conn);
	// execute(adao, conn, "addAuthor", author);
	//// try {
	//// adao.addAuthor(author);
	//// conn.commit();
	//// } catch (InstantiationException | IllegalAccessException |
	//// ClassNotFoundException | SQLException e) {
	//// e.printStackTrace();
	//// conn.rollback();
	//// } finally{
	//// conn.close();
	//// }
	// }
	// try {
	// adao.addAuthor(author);
	// conn.commit();
	// } catch (InstantiationException | IllegalAccessException |
	// ClassNotFoundException | SQLException e) {
	// e.printStackTrace();
	// conn.rollback();
	// } finally{
	// conn.close();
	// }

	// public void addBook(Book book)
	// throws InstantiationException, IllegalAccessException,
	// ClassNotFoundException, SQLException {
	// Connection conn = connUtil.getConnection();
	// BookDAO bdao = new BookDAO(conn);
	// // setting of book id
	// execute(bdao, conn, "addBookAuthors", book);
	// // try {
	// // book.setBookId(bdao.addBookWithID(book));
	// // bdao.addBookAuthors(book);
	// // //do for genre
	// // //Book
	// // conn.commit();
	// // } catch (InstantiationException | IllegalAccessException |
	// // ClassNotFoundException | SQLException e) {
	// // e.printStackTrace();
	// // conn.rollback();
	// // } finally{
	// // conn.close();
	// // }
	// }

}
