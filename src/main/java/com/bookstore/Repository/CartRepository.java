package com.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
