package com.bookstore.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.Repository.LineItemRepository;
import com.bookstore.entities.LineItem;

@RequestMapping("/api/")

@RestController
public class LineItemController {
	@Autowired
	private LineItemRepository lineItemRepository;

	@GetMapping("/list")
	public List<LineItem> getAllLineItems() {

		return lineItemRepository.findAll();
	}

	@PostMapping("/add")
	public LineItem CreateLineItem(@Valid @RequestBody LineItem lineItem) {
		return lineItemRepository.save(lineItem);
	}
}
