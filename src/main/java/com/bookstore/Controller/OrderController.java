package com.bookstore.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookstore.Exception.ResourceNotFoundException;
import com.bookstore.Repository.OrderRepository;
import com.bookstore.entities.Order;

@Controller
@RequestMapping("/api/")
public class OrderController {

	private OrderRepository orderRepository;

	@Autowired

	@GetMapping("/list")
	public List<Order> getAllOrders() {
		return (List<Order>) orderRepository.findAll();
	}

	@PostMapping("/add")
	public Order createOrder(@Valid @RequestBody Order order) {
		return orderRepository.save(order);
	}

	@PutMapping("/{orderId}")
	public Order updateOrder(@PathVariable Integer orderId, @Valid @RequestBody Order orderRequest)
			throws ResourceNotFoundException {
		return orderRepository.findById(orderId).map(order -> {

			order.setDate(orderRequest.getDate());
			order.setTotalPrice(orderRequest.getTotalPrice());
			return orderRepository.save(order);
		}).orElseThrow(() -> new ResourceNotFoundException("Odrer Id " + orderId + " not found"));

	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<?> deleteCommand(@PathVariable Integer orderId) throws ResourceNotFoundException {
		return orderRepository.findById(orderId).map(order -> {
			orderRepository.delete(order);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Orderd Id " + orderId + " not found"));
	}
}
