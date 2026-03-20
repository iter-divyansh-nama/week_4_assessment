package com.capgemini.model.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;
	private String author;
	private Integer availableCopies;
	private Integer borrowedCopies;

	public Book() {
		super();
	}

	public Book(Integer id, String title, String author, Integer availableCopies, Integer borrowedCopies) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.availableCopies = availableCopies;
		this.borrowedCopies = borrowedCopies;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getAvailableCopies() {
		return availableCopies;
	}

	public void setAvailableCopies(Integer availableCopies) {
		this.availableCopies = availableCopies;
	}

	public Integer getBorrowedCopies() {
		return borrowedCopies;
	}

	public void setBorrowedCopies(Integer borrowedCopies) {
		this.borrowedCopies = borrowedCopies;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author
				+ ", availableCopies=" + availableCopies + ", borrowedCopies=" + borrowedCopies + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, availableCopies, borrowedCopies, id, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author)
				&& Objects.equals(availableCopies, other.availableCopies)
				&& Objects.equals(borrowedCopies, other.borrowedCopies)
				&& Objects.equals(id, other.id)
				&& Objects.equals(title, other.title);
	}

}
