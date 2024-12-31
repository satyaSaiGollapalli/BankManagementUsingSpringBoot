package com.bank.controller;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.DAO.BankUserDao;
import com.bank.entity.BankUserDetails;
import com.bank.entity.UserStatement;
import com.bank.global.Globalvalues;
import com.bank.responseStructure.ResponseStructure;
import com.bank.service.StatementService;
import com.bank.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin("*")
public class BankUserController {
//	@Autowired
	//BankUserDao bankUserDao;
	
	@Autowired
	UserService service;
	@Autowired
	Globalvalues globalvalues;
	@Autowired
	StatementService statementService;
	
	/*It is used to specify the insertion operation
	 * It is also used to indicate as an end point or url pattern or API
	 * and also used to map the end point with the method
	 * 
	 * localhost:1411/userregistration*/
/*	@PostMapping("/userregistration")
	@ResponseBody
	public Object  userRegistration(@RequestBody BankUserDetails user) {
		Exception validateUser = service.validateUser(user);
		if(validateUser==null) {
			BankUserDetails userRegistration = bankUserDao.userRegistration(user);
			return userRegistration;
		}else {
			
			return validateUser;
		}
		
		
	}*/
	
	@PostMapping("/userregistration")
	@ResponseBody
	public ResponseEntity<ResponseStructure<BankUserDetails>>  userRegistration(@RequestBody BankUserDetails user) {
//		System.out.println("ok");
		return service.validateBankUser(user);
	
	}
	
	@GetMapping("/userlogin/{accountnum}/{pin}")
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> userLogin(@PathVariable("accountnum") long accountnumber,@PathVariable("pin") int pin) {
		
		globalvalues.setGlobalPin(pin);
		return service.validateUserlogin(accountnumber, pin);
	}
	
	@GetMapping("/getamount/{pin}")
	@ResponseBody
	public ResponseEntity<ResponseStructure<Double>> getAmount(@PathVariable("pin") int pin) {
//		System.out.println("controler");
		
		int globalPin = globalvalues.getGlobalPin();
		if(globalPin==0 ) {
			ResponseStructure<Double> structure=new ResponseStructure<Double>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("Login Before operations");
			
			return new ResponseEntity<ResponseStructure<Double>>(structure,HttpStatus.NOT_FOUND);
		}
//		System.out.println(globalPin);
		if(globalPin==pin) {
			return service.getAmountByPin(pin);
		}else {
			ResponseStructure<Double> structure=new ResponseStructure<Double>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("Inavlid PIN");
			
			return new ResponseEntity<ResponseStructure<Double>>(structure,HttpStatus.NOT_FOUND);
			
		}
		
	}
	@PutMapping("/updateamount/{status}/{amount}/{pin}")
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> updateMoney(@PathVariable("status") String status,@PathVariable("amount") double amount,@PathVariable("pin") int pin) {
		
		int globalPin=globalvalues.getGlobalPin();
		if(globalPin==0 ) {
			ResponseStructure<String> structure=new ResponseStructure<String>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("Login Before operations");
			
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
		}
		if(pin==globalPin) {
			return service.updateMoneyByPin(pin, status, amount);
		}else {
			ResponseStructure<String> structure=new ResponseStructure<String>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("Inavlid PIN");
			
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	@GetMapping("/statement")
	@ResponseBody
	public ResponseEntity<ResponseStructure<List<UserStatement>>> userStatementDetails() {
		int globalPin = globalvalues.getGlobalPin();
		if(globalPin==0 ) {
			ResponseStructure<List<UserStatement>> structure=new ResponseStructure<List<UserStatement>>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("Login Before operations");
			
			return new ResponseEntity<ResponseStructure<List<UserStatement>>>(structure,HttpStatus.NOT_FOUND);
		}
		
		return statementService.getStatement(globalPin);
		
	}
	
	@PutMapping("/changemapping/{oldpin}/{newpin}")
	public ResponseEntity<ResponseStructure<String>> changePin(@PathVariable("oldpin") int oldpin,@PathVariable("newpin") int newpin) {
		int globalPin=globalvalues.getGlobalPin();
		if(globalPin==0 ) {
			ResponseStructure<String> structure=new ResponseStructure<String>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("Login Before operations");
			
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
		}
		
		if(globalPin==oldpin) {
			return service.updatePin(oldpin, newpin);
		}else {
			ResponseStructure<String> structure=new ResponseStructure<String>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("Inavlid PIN");
			
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("/modifyuser")
	public ResponseEntity<ResponseStructure<String>> updateUser(@RequestBody BankUserDetails bankUserDetails) {
		return service.updateUser(bankUserDetails);
	}
	
	@PutMapping("/mobiletransaction/{mobile}/{pin}/{amount}")
	public ResponseEntity<ResponseStructure<String>> mobileTransaction(@PathVariable("mobile") long mobile,@PathVariable("pin") int pin,@PathVariable("amount") double amount) {
		int globalPin = globalvalues.getGlobalPin();
		if(globalPin==0 ) {
			ResponseStructure<String> structure=new ResponseStructure<String>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("Login Before operations");
			
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
		}
		
		if(pin==globalPin) {
			return service.mobileTransaction(mobile, pin, amount);
		}else {
			ResponseStructure<String> structure=new ResponseStructure<String>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("Inavlid PIN");
			
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/closerequest/{pin}")
	public ResponseEntity<ResponseStructure<String>> closingRequest(@PathVariable("pin") int pin) {
		int globalPin = globalvalues.getGlobalPin();
		if(globalPin==0 ) {
			ResponseStructure<String> structure=new ResponseStructure<String>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("Login Before operations");
			
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
		}
		
		if(pin==globalPin) {
			return service.updateAsClose(pin);
		}else {
			ResponseStructure<String> structure=new ResponseStructure<String>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("Inavlid PIN");
			
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/filteredstatement/{value}")
	public ResponseEntity<ResponseStructure<List<UserStatement>>> getFilteredStatement(@PathVariable("value") String value) {
		return statementService.getFilteredStatement(value);
	}
}
