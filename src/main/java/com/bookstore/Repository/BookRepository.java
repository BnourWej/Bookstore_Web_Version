package com.bookstore.Repository;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	Book findById(Integer id);

	Page<Book> findAllByBookStatusOrderByIdAsc(Integer bookStatus, Pageable pageable);

	Page<Book> findAllByOrderById(Pageable pageable);
}