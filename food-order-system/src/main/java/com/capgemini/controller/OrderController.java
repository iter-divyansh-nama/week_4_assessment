package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.model.entity.Order;
import com.capgemini.model.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public String createOrder(@RequestBody Order order) {
		return orderService.createOrder(order);
	}

	@GetMapping("/{id}")
	public Order getOrderById(@PathVariable Integer id) {
		return orderService.getOrderById(id);
	}

	@GetMapping
	public List<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	@PutMapping("/{id}")
	public String updateOrder(@PathVariable Integer id, @RequestBody Order order) {
		return orderService.updateOrder(id, order);
	}

	@DeleteMapping("/{id}")
	public String cancelOrder(@PathVariable Integer id) {
		return orderService.cancelOrder(id);
	}

}
