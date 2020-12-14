package com.bookstore.Repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
	User findByEmail(String email);

	Collection<User> findAllByRole(String role);

}
