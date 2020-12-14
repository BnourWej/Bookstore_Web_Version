package com.bookstore.Controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.Repository.BookInOrderRepository;
import com.bookstore.entities.BookInOrder;
import com.bookstore.entities.Cart;
import com.bookstore.entities.User;
import com.bookstore.form.ItemForm;
import com.bookstore.service.BookInOrderService;
import com.bookstore.service.BookService;
import com.bookstore.service.CartService;
import com.bookstore.service.UserService;

import lombok.experimental.var;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartService cartService;
	@Autowired
	UserService userService;
	@Autowired
	BookService bookService;
	@Autowired
	BookInOrderService bookInOrderService;
	@Autowired
	BookInOrderRepository bookInOrderRepository;

	@PostMapping("")
	public ResponseEntity<Cart> mergeCart(@RequestBody Collection<BookInOrder> productInOrders, Principal principal) {
		User user = userService.findOne(principal.getName());
		try {
			cartService.mergeLocalCart(productInOrders, user);
		} catch (Exception e) {
			ResponseEntity.badRequest().body("Merge Cart Failed");
		}
		return ResponseEntity.ok(cartService.getCart(user));
	}

	@GetMapping("")
	public Cart getCart(Principal principal) {
		User user = userService.findOne(principal.getName());
		return cartService.getCart(user);
	}

	@PostMapping("/add")
	public boolean addToCart(@RequestBody ItemForm form, Principal principal) {
		var book = bookService.findOne(form.getBookId());
		try {
			mergeCart(Collections.singleton(new BookInOrder(book, form.getQuantity())), principal);
		} catch (Exception e) {

			return false;
		}
		return true;
	}

	@PutMapping("/{itemId}")
	public BookInOrder modifyItem(@PathVariable("itemId") String itemId, @RequestBody Integer quantity,
			Principal principal) {
		User user = userService.findOne(principal.getName());
		bookInOrderService.update(itemId, quantity, user);
		return bookInOrderService.findOne(itemId, user);
	}

	@DeleteMapping("/{itemId}")
	public void deleteItem(@PathVariable("itemId") String itemId, Principal principal) {
		User user = userService.findOne(principal.getName());
		cartService.delete(itemId, user);
		// flush memory into DB
	}

	@PostMapping("/checkout")
	public ResponseEntity checkout(Principal principal) {
		User user = userService.findOne(principal.getName());// Email as username
		cartService.checkout(user);
		return ResponseEntity.ok(null);
	}

}
