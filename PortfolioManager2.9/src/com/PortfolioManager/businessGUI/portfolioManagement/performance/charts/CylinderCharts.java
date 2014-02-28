/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.businessGUI.portfolioManagement.performance.charts;

/**
 *
 * @author Ahmed
 */
import com.PortfolioManager.domain.dao.IAccountDAO;
import com.PortfolioManager.domain.entities.Account;
import com.PortfolioManager.domain.entities.Portfolio;
import com.PortfolioManager.domain.impl.AccountDAO;
import com.PortfolioManager.utilities.technic.JPA;
import com.gui.mainGUI.splash.ImageHelper;
import java.awt.*;
import java.text.NumberFormat;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.*;
import java.util.List;
import org.jfree.chart.axis.Axis;
// Referenced classes of package demo:
//                      CylinderRenderer
public class CylinderCharts extends JFrame {

    static class CustomCylinderRenderer extends CylinderRenderer {

        private Paint colors[];

        public Paint getItemPaint(int i, int j) {
            return colors[j % colors.length];
        }

        public CustomCylinderRenderer(Paint apaint[]) {
            colors = apaint;
        }
    }
    private static CylinderCharts cylinderCharts;
    private static ChartPanel chartPanel;

    public static CylinderCharts getMe(Long id) {
        if (cylinderCharts == null) {
            cylinderCharts = new CylinderCharts("title", id);
            cylinderCharts.pack();
            RefineryUtilities.centerFrameOnScreen(cylinderCharts);
            return cylinderCharts;
        }
        chartPanel = (ChartPanel) createPanel(id);
        chartPanel.setPreferredSize(new Dimension(800, 400));
        cylinderCharts.setContentPane(chartPanel);
        cylinderCharts.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        return cylinderCharts;
    }

    private CylinderCharts(String s, Long id) {
        super(s);
        setIconImage(ImageHelper.loadImage("/com/gui/img/bars.png").getImage());
        ChartPanel chartpanel = (ChartPanel) createPanel(id);
        chartpanel.setPreferredSize(new Dimension(800, 400));
        setContentPane(chartpanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private static CategoryDataset createDataset(Long id) {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        IAccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getAccountById(id);
        List<Portfolio> list = (List<Portfolio>) account.getPortfolios();
        defaultcategorydataset.addValue(account.getInvestedMoney(), "Invested Money", "Account");
        for (Portfolio portfolio : list) {
            defaultcategorydataset.addValue(portfolio.getPortfolioToDayValue(), "Today Value", portfolio.getName());
        }
        return defaultcategorydataset;
    }

    private static JFreeChart createChart(CategoryDataset categorydataset) {
        JFreeChart jfreechart = ChartFactory.createBarChart3D("Account Invested Money", "", "Value($)", categorydataset, PlotOrientation.HORIZONTAL, false, true, false);
        CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
        categoryplot.setBackgroundImage(ImageHelper.loadImage("/com/gui/img/background2.jpg").getImage());
        Paint apaint[] = createPaint();
        CustomCylinderRenderer customcylinderrenderer = new CustomCylinderRenderer(apaint);
        customcylinderrenderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_VERTICAL));
        customcylinderrenderer.setBaseOutlinePaint(Color.gray);
        customcylinderrenderer.setBaseOutlineStroke(new BasicStroke(0.3F));
        customcylinderrenderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        categoryplot.setRenderer(customcylinderrenderer);
        return jfreechart;
    }

    private static Paint[] createPaint() {
        Paint apaint[] = new Paint[5];
        apaint[0] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.red);
        apaint[1] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.green);
        apaint[2] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.blue);
        apaint[3] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.orange);
        apaint[4] = new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.magenta);
        return apaint;
    }

    public static JPanel createPanel(Long id) {
        JFreeChart jfreechart = createChart(createDataset(id));
        jfreechart.setBackgroundPaint(Color.gray);
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        return chartpanel;
    }

    public static void main(String args[]) {
        JPA.setPersistenceUnit("PortM");
        getMe(201l).setVisible(true);
    }
}
