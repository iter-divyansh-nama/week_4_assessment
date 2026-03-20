package com.capgemini.model.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capgemini.model.dto.OrderDTO;
import com.capgemini.model.dto.RevenueSummaryDTO;
import com.capgemini.model.entity.Customer;
import com.capgemini.model.entity.Order;
import com.capgemini.model.repository.CustomerRepository;
import com.capgemini.model.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	public ResponseEntity<Object> createOrder(Integer customerId, OrderDTO dto) {
		Optional<Customer> optional = customerRepository.findByCustomerIdAndIsActiveTrue(customerId);
		if (optional.isEmpty())
			return customerNotFound(customerId);

		Order order = new Order();
		order.setProductName(dto.getProductName());
		order.setQuantity(dto.getQuantity());
		order.setPricePerUnit(dto.getPricePerUnit());
		order.setTotalAmount(dto.getPricePerUnit().multiply(BigDecimal.valueOf(dto.getQuantity())));
		order.setStatus(dto.getStatus());
		order.setCustomer(optional.get());
		orderRepository.save(order);
		return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(order));
	}

	public ResponseEntity<Object> getOrders(Integer customerId, String status, int page, int size) {
		Optional<Customer> optional = customerRepository.findByCustomerIdAndIsActiveTrue(customerId);
		if (optional.isEmpty())
			return customerNotFound(customerId);

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Order> orderPage;
		if (status != null)
			orderPage = orderRepository.findByCustomerAndStatus(optional.get(), status, pageable);
		else
			orderPage = orderRepository.findByCustomer(optional.get(), pageable);

		Map<String, Object> response = new HashMap<>();
		List<OrderDTO> content = new ArrayList<>();
		for (Order o : orderPage.getContent())
			content.add(toDTO(o));

		response.put("content", content);
		response.put("pageNumber", orderPage.getNumber());
		response.put("pageSize", orderPage.getSize());
		response.put("totalElements", orderPage.getTotalElements());
		response.put("totalPages", orderPage.getTotalPages());
		response.put("last", orderPage.isLast());
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<Object> getOrderById(Integer customerId, Integer orderId) {
		Optional<Customer> customer = customerRepository.findByCustomerIdAndIsActiveTrue(customerId);
		if (customer.isEmpty())
			return customerNotFound(customerId);
		Optional<Order> optional = orderRepository.findById(orderId);
		if (optional.isEmpty())
			return orderNotFound(orderId);
		return ResponseEntity.ok(toDTO(optional.get()));
	}

	public ResponseEntity<Object> updateOrder(Integer customerId, Integer orderId, OrderDTO dto) {
		Optional<Customer> customer = customerRepository.findByCustomerIdAndIsActiveTrue(customerId);
		if (customer.isEmpty())
			return customerNotFound(customerId);
		Optional<Order> optional = orderRepository.findById(orderId);
		if (optional.isEmpty())
			return orderNotFound(orderId);
		try {
			Order order = optional.get();
			order.setProductName(dto.getProductName());
			order.setQuantity(dto.getQuantity());
			order.setPricePerUnit(dto.getPricePerUnit());
			order.setTotalAmount(dto.getPricePerUnit().multiply(BigDecimal.valueOf(dto.getQuantity())));
			order.setStatus(dto.getStatus());
			orderRepository.save(order);
			return ResponseEntity.ok(toDTO(order));
		} catch (OptimisticLockingFailureException e) {
			Map<String, Object> error = new HashMap<>();
			error.put("status", 409);
			error.put("error", "Conflict");
			error.put("message", "Order was modified by another request. Please retry.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
		}
	}

	public ResponseEntity<Object> cancelOrder(Integer customerId, Integer orderId) {
		Optional<Customer> customer = customerRepository.findByCustomerIdAndIsActiveTrue(customerId);
		if (customer.isEmpty())
			return customerNotFound(customerId);
		Optional<Order> optional = orderRepository.findById(orderId);
		if (optional.isEmpty())
			return orderNotFound(orderId);

		Order order = optional.get();
		if ("SHIPPED".equals(order.getStatus()) || "DELIVERED".equals(order.getStatus())) {
			Map<String, Object> error = new HashMap<>();
			error.put("status", 400);
			error.put("error", "Bad Request");
			error.put("message", "Cannot cancel an order with status " + order.getStatus());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}

		order.setStatus("CANCELLED");
		orderRepository.save(order);
		return ResponseEntity.ok(toDTO(order));
	}

	public List<RevenueSummaryDTO> getRevenueSummary() {
		List<RevenueSummaryDTO> list = new ArrayList<>();
		for (Object[] row : orderRepository.findRevenueSummary())
			list.add(new RevenueSummaryDTO((String) row[0], (BigDecimal) row[1]));
		return list;
	}

	private OrderDTO toDTO(Order order) {
		OrderDTO dto = new OrderDTO();
		dto.setOrderId(order.getOrderId());
		dto.setProductName(order.getProductName());
		dto.setQuantity(order.getQuantity());
		dto.setPricePerUnit(order.getPricePerUnit());
		dto.setTotalAmount(order.getTotalAmount());
		dto.setStatus(order.getStatus());
		dto.setVersion(order.getVersion());
		dto.setCustomerId(order.getCustomer().getCustomerId());
		dto.setCustomerName(order.getCustomer().getFullName());
		dto.setCreatedAt(order.getCreatedAt());
		dto.setUpdatedAt(order.getUpdatedAt());
		return dto;
	}

	private ResponseEntity<Object> customerNotFound(Integer id) {
		Map<String, Object> error = new HashMap<>();
		error.put("status", 404);
		error.put("error", "Not Found");
		error.put("message", "Customer with ID " + id + " not found");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	private ResponseEntity<Object> orderNotFound(Integer id) {
		Map<String, Object> error = new HashMap<>();
		error.put("status", 404);
		error.put("error", "Not Found");
		error.put("message", "Order with ID " + id + " not found");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}
