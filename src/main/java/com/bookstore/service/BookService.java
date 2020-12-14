package com.bookstore.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bookstore.entities.Book;

public interface BookService {
	Optional<Book> findOne(Integer integer);

	// All selling books
	Page<Book> findUpAll(Pageable pageable);

	// All products
	Page<Book> findAll(Pageable pageable);

	// increase stock
	void increaseStock(Integer id, int amount);

	// decrease stock
	void decreaseStock(Integer id, int amount);

	Book offSale(Integer id);

	Book onSale(Integer id);

	Book update(Book book);

	Book save(Optional<Book> book);

	void delete(Integer id);

}
