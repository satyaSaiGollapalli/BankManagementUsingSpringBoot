package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	Boolean existsByEmailAndPin(String email,String pin);
}
