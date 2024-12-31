package com.bank.controller;

import java.awt.PageAttributes.MediaType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.DAO.AdminDAO;
import com.bank.entity.Admin;
import com.bank.entity.BankUserDetails;
import com.bank.responseStructure.ResponseStructure;
import com.bank.service.AdminService;
import com.bank.service.UserService;

@Controller
@CrossOrigin("*")
public class AdminController {
	@Autowired
	AdminService service;
	@Autowired
	UserService service2;

	/*
	 * 1.Querypath: localhost:1212/loginadmin?emai=admin@gmail.com&pin=1234
	 * 
	 * @RequestParam 2.Path localhost:1212/loginadmin/admin@gmail.com/
	 * 1234
	 * 
	 * @PathVariable and end point like loginadmin/{email}/{pin}
	 */
	@GetMapping("/loginadmin/{email}/{pin}")
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> loginAdmin(@PathVariable("email") String email,
			@PathVariable("pin") String pin) {
		System.out.println("hai");
		return service.adminLogin(email, pin);
	}

	@GetMapping("/getpending")
	@ResponseBody
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> getPendingDetails() {
//		System.out.println("ok");
		return service2.getAllPendingDetails();
	}

	@GetMapping("/getapproved")
	@ResponseBody
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> getApprovedDetails() {
//		System.out.println("ok");
		return service2.getAllApprovedDetails();
	}

	@GetMapping("/getclosed")
	@ResponseBody
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> getClosedDetails() {
		return service2.getAllClosedDetails();
	}

	@PutMapping("/update/{status}/{id}")
	@ResponseBody
	public ResponseEntity<ResponseStructure<BankUserDetails>> updateUser(@PathVariable("status") String status,
			@PathVariable("id") int id) {
		System.out.println("ok");
		return service2.updateUser(status, id);
	}
}
