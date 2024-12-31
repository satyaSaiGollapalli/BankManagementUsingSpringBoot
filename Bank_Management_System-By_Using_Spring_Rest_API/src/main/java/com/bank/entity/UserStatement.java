package com.bank.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserStatement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private long accountnumber;
	@Column(nullable = false)
	private double transactionamount;
	@Column(nullable = false)
	private double balanceamount;
	@Column(nullable = false)
	private String operation;
	@Column(nullable = false)
	private LocalDate dateoftransaction;
	@Column(nullable = false)
	private LocalTime timeoftransaction;
}
