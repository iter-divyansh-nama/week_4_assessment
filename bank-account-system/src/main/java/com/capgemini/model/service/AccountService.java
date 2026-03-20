package com.capgemini.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.model.entity.Account;
import com.capgemini.model.entity.TransactionRequest;
import com.capgemini.model.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public String createAccount(Account account) {
		accountRepository.save(account);
		return "Account created successfully";
	}

	public Account getAccountById(Integer id) {
		Optional<Account> account = accountRepository.findById(id);
		if (account.isPresent())
			return account.get();
		return null;
	}

	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	public String updateAccount(Integer id, Account updatedData) {
		Optional<Account> optional = accountRepository.findById(id);
		if (optional.isEmpty())
			return "Account not found";

		Account existing = optional.get();
		existing.setAccountHolderName(updatedData.getAccountHolderName());
		existing.setBalance(updatedData.getBalance());
		accountRepository.save(existing);
		return "Account updated successfully";
	}

	public String deleteAccount(Integer id) {
		Optional<Account> optional = accountRepository.findById(id);
		if (optional.isEmpty())
			return "Account not found";

		accountRepository.deleteById(id);
		return "Account deleted successfully";
	}

	public String deposit(TransactionRequest request) {
		Optional<Account> optional = accountRepository.findById(request.getAccountId());
		if (optional.isEmpty())
			return "Account not found";

		if (request.getAmount() <= 0)
			return "Invalid amount";

		Account account = optional.get();
		account.setBalance(account.getBalance() + request.getAmount());
		accountRepository.save(account);
		return "Deposit successful. Updated balance: " + account.getBalance();
	}

	public String withdraw(TransactionRequest request) {
		Optional<Account> optional = accountRepository.findById(request.getAccountId());
		if (optional.isEmpty())
			return "Account not found";

		if (request.getAmount() <= 0)
			return "Invalid amount";

		Account account = optional.get();
		if (request.getAmount() > account.getBalance())
			return "Insufficient balance";

		account.setBalance(account.getBalance() - request.getAmount());
		accountRepository.save(account);
		return "Withdrawal successful. Updated balance: " + account.getBalance();
	}

}
