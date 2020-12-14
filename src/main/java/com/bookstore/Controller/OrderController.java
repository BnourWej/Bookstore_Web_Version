package com.bookstore.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
			@RequestParam(value = "size", defaultValue = "10") Integer size, Authentication authentication) {
		PageRequest request = PageRequest.of(page - 1, size);

		Page<OrderMain> orderPage;
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
			orderPage = orderService.findByBuyerEmail(authentication.getName(), request);
		} else {
			orderPage = orderService.findAll(request);
		}
		return orderPage;
	}

	@PatchMapping("/order/cancel/{id}")
	public ResponseEntity<OrderMain> cancel(@PathVariable("id") Long orderId, Authentication authentication) {
		OrderMain orderMain = orderService.findOne(orderId);
		if (!authentication.getName().equals(orderMain.getBuyerEmail())
				&& authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(orderService.cancel(orderId));
	}

	@PatchMapping("/order/finish/{id}")
	public ResponseEntity<OrderMain> finish(@PathVariable("id") Long orderId, Authentication authentication) {
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(orderService.finish(orderId));
	}

	@GetMapping("/order/{id}")
	public ResponseEntity show(@PathVariable("id") Long orderId, Authentication authentication) {
		boolean isCustomer = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
		OrderMain orderMain = orderService.findOne(orderId);
		if (isCustomer && !authentication.getName().equals(orderMain.getBuyerEmail())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Collection<BookInOrder> items = orderMain.getBooks();
		return ResponseEntity.ok(orderMain);
	}
}
