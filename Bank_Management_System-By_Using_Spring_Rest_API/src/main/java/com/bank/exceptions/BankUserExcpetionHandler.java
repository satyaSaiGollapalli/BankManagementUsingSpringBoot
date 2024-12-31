package com.bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.responseStructure.ResponseStructure;

@ControllerAdvice
public class BankUserExcpetionHandler {

	@ExceptionHandler(AadharExistException.class)
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> aadharExceptionHandler(AadharExistException exception) {
		ResponseStructure<String> structure=new ResponseStructure<String>();
		structure.setHttpstatuscode(HttpStatus.NOT_ACCEPTABLE.value());
		structure.setResponsemsg(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(EmailExistExceeption.class)
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> emailExceptionHandler(EmailExistExceeption exception) {
		
		ResponseStructure<String> structure=new ResponseStructure<String>();
		structure.setHttpstatuscode(HttpStatus.NOT_ACCEPTABLE.value());
		structure.setResponsemsg(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(MobileNumberExistException.class)
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> mobileExceptionHandler(MobileNumberExistException exception) {
		ResponseStructure<String> structure=new ResponseStructure<String>();
		structure.setHttpstatuscode(HttpStatus.NOT_ACCEPTABLE.value());
		structure.setResponsemsg(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(PANExistException.class)
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> panExceptionHandler(PANExistException exception) {
		ResponseStructure<String> structure=new ResponseStructure<String>();
		structure.setHttpstatuscode(HttpStatus.NOT_ACCEPTABLE.value());
		structure.setResponsemsg(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(BankUserException.class)
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> bankExceptionHandler(BankUserException exception) {
		
		ResponseStructure<String> structure=new ResponseStructure<String>();
		structure.setHttpstatuscode(HttpStatus.FAILED_DEPENDENCY.value());
		structure.setResponsemsg(exception.getMsg());
		
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.FAILED_DEPENDENCY);
	}
	
	@ExceptionHandler(AdminException.class)
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> adminExceptionHandler(AdminException e){
		ResponseStructure<String> structure=new ResponseStructure<String>();
		structure.setHttpstatuscode(HttpStatus.FAILED_DEPENDENCY.value());
		structure.setResponsemsg(e.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.FAILED_DEPENDENCY);
	}
}
