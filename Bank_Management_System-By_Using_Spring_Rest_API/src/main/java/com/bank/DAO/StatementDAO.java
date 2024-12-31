package com.bank.DAO;

import java.time.LocalDate;
import java.util.List;

import com.bank.entity.UserStatement;

public interface StatementDAO {

	UserStatement insertDetails(UserStatement statement);
	List<UserStatement> getDetailsBy(long accountnumber);
	List<UserStatement> getDetailsByValues(double amount, LocalDate date,String  operation);
}
