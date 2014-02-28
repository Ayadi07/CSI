/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.domain.dao;


import com.PortfolioManager.domain.entities.PortfolioOperation;
import java.util.List;

/**
 *
 * @author Ahmed
 */
public interface IPortfolioOperation {
    void persist(PortfolioOperation m);
    List<PortfolioOperation> getOperationtByPortfolio(Long portId);
    List<PortfolioOperation> getMonthlyOperations(Long portId,String year,String month);
}
