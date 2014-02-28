/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.businessGUI.portfolioManagement.performance.charts;

import com.PortfolioManager.domain.dao.IPortfolioDAO;
import com.PortfolioManager.domain.entities.Portfolio;
import com.PortfolioManager.domain.impl.PortfolioDAO;
import com.PortfolioManager.utilities.technic.JPA;
import com.gui.mainGUI.splash.ImageHelper;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.*;

/**
 *
 * @author Ahmed
 */
public class PortfolioSpiderWebChart extends JFrame {

    private static PortfolioSpiderWebChart portfolioSpiderWebChart;
    private static JPanel jPanel;

    public static PortfolioSpiderWebChart getMe(Long id, boolean b) {
        if (portfolioSpiderWebChart == null) {
            portfolioSpiderWebChart = new PortfolioSpiderWebChart("Portfolio Spider Web", id, b);
            portfolioSpiderWebChart.pack();
            RefineryUtilities.centerFrameOnScreen(portfolioSpiderWebChart);
            return portfolioSpiderWebChart;
        } else {
            jPanel = createPanel(id, b);
            jPanel.revalidate();
            portfolioSpiderWebChart.setContentPane(jPanel);
            portfolioSpiderWebChart.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            return portfolioSpiderWebChart;
        }
    }

    private PortfolioSpiderWebChart(String s, Long id, boolean b) {
        super(s);
        jPanel = createPanel(id, b);
        jPanel.setPreferredSize(new Dimension(800, 400));
        setContentPane(jPanel);
        setIconImage(ImageHelper.loadImage("/com/gui/img/Spider_web.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private static CategoryDataset createDatasetOnePortfolio(Long id) {
        String beta = "Portfolio Beta";
        String returnValue = "Return Value";
        String performance = "Performance";
        String riskless = "4 Weeks T-Bill rate";
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        IPortfolioDAO iPortfolioDAO = new PortfolioDAO();
        Portfolio portfolio = iPortfolioDAO.getPortfolioById(id);

        defaultcategorydataset.addValue(portfolio.getPortfolioBeta(), portfolio.getName(), beta);
        defaultcategorydataset.addValue(portfolio.getPortfolioReturn(), portfolio.getName(), returnValue);
        defaultcategorydataset.addValue(portfolio.getPortfolioPerformance(), portfolio.getName(), performance);
        defaultcategorydataset.addValue(portfolio.getRIRate(), portfolio.getName(), riskless);
        
        return defaultcategorydataset;
    }

    private static CategoryDataset createDatasetAll(Long id) {
        String beta = "Portfolio Beta";
        String returnValue = "Return Value";
        String performance = "Performance";
        String riskless = "4 Weeks T-Bill rate";
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        IPortfolioDAO iPortfolioDAO = new PortfolioDAO();
        List<Portfolio> listOfPortfoliosByAccount = iPortfolioDAO.getPortfoliByAccount(id);
        for (Portfolio portfolio : listOfPortfoliosByAccount) {

            defaultcategorydataset.addValue(portfolio.getPortfolioBeta(), portfolio.getName(), beta);
            defaultcategorydataset.addValue(portfolio.getPortfolioReturn(), portfolio.getName(), returnValue);
            defaultcategorydataset.addValue(portfolio.getPortfolioPerformance(), portfolio.getName(), performance);
            defaultcategorydataset.addValue(portfolio.getRIRate(), portfolio.getName(), riskless);
        }

        return defaultcategorydataset;
    }

    private static JFreeChart createChart(CategoryDataset categorydataset) {
        SpiderWebPlot spiderwebplot = new SpiderWebPlot(categorydataset);
        spiderwebplot.setStartAngle(-90D);
        spiderwebplot.setInteriorGap(0.3D);
        spiderwebplot.setToolTipGenerator(new StandardCategoryToolTipGenerator());

        spiderwebplot.setAxisLinePaint(Color.black);
        spiderwebplot.setAxisLineStroke(new BasicStroke(2));
        spiderwebplot.setBaseSeriesOutlineStroke(new BasicStroke(2));
        spiderwebplot.setLabelFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        spiderwebplot.setLabelPaint(new Color(64, 0, 0));

        spiderwebplot.setBackgroundImage(ImageHelper.loadImage("/com/gui/img/dark-blue.jpg").getImage());
        spiderwebplot.setBackgroundImageAlignment(Align.CENTER);

        JFreeChart jfreechart = new JFreeChart("Portfolio Details Spider Web", TextTitle.DEFAULT_FONT, spiderwebplot, false);
        LegendTitle legendtitle = new LegendTitle(spiderwebplot);
        legendtitle.setPosition(RectangleEdge.BOTTOM);
        jfreechart.addSubtitle(legendtitle);
        return jfreechart;
    }

    public static JPanel createPanel(Long id, boolean b) {
        JFreeChart chart;
        if (b) {
            chart = createChart(createDatasetAll(id));
        } else {
            chart = createChart(createDatasetOnePortfolio(id));
        }
        return new ChartPanel(chart);
    }

    public static void main(String args[]) {
        JPA.setPersistenceUnit("PortM");
//        PortfolioSpiderWebChart spiderwebchartdemo1 = new PortfolioSpiderWebChart("Portfolio Spider Web", 201l, true);        
        getMe(551l, false).setVisible(true);
    }
}
