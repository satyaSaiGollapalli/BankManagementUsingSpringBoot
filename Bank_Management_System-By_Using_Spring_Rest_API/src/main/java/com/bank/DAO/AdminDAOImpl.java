package com.bank.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.entity.Admin;
import com.bank.repository.AdminRepository;
@Component
public class AdminDAOImpl implements AdminDAO {

	@Autowired
	AdminRepository adminRepository;
	@Override
	public boolean isDetailsPresent(String email,String pin) {
		
		 return adminRepository.existsByEmailAndPin(email, pin);
		 
	}

}
