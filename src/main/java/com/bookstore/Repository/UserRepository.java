package com.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
	User findByEmail(String email);

}
