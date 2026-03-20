package com.capgemini.model.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String customerName;
	private String foodItem;
	private Integer quantity;
	private String status;

	public Order() {
		super();
	}

	public Order(Integer id, String customerName, String foodItem, Integer quantity, String status) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.foodItem = foodItem;
		this.quantity = quantity;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(String foodItem) {
		this.foodItem = foodItem;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerName=" + customerName + ", foodItem=" + foodItem
				+ ", quantity=" + quantity + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerName, foodItem, id, quantity, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(customerName, other.customerName)
				&& Objects.equals(foodItem, other.foodItem)
				&& Objects.equals(id, other.id)
				&& Objects.equals(quantity, other.quantity)
				&& Objects.equals(status, other.status);
	}

}
