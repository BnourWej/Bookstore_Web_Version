package com.bookstore.enums;

import lombok.Getter;

@Getter

public enum BookStatusEnum implements CodeEnum {
	UP(0, "Available"), DOWN(1, "Unavailable");

	private Integer code; // 0
	private String message; // Available

	BookStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getStatus(Integer code) {

		for (BookStatusEnum statusEnum : BookStatusEnum.values()) {
			if (statusEnum.getCode() == code)
				return statusEnum.getMessage();
		}
		return "Code Not Available ";
	}

	public Integer getCode() {
		return code;
	}
}
