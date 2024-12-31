package com.bank.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bank.entity.UserStatement;
import com.bank.responseStructure.ResponseStructure;

public interface StatementService {

	ResponseEntity<ResponseStructure<List<UserStatement>>> getStatement(int pin);
	ResponseEntity<ResponseStructure<List<UserStatement>>> getFilteredStatement(String  values);

}
