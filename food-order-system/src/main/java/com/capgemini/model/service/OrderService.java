package com.capgemini.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.model.entity.Order;
import com.capgemini.model.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public String createOrder(Order order) {
		order.setStatus("PLACED");
		orderRepository.save(order);
		return "Order created successfully";
	}

	public Order getOrderById(Integer id) {
		Optional<Order> order = orderRepository.findById(id);
		if (order.isPresent())
			return order.get();
		return null;
	}

	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public String updateOrder(Integer id, Order updatedData) {
		Optional<Order> optional = orderRepository.findById(id);
		if (optional.isEmpty())
			return "Order not found";

		Order existing = optional.get();

		if ("CANCELLED".equals(existing.getStatus()))
			return "Cannot update cancelled order";

		existing.setCustomerName(updatedData.getCustomerName());
		existing.setFoodItem(updatedData.getFoodItem());
		existing.setQuantity(updatedData.getQuantity());
		orderRepository.save(existing);
		return "Order updated successfully";
	}

	public String cancelOrder(Integer id) {
		Optional<Order> optional = orderRepository.findById(id);
		if (optional.isEmpty())
			return "Order not found";

		Order existing = optional.get();
		existing.setStatus("CANCELLED");
		orderRepository.save(existing);
		return "Order cancelled successfully";
	}

}
