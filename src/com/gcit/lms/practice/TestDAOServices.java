package com.gcit.lms.practice;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

public class TestDAOServices {

	public void readAllBooks(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookDAO bDao = new BookDAO(conn);
		List<Book> book = bDao.readAllBooks();
		for (Book book2 : book) {
			System.out.println(book2.getTitle());
		}
	}

	public void readBooksByTitle(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookDAO bDao = new BookDAO(conn);
		String title = "a";
		List<Book> book = bDao.readBooksByTitle(title);
		for (Book book2 : book) {
			System.out.println(book2.getTitle());
		}
	}

	public void readBookByPK(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookDAO bDao = new BookDAO(conn);
		Integer pk = 3;
		Book book = bDao.readBookByPK(pk);
		System.out.println(book.getTitle());
	}

	public void addBookWithID(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookDAO bDao = new BookDAO(conn);
		PublisherDAO pDao = new PublisherDAO(conn);
		Book book = new Book();
		Publisher publisher = new Publisher();
		publisher.setPublisherName("Apex");
		publisher.setPublisherAddress("975 Yahrmouth drive, SE");
		publisher.setPublisherPhone("222-389-8874");
		Integer id = pDao.addPublisherWithID(publisher);
		publisher.setPublisherId(id);
		book.setTitle("A Bucky Life");
		book.setPublisher(publisher);
		Integer id2 = bDao.addBookWithID(book);
		System.out.println(id + " " + id2);
	}

	public void addBookAuthors(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookDAO bDao = new BookDAO(conn);
		AuthorDAO aDao = new AuthorDAO(conn);
		List<Book> book = bDao.readBooksByTitle("A Bucky Life");
		List<Author> author = new ArrayList<>();
		author.add(aDao.readAuthorsByPK(5));
		book.get(0).setAuthors(author);
		bDao.addBookAuthors(book.get(0));
	}

	public void addBookGenres(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookDAO bDao = new BookDAO(conn);
		GenreDAO aDao = new GenreDAO(conn);
		List<Book> book = bDao.readBooksByTitle("A Bucky Life");
		List<Genre> genre = new ArrayList<>();
		genre.add(aDao.readGenreByPK(2));
		book.get(0).setGenres(genre);
		bDao.addBookGenres(book.get(0));
	}

	public void updateBookTitle(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookDAO bDao = new BookDAO(conn);
		Book book = new Book();
		book.setTitle("Johnny on the Spot");
		book.setBookId(11);
		bDao.updateBookTitle(book);
	}

	public void updateBookPublisher(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookDAO bDao = new BookDAO(conn);
		PublisherDAO pDao = new PublisherDAO(conn);
		Book book = bDao.readBookByPK(11);
		Publisher publisher = book.getPublisher();
		publisher.setPublisherName("Manking Factory");
		pDao.updatePublisherName(publisher);
	}

	public void updateBookAuthor(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookDAO bDao = new BookDAO(conn);
		AuthorDAO aDao = new AuthorDAO(conn);
		Book book = bDao.readBookByPK(11);
		for (Author author : book.getAuthors()) {
			if (author.getAuthorId().equals(8)) {
				author.setAuthorName("Ophicia");
				aDao.updateAuthor(author);
			}
		}
	}

	public void updateBookGenre(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookDAO bDao = new BookDAO(conn);
		GenreDAO aDao = new GenreDAO(conn);
		Book book = bDao.readBookByPK(6);
		for (Genre genre : book.getGenres()) {
			if (genre.get_Genre_Id().equals(6)) {
				genre.set_Genre_Name("Motocade");
				aDao.updateGenre(genre);
			}
		}
	}

	public void deleteBook(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookDAO bDao = new BookDAO(conn);
		Book book = bDao.readBookByPK(14);
		bDao.deleteBook(book);
	}

	public void readAllGenres(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		GenreDAO gDao = new GenreDAO(conn);
		List<Genre> genre = gDao.readAllGenres();
		for (Genre genre2 : genre) {
			System.out.println(genre2.get_Genre_Name());
		}
	}

	public void readGenresByName(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		GenreDAO gDao = new GenreDAO(conn);
		String title = "a";
		List<Genre> genre = gDao.readGenresByName(title);
		for (Genre genre2 : genre) {
			System.out.println(genre2.get_Genre_Name());
		}
	}

	public void readGenreByPK(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		GenreDAO gDao = new GenreDAO(conn);
		Integer pk = 3;
		Genre genre = gDao.readGenreByPK(pk);
		System.out.println(genre.get_Genre_Name());
	}

	public void addGenreWithID(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		GenreDAO gDao = new GenreDAO(conn);
		Genre genre = new Genre();
		genre.set_Genre_Name("Frulup");
		Integer id = gDao.addGenreWithID(genre);
		System.out.println(id);
	}

	public void deleteGenre(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		GenreDAO bDao = new GenreDAO(conn);
		Genre genre = bDao.readGenreByPK(9);
		bDao.deleteGenre(genre);
	}

	public void readAllAuthors(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		AuthorDAO aDao = new AuthorDAO(conn);
		List<Author> author = aDao.readAllAuthors();
		for (Author author2 : author) {
			System.out.println(author2.getAuthorName());
		}
	}

	public void readAuthorsByName(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		AuthorDAO aDao = new AuthorDAO(conn);
		String title = "a";
		List<Author> author = aDao.readAuthorsByName(title);
		for (Author author2 : author) {
			System.out.println(author2.getAuthorName());
		}
	}

	public void readAuthorByPK(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		AuthorDAO aDao = new AuthorDAO(conn);
		Integer pk = 3;
		Author author = aDao.readAuthorsByPK(pk);
		System.out.println(author.getAuthorName());
	}

	public void addAuthorWithID(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		AuthorDAO aDao = new AuthorDAO(conn);
		Author author = new Author();
		author.setAuthorName("Sulup");
		Integer id = aDao.addAuthorWithID(author);
		System.out.println(id);
	}

	public void deleteAuthor(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		AuthorDAO aDao = new AuthorDAO(conn);
		Author author = aDao.readAuthorsByPK(10);
		aDao.deleteAuthor(author);
	}
	
	public void readAllPublishers(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		PublisherDAO pDao = new PublisherDAO(conn);
		List<Publisher> publisher = pDao.readAllPublishers();
		for (Publisher publisher2 : publisher) {
			System.out.println(publisher2.getPublisherName());
		}
	}

	public void readPublishersByName(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		PublisherDAO pDao = new PublisherDAO(conn);
		String title = "a";
		List<Publisher> publisher = pDao.readPublishersByName(title);
		for (Publisher publisher2 : publisher) {
			System.out.println(publisher2.getPublisherName());
		}
	}

	public void readPublisherByPK(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		PublisherDAO pDao = new PublisherDAO(conn);
		Integer pk = 3;
		Publisher publisher = pDao.readPublisherByPK(pk);
		System.out.println(publisher.getPublisherName());
	}
	
	public void deletePublisher(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		PublisherDAO pDao = new PublisherDAO(conn);
		Publisher publisher = pDao.readPublisherByPK(12);
		pDao.deletePublisher(publisher);
	}
	
	public void readAllBookCopies(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookCopiesDAO pDao = new BookCopiesDAO(conn);
		List<BookCopies> bookCopies = pDao.readAllBookCopies();
		for (BookCopies bookCopies2 : bookCopies) {
			System.out.println(bookCopies2.getBook().getTitle());
			System.out.println(bookCopies2.getBranch().getBranchName());
			System.out.println(bookCopies2.getNoOfCopies());
		}
	}

	public void readBookCopiesByBookId(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookCopiesDAO pDao = new BookCopiesDAO(conn);
		Integer bookId = 6;
		List<BookCopies> bookCopies = pDao.readBookCopiesByBookId(bookId);
		for (BookCopies bookCopies2 : bookCopies) {
			System.out.println(bookCopies2.getBook().getTitle());
			System.out.println(bookCopies2.getBranch().getBranchName());
			System.out.println(bookCopies2.getNoOfCopies());
		}
	}

	public void readBookCopiesByBranchId(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookCopiesDAO pDao = new BookCopiesDAO(conn);
		Integer branchId = 4;
		List<BookCopies> bookCopies = pDao.readBookCopiesByBranchId(branchId);
		for (BookCopies bookCopies2 : bookCopies) {
			System.out.println(bookCopies2.getBook().getTitle());
			System.out.println(bookCopies2.getBranch().getBranchName());
			System.out.println(bookCopies2.getNoOfCopies());
		}
	}
	
	public void addBookCopies(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookCopiesDAO pDao = new BookCopiesDAO(conn);
		BookCopies bookCopies = new BookCopies();
		Book book = new Book();
		Branch branch = new Branch();
		book.setBookId(3);
		branch.setBranchId(3);
		bookCopies.setNoOfCopies(10);
		bookCopies.setBook(book);
		bookCopies.setBranch(branch);
		pDao.addBookCopies(bookCopies);
	}

	public void updateBookCopies(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookCopiesDAO pDao = new BookCopiesDAO(conn);
		BookCopies bookCopies = new BookCopies();
		Book book = new Book();
		Branch branch = new Branch();
		book.setBookId(3);
		branch.setBranchId(3);
		bookCopies.setNoOfCopies(12);
		bookCopies.setBook(book);
		bookCopies.setBranch(branch);
		pDao.updateBookCopies(bookCopies);
	}
	
	public void deleteBookCopies(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookCopiesDAO pDao = new BookCopiesDAO(conn);
		BookCopies bookCopies = pDao.readBookCopiesByIds(3, 3);
		pDao.deleteBookCopies(bookCopies);
	}

	public void readAllBranchs(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BranchDAO gDao = new BranchDAO(conn);
		List<Branch> branch = gDao.readAllBranchs();
		for (Branch branch2 : branch) {
			System.out.println(branch2.getBranchName());
		}
	}

	public void readBranchsByName(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BranchDAO gDao = new BranchDAO(conn);
		String title = "a";
		List<Branch> branch = gDao.readBranchsByName(title);
		for (Branch branch2 : branch) {
			System.out.println(branch2.getBranchName());
		}
	}

	public void readBranchByPK(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BranchDAO gDao = new BranchDAO(conn);
		Integer pk = 3;
		Branch branch = gDao.readBranchByPK(pk);
		System.out.println(branch.getBranchName());
	}

	public void addBranchWithID(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BranchDAO gDao = new BranchDAO(conn);
		Branch branch = new Branch();
		branch.setBranchName("Srulup Inc");
		Integer id = gDao.addBranchWithID(branch);
		System.out.println(id);
	}
	
	public void updateBranch(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BranchDAO gDao = new BranchDAO(conn);
		Branch branch = gDao.readBranchByPK(8);
		branch.setBranchName("Esecaer");
		gDao.updateBranch(branch);
	}

	public void deleteBranch(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BranchDAO bDao = new BranchDAO(conn);
		Branch branch = bDao.readBranchByPK(9);
		bDao.deleteBranch(branch);
	}
	
	public void readAllBookLoans(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookLoansDAO pDao = new BookLoansDAO(conn);
		List<BookLoans> bookLoans = pDao.readAllBookLoans();
		for (BookLoans bookLoans2 : bookLoans) {
			System.out.println(bookLoans2.getBook().getTitle());
			System.out.println(bookLoans2.getBranch().getBranchName());
			System.out.println(bookLoans2.getBorrower().getName());
			System.out.println(bookLoans2.getDateOut());
			System.out.println(bookLoans2.getDueDate());
			System.out.println(bookLoans2.getDateIn());
		}
	}

	public void readBookLoansByBookId(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookLoansDAO pDao = new BookLoansDAO(conn);
		Integer bookId = 6;
		List<BookLoans> bookLoans = pDao.readBookLoansByBookId(bookId);
		for (BookLoans bookLoans2 : bookLoans) {
			System.out.println(bookLoans2.getBook().getTitle());
			System.out.println(bookLoans2.getBranch().getBranchName());
			System.out.println(bookLoans2.getBorrower().getName());
			System.out.println(bookLoans2.getDateOut());
			System.out.println(bookLoans2.getDueDate());
			System.out.println(bookLoans2.getDateIn());
		}
	}

	public void readBookLoansByBranchId(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookLoansDAO pDao = new BookLoansDAO(conn);
		Integer branchId = 5;
		List<BookLoans> bookLoans = pDao.readBookLoansByBranchId(branchId);
		for (BookLoans bookLoans2 : bookLoans) {
			System.out.println(bookLoans2.getBook().getTitle());
			System.out.println(bookLoans2.getBranch().getBranchName());
			System.out.println(bookLoans2.getBorrower().getName());
			System.out.println(bookLoans2.getDateOut());
			System.out.println(bookLoans2.getDueDate());
			System.out.println(bookLoans2.getDateIn());
		}
	}
	
	public void readBookLoansByCardNo(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookLoansDAO pDao = new BookLoansDAO(conn);
		Integer cardNo = 3;
		List<BookLoans> bookLoans = pDao.readBookLoansByBranchId(cardNo);
		for (BookLoans bookLoans2 : bookLoans) {
			System.out.println(bookLoans2.getBook().getTitle());
			System.out.println(bookLoans2.getBranch().getBranchName());
			System.out.println(bookLoans2.getBorrower().getName());
			System.out.println(bookLoans2.getDateOut());
			System.out.println(bookLoans2.getDueDate());
			System.out.println(bookLoans2.getDateIn());
		}
	}
	
	public void addBookLoans(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookLoansDAO pDao = new BookLoansDAO(conn);
		BookLoans bookLoans = new BookLoans();
		Book book = new Book();
		Branch branch = new Branch();
		Borrower borrower = new Borrower();
		LocalDate dateOut = LocalDate.now();
		LocalDate dueDate = dateOut.plus(1, ChronoUnit.WEEKS);
		book.setBookId(9);
		branch.setBranchId(8);
		borrower.setCardNo(11231);
		bookLoans.setDateOut(dateOut.toString());
		bookLoans.setDueDate(dueDate.toString());
		bookLoans.setBook(book);
		bookLoans.setBranch(branch);
		bookLoans.setBorrower(borrower);
		pDao.addBookLoans(bookLoans);
	}

	public void updateBookLoansByDueDate(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookLoansDAO pDao = new BookLoansDAO(conn);
		BookLoans bookLoans = pDao.readBookLoansByIds(8, 5, 11232);
		String dueDate = "2017-02-28";
		bookLoans.setDueDate(dueDate);
		pDao.updateBookLoansByDueDate(bookLoans);
	}
	
	public void updateBookLoansByDateIn(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookLoansDAO pDao = new BookLoansDAO(conn);
		BookLoans bookLoans = pDao.readBookLoansByIds(8, 5, 11232);
		String DateIn = "2017-03-31";
		bookLoans.setDateIn(DateIn);
		pDao.updateBookLoansByDateIn(bookLoans);
	}
	
	public void deleteBookLoans(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BookLoansDAO pDao = new BookLoansDAO(conn);
		BookLoans bookLoans = pDao.readBookLoansByIds(9, 8, 11231);
		pDao.deleteBookLoans(bookLoans);
	}
	
	public void readAllBorrowers(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BorrowerDAO gDao = new BorrowerDAO(conn);
		List<Borrower> borrower = gDao.readAllBorrowers();
		for (Borrower borrower2 : borrower) {
			System.out.println(borrower2.getName());
		}
	}

	public void readBorrowersByName(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BorrowerDAO gDao = new BorrowerDAO(conn);
		String title = "a";
		List<Borrower> borrower = gDao.readBorrowersByName(title);
		for (Borrower borrower2 : borrower) {
			System.out.println(borrower2.getName());
		}
	}

	public void readBorrowerByPK(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BorrowerDAO gDao = new BorrowerDAO(conn);
		Integer pk = 3;
		Borrower borrower = gDao.readBorrowerByPK(pk);
		System.out.println(borrower.getName());
	}

	public void addBorrowerWithID(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BorrowerDAO gDao = new BorrowerDAO(conn);
		Borrower borrower = new Borrower();
		borrower.setName("Cream Cramer");
		borrower.setAddress("51 IESOJ street");
		Integer id = gDao.addBorrowerWithID(borrower);
		System.out.println(id);
	}
	
	public void updateBorrower(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BorrowerDAO gDao = new BorrowerDAO(conn);
		Borrower borrower = gDao.readBorrowerByPK(3);
		borrower.setName("Esecaer");
		borrower.setPhone("342-349-9485");
		gDao.updateBorrower(borrower);
	}

	public void deleteBorrower(Connection conn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		BorrowerDAO bDao = new BorrowerDAO(conn);
		Borrower borrower = bDao.readBorrowerByPK(11235);
		bDao.deleteBorrower(borrower);
	}

}
