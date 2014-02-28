package com.PortfolioManager.domain.dao;

import com.PortfolioManager.domain.entities.Investor;





public interface IInvestorDAO {

	public void persist(Investor i);
	public void update(Investor i);
	public void delete(Investor i);
	public Investor getInvestorById(String l);
}
