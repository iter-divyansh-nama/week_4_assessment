package com.capgemini.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.model.entity.Customer;
import com.capgemini.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	Page<Order> findByCustomer(Customer customer, Pageable pageable);

	Page<Order> findByCustomerAndStatus(Customer customer, String status, Pageable pageable);

	@Query("SELECT o.customer.fullName, SUM(o.totalAmount) FROM Order o WHERE o.status = 'DELIVERED' GROUP BY o.customer.fullName")
	List<Object[]> findRevenueSummary();

}
