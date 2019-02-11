/*
 *
 *9:34:25 PM
 *Dec 21, 2017
 */
package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

/**
 * @author ThankGod4Life
 * @date Dec 21, 2017
 *
 */
public class AdministratorService {
	public ConnectionUtil connUtil = new ConnectionUtil();

	public List<Book> readAllBooks()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		BookDAO bDao = new BookDAO(conn);
		List<Book> book = bDao.readAllBooks();
		return book;
	}

	public List<Book> readBooksByTitle(String title)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		BookDAO bDao = new BookDAO(conn);
		List<Book> book = bDao.readBooksByTitle(title);
		return book;
	}
	
	public Borrower readBorrowerByCardNo(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		BorrowerDAO bDao = new BorrowerDAO(conn);
		Borrower borrower = bDao.readBorrowerByPK(pk);
		return borrower;
	}

	public Book readBookByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		BookDAO bDao = new BookDAO(conn);
		Book book = bDao.readBookByPK(pk);
		return book;
	}

	public void addBookWithID(String title, Integer publisherId, List<Integer> authorIds, List<Integer> genreIds)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// must have title, publisherId, list of authorIds, and list of genreIds
		Connection conn = connUtil.getConnection();
		try {
			BookDAO bDao = new BookDAO(conn);
			PublisherDAO pDao = new PublisherDAO(conn);
			AuthorDAO aDao = new AuthorDAO(conn);
			GenreDAO gDao = new GenreDAO(conn);
			Book book = new Book();
			book.setTitle(title);
			book.setPublisher(pDao.readPublisherByPK(publisherId));
			List<Author> aList = new ArrayList<>();
			List<Genre> gList = new ArrayList<>();

			// add authors to book object
			for (Integer integer : authorIds) {
				aList.add(aDao.readAuthorsByPK(integer));
			}

			// add genres to book object
			for (Integer integer : genreIds) {
				gList.add(gDao.readGenreByPK(integer));
			}
			book.setAuthors(aList);
			book.setGenres(gList);
			Integer id = bDao.addBookWithID(book);
			book.setBookId(id);
			bDao.addBookAuthors(book);
			bDao.addBookGenres(book);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void updateBookTitle(Integer bookId, String title)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BookDAO bDao = new BookDAO(conn);
			Book book = bDao.readBookByPK(bookId);
			book.setTitle(title);
			bDao.updateBookTitle(book); // update book title
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteBook(Integer id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BookDAO bDao = new BookDAO(conn);
			Book book = bDao.readBookByPK(id);
			bDao.deleteBook(book);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void updateBookPublisher(Integer bookId, Integer publisherId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BookDAO bDao = new BookDAO(conn);
			PublisherDAO pDao = new PublisherDAO(conn);
			Book book = bDao.readBookByPK(bookId);
			book.setPublisher(pDao.readPublisherByPK(publisherId));
			bDao.updateBookPublisher(book); // update book title
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<Author> readAllAuthors()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		AuthorDAO aDao = new AuthorDAO(conn);
		List<Author> author = aDao.readAllAuthors();
		return author;
	}

	public List<Author> readAuthorsByName(String authorName)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// no transaction taking place, just reading
		Connection conn = connUtil.getConnection();
		AuthorDAO aDao = new AuthorDAO(conn);
		List<Author> author = aDao.readAuthorsByName(authorName);
		return author;
	}
	
	public List<Author> readAuthorsLimitSearch(Integer pageNo, String searchString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		if(searchString!=null){
			return adao.readAuthorsByName(searchString);
		}
		return adao.readAllAuthorsWithLimit(pageNo);
	}
	
	public Integer getAuthorsCount() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		return adao.getAuthorsCount();
	}
	
	public List<Book> readBooksLimitSearch(Integer pageNo, String searchString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		BookDAO adao = new BookDAO(conn);
		if(searchString!=null){
			return adao.readBooksByTitle(searchString);
		}
		return adao.readAllBooksWithLimit(pageNo);
	}
	
	public Integer getBooksCount() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		BookDAO adao = new BookDAO(conn);
		return adao.getBooksCount();
	}
	
	public List<Genre> readGenresLimitSearch(Integer pageNo, String searchString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		GenreDAO adao = new GenreDAO(conn);
		if(searchString!=null){
			return adao.readGenresByName(searchString);
		}
		return adao.readAllGenresWithLimit(pageNo);
	}
	
	public Integer getGenresCount() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		GenreDAO adao = new GenreDAO(conn);
		return adao.getGenresCount();
	}
	
	public List<Borrower> readBorrowersLimitSearch(Integer pageNo, String searchString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		BorrowerDAO adao = new BorrowerDAO(conn);
		if(searchString!=null){
			return adao.readBorrowersByName(searchString);
		}
		return adao.readAllBorrowersWithLimit(pageNo);
	}
	
	public Integer getBorrowersCount() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		BorrowerDAO adao = new BorrowerDAO(conn);
		return adao.getBorrowersCount();
	}
	
	public List<Publisher> readPublishersLimitSearch(Integer pageNo, String searchString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		PublisherDAO adao = new PublisherDAO(conn);
		if(searchString!=null){
			return adao.readPublishersByName(searchString);
		}
		return adao.readAllPublishersWithLimit(pageNo);
	}
	
	public Integer getPublishersCount() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		PublisherDAO adao = new PublisherDAO(conn);
		return adao.getPublishersCount();
	}
	
	public List<Branch> readBranchsLimitSearch(Integer pageNo, String searchString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		BranchDAO adao = new BranchDAO(conn);
		if(searchString!=null){
			return adao.readBranchsByName(searchString);
		}
		return adao.readAllBranchesWithLimit(pageNo);
	}
	
	public Integer getBranchsCount() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		BranchDAO adao = new BranchDAO(conn);
		return adao.getBranchesCount();
	}
	
	public List<BookLoans> readBookLoansLimitSearch(Integer pageNo, String searchString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		BookLoansDAO adao = new BookLoansDAO(conn);
		return adao.readAllBookLoansWithLimit(pageNo);
	}
	
	public Integer getBookLoansCount() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		BookLoansDAO adao = new BookLoansDAO(conn);
		return adao.getBookLoansCount();
	}
	
	public List<BookCopies> readBookCopiesLimitSearch(Integer pageNo, String searchString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		BookCopiesDAO adao = new BookCopiesDAO(conn);
		return adao.readAllBookCopiesWithLimit(pageNo);
	}
	
	public Integer getBookCopiesCount() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		BookCopiesDAO adao = new BookCopiesDAO(conn);
		return adao.getBookCopiesCount();
	}
	
	public List<BookCopies> readBookCopiesLimitSearchOverZero(Integer pageNo, String searchString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		BookCopiesDAO adao = new BookCopiesDAO(conn);
		return adao.readAllBookCopiesWithLimitOverZero(pageNo);
	}
	
	public Integer getBookCopiesCountOverZero() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn = connUtil.getConnection();
		BookCopiesDAO adao = new BookCopiesDAO(conn);
		return adao.getBookCopiesCountOverZero();
	}

	public Author readAuthorByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		AuthorDAO aDao = new AuthorDAO(conn);
		Author author = aDao.readAuthorsByPK(pk);
		return author;
	}

	public void addBookAuthors(Integer bookId, Integer authorId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BookDAO bDao = new BookDAO(conn);
			AuthorDAO pDao = new AuthorDAO(conn);
			Book book = bDao.readBookByPK(bookId);
			List<Author> authors = new ArrayList<>();
			authors.add(pDao.readAuthorsByPK(authorId));
			book.setAuthors(authors);
			bDao.addBookAuthors(book); // add to list
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public Integer addAuthorWithID(String name)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// must have title, publisherId, list of authorIds, and list of genreIds
		Connection conn = connUtil.getConnection();
		try {
			AuthorDAO aDao = new AuthorDAO(conn);
			Author author = new Author();
			author.setAuthorName(name);
			Integer id = aDao.addAuthorWithID(author);
			conn.commit();
			return id;
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
		return null;
	}

	public void updateAuthor(Integer id, String name)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			AuthorDAO aDao = new AuthorDAO(conn);
			Author author = aDao.readAuthorsByPK(id);
			author.setAuthorName(name);
			aDao.updateAuthor(author);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteAuthor(Integer id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			AuthorDAO bDao = new AuthorDAO(conn);
			Author author = bDao.readAuthorsByPK(id);
			bDao.deleteAuthor(author);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<Genre> readAllGenres()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		GenreDAO gDao = new GenreDAO(conn);
		List<Genre> genre = gDao.readAllGenres();
		return genre;
	}

	public List<Genre> readGenresByName(String title)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		GenreDAO gDao = new GenreDAO(conn);
		List<Genre> genre = gDao.readGenresByName(title);
		return genre;
	}

	public void deleteGenre(Integer id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			GenreDAO bDao = new GenreDAO(conn);
			Genre genre = bDao.readGenreByPK(id);
			bDao.deleteGenre(genre);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public Genre readGenreByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		GenreDAO gDao = new GenreDAO(conn);
		Genre genre = gDao.readGenreByPK(pk);
		return genre;
	}

	public void addBookGenres(Integer bookId, Integer genreId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BookDAO bDao = new BookDAO(conn);
			GenreDAO pDao = new GenreDAO(conn);
			Book book = bDao.readBookByPK(bookId);
			List<Genre> genres = new ArrayList<>();
			genres.add(pDao.readGenreByPK(genreId));
			book.setGenres(genres);
			bDao.addBookGenres(book); // add to list
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public Integer addGenreWithID(String name)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// must have title, publisherId, list of genreIds, and list of genreIds
		Connection conn = connUtil.getConnection();
		try {
			GenreDAO aDao = new GenreDAO(conn);
			Genre genre = new Genre();
			genre.set_Genre_Name(name);
			Integer id = aDao.addGenreWithID(genre);
			conn.commit();
			return id;
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
		return null;
	}

	public void updateGenre(Integer id, String name)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			GenreDAO aDao = new GenreDAO(conn);
			Genre genre = aDao.readGenreByPK(id);
			genre.set_Genre_Name(name);
			aDao.updateGenre(genre);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<Publisher> readAllPublishers()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		PublisherDAO pDao = new PublisherDAO(conn);
		List<Publisher> publisher = pDao.readAllPublishers();
		return publisher;
	}

	public List<Publisher> readPublishersByName(String title)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		PublisherDAO pDao = new PublisherDAO(conn);
		List<Publisher> publisher = pDao.readPublishersByName(title);
		return publisher;
	}

	public Publisher readPublisherByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		PublisherDAO pDao = new PublisherDAO(conn);
		Publisher publisher = pDao.readPublisherByPK(pk);
		return publisher;
	}

	public void deletePublisher(Integer id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			PublisherDAO bDao = new PublisherDAO(conn);
			Publisher publisher = bDao.readPublisherByPK(id);
			bDao.deletePublisher(publisher);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public Integer addPublisherWithID(String name, String address, String phone)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// must have title, publisherId, list of authorIds, and list of genreIds
		Connection conn = connUtil.getConnection();
		try {
			PublisherDAO pDao = new PublisherDAO(conn);
			Publisher publisher = new Publisher();
			publisher.setPublisherName(name);
			publisher.setPublisherAddress(address);
			publisher.setPublisherPhone(phone);
			Integer id = pDao.addPublisherWithID(publisher);
			conn.commit();
			return id;
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
		return null;
	}

	public void updatePublishersName(Integer id, String name, String address, String phone)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			PublisherDAO pDao = new PublisherDAO(conn);
			Publisher publisher = pDao.readPublisherByPK(id);
			publisher.setPublisherName(name);
			publisher.setPublisherAddress(address);
			publisher.setPublisherPhone(phone);
			pDao.updatePublisherName(publisher);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public Integer addBorrowerWithID(String name, String address, String phone)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BorrowerDAO gDao = new BorrowerDAO(conn);
			Borrower borrower = new Borrower();
			borrower.setName(name);
			borrower.setAddress(address);
			borrower.setPhone(phone);
			Integer id = gDao.addBorrowerWithID(borrower);
			conn.commit();
			return id;
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
		return null;
	}

	public void updateBorrower(Integer cardNo, String name, String address, String phone)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BorrowerDAO gDao = new BorrowerDAO(conn);
			Borrower borrower = gDao.readBorrowerByPK(cardNo);
			borrower.setName(name);
			borrower.setAddress(address);
			borrower.setPhone(phone);
			gDao.updateBorrower(borrower);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteBorrower(Integer id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BorrowerDAO bDao = new BorrowerDAO(conn);
			Borrower borrower = bDao.readBorrowerByPK(id);
			bDao.deleteBorrower(borrower);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public Branch readBranchByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		BranchDAO gDao = new BranchDAO(conn);
		Branch branch = gDao.readBranchByPK(pk);
		return branch;
	}

	public Integer addBranchWithID(String name, String address)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BranchDAO gDao = new BranchDAO(conn);
			Branch branch = new Branch();
			branch.setBranchName(name);
			branch.setBranchAddress(address);
			Integer id = gDao.addBranchWithID(branch);
			conn.commit();
			return id;
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
		return null;
	}

	public void updateBranch(Integer cardNo, String name, String address)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BranchDAO gDao = new BranchDAO(conn);
			Branch branch = gDao.readBranchByPK(cardNo);
			branch.setBranchName(name);
			branch.setBranchAddress(address);
			gDao.updateBranch(branch);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteBranch(Integer id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BranchDAO bDao = new BranchDAO(conn);
			Branch branch = bDao.readBranchByPK(id);
			bDao.deleteBranch(branch);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<BookLoans> readAllBookLoans()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		BookLoansDAO pDao = new BookLoansDAO(conn);
		List<BookLoans> bookLoans = pDao.readAllBookLoans();
		return bookLoans;
	}

	public void updateBookLoansByDueDate(Integer bookId, Integer branchId, Integer cardNo, String dueDate)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BookLoansDAO pDao = new BookLoansDAO(conn);
			BookLoans bookLoans = pDao.readBookLoansByIds(bookId, branchId, cardNo);
			bookLoans.setDueDate(dueDate);
			pDao.updateBookLoansByDueDate(bookLoans);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	public void addBookLoans(Book book, Branch branch, Borrower borrower, String dateOut, String dueDate)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BookLoansDAO gDao = new BookLoansDAO(conn);
			BookLoans bookLoans = new BookLoans();
			bookLoans.setBook(book);
			bookLoans.setBranch(branch);
			bookLoans.setBorrower(borrower);
			bookLoans.setDateOut(dateOut);
			bookLoans.setDueDate(dueDate);
			gDao.addBookLoans(bookLoans);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	public void updateBookLoansByDateIn(Integer bookId, Integer branchId, Integer cardNo, String dateIn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BookLoansDAO pDao = new BookLoansDAO(conn);
			BookLoans bookLoans = pDao.readBookLoansByIds(bookId, branchId, cardNo);
			bookLoans.setDateIn(dateIn);
			pDao.updateBookLoansByDateIn(bookLoans);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	public void updateBookCopiesByIds(Integer bookId, Integer branchId, Integer noOfCopies)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		try {
			BookCopiesDAO pDao = new BookCopiesDAO(conn);
			BookCopies bookCopies = pDao.readBookCopiesByIds(bookId, branchId);
			bookCopies.setNoOfCopies(bookCopies.getNoOfCopies() + noOfCopies);
			pDao.updateBookCopies(bookCopies);
			conn.commit();
		} catch (Exception e) {
			System.out.println("failed");
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public BookLoans readBookLoansByIds(Integer bookId, Integer branchId, Integer cardNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		BookLoansDAO pDao = new BookLoansDAO(conn);
		BookLoans bookLoans = pDao.readBookLoansByIds(bookId, branchId, cardNo);
		return bookLoans;

	}

	public List<Borrower> readAllBorrowers()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		BorrowerDAO gDao = new BorrowerDAO(conn);
		List<Borrower> borrower = gDao.readAllBorrowers();
		return borrower;
	}

	public Borrower readBorrowerByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		BorrowerDAO gDao = new BorrowerDAO(conn);
		Borrower borrower = gDao.readBorrowerByPK(pk);
		return borrower;
	}

	public List<Branch> readAllBranchs()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		BranchDAO gDao = new BranchDAO(conn);
		List<Branch> branch = gDao.readAllBranchs();
		return branch;
	}

	public BookCopies readBookCopiesByIds(Integer bookId, Integer branchId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = connUtil.getConnection();
		BookCopiesDAO pDao = new BookCopiesDAO(conn);
		BookCopies bookCopies = pDao.readBookCopiesByIds(bookId, branchId);
		return bookCopies;
	}

	// Connection conn = connUtil.getConnection();
	// try {
	// conn.commit();
	// } catch(Exception e) {
	// conn.rollback();
	// }
	// finally {
	// conn.close();
	// }
}
