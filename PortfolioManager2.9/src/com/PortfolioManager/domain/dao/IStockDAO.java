package com.PortfolioManager.domain.dao;

import com.PortfolioManager.domain.entities.Stock;
import java.util.Collection;


public interface IStockDAO {
	public void persist(Stock s);
	public void update(Stock s);
	public void delete(Stock s);
	public Stock getStockById(long stockId);
        public Collection<Stock> getStocks(String sym);
}
