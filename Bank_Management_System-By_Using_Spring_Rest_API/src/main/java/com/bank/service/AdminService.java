package com.bank.service;

import org.springframework.http.ResponseEntity;

import com.bank.responseStructure.ResponseStructure;

public interface AdminService {

	ResponseEntity<ResponseStructure<String>> adminLogin(String email,String pin);
	
}
