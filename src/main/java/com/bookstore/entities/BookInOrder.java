package com.bookstore.entities;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class BookInOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JsonIgnore
	private Cart cart;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	@JsonIgnore
	private OrderMain orderMain;

	@NotEmpty
	private Integer bookId;

	@Min(0)
	private Integer bookStock;

	private String BookTitle;
	@NotNull
	private double bookPrice;

	@Min(1)
	private Integer count;

	public BookInOrder(Optional<Book> book, Integer quantity) {
		this.bookId = book.get().getId();
		this.BookTitle = book.get().getTitle();
		this.bookPrice = book.get().getPrice();
		this.bookStock = book.get().getStock();
		this.count = quantity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		BookInOrder that = (BookInOrder) o;
		return Objects.equals(id, that.id) && Objects.equals(bookId, that.bookId)

				&& Objects.equals(BookTitle, that.BookTitle) && Objects.equals(bookPrice, that.bookPrice);
	}

}
