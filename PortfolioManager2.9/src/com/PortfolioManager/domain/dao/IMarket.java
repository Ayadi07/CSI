package com.PortfolioManager.domain.dao;

import com.PortfolioManager.domain.entities.Market;
import java.util.List;



public interface IMarket {

	void persist(Market m);

	void update(Market m);

	void delete(String market);

	Market getMarketById(String m);
        List<Market> getListMarket();

}
