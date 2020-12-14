package com.bookstore.service.implementation;

import java.util.concurrent.atomic.AtomicReference;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.Repository.BookInOrderRepository;
import com.bookstore.entities.BookInOrder;
import com.bookstore.entities.User;
import com.bookstore.service.BookInOrderService;

import lombok.experimental.var;

@Service
public class BookInOrderServiceImpl implements BookInOrderService {

	@Autowired
	BookInOrderRepository bookInOrderRepository;

	@Override
	@Transactional
	public void update(String itemId, Integer quantity, User user) {
		var op = user.getCart().getBooks().stream().filter(e -> itemId.equals(e.getBookId())).findFirst();
		op.ifPresent(bookInOrder -> {
			bookInOrder.setCount(quantity);
			bookInOrderRepository.save(bookInOrder);
		});

	}

	@Override
	public BookInOrder findOne(String itemId, User user) {
		var op = user.getCart().getBooks().stream().filter(e -> itemId.equals(e.getBookId())).findFirst();
		AtomicReference<BookInOrder> res = new AtomicReference<>();
		op.ifPresent(res::set);
		return res.get();
	}
}
