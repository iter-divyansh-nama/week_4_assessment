package com.capgemini.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.model.entity.Book;
import com.capgemini.model.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public String addBook(Book book) {
		book.setBorrowedCopies(0);
		bookRepository.save(book);
		return "Book added successfully";
	}

	public Book getBookById(Integer id) {
		Optional<Book> book = bookRepository.findById(id);
		if (book.isPresent())
			return book.get();
		return null;
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public String updateBook(Integer id, Book updatedData) {
		Optional<Book> optional = bookRepository.findById(id);
		if (optional.isEmpty())
			return "Book not found";

		Book existing = optional.get();
		existing.setTitle(updatedData.getTitle());
		existing.setAuthor(updatedData.getAuthor());
		existing.setAvailableCopies(updatedData.getAvailableCopies());
		bookRepository.save(existing);
		return "Book updated successfully";
	}

	public String deleteBook(Integer id) {
		Optional<Book> optional = bookRepository.findById(id);
		if (optional.isEmpty())
			return "Book not found";

		bookRepository.deleteById(id);
		return "Book deleted successfully";
	}

	public String borrowBook(Integer id) {
		Optional<Book> optional = bookRepository.findById(id);
		if (optional.isEmpty())
			return "Book not found";

		Book book = optional.get();
		if (book.getAvailableCopies() == 0)
			return "No copies available";

		book.setAvailableCopies(book.getAvailableCopies() - 1);
		book.setBorrowedCopies(book.getBorrowedCopies() + 1);
		bookRepository.save(book);
		return "Book borrowed successfully";
	}

	public String returnBook(Integer id) {
		Optional<Book> optional = bookRepository.findById(id);
		if (optional.isEmpty())
			return "Book not found";

		Book book = optional.get();
		if (book.getBorrowedCopies() == 0)
			return "No borrowed books to return";

		book.setBorrowedCopies(book.getBorrowedCopies() - 1);
		book.setAvailableCopies(book.getAvailableCopies() + 1);
		bookRepository.save(book);
		return "Book returned successfully";
	}

}
