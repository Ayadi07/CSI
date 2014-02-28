package com.PortfolioManager.domain.dao;
import com.PortfolioManager.domain.entities.Portfolio;
import java.util.List;


public interface IPortfolioDAO {
	public void persist(Portfolio p);
	public void update(Portfolio p);
	public void delete(Portfolio p);	
	public Portfolio getPortfolioById(Long portfolioId);
        public Portfolio getPortfolioByName(String name,Long id);
        public List<Portfolio> getPortfoliByAccount(Long accountId);
}
