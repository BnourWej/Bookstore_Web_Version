package com.bookstore.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.Exception.ResourceNotFoundException;
import com.bookstore.Repository.BookRepository;
import com.bookstore.model.Book;

@RequestMapping("/api/")
@RestController
public class Controller {
	@Autowired
	private BookRepository bookRepository;

	// display all books
	@GetMapping("books")
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	// adding a new book
	@PostMapping("add")
	public Book createBook(@RequestBody Book book) {
		return bookRepository.save(book);

	}

	// get book by id
	@GetMapping("books/{id}")

	public ResponseEntity<Book> getBookById(@PathVariable(value = "id") int bookId) throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("No Book with this ID : " + bookId));
		return ResponseEntity.ok().body(book);
	}

	// update book
	@PutMapping("books/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Integer bookId,
			@Valid @RequestBody Book bookDetails) throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("No Book with this ID : " + bookId));

		book.setAuthor(bookDetails.getAuthor());

		book.setTitle(bookDetails.getTitle());

		book.setPrice(bookDetails.getPrice());
		book.setReleaseD(bookDetails.getReleaseD());
		final Book updateBook = bookRepository.save(book);
		return ResponseEntity.ok(updateBook);
	}

	// delete book

	@DeleteMapping("books/{id}")
	public Map<String, Boolean> deleteEmployee(@Valid @PathVariable(value = "id") Integer bookId)
			throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + bookId));

		bookRepository.delete(book);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
