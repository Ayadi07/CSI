/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.businessGUI.portfolioManagement.performance.data;

import com.PortfolioManager.domain.dao.IPortfolioDAO;
import com.PortfolioManager.domain.dao.IPortfolioHistory;
import com.PortfolioManager.domain.dao.IStockDAO;
import com.PortfolioManager.domain.entities.OrderStock;
import com.PortfolioManager.domain.entities.Portfolio;
import com.PortfolioManager.domain.entities.PortfolioHistory;
import com.PortfolioManager.domain.entities.PortfolioOperation;
import com.PortfolioManager.domain.entities.Stock;
import com.PortfolioManager.domain.impl.PortfolioDAO;
import com.PortfolioManager.domain.impl.PortfolioHistoryDAO;
import com.PortfolioManager.domain.impl.StockDAO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ahmed
 */
public class PortfolioPerformanceUtilities {

    private static PortfolioPerformanceUtilities portfolioPerformanceUtilities;
    private static Date date;

    public PortfolioPerformanceUtilities() {
    }

    public static PortfolioPerformanceUtilities getMe() {
        if (portfolioPerformanceUtilities == null) {
            portfolioPerformanceUtilities = new PortfolioPerformanceUtilities();
        }
        date = new Date();
        return portfolioPerformanceUtilities;
    }

    public static void CalculateMonthReturn(Portfolio portfolio) {
        Date d = new Date();

        IPortfolioDAO iPortfolioDAO = new PortfolioDAO();

        PortfolioHistory ph = portfolio.getPortfolioHistoryForYearAndMonth();
        if (ph == null) {
            ph = new PortfolioHistory();
            ph.setMonthOfReturn(d);
            ph.setPortfolioInitalMoney(portfolio.calculateToDayValue());
            ph.setPortfolioTodayValue(portfolio.calculateToDayValue());
            portfolio.addPortfolioHistory(ph);
            portfolio.setPortfolioReturn(0.0);
        } else {
            ph.setPortfolioTodayValue(portfolio.calculateToDayValue());
            ph.setPortfolioReturn(ph.calculatePortfolioReturn());
            portfolio.setPortfolioReturn(ph.getPortfolioReturn());
            IPortfolioHistory iPortfolioHistory = new PortfolioHistoryDAO();
            iPortfolioHistory.update(ph);
        }
        iPortfolioDAO.update(portfolio);
    }

    public static void calculatePerformance(Portfolio portfolio) {

        PortfolioHistory portfolioHistory = portfolio.getPortfolioHistoryForYearAndMonth();
        double riskless = portfolio.getRIRate();
        CalculateMonthReturn(portfolio);
        double portfolioReturn = portfolio.getPortfolioReturn();
        calculatePortfolioBeta(portfolio);
        double beta = portfolio.getPortfolioBeta();
        if (beta != 0) {
            portfolio.setPortfolioPerformance((portfolioReturn - riskless) / beta);
        }
        IPortfolioDAO portfolioDAO = new PortfolioDAO();
        portfolioDAO.update(portfolio);

        if (portfolioHistory == null) {
            portfolioHistory = new PortfolioHistory();
            portfolioHistory.setMonthOfReturn(new Date());
            portfolioHistory.setPortfolioInitalMoney(portfolio.calculateToDayValue());
            portfolioHistory.setPortfolioTodayValue(portfolio.calculateToDayValue());
            portfolioHistory.setPortfolioBeta(beta);
            if (beta != 0) {
                portfolioHistory.setPortfolioPerformance((-riskless) / beta);
            } else {
                portfolioHistory.setPortfolioPerformance((-riskless));
            }
            portfolio.addPortfolioHistory(portfolioHistory);
            portfolio.setPortfolioReturn(0.0);

        } else {
            portfolioHistory.setPortfolioTodayValue(portfolio.calculateToDayValue());
            portfolioHistory.setPortfolioReturn(portfolioHistory.calculatePortfolioReturn());
            portfolio.setPortfolioReturn(portfolioHistory.getPortfolioReturn());
            portfolioHistory.setPortfolioBeta(beta);
            portfolioHistory.setPortfolioPerformance(portfolio.getPortfolioPerformance());
            IPortfolioHistory iPortfolioHistory = new PortfolioHistoryDAO();
            iPortfolioHistory.update(portfolioHistory);
        }
    }

    public static void calculatePortfolioBeta(Portfolio portfolio) {

        IStockDAO iStockDAO = new StockDAO();
        IPortfolioDAO iPortfolioDAO = new PortfolioDAO();
        double beta = 0;
        int stocks = 0;
        List<OrderStock> ordersList = (List<OrderStock>) portfolio.getOrders();
        if (!ordersList.isEmpty()) {
            for (OrderStock orderStock : ordersList) {
                stocks += orderStock.getStocksNumber();
                Stock s = orderStock.getUpdatedStock();
                beta += s.calculateMonthlyBeta(portfolio.getMarket().getMarketId()) * orderStock.getStocksNumber();
                List<Stock> list = (List<Stock>) iStockDAO.getStocks(s.getSymbol());//first stock is the same as second :p !! (more work on that)
                if (!list.isEmpty()) {
                    for (Stock stock : list) {
                        stock.setBeta(s.getBeta());
                        stock.setBetaLatest(new Date());
                        iStockDAO.update(stock);
                    }
                }

            }
            beta /= stocks;
            portfolio.setPortfolioBeta(beta);
            iPortfolioDAO.update(portfolio);
        }
    }

    public static void manageBuyOrders(Portfolio portfolio, OrderStock orderStock) {
        IPortfolioDAO portfolioDAO = new PortfolioDAO();
        IPortfolioHistory iPortfolioHistory = new PortfolioHistoryDAO();
        PortfolioHistory portfolioHistory = iPortfolioHistory.getHistoryByYearAndMonth(portfolio.getId());

        portfolio.addOrderStock(orderStock);

        if (portfolioHistory == null) {

            System.out.println("history not found");
            portfolioHistory = new PortfolioHistory();
            portfolioHistory.setMonthOfReturn(date);
            portfolioHistory.setPortfolioInitalMoney(portfolio.calculateToDayValue());
            portfolioHistory.setPortfolioTodayValue(portfolio.calculateToDayValue());
//            portfolio.setPortfolioBeta(orderStock.getStock()); getBeta
            portfolio.addPortfolioHistory(portfolioHistory);
            portfolio.setInitialMoney(portfolio.getPortfolioToDayValue());
        } else {
            portfolioHistory.setPortfolioTodayValue(portfolio.calculateToDayValue());
            iPortfolioHistory.update(portfolioHistory);
//            portfolio.setPortfolioBeta(orderStock.getStock()); getBeta
        }
        portfolioDAO.update(portfolio);
    }

    public static void manageSellOrders(Portfolio portfolio, OrderStock orderStock) {
        IPortfolioDAO portfolioDAO = new PortfolioDAO();
        IPortfolioHistory iPortfolioHistory = new PortfolioHistoryDAO();
        PortfolioHistory portfolioHistory = iPortfolioHistory.getHistoryByYearAndMonth(portfolio.getId());
        portfolio.removeOrderStock(orderStock);
        if (portfolioHistory == null) {
            portfolioHistory = new PortfolioHistory();
            portfolioHistory.setMonthOfReturn(date);
            portfolioHistory.setPortfolioInitalMoney(portfolio.calculateToDayValue());


            portfolio.addPortfolioHistory(portfolioHistory);
            portfolio.setInitialMoney(portfolio.getPortfolioToDayValue());
        } else {
            portfolioHistory.setPortfolioTodayValue(portfolio.calculateToDayValue());
            iPortfolioHistory.update(portfolioHistory);

        }
        portfolioDAO.update(portfolio);
    }

    public static void manageOperations(Portfolio portfolio, PortfolioOperation operation) {
        IPortfolioDAO portfolioDAO = new PortfolioDAO();
        IPortfolioHistory iPortfolioHistory = new PortfolioHistoryDAO();

        PortfolioHistory portfolioHistory = iPortfolioHistory.getHistoryByYearAndMonth(portfolio.getId());

        portfolio.addOperation(operation);
        if (portfolioHistory == null) {
            portfolioHistory = new PortfolioHistory();
            portfolioHistory.setMonthOfReturn(date);
            portfolioHistory.setPortfolioInitalMoney(portfolio.calculateToDayValue());
            portfolioHistory.setPortfolioTodayValue(portfolio.calculateToDayValue());
            portfolioHistory.setPortfolioNetAdditions(operation.getAmount());
            portfolio.addPortfolioHistory(portfolioHistory);
            portfolio.setInitialMoney(portfolio.calculateToDayValue());
        } else {
            portfolioHistory.setPortfolioNetAdditions(portfolioHistory.getPortfolioNetAdditions() + operation.getAmount());
            portfolioHistory.setPortfolioTodayValue(portfolio.calculateToDayValue());
            iPortfolioHistory.update(portfolioHistory);
        }
        portfolioDAO.update(portfolio);
    }
}
