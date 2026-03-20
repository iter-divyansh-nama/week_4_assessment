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

import com.capgemini.model.entity.Account;
import com.capgemini.model.entity.TransactionRequest;
import com.capgemini.model.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping
	public String createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@GetMapping("/{id}")
	public Account getAccountById(@PathVariable Integer id) {
		return accountService.getAccountById(id);
	}

	@GetMapping
	public List<Account> getAllAccounts() {
		return accountService.getAllAccounts();
	}

	@PutMapping("/{id}")
	public String updateAccount(@PathVariable Integer id, @RequestBody Account account) {
		return accountService.updateAccount(id, account);
	}

	@DeleteMapping("/{id}")
	public String deleteAccount(@PathVariable Integer id) {
		return accountService.deleteAccount(id);
	}

	@PostMapping("/deposit")
	public String deposit(@RequestBody TransactionRequest request) {
		return accountService.deposit(request);
	}

	@PostMapping("/withdraw")
	public String withdraw(@RequestBody TransactionRequest request) {
		return accountService.withdraw(request);
	}

}
