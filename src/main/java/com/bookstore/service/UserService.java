package com.bookstore.service;

import java.util.Collection;

import com.bookstore.entities.User;

public interface UserService {
	User findOne(String email);

	Collection<User> findByRole(String role);

	User save(User user);

	User update(User user);
}