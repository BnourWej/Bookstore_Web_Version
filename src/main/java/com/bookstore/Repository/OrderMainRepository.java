package com.bookstore.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entities.OrderMain;

public interface OrderMainRepository extends JpaRepository<OrderMain, Long> {

	OrderMain findByOrderId(Long orderId);

	Page<OrderMain> findAllByOrderStatusOrderByCreateTimeDesc(Integer orderStatus, Pageable pageable);

	Page<OrderMain> findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(String buyerEmail, Pageable pageable);

	Page<OrderMain> findAllByOrderByOrderStatusAscCreateTimeDesc(Pageable pageable);

	Page<OrderMain> findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(String buyerPhone, Pageable pageable);
}