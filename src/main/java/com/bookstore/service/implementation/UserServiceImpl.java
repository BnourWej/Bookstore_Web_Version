package com.bookstore.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.Exception.MyException;
import com.bookstore.Repository.CartRepository;
import com.bookstore.Repository.UserRepository;
import com.bookstore.entities.Cart;
import com.bookstore.entities.User;
import com.bookstore.enums.ResultEnum;
import com.bookstore.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CartRepository cartRepository;

	@Override
	public User findOne(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public User save(User user) {
		// register
		try {
			User savedUser = userRepository.save(user);

			// initial Cart
			Cart savedCart = cartRepository.save(new Cart(savedUser));
			savedUser.setCart(savedCart);
			return userRepository.save(savedUser);

		} catch (Exception e) {
			throw new MyException(ResultEnum.VALID_ERROR);
		}

	}

	@Override
	@Transactional
	public User update(User user) {
		User oldUser = userRepository.findByEmail(user.getEmail());
		oldUser.setName(user.getName());
		oldUser.setPhone(user.getPhone());
		oldUser.setAddress(user.getAddress());
		return userRepository.save(oldUser);
	}

}
