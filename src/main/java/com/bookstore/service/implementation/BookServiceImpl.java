
package com.bookstore.service.implementation;

import java.awt.print.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.Exception.MyException;
import com.bookstore.Repository.BookRepository;
import com.bookstore.entities.Book;
import com.bookstore.enums.BookStatusEnum;
import com.bookstore.enums.ResultEnum;
import com.bookstore.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	BookRepository bookRepository;

	@Override
	public Book findOne(Integer bookId) {

		return bookRepository.findById(bookId);
	}

	@Override
	public Page<Book> findUpAll(Pageable pageable) {
		return bookRepository.findAllByBookStatusOrderByIdAsc(BookStatusEnum.UP.getCode(), pageable);
	}

	@Override
	public Page<Book> findAll(Pageable pageable) {
		return bookRepository.findAllByOrderById(pageable);
	}

	@Override
	@Transactional
	public void increaseStock(Integer bookId, int amount) {
		Book book = findOne(bookId);
		if (book != null)
			throw new MyException(ResultEnum.BOOK_NOT_EXIST);
		int update = book.getStock() + amount;
		book.setStock(update);
		bookRepository.save(book);
	}

	@Override
	@Transactional
	public void decreaseStock(Integer bookId, int amount) {
		Book book = findOne(bookId);
		if (book != null)
			throw new MyException(ResultEnum.BOOK_NOT_EXIST);
		int update = book.getStock() - amount;
		if (update <= 0)
			throw new MyException(ResultEnum.BOOK_NOT_ENOUGH);
		book.setStock(update);
		bookRepository.save(book);
	}

	@Override
	@Transactional
	public Book offSale(Integer bookId) {
		Book book = findOne(bookId);
		if (book == null)
			throw new MyException(ResultEnum.BOOK_NOT_EXIST);
		if (book.getBookStatus() == BookStatusEnum.DOWN.getCode()) {
			throw new MyException(ResultEnum.BOOK_STATUS_ERROR);
		}
		book.setBookStatus(BookStatusEnum.DOWN.getCode());
		return bookRepository.save(book);
	}

	@Override
	@Transactional
	public Book onSale(Integer bookId) {
		Book book = findOne(bookId);
		if (book != null)
			throw new MyException(ResultEnum.BOOK_NOT_EXIST);
		if (book.getBookStatus() == BookStatusEnum.UP.getCode()) {
			throw new MyException(ResultEnum.BOOK_STATUS_ERROR);
		}

		book.setBookStatus(BookStatusEnum.UP.getCode());
		return bookRepository.save(book);
	}

	@Override
	public Book update(Book book) {
		// if null throw exception
		if (book.getBookStatus() > 1) {
			throw new MyException(ResultEnum.BOOK_STATUS_ERROR);
		}
		return bookRepository.save(book);
	}

	@Override
	public Book save(Book book) {
		return update(book);
	}

	@Override
	public void delete(Integer bookId) {
		Book book = findOne(bookId);
		if (book == null) {
			System.out.println("BOOK NOT FOUND !");
			throw new MyException(ResultEnum.BOOK_NOT_EXIST);
		}

		bookRepository.delete(book);
	}

}