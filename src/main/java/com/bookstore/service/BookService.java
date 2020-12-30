package com.bookstore.service;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;

import com.bookstore.entities.Book;

public interface BookService {
	Book findOne(Integer integer);

	// All selling books
	Page<Book> findUpAll(Pageable pageable);

	// All books
	Page<Book> findAll(Pageable pageable);

	// increase stock
	void increaseStock(Integer id, int amount);

	// decrease stock
	void decreaseStock(Integer id, int amount);

	Book offSale(Integer id);

	Book onSale(Integer id);

	Book update(Book book);

	Book save(Book book);

	void delete(Integer id);

}
