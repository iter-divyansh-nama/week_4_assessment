package com.capgemini.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.model.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
