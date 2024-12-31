package com.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.BankUserDetails;

public interface BankUserRepository extends JpaRepository<BankUserDetails, Integer> {

	BankUserDetails findByEmailid(String email);
	BankUserDetails findByAadharnumber(long aadhar);
	BankUserDetails findByMobilenumber(long mobile);
	BankUserDetails findByPannumber(String pan);
	BankUserDetails findByPin(int pin);
	boolean existsByAccountnumberAndPin(long accountnumber, int pin);
}
