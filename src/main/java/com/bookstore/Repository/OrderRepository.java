package com.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}