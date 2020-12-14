package com.bookstore.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	Optional<Book> findById(Integer id);

	// Onsale book

	// rerourner le nombre de stocke des livres
	// retourner le

	Page<Book> findAllByBookStatusOrderByIdAsc(Integer bookStatus, Pageable pageable);

	Page<Book> findAllByOrderById(Pageable pageable);
}