
package com.bookstore.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Optional<Book> findOne(Integer bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		return book;
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
		Optional<Book> book = findOne(bookId);
		if (book.isPresent())
			throw new MyException(ResultEnum.BOOK_NOT_EXIST);
		int update = book.get().getStock() + amount;
		book.get().setStock(update);
		bookRepository.save(book.get());
	}

	@Override
	@Transactional
	public void decreaseStock(Integer bookId, int amount) {
		Optional<Book> book = findOne(bookId);
		if (book.isPresent())
			throw new MyException(ResultEnum.BOOK_NOT_EXIST);
		int update = book.get().getStock() - amount;
		if (update <= 0)
			throw new MyException(ResultEnum.BOOK_NOT_ENOUGH);
		book.get().setStock(update);
		bookRepository.save(book.get());
	}

	@Override
	@Transactional
	public Book offSale(Integer bookId) {
		Optional<Book> book = findOne(bookId);
		if (book == null)
			throw new MyException(ResultEnum.BOOK_NOT_EXIST);
		if (book.get().getBookStatus() == BookStatusEnum.DOWN.getCode()) {
			throw new MyException(ResultEnum.BOOK_STATUS_ERROR);
		}
		book.get().setBookStatus(BookStatusEnum.DOWN.getCode());
		return bookRepository.save(book.get());
	}

	@Override
	@Transactional
	public Book onSale(Integer bookId) {
		Optional<Book> book = findOne(bookId);
		if (book.isPresent())
			throw new MyException(ResultEnum.BOOK_NOT_EXIST);
		if (book.get().getBookStatus() == BookStatusEnum.UP.getCode()) {
			throw new MyException(ResultEnum.BOOK_STATUS_ERROR);
		}

		book.get().setBookStatus(BookStatusEnum.UP.getCode());
		return bookRepository.save(book.get());
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
	public Book save(Optional<Book> book) {
		return update(book.get());
	}

	@Override
	public void delete(Integer bookId) {
		Optional<Book> book = findOne(bookId);
		if (book == null)
			throw new MyException(ResultEnum.BOOK_NOT_EXIST);
		bookRepository.delete(book.get());
	}

}