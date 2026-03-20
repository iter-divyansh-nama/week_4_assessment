package com.capgemini.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
