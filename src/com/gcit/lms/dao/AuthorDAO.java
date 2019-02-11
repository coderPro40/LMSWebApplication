package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;

public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection conn) {
		super(conn);
	}

	// front end users need to know if operation was successful or fail. So
	// exception throw to front end
	public Integer addAuthorWithID(Author author)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return saveWithID("INSERT INTO tbl_author (authorName) VALUES (?)", new Object[] { author.getAuthorName() });
	}

	public Integer getAuthorsCount()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return getCount("SELECT COUNT(*) AS COUNT FROM tbl_author", null);
	}
	
	public void updateAuthor(Author author)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("UPDATE tbl_author SET authorName =? WHERE authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("DELETE FROM tbl_author WHERE authorId = ?", new Object[] { author.getAuthorId() });
	}

	public List<Author> readAllAuthors()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_author", null);
	}
	
	public List<Author> readAllAuthorsWithLimit(Integer pageNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		return readLimit("SELECT * FROM tbl_author", null);
	}

	public List<Author> readAuthorsByName(String authorName)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		authorName = "%" + authorName + "%";
		return read("SELECT * FROM tbl_author WHERE authorName LIKE ?", new Object[] { authorName });
	}

	public Author readAuthorsByPK(Integer authorId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Author> authors = read("SELECT * FROM tbl_author WHERE authorId  = ?", new Object[] { authorId });
		if (authors != null) {
			return authors.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Author> extractData(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		BookDAO bDao = new BookDAO(conn);
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			a.setBook(bDao.readFirstLevel(
					"SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_authors WHERE authorId = ?)",
					new Object[] { a.getAuthorId() }));
			authors.add(a);
		}
		return authors;
	}

	@Override
	public List<Author> extractDataFirstLevel(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}

}
