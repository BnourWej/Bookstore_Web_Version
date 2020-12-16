package com.bookstore.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.entities.User;
import com.bookstore.service.UserService;

@CrossOrigin
@RestController
public class UserController {

	@Autowired
	UserService userService;

	@PutMapping("/profile")
	public ResponseEntity<User> update(@RequestBody User user, Principal principal) {

		try {
			if (!principal.getName().equals(user.getEmail()))
				throw new IllegalArgumentException();
			return ResponseEntity.ok(userService.update(user));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/profile/{email}")
	public ResponseEntity<User> getProfile(@PathVariable("email") String email, Principal principal) {
		if (principal.getName().equals(email)) {
			return ResponseEntity.ok(userService.findOne(email));
		} else {
			return ResponseEntity.badRequest().build();
		}

	}
}