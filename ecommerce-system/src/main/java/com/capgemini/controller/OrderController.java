package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.model.dto.OrderDTO;
import com.capgemini.model.dto.RevenueSummaryDTO;
import com.capgemini.model.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/api/customers/{customerId}/orders")
	public ResponseEntity<Object> createOrder(@PathVariable Integer customerId,
			@RequestBody OrderDTO dto) {
		return orderService.createOrder(customerId, dto);
	}

	@GetMapping("/api/customers/{customerId}/orders")
	public ResponseEntity<Object> getOrders(@PathVariable Integer customerId,
			@RequestParam(required = false) String status,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		return orderService.getOrders(customerId, status, page, size);
	}

	@GetMapping("/api/customers/{customerId}/orders/{orderId}")
	public ResponseEntity<Object> getOrderById(@PathVariable Integer customerId,
			@PathVariable Integer orderId) {
		return orderService.getOrderById(customerId, orderId);
	}

	@PutMapping("/api/customers/{customerId}/orders/{orderId}")
	public ResponseEntity<Object> updateOrder(@PathVariable Integer customerId,
			@PathVariable Integer orderId,
			@RequestBody OrderDTO dto) {
		return orderService.updateOrder(customerId, orderId, dto);
	}

	@PatchMapping("/api/customers/{customerId}/orders/{orderId}/cancel")
	public ResponseEntity<Object> cancelOrder(@PathVariable Integer customerId,
			@PathVariable Integer orderId) {
		return orderService.cancelOrder(customerId, orderId);
	}

	@GetMapping("/api/customers/revenue-summary")
	public List<RevenueSummaryDTO> getRevenueSummary() {
		return orderService.getRevenueSummary();
	}

}
