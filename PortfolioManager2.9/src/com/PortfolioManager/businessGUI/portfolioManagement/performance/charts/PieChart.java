/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.businessGUI.portfolioManagement.performance.charts;

import com.PortfolioManager.domain.dao.IPortfolioDAO;
import com.PortfolioManager.domain.entities.OrderStock;
import com.PortfolioManager.domain.entities.Portfolio;
import com.PortfolioManager.domain.impl.PortfolioDAO;
import com.PortfolioManager.utilities.technic.JPA;
import com.gui.mainGUI.splash.ImageHelper;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Rotation;

// Referenced classes of package demo:
//                      Spinner
public class PieChart extends JFrame {

    private static JPanel jpanel;
    private static PieChart pieChart;

    public static PieChart getMe(Long id) {
        if (pieChart == null) {
            pieChart = new PieChart("Portfolio Composition", id);
            pieChart.pack();
            RefineryUtilities.centerFrameOnScreen(pieChart);
            return pieChart;

        } else {
            jpanel = createPanel(id);
            jpanel.setPreferredSize(new Dimension(800, 400));
            pieChart.setContentPane(jpanel); 
            pieChart.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            return pieChart;
        }
    }

    private PieChart(String s, Long id) {
        super(s);
        jpanel = createPanel(id);
        jpanel.setPreferredSize(new Dimension(800, 400));
        setContentPane(jpanel);
        setIconImage(ImageHelper.loadImage("/com/gui/img/chart_pie.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private static JFreeChart createChart(PieDataset piedataset) {
        JFreeChart jfreechart = ChartFactory.createPieChart3D("Portfolio Stocks", piedataset, true, false, false);
        PiePlot3D pieplot3d = (PiePlot3D) jfreechart.getPlot();
        pieplot3d.setStartAngle(270D);
        pieplot3d.setDirection(Rotation.ANTICLOCKWISE);
        pieplot3d.setForegroundAlpha(0.6F);
//        pieplot3d.setDarkerSides(true);
        pieplot3d.setBackgroundImage(ImageHelper.loadImage("/com/gui/img/Dark.jpg").getImage());
        
        GradientPaint g = new GradientPaint(0.0f, 0.0f,new Color(43,162, 183),
                0.0f, 0.0f, new Color(143, 216, 228));
        pieplot3d.setBaseSectionOutlinePaint(g);
        pieplot3d.setBaseSectionOutlineStroke(new BasicStroke(2));
        
        pieplot3d.setLabelBackgroundPaint(g);
        pieplot3d.setLabelLinkPaint(Color.magenta);
        pieplot3d.setLabelPaint(new Color(64, 0, 0));
        pieplot3d.setLabelFont(new Font(Font.DIALOG, Font.TRUETYPE_FONT, 15));
        
        pieplot3d.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}"));
//        pieplot3d.setSimpleLabels(true);
               
        return jfreechart;
    }

    private static PieDataset createDataset(Long id) {
        DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
        IPortfolioDAO portfolioDAO = new PortfolioDAO();
        Portfolio portfolio = portfolioDAO.getPortfolioById(id);
        List<OrderStock> list = (List<OrderStock>) portfolio.getOrders();
        System.out.println(list);
        for (OrderStock orderStock : list) {
            double val = orderStock.getPrice() + orderStock.getPotentialEarnings();
            defaultpiedataset.setValue(orderStock.getStock().getName(),orderStock.getStocksNumber());
        }
        return defaultpiedataset;
    }

    public static JPanel createPanel(Long id) {
        JFreeChart jfreechart = createChart(createDataset(id));
        jfreechart.setBackgroundPaint(Color.gray);        
        Spinner rotator = new Spinner((PiePlot3D) jfreechart.getPlot());
        rotator.start();       
        return new ChartPanel(jfreechart);
    }

    public static void main(String args[]) {
        JPA.setPersistenceUnit("PortM");
        getMe(2l).setVisible(true);
    }
}