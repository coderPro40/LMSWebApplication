package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> {

	public PublisherDAO(Connection conn) {
		super(conn);
	}

	// front end users need to know if operation was successful or fail. So
	// exception throw to front end
	public Integer addPublisherWithID(Publisher publisher)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return saveWithID(
				"INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) VALUES (?, ?, ?)",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(),
						publisher.getPublisherPhone() });
	}

	public void updatePublisherName(Publisher publisher)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("UPDATE tbl_publisher SET publisherName =?, publisherAddress =?, publisherPhone =? WHERE publisherId = ?",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(),
						publisher.getPublisherPhone(), publisher.getPublisherId() });
	}

	public void deletePublisher(Publisher publisher)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		save("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[] { publisher.getPublisherId() });
	}

	public List<Publisher> readAllPublishers()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_publisher", null);
	}
	
	public List<Publisher> readAllPublishersWithLimit(Integer pageNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		return readLimit("SELECT * FROM tbl_publisher", null);
	}
	
	public Integer getPublishersCount()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return getCount("SELECT COUNT(*) AS COUNT FROM tbl_publisher", null);
	}

	public List<Publisher> readPublishersByName(String publisherName)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		publisherName = "%" + publisherName + "%";
		return read("SELECT * FROM tbl_publisher WHERE publisherName LIKE ?", new Object[] { publisherName });
	}

	public Publisher readPublisherByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Publisher> publishers = read("SELECT * FROM tbl_publisher WHERE publisherId  = ?", new Object[] { pk });
		if (publishers != null) {
			return publishers.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Publisher> extractData(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		BookDAO bDao = new BookDAO(conn);
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			p.setBook(bDao.readFirstLevel(
					"SELECT * FROM tbl_book WHERE pubId IN (SELECT publisherId FROM tbl_publisher WHERE publisherId = ?)",
					new Object[] { p.getPublisherId() }));
			publishers.add(p);
		}
		return publishers;
	}

	@Override
	public List<Publisher> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(p);
		}
		return publishers;
	}

}
