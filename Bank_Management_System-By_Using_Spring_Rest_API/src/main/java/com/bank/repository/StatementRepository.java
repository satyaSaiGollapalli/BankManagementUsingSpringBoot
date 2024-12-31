package com.bank.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.entity.UserStatement;

public interface StatementRepository extends JpaRepository<UserStatement, Integer> {

	@Query("select statement from UserStatement statement where statement.accountnumber = :accountnumber order by statement.dateoftransaction")
	List<UserStatement> getByAccountnumber(@Param("accountnumber") long accountnumber);
	List<UserStatement> getByTransactionamountOrDateoftransactionOrOperation(double amount,LocalDate date,String operation);

	
}
