/*
 *
 *2:32:13 PM
 *Dec 21, 2017
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Genre;

/**
 * @genre ThankGod4Life
 * @date Dec 21, 2017
 *
 */
public class GenreDAO extends BaseDAO<Genre> {

	public GenreDAO(Connection conn) {
		super(conn);
	}
	
	public Integer addGenreWithID(Genre genre)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return saveWithID("INSERT INTO tbl_genre (genre_name) VALUES (?)", new Object[] { genre.get_Genre_Name() });
	}

	public void updateGenre(Genre genre)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("UPDATE tbl_genre SET genre_name =? WHERE genre_id = ?",
				new Object[] { genre.get_Genre_Name(), genre.get_Genre_Id() });
	}

	public void deleteGenre(Genre genre)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("DELETE FROM tbl_genre WHERE genre_id = ?", new Object[] { genre.get_Genre_Id() });
	}

	public List<Genre> readAllGenres()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_genre", null);
	}
	
	public List<Genre> readAllGenresWithLimit(Integer pageNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		return readLimit("SELECT * FROM tbl_genre", null);
	}
	
	public Integer getGenresCount()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return getCount("SELECT COUNT(*) AS COUNT FROM tbl_genre", null);
	}

	public List<Genre> readGenresByName(String genre_name)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		genre_name = "%" + genre_name + "%";
		return read("SELECT * FROM tbl_genre WHERE genre_name LIKE ?", new Object[] { genre_name });
	}

	public Genre readGenreByPK(Integer genre_id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Genre> genres = read("SELECT * FROM tbl_genre WHERE genre_id  = ?", new Object[] { genre_id });
		if (genres != null) {
			return genres.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		BookDAO bDao = new BookDAO(conn);
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre a = new Genre();
			a.set_Genre_Id(rs.getInt("genre_id"));
			a.set_Genre_Name(rs.getString("genre_name"));
			a.setBook(bDao.readFirstLevel("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_genres WHERE genre_id = ?)", new Object[]{a.get_Genre_Id()}));
			genres.add(a);
		}
		return genres;
	}
	
	@Override
	public List<Genre> extractDataFirstLevel(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre a = new Genre();
			a.set_Genre_Id(rs.getInt("genre_id"));
			a.set_Genre_Name(rs.getString("genre_name"));
			genres.add(a);
		}
		return genres;
	}

}
