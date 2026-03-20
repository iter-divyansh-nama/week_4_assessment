package com.capgemini.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
