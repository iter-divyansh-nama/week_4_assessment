package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.model.entity.Book;
import com.capgemini.model.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping
	public String addBook(@RequestBody Book book) {
		return bookService.addBook(book);
	}

	@GetMapping("/{id}")
	public Book getBookById(@PathVariable Integer id) {
		return bookService.getBookById(id);
	}

	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@PutMapping("/{id}")
	public String updateBook(@PathVariable Integer id, @RequestBody Book book) {
		return bookService.updateBook(id, book);
	}

	@DeleteMapping("/{id}")
	public String deleteBook(@PathVariable Integer id) {
		return bookService.deleteBook(id);
	}

	@PostMapping("/borrow/{id}")
	public String borrowBook(@PathVariable Integer id) {
		return bookService.borrowBook(id);
	}

	@PostMapping("/return/{id}")
	public String returnBook(@PathVariable Integer id) {
		return bookService.returnBook(id);
	}

}
