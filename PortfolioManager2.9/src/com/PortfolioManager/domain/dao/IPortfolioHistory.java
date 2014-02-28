/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.domain.dao;

import com.PortfolioManager.domain.entities.PortfolioHistory;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ahmed
 */
public interface IPortfolioHistory {
    
    void persist(PortfolioHistory p);
    void update(PortfolioHistory p);
    PortfolioHistory getHistoryByYearAndMonth(Long portId);
    List<PortfolioHistory> getPortfolioHistory(Long portId);
    
}
