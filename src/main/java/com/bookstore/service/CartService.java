package com.bookstore.service;

import java.util.Collection;

import com.bookstore.entities.BookInOrder;
import com.bookstore.entities.Cart;
import com.bookstore.entities.User;

public interface CartService {
	Cart getCart(User user);

	void mergeLocalCart(Collection<BookInOrder> productInOrders, User user);

	void delete(String itemId, User user);

	void checkout(User user);
}
