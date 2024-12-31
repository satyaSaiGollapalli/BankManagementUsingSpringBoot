package com.bank.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bank.DAO.BankUserDao;
import com.bank.DAO.StatementDAO;
import com.bank.entity.BankUserDetails;
import com.bank.entity.UserStatement;
import com.bank.exceptions.AadharExistException;
import com.bank.exceptions.BankUserException;
import com.bank.exceptions.EmailExistExceeption;
import com.bank.exceptions.MobileNumberExistException;
import com.bank.exceptions.PANExistException;
import com.bank.responseStructure.ResponseStructure;

import ch.qos.logback.core.boolex.Matcher;
@Component
public class UserServiceImpl implements UserService{

	@Autowired
	 StatementDAO dao;
	@Autowired
	BankUserDao bankUserDao;
	
	@Autowired
	StatementDAO statementDao;

	@Override
	public ResponseEntity<ResponseStructure<BankUserDetails>> validateBankUser(BankUserDetails bankUserDetails)  {
		// TODO Auto-generated method stub
		List<BankUserDetails> allDetails = bankUserDao.getAllDetails();
		if(allDetails.stream().anyMatch(user->user.getEmailid().equalsIgnoreCase(bankUserDetails.getEmailid()))) {
			throw new EmailExistExceeption("Email already exist try another one");
		}
		if(allDetails.stream().anyMatch(user->user.getAadharnumber()==bankUserDetails.getAadharnumber())) {
			throw new AadharExistException("Aadhar already exist try another one");
		}
		if(allDetails.stream().anyMatch(user->user.getMobilenumber()==bankUserDetails.getMobilenumber())) {
			
			throw new MobileNumberExistException("Mobile already exist try another one");
		}
		if(allDetails.stream().anyMatch(user->user.getPannumber().equals(bankUserDetails.getPannumber()))) {
			throw new PANExistException("PAN already exist try another one");
		}
		bankUserDetails.setStatus("Pending");
		bankUserDetails.setIfsccode("ONE0001120");
		BankUserDetails userRegistration = bankUserDao.userRegistration(bankUserDetails);
		if(userRegistration.getId()>0) {
			ResponseStructure <BankUserDetails> responseStructure=new ResponseStructure<BankUserDetails>();
			responseStructure.setData(userRegistration);
			responseStructure.setHttpstatuscode(HttpStatus.CREATED.value());
			responseStructure.setResponsemsg("Registration succesfull");
			ResponseEntity<ResponseStructure<BankUserDetails>> entity=new ResponseEntity<ResponseStructure<BankUserDetails>>(responseStructure,HttpStatus.CREATED);
		return entity;
		}
		else {
			throw new BankUserException("Server Error");
		}
		
	}

	@Override
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> getAllPendingDetails() {
		// TODO Auto-generated method stub
		List<BankUserDetails> allDetails = bankUserDao.getAllDetails();
		
		List<BankUserDetails> pending=new ArrayList<BankUserDetails>();
		if(allDetails.size()!=0) {
		allDetails.forEach(user->{
			if(user.getStatus().equalsIgnoreCase("Pending")) {
				pending.add(user);
			}
		});
//		System.out.println(pending);
		ResponseStructure<List<BankUserDetails>> structure=new ResponseStructure<List<BankUserDetails>>();
		structure.setData(pending);
		structure.setHttpstatuscode(HttpStatus.OK.value());
		structure.setResponsemsg("Pending User");
		return new ResponseEntity<ResponseStructure<List<BankUserDetails>>> (structure,HttpStatus.OK);
		}else {
			ResponseStructure<List<BankUserDetails>> structure=new ResponseStructure<List<BankUserDetails>>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("No Pending Users");
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>> (structure,HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> getAllApprovedDetails() {
		// TODO Auto-generated method stub
		List<BankUserDetails> allDetails = bankUserDao.getAllDetails();
		List<BankUserDetails> pending=new ArrayList<BankUserDetails>();
		if(allDetails.size()!=0) {
		allDetails.forEach(user->{
			if(user.getStatus().equalsIgnoreCase("accepted")) {
				pending.add(user);
			}
		});
		ResponseStructure<List<BankUserDetails>> structure=new ResponseStructure<List<BankUserDetails>>();
		structure.setData(pending);
		structure.setHttpstatuscode(HttpStatus.OK.value());
		structure.setResponsemsg("Approved Users");
		return new ResponseEntity<ResponseStructure<List<BankUserDetails>>> (structure,HttpStatus.OK);
		}else {
			ResponseStructure<List<BankUserDetails>> structure=new ResponseStructure<List<BankUserDetails>>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg(" No Approved Users");
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>> (structure,HttpStatus.NOT_FOUND);
		}
		
	}

	@Override
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> getAllClosedDetails() {
		List<BankUserDetails> allDetails = bankUserDao.getAllDetails();
		List<BankUserDetails> pending=new ArrayList<BankUserDetails>();
		if(allDetails.size()!=0) {
			
		allDetails.forEach(user->{
			if(user.getStatus().equalsIgnoreCase("Closed")) {
				pending.add(user);
			}
		});
		ResponseStructure<List<BankUserDetails>> structure=new ResponseStructure<List<BankUserDetails>>();
		structure.setData(pending);
		structure.setHttpstatuscode(HttpStatus.OK.value());
		structure.setResponsemsg("Account Closed Users");
		return new ResponseEntity<ResponseStructure<List<BankUserDetails>>> (structure,HttpStatus.OK);
	}else {
		ResponseStructure<List<BankUserDetails>> structure=new ResponseStructure<List<BankUserDetails>>();
		structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
		structure.setResponsemsg("No Closed Users");
		return new ResponseEntity<ResponseStructure<List<BankUserDetails>>> (structure,HttpStatus.NOT_FOUND);
	}
	}

	@Override
	public  ResponseEntity<ResponseStructure<BankUserDetails>> updateUser(String status, int id) {
		if(status.equalsIgnoreCase("accept")) {
			List<BankUserDetails> allDetails = bankUserDao.getAllDetails();
			Random r=new Random();
			int pin=0;
			while(true) {
				pin=r.nextInt(10000);
				if(pin<1000) {
					pin+=1000;
				}
				int gpin=pin;
				boolean pinCheck = allDetails.stream().anyMatch(user->user.getPin()==gpin);
				if(!pinCheck) {
					break;
					
				}
			}
			int accountnumber;
			while(true) {
				accountnumber=r.nextInt(10000000);
				if(accountnumber<1000000) {
					accountnumber+=1000000;
				}
				int gacc=accountnumber;
				boolean accCheck = allDetails.stream().anyMatch(user->user.getAccountnumber()==gacc);
				if(!accCheck) {
					break;
					
				}
			}
			
			BankUserDetails updateUser = bankUserDao.updateUser("Accepted", pin, accountnumber, id);
			ResponseStructure<BankUserDetails> structure=new ResponseStructure<BankUserDetails>();
			structure.setResponsemsg("User accepted");
			structure.setHttpstatuscode(HttpStatus.CREATED.value());
			structure.setData(updateUser);
			return new ResponseEntity<ResponseStructure<BankUserDetails>>(structure, HttpStatus.CREATED);
			
		}else if(status.equalsIgnoreCase("delete")) {
			
			BankUserDetails detailsById = bankUserDao.getDetailsById(id);
			bankUserDao.deleteUser(id);
			ResponseStructure<BankUserDetails> structure=new ResponseStructure<BankUserDetails>();
			structure.setData(detailsById);
			structure.setResponsemsg("User Deleted");
			structure.setHttpstatuscode(HttpStatus.GONE.value());
			return new ResponseEntity<ResponseStructure<BankUserDetails>>(structure, HttpStatus.GONE);
		}
		else {
			throw new BankUserException("Invalid updation");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> validateUserlogin(long accountnumber, int pin) {
		
		if(bankUserDao.getDetailsByAccountNumberAndPin(accountnumber, pin)) {
			ResponseStructure<String> structure= new ResponseStructure<String>();
			structure.setHttpstatuscode(HttpStatus.FOUND.value());
			structure.setResponsemsg("Login Succesfull");
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.FOUND);
		}else {
			ResponseStructure<String> structure= new ResponseStructure<String>();
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			structure.setResponsemsg("Invalid Credentials");
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<Double>> getAmountByPin(int pin) {
//		System.out.println("Service");
		BankUserDetails detailsByPin = bankUserDao.findDetailsByPin(pin);
		if(detailsByPin!=null) {
		ResponseStructure<Double> structure=new  ResponseStructure<Double>();
		structure.setData(detailsByPin.getAmount());
		structure.setResponsemsg("Current_Amount");
		structure.setHttpstatuscode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<Double>>(structure,HttpStatus.OK);
		}else {
			ResponseStructure<Double> structure=new  ResponseStructure<Double>();
//			structure.setData(detailsByPin.getAmount());
			structure.setResponsemsg("Invalid_Pin");
			structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ResponseStructure<Double>>(structure,HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> updateMoneyByPin(int pin, String status, double money) {
		// TODO Auto-generated method stub
		if(status.equalsIgnoreCase("credit")) {
			BankUserDetails detailsByPin = bankUserDao.findDetailsByPin(pin);
			double oldAmount=detailsByPin.getAmount();
			double newAmount=oldAmount+money;
			detailsByPin.setAmount(newAmount);
			UserStatement statement=new UserStatement();
			statement.setAccountnumber(detailsByPin.getAccountnumber());
			statement.setBalanceamount(newAmount);
			statement.setDateoftransaction(LocalDate.now());
			statement.setOperation(status);
			statement.setTimeoftransaction(LocalTime.now());
			statement.setTransactionamount(money);
			dao.insertDetails(statement);
			BankUserDetails updateAmount = bankUserDao.updateAmount(detailsByPin);
			if(updateAmount!=null) {
				ResponseStructure<String> structure=new ResponseStructure<String>();
				structure.setResponsemsg("Amount Credited");
				structure.setHttpstatuscode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.OK);
			}else {
				ResponseStructure<String> structure=new ResponseStructure<String>();
				structure.setResponsemsg("Unable to Credit");
				structure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
				return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.BAD_REQUEST);
			}
		}else {
			BankUserDetails detailsByPin = bankUserDao.findDetailsByPin(pin);
			double oldAmount=detailsByPin.getAmount();
			if(oldAmount>money) {
				double newAmount=oldAmount-money;
				detailsByPin.setAmount(newAmount);
				UserStatement statement=new UserStatement();
				statement.setAccountnumber(detailsByPin.getAccountnumber());
				statement.setBalanceamount(newAmount);
				statement.setDateoftransaction(LocalDate.now());
				statement.setOperation(status);
				statement.setTimeoftransaction(LocalTime.now());
				statement.setTransactionamount(money);
				dao.insertDetails(statement);
				BankUserDetails updateAmount = bankUserDao.updateAmount(detailsByPin);
				if(updateAmount!=null) {
					ResponseStructure<String> structure=new ResponseStructure<String>();
					structure.setResponsemsg("Amount debited");
					structure.setHttpstatuscode(HttpStatus.OK.value());
					return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.OK);
				}else {
					ResponseStructure<String> structure=new ResponseStructure<String>();
					structure.setResponsemsg("Unable to debit");
					structure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
					return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.BAD_REQUEST);
				}
				
			}else {
				
				ResponseStructure<String> structure=new ResponseStructure<String>();
				structure.setResponsemsg("Insufficient_Balance");
				structure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
				return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.BAD_REQUEST);
				
			}
			
			
		}
		
	}

	

	@Override
	public ResponseEntity<ResponseStructure<String>> updatePin(int oldpin, int newpin) {
				if(oldpin==newpin) {
					ResponseStructure<String> structure=new ResponseStructure<String>();
					structure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
					structure.setResponsemsg("PIN cannot be same");
					
					return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.BAD_REQUEST);
				}
				BankUserDetails detailsByPin = bankUserDao.findDetailsByPin(oldpin);
				List<BankUserDetails> allDetails = bankUserDao.getAllDetails();
					List<BankUserDetails> acceptedUsers = allDetails.stream().filter(user->user.getStatus().equalsIgnoreCase("accepted")).collect(Collectors.toList());
					if(allDetails.stream().anyMatch(user->user.getPin()==newpin)) {
						
						ResponseStructure<String> structure=new ResponseStructure<String>();
						structure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
						structure.setResponsemsg("PIN already exist try another");
						
						return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.BAD_REQUEST);
					}
				/*Random r=new Random();
				
				while(true) {
					int pin=r.nextInt(10000);
					if(pin<1000) {
						pin+=1000;
						 int gpin=pin;
					
					if(!acceptedUsers.stream().anyMatch(user->user.getPin()==gpin)) {*/
						detailsByPin.setPin(newpin);
						BankUserDetails userRegistration = bankUserDao.userRegistration(detailsByPin);
						if(userRegistration!=null) {
							ResponseStructure<String> structure=new ResponseStructure<String>();
							structure.setHttpstatuscode(HttpStatus.ACCEPTED.value());
							structure.setResponsemsg("Pin Changed Succefully");
							
							return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.ACCEPTED);
									
						}else {
							ResponseStructure<String> structure=new ResponseStructure<String>();
							structure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
							structure.setResponsemsg("Unable to change");
							
							return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.BAD_REQUEST);
						}
					}

	@Override
	public ResponseEntity<ResponseStructure<String>> updateUser(BankUserDetails bankUserDetails) {
		List<BankUserDetails> allDetails = bankUserDao.getAllDetails();
		List<BankUserDetails> acceptedUsers = allDetails.stream().filter(user->user.getStatus().equalsIgnoreCase("accepted")).collect(Collectors.toList());
		
		acceptedUsers=acceptedUsers.stream().filter(user-> user.getId()!=bankUserDetails.getId()).collect(Collectors.toList());
		if(bankUserDetails.getAadharnumber()<100000000000l || bankUserDetails.getAadharnumber()>999999999999l ) throw new BankUserException("Invalid aadhar length ");
		if(acceptedUsers.stream().anyMatch(user->user.getAadharnumber()==bankUserDetails.getAadharnumber())) throw new BankUserException("Aadhar already in use ");
		if(bankUserDetails.getAccountnumber()<1000000 || bankUserDetails.getAccountnumber()>10000000) throw new BankUserException("Invalid account number length ");
		if(acceptedUsers.stream().anyMatch(user->user.getAccountnumber()==bankUserDetails.getAccountnumber())) throw new BankUserException("Account number already in use");
		if(!isValidEmail(bankUserDetails.getEmailid())) throw new BankUserException("Invalid Email FORMAT");
		if(acceptedUsers.stream().anyMatch(user->user.getEmailid().equalsIgnoreCase(bankUserDetails.getEmailid()))) throw new BankUserException("Email already in use");
		if(!isValidPAN(bankUserDetails.getPannumber())) throw new BankUserException("Invalid PAN FORMAT");
		if(bankUserDetails.getMobilenumber()<1000000000 || bankUserDetails.getMobilenumber()>9999999999l) throw new BankUserException("Invalid mobile number length ");
		if(acceptedUsers.stream().anyMatch(user->user.getMobilenumber()==bankUserDetails.getMobilenumber())) throw new BankUserException("Mobile already in use");
		if(acceptedUsers.stream().anyMatch(user->user.getPannumber().equalsIgnoreCase(bankUserDetails.getPannumber()))) throw new BankUserException("PAN already in use");
		if(bankUserDetails.getPin()<1000 || bankUserDetails.getPin()>9999) throw new BankUserException("Invalid pin  length ");
		if(acceptedUsers.stream().anyMatch(user->user.getPin()==bankUserDetails.getPin())) throw new BankUserException("Aadhar already taken");
		if(!bankUserDetails.getGender().equalsIgnoreCase("Male") && !bankUserDetails.getGender().equalsIgnoreCase("female")) {
			throw new BankUserException("Invalid gender selection ");
		}
		String name=bankUserDetails.getName();
		if(name.length()<=2) {
			throw new BankUserException("Invalid name length");
		}
		for(int i=0;i<name.length();i++) {
			char ch=name.charAt(i);
			if(!Character.isAlphabetic(ch) || !Character.isSpace(ch)) {
				
				throw new BankUserException("Name should contain Alphabets only");
			}
		}
		bankUserDetails.setStatus("Accepted");
		bankUserDetails.setIfsccode("ONE0001120");
		BankUserDetails userRegistration = bankUserDao.userRegistration(bankUserDetails);
		if(userRegistration!=null) {
			ResponseStructure<String> structure= new ResponseStructure<String>();
			structure.setHttpstatuscode(HttpStatus.CREATED.value());
			structure.setResponsemsg("Changes Applied");
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.CREATED);
		}else {
			ResponseStructure<String> structure= new ResponseStructure<String>();
			structure.setHttpstatuscode(HttpStatus.FOUND.value());
			structure.setResponsemsg("Unable to Changes");
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.FOUND);
		}
		
	}
	public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Regular expression for validating email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        // Create Pattern object
        Pattern pattern = Pattern.compile(emailRegex);

        // Match email against the pattern
        java.util.regex.Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
	
				
	 public static boolean isValidPAN(String pan) {
	        // Regular expression for PAN format
	        String panRegex = "^[A-Z]{5}[0-9]{4}[A-Z]$";

	        // Check if PAN matches the regex
	        if (pan == null || !pan.matches(panRegex) ) {
	            return false;
	        }
	        return true;
	    }

	@Override
	public ResponseEntity<ResponseStructure<String>> mobileTransaction(long mobile, int pin, double amount) {

		    BankUserDetails client = bankUserDao.getDetailsByMobile(mobile);
		    if (client != null && client.getStatus().equalsIgnoreCase("Accepted")) {
		        BankUserDetails user = bankUserDao.findDetailsByPin(pin);
		        double userAmount = user.getAmount();
		        if (userAmount < amount) {
		            ResponseStructure<String> structure = new ResponseStructure<>();
		            structure.setResponsemsg("Insufficient_Balance");
		            structure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
		            return new ResponseEntity<>(structure, HttpStatus.BAD_REQUEST);
		        }

		        double clientAmount = client.getAmount();
		        double userBalanceAmount = userAmount - amount;
		        double clientBalanceAmount = clientAmount + amount;
		        client.setAmount(clientBalanceAmount);
		        user.setAmount(userBalanceAmount);
		        BankUserDetails userRegistration = bankUserDao.userRegistration(client);
		        BankUserDetails userRegistration2 = bankUserDao.userRegistration(user);

		        if (userRegistration != null && userRegistration2 != null) {
		            UserStatement userStatement = new UserStatement();
		            userStatement.setAccountnumber(user.getAccountnumber());
		            userStatement.setBalanceamount(userBalanceAmount);
		            userStatement.setDateoftransaction(LocalDate.now());
		            userStatement.setOperation("Debit");
		            userStatement.setTimeoftransaction(LocalTime.now());
		            userStatement.setTransactionamount(amount);

		            UserStatement clientStatement = new UserStatement();
		            clientStatement.setAccountnumber(client.getAccountnumber());
		            clientStatement.setBalanceamount(clientBalanceAmount);
		            clientStatement.setDateoftransaction(LocalDate.now());
		            clientStatement.setOperation("Credit");
		            clientStatement.setTimeoftransaction(LocalTime.now());
		            clientStatement.setTransactionamount(amount);

		            UserStatement insertDetails = statementDao.insertDetails(clientStatement);
		            UserStatement insertDetails2 = statementDao.insertDetails(userStatement);

		            if (insertDetails != null && insertDetails2 != null) {
		                ResponseStructure<String> structure = new ResponseStructure<>();
		                structure.setHttpstatuscode(HttpStatus.OK.value());
		                structure.setResponsemsg("Transaction Successful");
		                return new ResponseEntity<>(structure, HttpStatus.OK);
		            } else {
		                ResponseStructure<String> structure = new ResponseStructure<>();
		                structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
		                structure.setResponsemsg("Transaction failed");
		                return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
		            }
		        } else {
		            ResponseStructure<String> structure = new ResponseStructure<>();
		            structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
		            structure.setResponsemsg("Transaction failed");
		            return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
		        }
		    } else {
		        ResponseStructure<String> structure = new ResponseStructure<>();
		        structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
		        structure.setResponsemsg("Invalid Mobile Number");
		        return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
		    }
		}

	@Override
	public ResponseEntity<ResponseStructure<String>> updateAsClose(int pin) {
		BankUserDetails detailsByPin = bankUserDao.findDetailsByPin(pin);
			if(detailsByPin.getStatus().equalsIgnoreCase("closed")) throw new BankUserException("Request already sent");
			detailsByPin.setStatus("closed");
			BankUserDetails userRegistration = bankUserDao.userRegistration(detailsByPin);
			 ResponseStructure<String> structure = new ResponseStructure<>();
			if(userRegistration!=null) {
				 structure.setHttpstatuscode(HttpStatus.CREATED.value());
			        structure.setResponsemsg("Request sent to Admin ");
			        return new ResponseEntity<>(structure, HttpStatus.CREATED);
			}else {
				 structure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			        structure.setResponsemsg("Unable to send");
			        return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
			}
		
	}

	
	

}
/*	@Override
public Exception validateUser(BankUserDetails bankUserDetails) {
	BankUserDetails detailsByAadhar = bankUserDao.getDetailsByAadhar(bankUserDetails.getAadharnumber());
	if(detailsByAadhar!=null) {
		return new AadharExistException("Aadhar already exist try another one");
	}
	BankUserDetails detailsByEmail = bankUserDao.getDetailsByEmail(bankUserDetails.getEmailid());
	if(detailsByEmail!=null) {
		return new EmailExistExceeption("Email already exist try another one");
	}
	BankUserDetails detailsByMobile = bankUserDao.getDetailsByMobile(bankUserDetails.getMobilenumber());
	if(detailsByMobile!=null) {
		return new MobileNumberExistException("Mobile already exist try another one");
	}
	BankUserDetails detailsByPan = bankUserDao.getDetailsByPan(bankUserDetails.getPannumber());
	if(detailsByPan!=null) {
		return new PANExistException("PAN already exist try another one");
	}
	return null;
}*/