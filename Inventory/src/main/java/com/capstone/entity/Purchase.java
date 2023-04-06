package com.capstone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchases")

//This class represents a purchase in a system
public class Purchase {

	/*
	 * Declaring all my variables for the purchase entity. LOMBOK annotations
	 * provide methods for getters, setters, and constructors without the need of
	 * explicitly writing them. This is the case for the employee and item entities.
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "Purchase Date")
	private String purchaseDate;

	@Column(name = "Quantity Purchased")
	private int quantity;

	@Column(name = "Purchase Price")
	private double price;

	@Column(name = "Profit")
	private double profit;

	@ManyToOne
	private Item item;

}
