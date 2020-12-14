package com.bookstore.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

	PARAM_ERROR(1, "Parameter Error!"), BOOK_NOT_EXIST(10, "Book does not exit!"),
	BOOK_NOT_ENOUGH(11, "Not enough products in stock!"), BOOK_STATUS_ERROR(12, "Status is incorrect!"),
	BOOK_OFF_SALE(13, "Book is off sale!"), BOOK_NOT_IN_CART(14, "Product is not in the cart!"),
	CART_CHECKOUT_SUCCESS(20, "Checkout successfully! "),

	ORDER_NOT_FOUND(60, "OrderMain is not exit!"), ORDER_STATUS_ERROR(61, "Status is not correct"),

	VALID_ERROR(50, "Wrong information"), USER_NOT_FOUNT(40, "User is not found!");

	private Integer code;

	private String message;

	ResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
