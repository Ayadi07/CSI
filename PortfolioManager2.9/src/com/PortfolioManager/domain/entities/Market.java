package com.PortfolioManager.domain.entities;

import com.PortfolioManager.domain.dao.IMarket;
import com.PortfolioManager.domain.impl.MarketDAO;
import com.PortfolioManager.businessGUI.portfolioManagement.performance.data.MarketDataUtilities;
import com.PortfolioManager.businessGUI.chartTools.StockChart;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class Market implements Serializable {

    private String marketId;
    private String marketSymbol;
    private double indice;
    private double oldIndice;
    private Date indiceLatest;
    private double monthlyVariance;
    private Date varianceLatest;

    public Market() {
    }

    public Market(String marketSymbol) {
        super();

        this.indiceLatest = new Date();
        this.marketSymbol = marketSymbol;
    }

    public double getIndice() {
        return indice;
    }

    public void setIndice(double indice) {
        this.indice = indice;
    }

    public double getMonthlyVariance() {
        return monthlyVariance;
    }

    public void setMonthlyVariance(double monthlyVariance) {
        this.monthlyVariance = monthlyVariance;
    }

    /**
     * @return the monthlyVariance
     */
    public double calculateGetMonthlyVariance() {
        if (varianceLatest.getDay() != new Date().getDay()) {
            downloadMonthlyVariance();
        }
        return monthlyVariance;
    }

    /**
     * @param monthlyVariance the monthlyVariance to set
     */
    private void downloadMonthlyVariance() {
        dateManagerForStockChart();
        StockChart.getMe().setXyDataSetFromYahoo();
        MarketDataUtilities.getMe().setTypicalValues(StockChart.getMe().getTypicalPrice());
        MarketDataUtilities.getMe().setYields();
        setMonthlyVariance(MarketDataUtilities.calculateVariance());
        setVarianceLatest(new Date());

        IMarket iMarket = new MarketDAO();
        iMarket.update(this);
    }

    @Id
    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    /**
     * @return the marketSymbol
     */
    public String getMarketSymbol() {
        return marketSymbol;
    }

    /**
     * @param marketSymbol the marketSymbol to set
     */
    public void setMarketSymbol(String marketSymbol) {
        this.marketSymbol = marketSymbol;
    }

    /**
     * @return the indiceLatest
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getIndiceLatest() {
        return indiceLatest;
    }

    /**
     * @param indiceLatest the indiceLatest to set
     */
    public void setIndiceLatest(Date indiceLatest) {
        this.indiceLatest = indiceLatest;
    }

    public double CalulateGetIndice() {
        if (!dateCompare(indiceLatest, new Date())) {
            downloadIndice();
        }
        return indice;
    }

    private boolean dateCompare(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }

        if (d1.getDay() == d2.getDay()) {
            if (d1.getMonth() == d2.getMonth()) {
                if (d1.getYear() == d2.getYear()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void downloadIndice() {
        IMarket iMarket = new MarketDAO();

        Stock s = new Stock();
        s.setSymbol(marketSymbol);

        oldIndice = indice;
        iMarket.update(this);
        indice = StockChart.getStockLatestPrice(s);
        setIndiceLatest(new Date());
        iMarket.update(this);
    }

    /**
     * @return the varianceLatest
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getVarianceLatest() {
        return varianceLatest;
    }

    /**
     * @param varianceLatest the varianceLatest to set
     */
    public void setVarianceLatest(Date latesVariance) {
        this.varianceLatest = latesVariance;
    }

    /**
     * @return the oldIndice
     */
    public double getOldIndice() {
        return oldIndice;
    }

    /**
     * @param oldIndice the oldIndice to set
     */
    public void setOldIndice(double oldIndice) {
        this.oldIndice = oldIndice;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.marketId != null ? this.marketId.hashCode() : 0);
        hash = 59 * hash + (this.marketSymbol != null ? this.marketSymbol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Market other = (Market) obj;
        if ((this.marketSymbol == null) ? (other.marketSymbol != null) : !this.marketSymbol.equals(other.marketSymbol)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Market{" + "marketId=" + marketId + ", marketSymbol=" + marketSymbol + ", indice=" + indice + ", indiceLatest=" + indiceLatest + '}';
    }

    public void dateManagerForStockChart() {
        Date dateToDay = new Date();
        Date dateMonthAgo = new Date();

        // yahoo specifications
        dateToDay.setMonth(dateToDay.getMonth() - 1);
        dateMonthAgo.setMonth(dateMonthAgo.getMonth() - 2);

        // yahoo specifications

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String date = dateFormat.format(dateToDay);

        StringTokenizer st = new StringTokenizer(date, "-");

        date = dateFormat.format(dateMonthAgo);

        StringTokenizer st2 = new StringTokenizer(date, "-");

        StockChart.getMe().setStockPriceDataRequirement(
                marketSymbol,
                st2.nextToken(), st2.nextToken(), st2.nextToken(),
                st.nextToken(), st.nextToken(), st.nextToken());
    }
}
