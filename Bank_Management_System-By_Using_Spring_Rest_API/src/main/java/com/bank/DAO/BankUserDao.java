package com.bank.DAO;

import java.util.List;

import com.bank.entity.BankUserDetails;

public interface BankUserDao {

	BankUserDetails userRegistration(BankUserDetails bankUserDetails);
	BankUserDetails getDetailsByAadhar(long aadhar);
	BankUserDetails getDetailsByPan(String pan);
	BankUserDetails getDetailsByMobile(long mobile);
	BankUserDetails getDetailsByEmail(String email);
	List<BankUserDetails> getAllDetails();
	BankUserDetails updateUser(String status,int pin,long acc,int id);
	void deleteUser(int id);
	BankUserDetails getDetailsById(int id);
	boolean getDetailsByAccountNumberAndPin(long accountnumber, int pin);
	BankUserDetails findDetailsByPin(int pin);
	BankUserDetails updateAmount(BankUserDetails bankUserDetails);
	

}
