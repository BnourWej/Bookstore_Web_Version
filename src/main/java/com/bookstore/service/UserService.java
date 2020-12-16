package com.bookstore.service;

import com.bookstore.entities.User;

public interface UserService {
	User findOne(String email);

	User save(User user);

	User update(User user);
}