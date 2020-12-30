package com.bookstoreTest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bookstore.Controller.BookController;
import com.bookstore.entities.Book;
import com.bookstore.service.BookService;

public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private BookService MockedBookService;

	@InjectMocks
	private BookController bookController;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}

	@Test
	public void testGetAllBooks() throws Exception {
		Pageable pageable = (Pageable) PageRequest.of(0, 8);

		List<Book> books = new ArrayList<Book>();
		books.add(new Book(252, "L'Alchimiste", " Paulo Coelho", 5.12, "20/11/2020", 100, 0));
		books.add(new Book(3245, "L'Alchimiste", " Paulo Coelho", 5.12, "20/12/2020", 100, 0));
		books.add(new Book(255, "L'Alchimiste", " Paulo Coelho", 5.12, "20/12/2020", 100, 0));

		when(MockedBookService.findAll(pageable)).thenReturn((Page<Book>) books);

		this.mockMvc.perform(get("/book")).andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3))).andExpect(status().is2xxSuccessful());

	}

	@Test
	public void testGetBook() throws Exception {
		List<Book> books = new ArrayList<Book>();
		Book book = new Book(252, "L'Alchimiste", " Paulo Coelho", 5.12, "20/11/2020", 100, 0);
		books.add(book);
		when(MockedBookService.findOne(1)).thenReturn(book);
		this.mockMvc.perform(get("/book/{id}", 1)).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(4))
				.andExpect(status().is2xxSuccessful());
	}

}