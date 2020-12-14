package com.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entities.BookInOrder;

public interface BookInOrderRepository extends JpaRepository<BookInOrder, Long> {

}
