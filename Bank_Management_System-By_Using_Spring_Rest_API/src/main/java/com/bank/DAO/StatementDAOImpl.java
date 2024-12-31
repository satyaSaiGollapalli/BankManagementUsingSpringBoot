package com.bank.DAO;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.entity.UserStatement;
import com.bank.repository.StatementRepository;

@Component
public class StatementDAOImpl implements StatementDAO {

	@Autowired
	StatementRepository repository;
	@Override
	public UserStatement insertDetails(UserStatement statement) {
		// TODO Auto-generated method stub
		return repository.save(statement);
	}
	@Override
	public List<UserStatement> getDetailsBy(long accountnumber) {
		// TODO Auto-generated method stub
		return repository.getByAccountnumber(accountnumber);
	}
	@Override
	public List<UserStatement> getDetailsByValues(double amount, LocalDate date, String operation) {
		// TODO Auto-generated method stub
		return repository.getByTransactionamountOrDateoftransactionOrOperation(amount, date, operation);
	}

}
