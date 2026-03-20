package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.model.dto.CustomerDTO;
import com.capgemini.model.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping
	public ResponseEntity<Object> createCustomer(@RequestBody CustomerDTO dto) {
		return customerService.createCustomer(dto);
	}

	@GetMapping
	public List<CustomerDTO> getAllActiveCustomers() {
		return customerService.getAllActiveCustomers();
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<Object> getCustomerById(@PathVariable Integer customerId) {
		return customerService.getCustomerById(customerId);
	}

	@PutMapping("/{customerId}")
	public ResponseEntity<Object> updateCustomer(@PathVariable Integer customerId, @RequestBody CustomerDTO dto) {
		return customerService.updateCustomer(customerId, dto);
	}

	@DeleteMapping("/{customerId}")
	public ResponseEntity<Object> softDeleteCustomer(@PathVariable Integer customerId) {
		return customerService.softDeleteCustomer(customerId);
	}

	@GetMapping("/{customerId}/restore")
	public ResponseEntity<Object> restoreCustomer(@PathVariable Integer customerId) {
		return customerService.restoreCustomer(customerId);
	}

}
