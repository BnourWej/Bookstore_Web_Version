
package com.bookstore.Test.serviceImpl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import com.bookstore.Repository.BookRepository;
import com.bookstore.entities.Book;
import com.bookstore.service.BookService;

public class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAll() {
		Pageable pageable = (Pageable) PageRequest.of(0, 8);

		List<Book> books = Arrays.asList(new Book("L'Alchimiste", " Paulo Coelhon", 50.00, "20/11/2020", 100, 0),
				new Book("L'Alchimiste", "author3", 5.12, "20/12/2020", 100, 0));

		when(this.bookRepository.findAll()).thenReturn(books);
		assertTrue("Verify the size !!!! ", this.bookService.findAll(pageable).getSize() == books.size());
	}

	@Test
	public void testFindById() {
		Book book = new Book(4, "L'Alchimiste", " Paulo Coelho", 63.02, "20/12/2020", 100, 0);
		int id = book.getId();
		when(bookRepository.findById(id)).thenReturn(book);
		// assertEquals(4, book.getId());
		assertTrue(" No matching Book ID", this.bookService.findOne(id) == book);
	}

	@Test
	public void testAddBook() {
		Book book = new Book(5658, "L'Alchimiste", " Paulo Coelho", 5.12, "20/12/2020", 100, 0);
		bookService.save(book);
		verify(bookRepository, times(1)).save(book);
	}

	@Test
	public void testUpdateBookSuccessfully() {
		Book book = new Book(5878, "L'Alchimiste", " Paulo Coelho", 5.12, "20/12/2020", 100, 0);
		bookService.update(book);
		((BookService) verify(bookRepository, times(1))).update(book);
	}

	@Test
	public void testDeleteBookSuccessfully() {
		Book book = new Book(2, "L'Alchimiste", " Paulo Coelho", 5.12, "20/12/2020", 100, 0);
		bookService.delete(25);
		verify(bookRepository, times(1)).delete(book);
	}
}
