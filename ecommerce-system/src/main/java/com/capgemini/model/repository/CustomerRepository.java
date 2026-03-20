package com.capgemini.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.model.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	boolean existsByEmail(String email);

	List<Customer> findByIsActiveTrue();

	Optional<Customer> findByCustomerIdAndIsActiveTrue(Integer customerId);

}
