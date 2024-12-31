package com.bank.DAO;

import com.bank.entity.Admin;

public interface AdminDAO {

	boolean isDetailsPresent(String email,String pin);
}
