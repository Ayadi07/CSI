package com.PortfolioManager.domain.dao;

import com.PortfolioManager.domain.entities.Account;

public interface IAccountDAO {
	public void persist(Account a);
	public void update(Account a);
	public void delete(Account a);
	public Account getAccountById(Long accountId);

}
