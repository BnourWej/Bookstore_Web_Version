package com.bookstore.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.entities.BookInOrder;
import com.bookstore.entities.OrderMain;
import com.bookstore.service.OrderService;
import com.bookstore.service.UserService;

@RestController
public class OrderController {
	@Autowired
	OrderService orderService;
	@Autowired
	UserService userService;

	@GetMapping("/order")
	public Page<OrderMain> orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		PageRequest request = PageRequest.of(page - 1, size);

		Page<OrderMain> orderPage;

		orderPage = orderService.findAll(request);

		return orderPage;
	}

	@PatchMapping("/order/cancel/{id}")
	public ResponseEntity<OrderMain> cancel(@PathVariable("id") Long orderId) {
		OrderMain orderMain = orderService.findOne(orderId);

		return ResponseEntity.ok(orderService.cancel(orderId));
	}

	@PatchMapping("/order/finish/{id}")
	public ResponseEntity<OrderMain> finish(@PathVariable("id") Long orderId) {

		return ResponseEntity.ok(orderService.finish(orderId));
	}

	@GetMapping("/order/{id}")
	public ResponseEntity show(@PathVariable("id") Long orderId) {
		OrderMain orderMain = orderService.findOne(orderId);

		Collection<BookInOrder> items = orderMain.getBooks();
		return ResponseEntity.ok(orderMain);
	}
}
