package com.bookstore.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@NoArgsConstructor
@Data
@DynamicUpdate
public class OrderMain implements Serializable {
	private static final long serialVersionUID = -3819883511505235030L;

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "orderMain")
	private Set<BookInOrder> books = new HashSet<>();

	@NotEmpty
	private String buyerEmail;

	@NotEmpty
	private String buyerName;

	@NotEmpty
	private String buyerPhone;

	@NotEmpty
	private String buyerAddress;

	// Total Price
	@NotNull
	private double orderAmount;

	@NotNull
	@ColumnDefault("0")
	private Integer orderStatus;

	@CreationTimestamp
	private LocalDate createTime;

	public OrderMain(User buyer) {
		this.buyerEmail = buyer.getEmail();
		this.buyerName = buyer.getName();
		this.buyerPhone = buyer.getPhone();
		this.buyerAddress = buyer.getAddress();
		this.orderAmount = buyer.getCart().getBooks().stream().map(item -> item.getBookPrice() * item.getCount())
				.reduce(0.0, null);

		this.orderStatus = 0;

	}
}
