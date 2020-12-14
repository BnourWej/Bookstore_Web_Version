package com.bookstore.service;

import com.bookstore.entities.BookInOrder;
import com.bookstore.entities.User;

public interface BookInOrderService {
	void update(String itemId, Integer quantity, User user);

	BookInOrder findOne(String itemId, User user);
}
