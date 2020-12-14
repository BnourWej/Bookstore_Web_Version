package com.bookstore.service.implementation;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.Repository.BookInOrderRepository;
import com.bookstore.Repository.CartRepository;
import com.bookstore.Repository.OrderMainRepository;
import com.bookstore.Repository.UserRepository;
import com.bookstore.entities.BookInOrder;
import com.bookstore.entities.Cart;
import com.bookstore.entities.OrderMain;
import com.bookstore.entities.User;
import com.bookstore.service.BookService;
import com.bookstore.service.CartService;
import com.bookstore.service.UserService;

import lombok.experimental.var;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	BookService bookService;
	@Autowired
	OrderMainRepository orderRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	BookInOrderRepository bookInOrderRepository;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	UserService userService;

	@Override
	public Cart getCart(User user) {
		return user.getCart();
	}

	@Override
	@Transactional
	public void delete(String itemId, User user) {
		var op = user.getCart().getBooks().stream().filter(e -> itemId.equals(e.getBookId())).findFirst();
		op.ifPresent(bookInOrder -> {
			bookInOrder.setCart(null);
			bookInOrderRepository.deleteById(bookInOrder.getId());
		});
	}

	@Override
	@Transactional
	public void checkout(User user) {
		// Create an order
		OrderMain order = new OrderMain(user);
		orderRepository.save(order);

		// clear cart's foreign key & set order's foreign key & decrease stock
		user.getCart().getBooks().forEach(bookInOrder -> {
			bookInOrder.setCart(null);
			bookInOrder.setOrderMain(order);
			bookService.decreaseStock(bookInOrder.getBookId(), bookInOrder.getCount());
			bookInOrderRepository.save(bookInOrder);
		});

	}

	@Transactional

	@Override
	public void mergeLocalCart(Collection<BookInOrder> bookInOrders, User user) {
		{
			Cart finalCart = user.getCart();
			bookInOrders.forEach(bookInOrder -> {
				Set<BookInOrder> set = finalCart.getBooks();
				Optional<BookInOrder> old = set.stream().filter(e -> e.getBookId().equals(bookInOrder.getBookId()))
						.findFirst();
				BookInOrder prod;
				if (old.isPresent()) {
					prod = old.get();
					prod.setCount(bookInOrder.getCount() + prod.getCount());
				} else {
					prod = bookInOrder;
					prod.setCart(finalCart);
					finalCart.getBooks().add(prod);
				}
				bookInOrderRepository.save(prod);
			});
			cartRepository.save(finalCart);

		}

	}

}
