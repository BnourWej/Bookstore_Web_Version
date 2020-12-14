package com.bookstore.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.Exception.MyException;
import com.bookstore.Repository.BookInOrderRepository;
import com.bookstore.Repository.BookRepository;
import com.bookstore.Repository.OrderMainRepository;
import com.bookstore.Repository.UserRepository;
import com.bookstore.entities.Book;
import com.bookstore.entities.BookInOrder;
import com.bookstore.entities.OrderMain;
import com.bookstore.enums.OrderStatusEnum;
import com.bookstore.enums.ResultEnum;
import com.bookstore.service.BookService;
import com.bookstore.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderMainRepository orderRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	BookService bookService;
	@Autowired
	BookInOrderRepository BookInOrderRepository;

	@Override
	public Page<OrderMain> findAll(Pageable pageable) {
		return orderRepository.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
	}

	@Override
	public Page<OrderMain> findByStatus(Integer status, Pageable pageable) {
		return orderRepository.findAllByOrderStatusOrderByCreateTimeDesc(status, pageable);
	}

	@Override
	public Page<OrderMain> findByBuyerEmail(String email, Pageable pageable) {
		return orderRepository.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
	}

	@Override
	public Page<OrderMain> findByBuyerPhone(String phone, Pageable pageable) {
		return orderRepository.findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(phone, pageable);
	}

	@Override
	public OrderMain findOne(Long orderId) {
		OrderMain orderMain = orderRepository.findByOrderId(orderId);
		if (orderMain == null) {
			throw new MyException(ResultEnum.ORDER_NOT_FOUND);
		}
		return orderMain;
	}

	@Override
	@Transactional
	public OrderMain finish(Long orderId) {
		OrderMain orderMain = findOne(orderId);
		if (!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
		}

		orderMain.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		orderRepository.save(orderMain);
		return orderRepository.findByOrderId(orderId);
	}

	@Override
	@Transactional
	public OrderMain cancel(Long orderId) {
		OrderMain orderMain = findOne(orderId);
		if (!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
		}

		orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
		orderRepository.save(orderMain);

		// Restore Stock
		Iterable<BookInOrder> books = orderMain.getBooks();
		for (BookInOrder bookInOrder : books) {
			Optional<Book> book = bookRepository.findById(bookInOrder.getBookId());
			if (book.isPresent()) {
				bookService.increaseStock(bookInOrder.getBookId(), bookInOrder.getCount());
			}
		}
		return orderRepository.findByOrderId(orderId);

	}
}