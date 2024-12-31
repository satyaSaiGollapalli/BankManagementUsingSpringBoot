package com.bank.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.entity.BankUserDetails;
import com.bank.repository.BankUserRepository;
@Component
public class BankUserDaoImpl implements BankUserDao{

	@Autowired
	BankUserRepository bankUserRepository;
	@Override
	public BankUserDetails userRegistration(BankUserDetails bankUserDetails) {
		// TODO Auto-generated method stub
		return bankUserRepository.save(bankUserDetails);
	}
	@Override
	public BankUserDetails getDetailsByAadhar(long aadhar) {
		// TODO Auto-generated method stub
		return bankUserRepository.findByAadharnumber(aadhar);
	}
	@Override
	public BankUserDetails getDetailsByPan(String pan) {
		// TODO Auto-generated method stub
		return bankUserRepository.findByPannumber(pan);
	}
	@Override
	public BankUserDetails getDetailsByMobile(long mobile) {
		// TODO Auto-generated method stub
		return bankUserRepository.findByMobilenumber(mobile);
	}
	@Override
	public BankUserDetails getDetailsByEmail(String email) {
		// TODO Auto-generated method stub
		return bankUserRepository.findByEmailid(email);
	}
	@Override
	public List<BankUserDetails> getAllDetails() {
		// TODO Auto-generated method stub
		return bankUserRepository.findAll();
	}
	@Override
	public BankUserDetails updateUser(String status, int pin, long acc, int id) {
		Optional<BankUserDetails> user = bankUserRepository.findById(id);
		if(user.isPresent()) {
			BankUserDetails bankUserDetails = user.get();
			bankUserDetails.setStatus(status);
			bankUserDetails.setAccountnumber(acc);
			bankUserDetails.setPin(pin);
			return bankUserRepository.save(bankUserDetails);
		}
		
		return null;
	}
	@Override
	public void deleteUser(int id) {
		bankUserRepository.deleteById(id);
		
	}
	@Override
	public BankUserDetails getDetailsById(int id) {
		// TODO Auto-generated method stub
		return bankUserRepository.findById(id).get();
	}
	@Override
	public boolean getDetailsByAccountNumberAndPin(long accountnumber, int pin) {
		
		return bankUserRepository.existsByAccountnumberAndPin(accountnumber, pin);
	}
	@Override
	public BankUserDetails findDetailsByPin(int pin) {
		// TODO Auto-generated method stub
//		System.out.println("DAO");
		return bankUserRepository.findByPin(pin);
	}
	@Override
	public BankUserDetails updateAmount(BankUserDetails bankUserDetails) {
		// TODO Auto-generated method stub
		 return bankUserRepository.save(bankUserDetails);
	}
	
	

}
