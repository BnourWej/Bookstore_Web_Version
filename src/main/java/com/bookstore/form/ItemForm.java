package com.bookstore.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ItemForm {
	@Min(value = 1)
	private Integer quantity;
	@NotEmpty
	private Integer bookId;
}
