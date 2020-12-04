package com.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entities.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}