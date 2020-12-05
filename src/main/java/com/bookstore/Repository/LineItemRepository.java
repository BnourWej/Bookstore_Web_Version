package com.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entities.LineItem;

public interface LineItemRepository extends JpaRepository<LineItem, Integer> {

}
