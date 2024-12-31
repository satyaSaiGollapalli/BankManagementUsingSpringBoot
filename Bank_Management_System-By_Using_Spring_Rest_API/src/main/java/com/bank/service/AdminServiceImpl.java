package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bank.DAO.AdminDAO;
import com.bank.exceptions.AdminException;
import com.bank.responseStructure.ResponseStructure;

@Component
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminDAO  dao;
	@Override
	public ResponseEntity<ResponseStructure<String>> adminLogin(String email, String pin) {
		if(dao.isDetailsPresent(email,pin)){
			ResponseStructure< String> structure=new  ResponseStructure<String>();
			structure.setResponsemsg("Login Succesfull");
			structure.setHttpstatuscode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.FOUND);
		}else {
			throw new AdminException("Inavlid Credentials");
		}
		
	}

}
