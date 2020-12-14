package com.bookstore.Controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.entities.Book;
import com.bookstore.service.BookService;

@RestController
public class BookController {

	@Autowired
	BookService bookService;

	@GetMapping("/book")
	public Page<Book> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "3") Integer size) {
		PageRequest request = PageRequest.of(page - 1, size);
		return bookService.findAll(request);
	}

	@GetMapping("/book/{bookId}")
	public Book showOne(@PathVariable("bookId") Integer bookId) {
		Optional<Book> book = bookService.findOne(bookId);

		return book.get();
	}

	@PostMapping("/seller/book/new")
	public ResponseEntity create(@Valid @RequestBody Optional<Book> book, BindingResult bindingResult) {
		Optional<Book> bookIdExists = bookService.findOne(book.get().getId());
		if (bookIdExists != null) {
			bindingResult.rejectValue("bookId", "error.book", "There is already a book with the code provided");
		}
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult);
		}
		return ResponseEntity.ok(bookService.save(book));
	}

	@PutMapping("/seller/book/{id}/edit")
	public ResponseEntity edit(@PathVariable("id") Integer bookId, @Valid @RequestBody Book book,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult);
		}
		if (!bookId.equals(book.getId())) {
			return ResponseEntity.badRequest().body("Id Not Matched");
		}

		return ResponseEntity.ok(bookService.update(book));
	}

	@DeleteMapping("/seller/book/{id}/delete")
	public ResponseEntity delete(@PathVariable("id") Integer bookId) {
		bookService.delete(bookId);
		return ResponseEntity.ok().build();
	}

}
