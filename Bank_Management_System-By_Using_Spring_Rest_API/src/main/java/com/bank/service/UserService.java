package com.bank.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bank.entity.BankUserDetails;
import com.bank.entity.UserStatement;
import com.bank.responseStructure.ResponseStructure;

public interface UserService {

	//Exception validateUser(BankUserDetails bankUserDetails);
	public ResponseEntity<ResponseStructure<BankUserDetails>> validateBankUser(BankUserDetails bankUserDetails) ;
	ResponseEntity<ResponseStructure<List<BankUserDetails>>> getAllPendingDetails();
	ResponseEntity<ResponseStructure<List<BankUserDetails>>> getAllApprovedDetails();
	ResponseEntity<ResponseStructure<List<BankUserDetails>>> getAllClosedDetails();
    ResponseEntity<ResponseStructure<BankUserDetails>> updateUser(String status,int id);
    ResponseEntity<ResponseStructure<String>>  validateUserlogin(long accountnumber,int pin);
    ResponseEntity<ResponseStructure<Double>> getAmountByPin(int pin);
    ResponseEntity<ResponseStructure<String>> updateMoneyByPin(int pin, String status ,double money);
    ResponseEntity<ResponseStructure<String>> updatePin(int oldpin, int newpin);
    //ResponseEntity<ResponseStructure<List<UserStatement>>> getStatement(int pin);
    ResponseEntity<ResponseStructure<String>> updateUser(BankUserDetails bankUserDetails);
    ResponseEntity<ResponseStructure<String>> mobileTransaction(long mobile,int pin,double amount);
    ResponseEntity<ResponseStructure<String>> updateAsClose(int pin);
    
    
}
