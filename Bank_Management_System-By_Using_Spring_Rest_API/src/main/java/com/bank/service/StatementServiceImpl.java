package com.bank.service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bank.DAO.BankUserDao;
import com.bank.DAO.StatementDAO;
import com.bank.entity.BankUserDetails;
import com.bank.entity.UserStatement;
import com.bank.responseStructure.ResponseStructure;
@Component
public class StatementServiceImpl implements StatementService {
	@Autowired
	BankUserDao bankUserDao;
	@Autowired
	StatementDAO statementDao;
	
	
	
	@Override
	public ResponseEntity<ResponseStructure<List<UserStatement>>> getStatement(int pin) {
		// TODO Auto-generated method stub
		BankUserDetails detailsByPin = bankUserDao.findDetailsByPin(pin);
		List<UserStatement> detailsBy = statementDao.getDetailsBy(detailsByPin.getAccountnumber());
		if(detailsBy!=null) {
			ResponseStructure<List<UserStatement>> structure=new ResponseStructure<List<UserStatement>>();
			structure.setData(detailsBy);
			structure.setHttpstatuscode(HttpStatus.FOUND.value());
			structure.setResponsemsg("User Bank Statement");
			return new ResponseEntity<ResponseStructure<List<UserStatement>>>(structure,HttpStatus.FOUND);
					
		}else {
			ResponseStructure<List<UserStatement>> structure=new ResponseStructure<List<UserStatement>>();
//			structure.setData(detailsBy);
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("No statement found");
			return new ResponseEntity<ResponseStructure<List<UserStatement>>>(structure,HttpStatus.NOT_FOUND);
			
		}
		
		
	}

	@Override
	public ResponseEntity<ResponseStructure<List<UserStatement>>> getFilteredStatement(String values) {
		// TODO Auto-generated method stub
		Double amount=null;
		String operation = null;
		LocalDate date = null;
		try {
			
			operation=values;
			amount=Double.valueOf(values);
			
			
		}catch (NumberFormatException e) {
			// TODO: handle exception
			amount=Double.MAX_VALUE;
			try {
				date=LocalDate.parse(values);
			}catch(DateTimeException e1) {
				date=LocalDate.now();
			}
		}
		finally {
			{
				if(amount==null) {
					amount=Double.MAX_VALUE;
				}else if(date==null) {
					System.out.println(date);
					date=LocalDate.now();
				}
				//System.out.println(date);
				List<UserStatement> detailsByValues = statementDao.getDetailsByValues(amount, date, operation);
				ResponseStructure<List<UserStatement>> structure=new ResponseStructure<List<UserStatement>>();
				//System.out.println(detailsByValues);
				if(detailsByValues.size()!=0) {
					structure.setData(detailsByValues);
					structure.setResponsemsg("Filtered DEtails");
					structure.setHttpstatuscode(HttpStatus.FOUND.value());
					return new ResponseEntity<ResponseStructure<List<UserStatement>>>(structure,HttpStatus.FOUND);
					
				}else {
					structure.setResponsemsg("No data found");
					structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
					return new ResponseEntity<ResponseStructure<List<UserStatement>>>(structure,HttpStatus.NOT_FOUND);
				}
			}
		}
		
	}
}
