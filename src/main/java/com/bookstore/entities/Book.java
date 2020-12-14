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
}
