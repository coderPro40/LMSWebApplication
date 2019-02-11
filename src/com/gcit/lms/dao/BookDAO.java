package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;

public class BookDAO extends BaseDAO<Book> {

	public BookDAO(Connection conn) {
		super(conn);
	}

	// adding book and publisher
	public Integer addBookWithID(Book book)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return saveWithID("INSERT INTO tbl_book (title, pubId) VALUES (?, ?)",
				new Object[] { book.getTitle(), book.getPublisher().getPublisherId() });
	}

	public void addBookAuthors(Book book)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		for (Author a : book.getAuthors()) {
			save("INSERT INTO tbl_book_authors VALUES (?, ?)", new Object[] { book.getBookId(), a.getAuthorId() });
		}
	}

	public void addBookGenres(Book book)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		for (Genre a : book.getGenres()) {
			save("INSERT INTO tbl_book_genres VALUES (?, ?)", new Object[] { a.get_Genre_Id(), book.getBookId() });
		}
	}

	// can have author and genre and publisher change names and addresses but to
	// change id can't happen after addition except deletion
	public void updateBookTitle(Book book)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("UPDATE tbl_book SET title =? WHERE bookId = ?", new Object[] { book.getTitle(), book.getBookId() });
	}

	public void updateBookPublisher(Book book)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("UPDATE tbl_book SET pubId =? WHERE bookId = ?",
				new Object[] { book.getPublisher().getPublisherId(), book.getBookId() });
	}

	public void deleteBook(Book book)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("DELETE FROM tbl_book WHERE bookId = ?", new Object[] { book.getBookId() });
	}

	public List<Book> readAllBooks()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book", null);
	}
	public Integer getBooksCount()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return getCount("SELECT COUNT(*) AS COUNT FROM tbl_book", null);
	}
	
	public List<Book> readAllBooksWithLimit(Integer pageNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		return readLimit("SELECT * FROM tbl_book", null);
	}

	public List<Book> readBooksByTitle(String title)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		title = "%" + title + "%";
		return read("SELECT * FROM tbl_book WHERE title LIKE ?", new Object[] { title });
	}

	public Book readBookByPK(Integer bookId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Book> books = read("SELECT * FROM tbl_book WHERE bookId  = ?", new Object[] { bookId });
		if (books != null) {
			return books.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Book> extractData(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		PublisherDAO pDao = new PublisherDAO(conn);
		AuthorDAO aDao = new AuthorDAO(conn);
		GenreDAO gDao = new GenreDAO(conn);
		BorrowerDAO bDao = new BorrowerDAO(conn);
		BranchDAO brDao = new BranchDAO(conn);
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setPublisher(pDao.readPublisherByPK(rs.getInt("pubId")));
			b.setAuthors(aDao.readFirstLevel(
					"SELECT * FROM tbl_author WHERE authorId IN (SELECT authorId FROM tbl_book_authors WHERE bookId = ?)",
					new Object[] { b.getBookId() }));
			b.setGenres(gDao.readFirstLevel(
					"SELECT * FROM tbl_genre WHERE genre_id IN (SELECT genre_id FROM tbl_book_genres WHERE bookId = ?)",
					new Object[] { b.getBookId() }));
			b.setBorrower(bDao.readFirstLevel(
					"SELECT * FROM tbl_borrower WHERE cardNo IN (SELECT cardNo FROM tbl_book_loans WHERE bookId = ?)",
					new Object[] { b.getBookId() }));
			b.setBranch(brDao.readFirstLevel(
					"SELECT * FROM tbl_library_branch WHERE branchId IN (SELECT branchId FROM tbl_book_copies WHERE bookId = ?)",
					new Object[] { b.getBookId() }));
			books.add(b);
		}
		return books;
	}

	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			books.add(b);
		}
		return books;
	}

}
