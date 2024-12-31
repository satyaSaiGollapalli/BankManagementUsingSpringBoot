package com.bank.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankUserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(unique = true,nullable = false)
	private String emailid;
	@Column(unique = true,nullable = false,length=10)
	private long mobilenumber;
	@Column(unique = true,nullable = false,length=12)
	private long aadharnumber;
	@Column(unique = true,nullable = false,length=10)
	private String pannumber;
	//@Column(unique = true)
	private long accountnumber;
	//@Column(unique = true)
	private int pin;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private String gender;
	@Column(nullable = false)
	private double amount;
	@Column(nullable = false)
	private String status;
	@Column(nullable = false)
	private LocalDate dateofbirth;
	@Column(nullable = false)
	private String ifsccode;
}
