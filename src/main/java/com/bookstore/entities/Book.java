package com.bookstore.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@DynamicUpdate
@Data
@Entity
@Table(name = "book")
public class Book implements Serializable {
	public Book(int i) {
		this.id = i;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String author;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private double price;

	@Column(nullable = false)
	private String releaseD;

	@Column(nullable = false)
	private int stock;

	@ColumnDefault("0")
	private Integer bookStatus;

	public Book() {
		super();
	}

	public Book(String author, String title, double price, String releaseD, int stock, Integer bookStatus) {
		super();

		this.author = author;
		this.title = title;
		this.price = price;
		this.releaseD = releaseD;
		this.stock = stock;
		this.bookStatus = bookStatus;
	}

	public Book(int id, String author, String title, double price, String releaseD, int stock, Integer bookStatus) {
		super();
		this.id = id;
		this.author = author;
		this.title = title;
		this.price = price;
		this.releaseD = releaseD;
		this.stock = stock;
		this.bookStatus = bookStatus;
	}

}
