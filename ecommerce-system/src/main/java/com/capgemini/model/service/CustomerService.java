package com.capgemini.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capgemini.model.dto.CustomerDTO;
import com.capgemini.model.entity.Customer;
import com.capgemini.model.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public ResponseEntity<Object> createCustomer(CustomerDTO dto) {
		if (customerRepository.existsByEmail(dto.getEmail())) {
			Map<String, Object> error = new HashMap<>();
			error.put("status", 409);
			error.put("error", "Conflict");
			error.put("message", "Email already registered");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
		}
		Customer customer = new Customer();
		customer.setFullName(dto.getFullName());
		customer.setEmail(dto.getEmail());
		customer.setPhone(dto.getPhone());
		customer.setIsActive(true);
		customerRepository.save(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(customer));
	}

	public List<CustomerDTO> getAllActiveCustomers() {
		List<CustomerDTO> list = new ArrayList<>();
		for (Customer c : customerRepository.findByIsActiveTrue())
			list.add(toDTO(c));
		return list;
	}

	public ResponseEntity<Object> getCustomerById(Integer id) {
		Optional<Customer> optional = customerRepository.findByCustomerIdAndIsActiveTrue(id);
		if (optional.isEmpty())
			return notFound(id);
		return ResponseEntity.ok(toDTO(optional.get()));
	}

	public ResponseEntity<Object> updateCustomer(Integer id, CustomerDTO dto) {
		Optional<Customer> optional = customerRepository.findByCustomerIdAndIsActiveTrue(id);
		if (optional.isEmpty())
			return notFound(id);
		Customer customer = optional.get();
		customer.setFullName(dto.getFullName());
		customer.setEmail(dto.getEmail());
		customer.setPhone(dto.getPhone());
		customerRepository.save(customer);
		return ResponseEntity.ok(toDTO(customer));
	}

	public ResponseEntity<Object> softDeleteCustomer(Integer id) {
		Optional<Customer> optional = customerRepository.findByCustomerIdAndIsActiveTrue(id);
		if (optional.isEmpty())
			return notFound(id);
		Customer customer = optional.get();
		customer.setIsActive(false);
		customerRepository.save(customer);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Customer deactivated successfully");
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<Object> restoreCustomer(Integer id) {
		Optional<Customer> optional = customerRepository.findById(id);
		if (optional.isEmpty())
			return notFound(id);
		Customer customer = optional.get();
		customer.setIsActive(true);
		customerRepository.save(customer);
		return ResponseEntity.ok(toDTO(customer));
	}

	private CustomerDTO toDTO(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setCustomerId(customer.getCustomerId());
		dto.setFullName(customer.getFullName());
		dto.setEmail(customer.getEmail());
		dto.setPhone(customer.getPhone());
		dto.setIsActive(customer.getIsActive());
		dto.setCreatedAt(customer.getCreatedAt());
		dto.setUpdatedAt(customer.getUpdatedAt());
		return dto;
	}

	private ResponseEntity<Object> notFound(Integer id) {
		Map<String, Object> error = new HashMap<>();
		error.put("status", 404);
		error.put("error", "Not Found");
		error.put("message", "Customer with ID " + id + " not found");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}
