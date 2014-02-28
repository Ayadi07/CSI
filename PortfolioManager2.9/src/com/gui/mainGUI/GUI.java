/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gui.mainGUI;

import com.gui.mainGUI.splash.ImageHelper;
import com.gui.mainGUI.splash.SplashPanel;
import com.PortfolioManager.domain.dao.IMarket;
import com.PortfolioManager.domain.dao.IOrderStock;
import com.PortfolioManager.domain.entities.Investor;
import com.PortfolioManager.domain.entities.Market;
import com.PortfolioManager.domain.entities.PortfolioOperation;
import com.PortfolioManager.domain.entities.OrderStock;
import com.PortfolioManager.domain.entities.Portfolio;
import com.PortfolioManager.domain.entities.Stock;
import com.PortfolioManager.domain.impl.MarketDAO;
import com.PortfolioManager.domain.impl.OrderStockDAO;
import com.PortfolioManager.utilities.technic.JPA;
import com.PortfolioManager.businessGUI.accountManagement.AccountModification;
import com.PortfolioManager.businessGUI.accountManagement.AccoutRemove;
import com.PortfolioManager.businessGUI.accountManagement.Authenticate;
import com.PortfolioManager.businessGUI.accountManagement.CreateAccount;
import com.PortfolioManager.businessGUI.chartTools.MarketStocks;
import com.PortfolioManager.businessGUI.chartTools.StockChart;
import com.PortfolioManager.businessGUI.chartTools.indicators.BandsOfBolinger;
import com.PortfolioManager.businessGUI.chartTools.indicators.MACrossover;
import com.PortfolioManager.businessGUI.portfolioManagement.PDFReport.PDFReportGen;
import com.PortfolioManager.businessGUI.portfolioManagement.RSS.RssFeedParser;
import com.PortfolioManager.businessGUI.portfolioManagement.RSS.RssFeedParser.RssFeed;
import com.PortfolioManager.businessGUI.portfolioManagement.gui.OrderBuy;
import com.PortfolioManager.businessGUI.portfolioManagement.gui.OrderSell;
import com.PortfolioManager.businessGUI.portfolioManagement.gui.PortfolioCreation;
import com.PortfolioManager.businessGUI.portfolioManagement.gui.PortfolioEdition;
import com.PortfolioManager.businessGUI.portfolioManagement.gui.PortfolioSelection;
import com.PortfolioManager.businessGUI.portfolioManagement.performance.charts.CylinderCharts;
import com.PortfolioManager.businessGUI.portfolioManagement.performance.charts.PieChart;
import com.PortfolioManager.businessGUI.portfolioManagement.performance.charts.PortfolioSpiderWebChart;
import com.PortfolioManager.businessGUI.portfolioManagement.performance.data.PortfolioPerformanceUtilities;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Ahmed
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    public static Investor loggedInvestor;
    public static int logged;
    private static GUI gui;
    private static JFreeChart chart;
    private static ChartPanel chartPanel;
    public static Stock stock;
    private static Date dateToDay;
    private static Date dateYearAgo;
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension appSize = new Dimension(880, 660);
    private static final int appPosX = (screenSize.width / 2) - (appSize.width / 2);
    private static final int appPosY = (screenSize.height / 2) - (appSize.height / 2);
    private static Rectangle appBounds = new Rectangle(appPosX, appPosY, appSize.width, appSize.height);
    private JWindow splashScreen = null;
    private SplashPanel splashPanel = null;
    private static boolean buyBoolean;

    public static Stock getStock() {
        return stock;
    }

    /**
     * @return the cmb_market
     */
    public static javax.swing.JComboBox getCmb_market() {
        return cmb_market;
    }

    public Investor getLoggedInvestor() {
        return loggedInvestor;
    }

    private GUI() {
        // create splash screen
        splashPanel = new SplashPanel();
        splashScreen = new JWindow();
        splashScreen.getContentPane().add(splashPanel);
        splashScreen.pack();
        Dimension size = splashScreen.getSize();
        splashScreen.setLocation(screenSize.width / 2 - size.width / 2, screenSize.height / 2 - size.height / 2);

        // Show the splash screen on the gui thread using invokeLater
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                splashScreen.setVisible(true);
            }
        });

        initComponents();
        JPA.setPersistenceUnit("PortM");
        cmb_hiddenSymbols.setVisible(false);
        enabler(false);
        buttonGroup1.add(jrbAcryl);
        buttonGroup1.add(jrbAluminium);
        buttonGroup1.add(jrbHiFi);
        buttonGroup1.add(jrbLuna);
        buttonGroup1.add(jrbLuna);
        buttonGroup1.add(jrbMcWin);
        buttonGroup1.add(jrbMint);
        buttonGroup1.add(jrbNoire);
        buttonGroup1.add(jrbSmart);
        buttonGroup1.add(jrbTexture);
        stock = new Stock();
        // this is important for retrieving
        // order_id***********************************
        jTable1Orders.getTableHeader().setReorderingAllowed(false);
        // this is important for retrieving
        // order_id************************************

        // Show the demo and take down the splash screen. Note that we again must do this on the GUI thread using invokeLater.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showApp();
                if (splashScreen != null) {
                    splashScreen.setVisible(false);
                }
            }
        });

    }

    public static GUI getMe() {
        if (gui == null) {
            gui = new GUI();
        }
        return gui;
    }

    private void themeSet(int i) {
        String ACRYL = "com.jtattoo.plaf.acryl.AcrylLookAndFeel";
        String ALUMINIUM = "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel";
        String HIFI = "com.jtattoo.plaf.hifi.HiFiLookAndFeel";
        String LUNA = "com.jtattoo.plaf.luna.LunaLookAndFeel";
        String MCWIN = "com.jtattoo.plaf.mcwin.McWinLookAndFeel";
        String MINT = "com.jtattoo.plaf.mint.MintLookAndFeel";
        String NOIRE = "com.jtattoo.plaf.noire.NoireLookAndFeel";
        String SMART = "com.jtattoo.plaf.smart.SmartLookAndFeel";
        String SYSTEM = UIManager.getSystemLookAndFeelClassName();
        String TEXTURE = "com.jtattoo.plaf.texture.TextureLookAndFeel";
        try {
            switch (i) {
                case 1:
                    UIManager.setLookAndFeel(ACRYL);
                    break;
                case 2:
                    UIManager.setLookAndFeel(ALUMINIUM);
                    break;
                case 4:
                    UIManager.setLookAndFeel(HIFI);
                    break;
                case 5:
                    UIManager.setLookAndFeel(LUNA);
                    break;
                case 6:
                    UIManager.setLookAndFeel(MCWIN);
                    break;
                case 7:
                    UIManager.setLookAndFeel(MINT);
                    break;
                case 8:
                    UIManager.setLookAndFeel(NOIRE);
                    break;
                case 9:
                    UIManager.setLookAndFeel(SMART);
                    break;
                case 10:
                    UIManager.setLookAndFeel(TEXTURE);
                    break;
                case 11:
                    UIManager.setLookAndFeel(SYSTEM);
                    break;
                default:
                    System.exit(2013);// unreached ;) :p
                }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        Window windows[] = Window.getWindows();
        for (int l = 0; l < windows.length; l++) {
            if (windows[l].isDisplayable()) {
                SwingUtilities.updateComponentTreeUI(windows[l]);
            }
        }
        // SwingUtilities.updateComponentTreeUI(this);
        // this.pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        cmb_market = new javax.swing.JComboBox();
        cmb_stocks = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnStockChart = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jCheckBoxMAC = new javax.swing.JCheckBox();
        jCheckBoxBB = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        cmb_hiddenSymbols = new javax.swing.JComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jtfIndexValue = new javax.swing.JTextField();
        btndown = new javax.swing.JButton();
        btnup = new javax.swing.JButton();
        btnIndex = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnacc = new javax.swing.JButton();
        btnSelectPortf = new javax.swing.JButton();
        btneditPortf = new javax.swing.JButton();
        btnbuy = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jTabbedPane = new javax.swing.JTabbedPane();
        jTabbedPaneTables = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1Orders = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2Portfolio = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3Operations = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        JtableRss = new javax.swing.JTable();
        btnRssFeed = new javax.swing.JButton();
        cmxRssurls = new javax.swing.JComboBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuAcount = new javax.swing.JMenu();
        jmitAccLogin = new javax.swing.JMenuItem();
        jmitAccLogout = new javax.swing.JMenuItem();
        jmAccManagement = new javax.swing.JMenu();
        jmitAccMgCreate = new javax.swing.JMenuItem();
        jmitAccMgEdit = new javax.swing.JMenuItem();
        jmitAccMgDelete = new javax.swing.JMenuItem();
        jmExit = new javax.swing.JMenuItem();
        jMenuPortfolios = new javax.swing.JMenu();
        jmitPortSelect = new javax.swing.JMenuItem();
        jmitPortBuy = new javax.swing.JMenuItem();
        jmPortManagement = new javax.swing.JMenu();
        jmitPortMgCreate = new javax.swing.JMenuItem();
        jmitPortMgEdit = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItemAccountInvest = new javax.swing.JMenuItem();
        jMenuItemSelectedPortfInvest = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemSelectPortWeb = new javax.swing.JMenuItem();
        jMenuItemAllWeb = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuThemes = new javax.swing.JMenu();
        jrbAcryl = new javax.swing.JRadioButtonMenuItem();
        jrbAluminium = new javax.swing.JRadioButtonMenuItem();
        jrbHiFi = new javax.swing.JRadioButtonMenuItem();
        jrbLuna = new javax.swing.JRadioButtonMenuItem();
        jrbMcWin = new javax.swing.JRadioButtonMenuItem();
        jrbMint = new javax.swing.JRadioButtonMenuItem();
        jrbNoire = new javax.swing.JRadioButtonMenuItem();
        jrbSmart = new javax.swing.JRadioButtonMenuItem();
        jrbTexture = new javax.swing.JRadioButtonMenuItem();
        jMenuAbout = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Welcome!: CSI Interface.");
        setMinimumSize(new java.awt.Dimension(800, 500));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 204, 255), java.awt.Color.lightGray, new java.awt.Color(0, 153, 255), java.awt.Color.gray));
        jPanel1.setMaximumSize(new java.awt.Dimension(310, 380));
        jPanel1.setMinimumSize(new java.awt.Dimension(310, 380));
        jPanel1.setPreferredSize(new java.awt.Dimension(310, 380));

        cmb_market.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        cmb_market.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-select market-", "France_Paris_CAC40", "US_Nasdaq_NDX" }));
        cmb_market.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_marketItemStateChanged(evt);
            }
        });

        cmb_stocks.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        cmb_stocks.setMaximumSize(new java.awt.Dimension(291, 21));
        cmb_stocks.setMinimumSize(new java.awt.Dimension(291, 21));
        cmb_stocks.setPreferredSize(new java.awt.Dimension(291, 21));
        cmb_stocks.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_stocksItemStateChanged(evt);
            }
        });

        jLabel1.setText("Market index:");

        jLabel2.setText("Stock:");

        btnStockChart.setFont(new java.awt.Font("Verdana", 1, 15)); // NOI18N
        btnStockChart.setForeground(new java.awt.Color(0, 113, 198));
        btnStockChart.setBorder(null);
        btnStockChart.setBorderPainted(false);
        btnStockChart.setIconTextGap(2);
        btnStockChart.setMaximumSize(new java.awt.Dimension(291, 51));
        btnStockChart.setMinimumSize(new java.awt.Dimension(291, 51));
        btnStockChart.setPreferredSize(new java.awt.Dimension(291, 51));
        btnStockChart.setSelected(true);
        btnStockChart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockChartMouseClicked(evt);
            }
        });

        jLabel4.setText("Indicators:");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jCheckBoxMAC.setText("MACrossover");
        jCheckBoxMAC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMACActionPerformed(evt);
            }
        });

        jCheckBoxBB.setText("Bollinger Bands");
        jCheckBoxBB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxBBActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Vertical Axis Trace");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jCheckBox3.setText("Horizontal Axis Trace");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        cmb_hiddenSymbols.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jtfIndexValue.setEditable(false);
        jtfIndexValue.setBackground(new java.awt.Color(173, 214, 255));
        jtfIndexValue.setForeground(new java.awt.Color(0, 0, 102));
        jtfIndexValue.setMaximumSize(new java.awt.Dimension(75, 23));
        jtfIndexValue.setMinimumSize(new java.awt.Dimension(75, 23));
        jtfIndexValue.setPreferredSize(new java.awt.Dimension(75, 23));

        btndown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/down_arrow.png"))); // NOI18N
        btndown.setBorderPainted(false);
        btndown.setMaximumSize(new java.awt.Dimension(23, 23));
        btndown.setMinimumSize(new java.awt.Dimension(23, 23));
        btndown.setPreferredSize(new java.awt.Dimension(23, 23));

        btnup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/up_arrow.png"))); // NOI18N
        btnup.setBorderPainted(false);
        btnup.setMaximumSize(new java.awt.Dimension(23, 23));
        btnup.setMinimumSize(new java.awt.Dimension(23, 23));
        btnup.setPreferredSize(new java.awt.Dimension(23, 23));

        btnIndex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/indexChart.png"))); // NOI18N
        btnIndex.setBorderPainted(false);
        btnIndex.setFocusable(false);
        btnIndex.setMaximumSize(new java.awt.Dimension(54, 54));
        btnIndex.setMinimumSize(new java.awt.Dimension(54, 54));
        btnIndex.setPreferredSize(new java.awt.Dimension(54, 54));
        btnIndex.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnIndexMouseClicked(evt);
            }
        });

        jLabel3.setText("Tools:");

        btnacc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/1367292005_25.png"))); // NOI18N
        btnacc.setToolTipText("Edit Account");
        btnacc.setBorderPainted(false);
        btnacc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnacc.setMaximumSize(new java.awt.Dimension(24, 24));
        btnacc.setMinimumSize(new java.awt.Dimension(24, 24));
        btnacc.setPreferredSize(new java.awt.Dimension(24, 24));
        btnacc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnaccMouseClicked(evt);
            }
        });

        btnSelectPortf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/1367291994_8.png"))); // NOI18N
        btnSelectPortf.setToolTipText("Select Portfolio");
        btnSelectPortf.setBorderPainted(false);
        btnSelectPortf.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSelectPortf.setMaximumSize(new java.awt.Dimension(24, 24));
        btnSelectPortf.setMinimumSize(new java.awt.Dimension(24, 24));
        btnSelectPortf.setPreferredSize(new java.awt.Dimension(24, 24));
        btnSelectPortf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSelectPortfMouseClicked(evt);
            }
        });

        btneditPortf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/1367292009_31.png"))); // NOI18N
        btneditPortf.setBorderPainted(false);
        btneditPortf.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditPortf.setMaximumSize(new java.awt.Dimension(24, 24));
        btneditPortf.setMinimumSize(new java.awt.Dimension(24, 24));
        btneditPortf.setPreferredSize(new java.awt.Dimension(24, 24));
        btneditPortf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btneditPortfMouseClicked(evt);
            }
        });

        btnbuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/1367292046_27.png"))); // NOI18N
        btnbuy.setToolTipText("Buy Stock");
        btnbuy.setBorderPainted(false);
        btnbuy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnbuy.setMaximumSize(new java.awt.Dimension(24, 24));
        btnbuy.setMinimumSize(new java.awt.Dimension(24, 24));
        btnbuy.setPreferredSize(new java.awt.Dimension(24, 24));
        btnbuy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnbuyMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxBB)
                    .addComponent(jCheckBoxMAC)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox2)
                    .addComponent(jLabel3))
                .addGap(8, 8, 8))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnStockChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_stocks, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmb_hiddenSymbols, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btndown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(131, 131, 131))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(cmb_market, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtfIndexValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnacc, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSelectPortf, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btneditPortf, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnbuy, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jSeparator4)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnacc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectPortf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btneditPortf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_hiddenSymbols, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btndown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_market, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfIndexValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_stocks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnStockChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(9, 9, 9)
                                .addComponent(jCheckBoxMAC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBoxBB))
                            .addComponent(jSeparator2))
                        .addGap(14, 14, 14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox2)
                        .addGap(2, 2, 2)
                        .addComponent(jCheckBox3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        btndown.setVisible(false);
        btnup.setVisible(false);
        btnacc.setEnabled(false);
        btnSelectPortf.setEnabled(false);
        btneditPortf.setEnabled(false);
        btnbuy.setEnabled(false);

        jTabbedPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 204, 255), java.awt.Color.lightGray, new java.awt.Color(0, 153, 204), java.awt.Color.gray));
        jTabbedPane.setMaximumSize(new java.awt.Dimension(1006, 380));
        jTabbedPane.setMinimumSize(new java.awt.Dimension(980, 380));
        jTabbedPane.setPreferredSize(new java.awt.Dimension(1006, 380));

        jTabbedPaneTables.setBorder(jTabbedPane.getBorder());
        jTabbedPaneTables.setMaximumSize(new java.awt.Dimension(1256, 295));
        jTabbedPaneTables.setMinimumSize(new java.awt.Dimension(800, 295));
        jTabbedPaneTables.setPreferredSize(new java.awt.Dimension(1256, 295));

        jTable1Orders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Order Number", "Date", "Stock", "Quantity", "Price", "Potential Returns"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1Orders.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable1Orders.getTableHeader().setResizingAllowed(false);
        jTable1Orders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1OrdersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1Orders);

        jTabbedPaneTables.addTab("Opened Orders", jScrollPane1);

        jTable2Portfolio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Initial Value", "Value Today", "Yield", "Invested Money", "Returns", "Performance"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2Portfolio.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2Portfolio);

        jTabbedPaneTables.addTab("Selected Portfolio", jScrollPane2);

        jTable3Operations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Operation number", "Date", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3Operations.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable3Operations);

        jTabbedPaneTables.addTab("Operations", jScrollPane3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );

        JtableRss.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Date", "Title", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        JtableRss.setMaximumSize(new java.awt.Dimension(1110, 235));
        JtableRss.setMinimumSize(new java.awt.Dimension(1110, 235));
        JtableRss.setPreferredSize(new java.awt.Dimension(1110, 235));
        jScrollPane4.setViewportView(JtableRss);

        btnRssFeed.setText("Rss");
        btnRssFeed.setToolTipText("Get RSS feed");
        btnRssFeed.setIconTextGap(1);
        btnRssFeed.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnRssFeed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRssFeedMouseClicked(evt);
            }
        });

        cmxRssurls.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "France, Paris market", "Stocks", "Technical Analysis", "USA, New York market", "Yahoo, Finance" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmxRssurls, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRssFeed)
                .addGap(14, 14, 14)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmxRssurls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRssFeed)))
                .addContainerGap())
        );

        jTabbedPaneTables.addTab("", new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/rss.png")), jPanel2); // NOI18N

        jMenuAcount.setText("Accounts");

        jmitAccLogin.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jmitAccLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/open.png"))); // NOI18N
        jmitAccLogin.setMnemonic('n');
        jmitAccLogin.setText("Login");
        jmitAccLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmitAccLoginActionPerformed(evt);
            }
        });
        jMenuAcount.add(jmitAccLogin);

        jmitAccLogout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jmitAccLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/closed.png"))); // NOI18N
        jmitAccLogout.setMnemonic('t');
        jmitAccLogout.setText("Logout");
        jmitAccLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmitAccLogoutActionPerformed(evt);
            }
        });
        jMenuAcount.add(jmitAccLogout);

        jmAccManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/users.png"))); // NOI18N
        jmAccManagement.setMnemonic('m');
        jmAccManagement.setText("Management");

        jmitAccMgCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/add.png"))); // NOI18N
        jmitAccMgCreate.setMnemonic('c');
        jmitAccMgCreate.setText("Create");
        jmitAccMgCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmitAccMgCreateActionPerformed(evt);
            }
        });
        jmAccManagement.add(jmitAccMgCreate);

        jmitAccMgEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/settings.png"))); // NOI18N
        jmitAccMgEdit.setMnemonic('e');
        jmitAccMgEdit.setText("Edit");
        jmitAccMgEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmitAccMgEditActionPerformed(evt);
            }
        });
        jmAccManagement.add(jmitAccMgEdit);

        jmitAccMgDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/cross-24.png"))); // NOI18N
        jmitAccMgDelete.setMnemonic('d');
        jmitAccMgDelete.setText("Delete");
        jmitAccMgDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmitAccMgDeleteActionPerformed(evt);
            }
        });
        jmAccManagement.add(jmitAccMgDelete);

        jMenuAcount.add(jmAccManagement);

        jmExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jmExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/exit_delete_small.png"))); // NOI18N
        jmExit.setMnemonic('e');
        jmExit.setText("Exit");
        jmExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmExitActionPerformed(evt);
            }
        });
        jMenuAcount.add(jmExit);

        jMenuBar1.add(jMenuAcount);

        jMenuPortfolios.setText("Portfolios");

        jmitPortSelect.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jmitPortSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/portfolio.png"))); // NOI18N
        jmitPortSelect.setMnemonic('s');
        jmitPortSelect.setText("Select");
        jmitPortSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmitPortSelectActionPerformed(evt);
            }
        });
        jMenuPortfolios.add(jmitPortSelect);

        jmitPortBuy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jmitPortBuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/buy.png"))); // NOI18N
        jmitPortBuy.setMnemonic('b');
        jmitPortBuy.setText("Buy");
        jmitPortBuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmitPortBuyActionPerformed(evt);
            }
        });
        jMenuPortfolios.add(jmitPortBuy);

        jmPortManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/settings_gear_1.png"))); // NOI18N
        jmPortManagement.setMnemonic('m');
        jmPortManagement.setText("Management");

        jmitPortMgCreate.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jmitPortMgCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/add.png"))); // NOI18N
        jmitPortMgCreate.setMnemonic('c');
        jmitPortMgCreate.setText("Create");
        jmitPortMgCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmitPortMgCreateActionPerformed(evt);
            }
        });
        jmPortManagement.add(jmitPortMgCreate);

        jmitPortMgEdit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        jmitPortMgEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/settings.png"))); // NOI18N
        jmitPortMgEdit.setMnemonic('e');
        jmitPortMgEdit.setText("Edit");
        jmitPortMgEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmitPortMgEditActionPerformed(evt);
            }
        });
        jmPortManagement.add(jmitPortMgEdit);

        jMenuPortfolios.add(jmPortManagement);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/eye.png"))); // NOI18N
        jMenuItem1.setText("Update Performance");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuPortfolios.add(jMenuItem1);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/chart.png"))); // NOI18N
        jMenu1.setMnemonic('v');
        jMenu1.setText("View Statistics");

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/money_cash.png"))); // NOI18N
        jMenu3.setText("Invested Money");

        jMenuItemAccountInvest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/bar-chart.png"))); // NOI18N
        jMenuItemAccountInvest.setText("Account");
        jMenuItemAccountInvest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAccountInvestActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemAccountInvest);

        jMenuItemSelectedPortfInvest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/chart_pie.png"))); // NOI18N
        jMenuItemSelectedPortfInvest.setText("Selected Portfolio");
        jMenuItemSelectedPortfInvest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSelectedPortfInvestActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemSelectedPortfInvest);

        jMenu1.add(jMenu3);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/Spider_web.png"))); // NOI18N
        jMenu2.setText("Portfolio Spider Web");

        jMenuItemSelectPortWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/Spider_web.png"))); // NOI18N
        jMenuItemSelectPortWeb.setText("Selected Portfolio");
        jMenuItemSelectPortWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSelectPortWebActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemSelectPortWeb);

        jMenuItemAllWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/Spider_web.png"))); // NOI18N
        jMenuItemAllWeb.setText("All");
        jMenuItemAllWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAllWebActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemAllWeb);

        jMenu1.add(jMenu2);

        jMenuPortfolios.add(jMenu1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/pdf.png"))); // NOI18N
        jMenuItem2.setText("Portfolios PDF Report");
        jMenuItem2.setToolTipText("Generate PDF Report");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenuPortfolios.add(jMenuItem2);

        jMenuBar1.add(jMenuPortfolios);

        jMenuThemes.setMnemonic('t');
        jMenuThemes.setText("Themes");

        jrbAcryl.setText("Acryl");
        jrbAcryl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbAcrylActionPerformed(evt);
            }
        });
        jMenuThemes.add(jrbAcryl);

        jrbAluminium.setText("Aluminimu");
        jrbAluminium.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbAluminiumActionPerformed(evt);
            }
        });
        jMenuThemes.add(jrbAluminium);

        jrbHiFi.setText("HiFi");
        jrbHiFi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbHiFiActionPerformed(evt);
            }
        });
        jMenuThemes.add(jrbHiFi);

        jrbLuna.setText("Luna");
        jrbLuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbLunaActionPerformed(evt);
            }
        });
        jMenuThemes.add(jrbLuna);

        jrbMcWin.setText("McWin");
        jrbMcWin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbMcWinActionPerformed(evt);
            }
        });
        jMenuThemes.add(jrbMcWin);

        jrbMint.setText("Mint");
        jrbMint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbMintActionPerformed(evt);
            }
        });
        jMenuThemes.add(jrbMint);

        jrbNoire.setText("Noire");
        jrbNoire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbNoireActionPerformed(evt);
            }
        });
        jMenuThemes.add(jrbNoire);

        jrbSmart.setSelected(true);
        jrbSmart.setText("Smart");
        jrbSmart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbSmartActionPerformed(evt);
            }
        });
        jMenuThemes.add(jrbSmart);

        jrbTexture.setText("Texture");
        jrbTexture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbTextureActionPerformed(evt);
            }
        });
        jMenuThemes.add(jrbTexture);

        jMenuBar1.add(jMenuThemes);

        jMenuAbout.setText("About");
        jMenuAbout.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenuAboutMenuSelected(evt);
            }
        });
        jMenuBar1.add(jMenuAbout);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPaneTables, javax.swing.GroupLayout.DEFAULT_SIZE, 1320, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPaneTables, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//begin------------------------------------------------Theme Actions------------------------------------------------//
    private void jrbAcrylActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbAcrylActionPerformed
        if (jrbAcryl.isSelected()) {
            themeSet(1);
        } else {
            themeSet(11);
        }
    }//GEN-LAST:event_jrbAcrylActionPerformed

    private void jrbAluminiumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbAluminiumActionPerformed
        if (jrbAluminium.isSelected()) {
            themeSet(2);
        } else {
            themeSet(11);
        }
    }//GEN-LAST:event_jrbAluminiumActionPerformed

    private void jrbHiFiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbHiFiActionPerformed
        if (jrbHiFi.isSelected()) {
            themeSet(4);
        } else {
            themeSet(11);
        }
    }//GEN-LAST:event_jrbHiFiActionPerformed

    private void jrbLunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbLunaActionPerformed
        if (jrbLuna.isSelected()) {
            themeSet(5);
        } else {
            themeSet(11);
        }
    }//GEN-LAST:event_jrbLunaActionPerformed

    private void jrbMcWinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbMcWinActionPerformed
        if (jrbMcWin.isSelected()) {
            themeSet(6);
        } else {
            themeSet(11);
        }
    }//GEN-LAST:event_jrbMcWinActionPerformed

    private void jrbMintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbMintActionPerformed
        if (jrbMint.isSelected()) {
            themeSet(7);
        } else {
            themeSet(11);
        }
    }//GEN-LAST:event_jrbMintActionPerformed

    private void jrbNoireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbNoireActionPerformed
        if (jrbNoire.isSelected()) {
            themeSet(8);
        } else {
            themeSet(11);
        }
    }//GEN-LAST:event_jrbNoireActionPerformed

    private void jrbSmartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbSmartActionPerformed
        if (jrbSmart.isSelected()) {
            themeSet(9);
        } else {
            themeSet(11);
        }
    }//GEN-LAST:event_jrbSmartActionPerformed

    private void jrbTextureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbTextureActionPerformed
        if (jrbTexture.isSelected()) {
            themeSet(10);
        } else {
            themeSet(11);
        }
    }//GEN-LAST:event_jrbTextureActionPerformed
//------------------------------------------------Theme Actions------------------------------------------------end//

    private void indicatorsJcheckBoxDisable() {

        if (jCheckBoxBB.isSelected()) {
            BandsOfBolinger.setIndicator(false);
//        } else if (jCheckBoxCCI.isSelected()) {
//            CommodityChannelIndex.setIndicator(false);
        } else if (jCheckBoxMAC.isSelected()) {
            MACrossover.setIndicator(false);
        }
        jCheckBoxBB.setSelected(false);
        jCheckBoxMAC.setSelected(false);
//        jCheckBoxCCI.setSelected(false);

        jCheckBoxBB.setEnabled(true);
        jCheckBoxMAC.setEnabled(true);
//        jCheckBoxCCI.setEnabled(true);
    }

    private static void dateManagerForStockChart() {
        dateToDay = new Date();
        dateYearAgo = new Date();
        dateYearAgo.setYear(dateToDay.getYear() - 1);

        // yahoo specifications
        dateToDay.setMonth(dateToDay.getMonth() - 1);
        dateYearAgo.setMonth(dateYearAgo.getMonth() - 1);
        // yahoo specifications

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String date = dateFormat.format(dateToDay);

        StringTokenizer st = new StringTokenizer(date, "-");

        date = dateFormat.format(dateYearAgo);

        StringTokenizer st2 = new StringTokenizer(date, "-");

        StockChart.getMe().setStockPriceDataRequirement(
                cmb_hiddenSymbols.getSelectedItem().toString(),
                st2.nextToken(), st2.nextToken(), st2.nextToken(),
                st.nextToken(), st.nextToken(), st.nextToken());
    }

    private static void dateManagerForIndexChart() {
        dateToDay = new Date();
        dateYearAgo = new Date();
        dateYearAgo.setYear(dateToDay.getYear() - 1);

        // yahoo specifications
        dateToDay.setMonth(dateToDay.getMonth() - 1);
        dateYearAgo.setMonth(dateYearAgo.getMonth() - 1);
        // yahoo specifications

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String date = dateFormat.format(dateToDay);

        StringTokenizer st = new StringTokenizer(date, "-");

        date = dateFormat.format(dateYearAgo);

        StringTokenizer st2 = new StringTokenizer(date, "-");
        IMarket iMarket = new MarketDAO();
        Market market = iMarket.getMarketById(cmb_market.getSelectedItem().toString());
        StockChart.getMe().setStockPriceDataRequirement(
                market.getMarketSymbol(),
                st2.nextToken(), st2.nextToken(), st2.nextToken(),
                st.nextToken(), st.nextToken(), st.nextToken());
    }

    private void jmitAccLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmitAccLoginActionPerformed
        Authenticate.getMe().setVisible(true);
    }//GEN-LAST:event_jmitAccLoginActionPerformed

    private void jmitAccLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmitAccLogoutActionPerformed
        loggedInvestor = null;
        logged = 2;

        enabler(false);

        jTable2Portfolio.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null, null}
                },
                new String[]{
                    "ID", "Initial Value", "Value Today", "Yield", "Invested Money", "Returns", "Performance"
                }) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        jTable1Orders.setModel(new javax.swing.table.DefaultTableModel(
                new Object[1][1], new String[]{"Order Number", "Date",
                    "Stock", "Quantity", "Price", "Potential Returns"}));

        jTable3Operations.setModel(new javax.swing.table.DefaultTableModel(
                new Object[1][1], new String[]{"Operation number", "Portfolio", "Date", "Amount"}));

        jTabbedPaneTables.revalidate();
    }//GEN-LAST:event_jmitAccLogoutActionPerformed

    private void jmitAccMgCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmitAccMgCreateActionPerformed
        CreateAccount.getMe().setVisible(true);
    }//GEN-LAST:event_jmitAccMgCreateActionPerformed

    private void jmitAccMgEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmitAccMgEditActionPerformed
        AccountModification.getMe().setAlwaysOnTop(true);
        AccountModification.getMe().setVisible(true);
    }//GEN-LAST:event_jmitAccMgEditActionPerformed

    private void jmitAccMgDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmitAccMgDeleteActionPerformed
        AccoutRemove.getMe().setAlwaysOnTop(true);
        AccoutRemove.getMe().setVisible(true);

    }//GEN-LAST:event_jmitAccMgDeleteActionPerformed

    private void jmitPortSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmitPortSelectActionPerformed
        PortfolioSelection.getMe().setAlwaysOnTop(true);
        PortfolioSelection.getMe().setVisible(true);
    }//GEN-LAST:event_jmitPortSelectActionPerformed

    private void jmitPortBuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmitPortBuyActionPerformed

        if (buyBoolean) {
            OrderBuy.getMe().setVisible(true);
        }
    }//GEN-LAST:event_jmitPortBuyActionPerformed

    private void jmitPortMgCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmitPortMgCreateActionPerformed

        PortfolioCreation.getMe().setAlwaysOnTop(true);
        PortfolioCreation.getMe().setVisible(true);

    }//GEN-LAST:event_jmitPortMgCreateActionPerformed

    private void jmitPortMgEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmitPortMgEditActionPerformed
        if (getLoggedInvestor().getAccount().getPortfolio() != null) {
            PortfolioEdition.getMe().setAlwaysOnTop(false);
            PortfolioEdition.getMe().setVisible(true);
        }
    }//GEN-LAST:event_jmitPortMgEditActionPerformed

    private void jMenuAboutMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenuAboutMenuSelected
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuAboutMenuSelected

    private void jmExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmExitActionPerformed
        String logged = getLoggedInvestor() == null ? "!" : "Mr. " + getLoggedInvestor().getLastName();
        JOptionPane.showMessageDialog(this, "Till next Time " + logged, "Good Bye!", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }//GEN-LAST:event_jmExitActionPerformed

    private void jTable1OrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1OrdersMouseClicked
        if (getLoggedInvestor() != null) {
            if (getLoggedInvestor().getAccount().getPortfolio() != null) {
                if (!getLoggedInvestor().getAccount().getPortfolio()
                        .getOrders().isEmpty()) {
                    int row = jTable1Orders.getSelectedRow();
                    Long id = (Long) jTable1Orders.getValueAt(row, 0);

                    IOrderStock iOrderStock = new OrderStockDAO();
                    OrderStock orderStock = iOrderStock
                            .getOrderById(id);
                    OrderSell.getMe(orderStock).setVisible(true);

                }
            }
        }
    }//GEN-LAST:event_jTable1OrdersMouseClicked

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        if (chartPanel != null) {
            if (jCheckBox3.isSelected()) {
                chartPanel.setHorizontalAxisTrace(true);
            } else {
                chartPanel.setHorizontalAxisTrace(false);
            }
            chartPanel.repaint();
        } else {
            jCheckBox3.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        if (chartPanel != null) {
            if (jCheckBox2.isSelected()) {
                chartPanel.setVerticalAxisTrace(true);
            } else {
                chartPanel.setVerticalAxisTrace(false);
            }
            chartPanel.repaint();
        } else {
            jCheckBox2.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBoxBBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxBBActionPerformed
        if (btnStockChart.getText() != "") {
            if (chart != null) {
                boolean b = jCheckBoxBB.isSelected();
                BandsOfBolinger.setIndicator(b);
                jCheckBoxMAC.setEnabled(!b);
                //                jCheckBoxCCI.setEnabled(!b);
                chartPanel.revalidate();
            } else {
                jCheckBoxBB.setSelected(!jCheckBoxBB.isSelected());
            }
        } else {
            jCheckBoxBB.setSelected(!jCheckBoxBB.isSelected());
        }
    }//GEN-LAST:event_jCheckBoxBBActionPerformed

    private void jCheckBoxMACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMACActionPerformed
        if (btnStockChart.getText() != "") {
            if (chart != null) {
                boolean b = jCheckBoxMAC.isSelected();
                MACrossover.setIndicator(b);
                jCheckBoxBB.setEnabled(!b);
                //                jCheckBoxCCI.setEnabled(!b);
                chartPanel.revalidate();
            } else {
                jCheckBoxMAC.setSelected(!jCheckBoxMAC.isSelected());
            }
        } else {
            jCheckBoxMAC.setSelected(!jCheckBoxMAC.isSelected());
        }
    }//GEN-LAST:event_jCheckBoxMACActionPerformed

    private void btnStockChartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockChartMouseClicked
        if (!btnStockChart.getText().equals("")) {
            indicatorsJcheckBoxDisable();
            stock = new Stock();
            dateManagerForStockChart();
            StockChart.getMe().setXyDataSetFromYahoo();
            StockChart.getMe().setMainPlot();
            stock.setSymbol(cmb_hiddenSymbols.getSelectedItem().toString());
            stock.setName(cmb_stocks.getSelectedItem().toString());
            stock.setCurrentPrice(StockChart.getClose());
            stock.setBuyPrice(stock.getCurrentPrice());
            if (stock.getCurrentPrice() > 0) {
                buyBoolean = true;
            }
            if (gui.getMe().getLoggedInvestor() != null) {
                if (OrderBuy.getMe().isActive()) {
                    OrderBuy.getMe().refresher();
                }
            }

            if (chart != null) {
                jTabbedPane.removeAll();
            }
            chart = new JFreeChart(btnStockChart.getText(), null, StockChart
                    .getMe().getMainPlot(), true);
            chartPanel = new ChartPanel(chart);

            chartPanel.setName(btnStockChart.getText());
            chartPanel.setPreferredSize(jTabbedPane.getSize());
            jTabbedPane.add(chartPanel);
            Cursor cursor = new Cursor(1);
            chartPanel.setCursor(cursor);
            chartPanel.revalidate();
        }
    }//GEN-LAST:event_btnStockChartMouseClicked

    private void cmb_stocksItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_stocksItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            btnStockChart.setText(cmb_stocks.getSelectedItem().toString());
            cmb_hiddenSymbols.setSelectedIndex(cmb_stocks.getSelectedIndex());
        }
    }//GEN-LAST:event_cmb_stocksItemStateChanged

    private void cmb_marketItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_marketItemStateChanged
        buyBoolean = false;
        if (getCmb_market().getSelectedIndex() == 0) {
            cmb_stocks.removeAllItems();
            cmb_hiddenSymbols.removeAll();
            btnStockChart.setText("");
            btndown.setVisible(false);
            btnup.setVisible(false);
        } else {
            String market = getCmb_market().getSelectedItem().toString();
            IMarket iMarket = new MarketDAO();
            Market market1 = iMarket.getMarketById(market);
            market1.CalulateGetIndice();
            jtfIndexValue.setText(market1.getIndice() + "");
            market1 = iMarket.getMarketById(market);
            if (market1.getOldIndice() > market1.getIndice()) {
                btndown.setVisible(true);
                btnup.setVisible(false);
            } else {
                btnup.setVisible(true);
                btndown.setVisible(false);
            }
            cmbUpdate(MarketStocks.getMarketStoks(market),
                    MarketStocks.getMarketSymbols(market));
        }
    }//GEN-LAST:event_cmb_marketItemStateChanged

    private void btnIndexMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIndexMouseClicked
        if (cmb_market.getSelectedIndex() != 0) {
            indicatorsJcheckBoxDisable();
            dateManagerForIndexChart();
            StockChart.getMe().setXyDataSetFromYahoo();
            StockChart.getMe().setMainPlot();
//            stock.setSymbol(cmb_hiddenSymbols.getSelectedItem().toString());
//            stock.setName(cmb_stocks.getSelectedItem().toString());
//            stock.setCurrentPrice(StockChart.getClose());
//            stock.setBuyPrice(stock.getCurrentPrice());

            if (chart != null) {
                jTabbedPane.removeAll();
            }
            chart = new JFreeChart(cmb_market.getSelectedItem().toString(), null, StockChart
                    .getMe().getMainPlot(), true);
            chartPanel = new ChartPanel(chart);
            chartPanel.setName(cmb_market.getSelectedItem().toString());
            chartPanel.setPreferredSize(jTabbedPane.getSize());
            jTabbedPane.add(chartPanel);
            Cursor cursor = new Cursor(1);
            chartPanel.setCursor(cursor);
            chartPanel.revalidate();
        }
    }//GEN-LAST:event_btnIndexMouseClicked

    private void btnaccMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnaccMouseClicked
        AccountModification.getMe().setVisible(true);
    }//GEN-LAST:event_btnaccMouseClicked

    private void btnSelectPortfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSelectPortfMouseClicked
        PortfolioSelection.getMe().setVisible(true);
    }//GEN-LAST:event_btnSelectPortfMouseClicked

    private void btneditPortfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btneditPortfMouseClicked
        PortfolioEdition.getMe().setVisible(true);
    }//GEN-LAST:event_btneditPortfMouseClicked

    private void btnbuyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnbuyMouseClicked
        if (buyBoolean) {
            OrderBuy.getMe().setVisible(true);
//            Buy.getMe().setAlwaysOnTop(true);
//            Buy.getMe().setVisible(true);
        }
    }//GEN-LAST:event_btnbuyMouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (getLoggedInvestor().getAccount().getPortfolio() != null) {
            Portfolio portfolio = loggedInvestor.getAccount().getPortfolio();
            JOptionPane.showMessageDialog(this, "Updating portfolio performance will take some time downloading stocks data...", "Begin Update", JOptionPane.INFORMATION_MESSAGE, ImageHelper.loadImage("/com/gui/img/eyeBleu.png"));
            PortfolioPerformanceUtilities.calculatePerformance(portfolio);
            UpdateJTable(true, true, false, false);
        } else {
            JOptionPane.showMessageDialog(this, "Please Select a portfolio first", "Cannot Update!", JOptionPane.WARNING_MESSAGE, ImageHelper.loadImage("/com/gui/img/eyeBleu.png"));
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItemAccountInvestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAccountInvestActionPerformed
        CylinderCharts.getMe(loggedInvestor.getAccount().getId()).setVisible(true);
    }//GEN-LAST:event_jMenuItemAccountInvestActionPerformed

    private void jMenuItemSelectedPortfInvestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSelectedPortfInvestActionPerformed
        PieChart.getMe(loggedInvestor.getAccount().getPortfolio().getId()).setVisible(true);
    }//GEN-LAST:event_jMenuItemSelectedPortfInvestActionPerformed

    private void jMenuItemSelectPortWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSelectPortWebActionPerformed
        PortfolioSpiderWebChart.getMe(loggedInvestor.getAccount().getPortfolio().getId(), false).setVisible(true);
    }//GEN-LAST:event_jMenuItemSelectPortWebActionPerformed

    private void jMenuItemAllWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAllWebActionPerformed
        PortfolioSpiderWebChart.getMe(loggedInvestor.getAccount().getId(), true).setVisible(true);
    }//GEN-LAST:event_jMenuItemAllWebActionPerformed

    private void btnRssFeedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRssFeedMouseClicked
        int urlIndex = cmxRssurls.getSelectedIndex();
        RssFeedParser parser = null;
        switch (urlIndex) {
            case 0:
                parser = new RssFeedParser(RssFeedParser.Frmarket);
                break;
            case 1:
                parser = new RssFeedParser(RssFeedParser.NYmarket);
                break;
            case 2:
                parser = new RssFeedParser(RssFeedParser.stocks);
                break;
            case 3:
                parser = new RssFeedParser(RssFeedParser.techAnalysis);
                break;
            case 4:
                parser = new RssFeedParser(RssFeedParser.yahooFin);
                break;
            default:
                break;
        }
        parser.parse();
        RssFeed feed = parser.getFeed();
        RssTableUpdate(feed);
    }//GEN-LAST:event_btnRssFeedMouseClicked

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        PDFReportGen.getReport(loggedInvestor.getAccount().getId());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    public static void RssTableUpdate(RssFeed feed) {

        TableModel tableModel = new DefaultTableModel(
                new Object[feed.getItems().size()][feed.getItems().size()],
                new String[]{
                    "Date", "Title", "Description"
                }) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        for (int i = 0; i < feed.getItems().size(); i++) {
            tableModel.setValueAt(feed.getItems().get(i).pubDate, i, 0);
            tableModel.setValueAt(feed.getItems().get(i).title, i, 1);
            tableModel.setValueAt("<html>" + feed.getItems().get(i).description + "</html>", i, 2);
        }
        JtableRss.setModel(tableModel);
    }

    public static void enabler(boolean b) {
        jmitAccLogin.setEnabled(!b);
        jmitAccLogout.setEnabled(b);
        jmitAccMgDelete.setEnabled(b);
        jmitAccMgEdit.setEnabled(b);
        jMenuPortfolios.setEnabled(b);
        btnacc.setEnabled(b);
        if (loggedInvestor != null) {
            if (loggedInvestor.getAccount().getPortfolio() != null) {
                cmb_market.setEnabled(!b);
                btnbuy.setEnabled(b);
                btnSelectPortf.setEnabled(b);
                btneditPortf.setEnabled(b);

            } else {
                cmb_market.setEnabled(true);
                btnbuy.setEnabled(!b);
                btnSelectPortf.setEnabled(!b);
                btneditPortf.setEnabled(!b);
            }
        } else {
            cmb_market.setEnabled(true);
            btnbuy.setEnabled(false);
            btnSelectPortf.setEnabled(false);
            btneditPortf.setEnabled(false);
        }

    }

    private static void cmbUpdate(Vector<String> stocks, Vector<String> symbols) {
        cmb_stocks.removeAllItems();
        cmb_hiddenSymbols.removeAllItems();
        Iterator<String> it = stocks.iterator();
        Iterator<String> it2 = symbols.iterator();
        while ((it.hasNext()) && (it2.hasNext())) {
            cmb_hiddenSymbols.addItem(it2.next());
            cmb_stocks.addItem(it.next());
        }
    }

    public static void JTableOrdersUpdater(boolean enabled) {

        if (enabled) {
            // *********************************************************Orders Table
            IOrderStock iOrderStock = new OrderStockDAO();
            List<OrderStock> list = iOrderStock.listOrderByPortfolio(loggedInvestor
                    .getAccount().getSelectedPortfolio());

            Iterator<OrderStock> iterator = list.iterator();
            TableModel tableModel = new javax.swing.table.DefaultTableModel(
                    new Object[list.size()][list.size()], new String[]{
                        "Order Number", "Date", "Stock", "Quantity", "Price",
                        "Potential Returns"}) {
                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
            int j = 0;

            while (iterator.hasNext()) {
                OrderStock orderStock = iterator.next();
                tableModel.setValueAt(orderStock.getOrderID(), j, 0);
                // System.out.println(orderStock.getOrderId());
                tableModel.setValueAt(orderStock.getDateFirst(), j, 1);
                // System.out.println(orderStock.getDate());
                tableModel.setValueAt(orderStock.getStock().getName(), j, 2);
                // System.out.println(orderStock.getStock().getName());
                tableModel.setValueAt(orderStock.getStocksNumber(), j, 3);
                // System.out.println(orderStock.getStocksNumber());
                tableModel.setValueAt(
                        "$" + OrderStock.displaymoney(orderStock.getPrice()), j, 4);
                orderStock.calculateEarnings();
                tableModel.setValueAt(
                        "$" + OrderStock.displaymoney(orderStock.getPotentialEarnings()), j,
                        5);
                // System.out.println(orderStock.getReturns());
                j++;
            }
            jTable1Orders.setModel(tableModel);
            jTable1Orders.setEditingColumn(5);
            jTable1Orders.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            jTable1Orders.getTableHeader().setResizingAllowed(false);
            // *********************************************************Orders Table
        }
    }

    public static void jTablePortfolioUpdater(boolean enabled) {
        if (enabled) {
            // *****************************************************Portfolios Table
            TableModel tableModel2 = jTable2Portfolio.getModel();

            Portfolio portfolio = loggedInvestor.getAccount().getPortfolio();
            PortfolioPerformanceUtilities.calculatePerformance(portfolio);


            tableModel2.setValueAt(portfolio.getId(), 0, 0);
            tableModel2.setValueAt("$" + portfolio.displayMoney(portfolio.getInitialMoney()), 0, 1);
            tableModel2.setValueAt("$" + portfolio.displayMoney(portfolio.getPortfolioToDayValue()), 0, 2);
            tableModel2.setValueAt(roundDBL(portfolio.getPortfolioReturn()), 0, 3);
            tableModel2.setValueAt(portfolio.displayMoney(portfolio.getInvestedMoney()), 0, 4);
            tableModel2.setValueAt(portfolio.displayMoney(portfolio.getPotentialEarnings()), 0, 5);
            tableModel2.setValueAt(roundDBL(portfolio.getPortfolioPerformance()), 0, 6);

            jTable2Portfolio.setModel(tableModel2);
            jTable2Portfolio.setEnabled(false);
            // *****************************************************Portfolios Table
        }
    }

    public static void JTableOperationsUpdater(boolean enabled) {
        if (enabled) {
            // *****************************************************Operations Table
            Portfolio portfolio = loggedInvestor.getAccount().getPortfolio();
            List<PortfolioOperation> operations = (List<PortfolioOperation>) portfolio.getOperations();
            TableModel tableModel1 = new DefaultTableModel(
                    new Object[operations.size()][operations.size()], new String[]{
                        "Operation number", "Date", "Amount"
                    });
            Iterator<PortfolioOperation> iterator2 = operations.iterator();
            int j = 0;

            while (iterator2.hasNext()) {
                PortfolioOperation operation = iterator2.next();
                tableModel1.setValueAt(operation.getId(), j, 0);
                // System.out.println(orderStock.getOrderId());
                tableModel1.setValueAt(operation.getOperationDate().toLocaleString(), j, 1);
                // System.out.println(orderStock.getDate());
                tableModel1.setValueAt(operation.getAmount(), j, 2);
                j++;
            }
            jTable3Operations.setModel(tableModel1);
            // *****************************************************Operations Table
        }
    }

    public static void JcomboboxMarketsUpdater(boolean enabled) {
        if (enabled) {
            String selectedPortfolio_Market = loggedInvestor.getAccount().getPortfolio().getMarket().getMarketId();

            for (int i = 0; i < getCmb_market().getItemCount(); i++) {
                if (getCmb_market().getItemAt(i).equals(selectedPortfolio_Market)) {
                    getCmb_market().setSelectedIndex(i);
                    getCmb_market().setEnabled(false);
                    cmbUpdate(MarketStocks.getMarketStoks(selectedPortfolio_Market),
                            MarketStocks.getMarketSymbols(selectedPortfolio_Market));
                    break;
                }
            }
        }
    }

    public static void UpdateJTable(boolean portfolio, boolean orders, boolean operations, boolean marketList) {
        enabler(true);
        JTableOperationsUpdater(operations);
        JTableOrdersUpdater(orders);
        jTablePortfolioUpdater(portfolio);
        JcomboboxMarketsUpdater(marketList);
        jTabbedPaneTables.revalidate();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    private void showApp() {
        setIconImage(ImageHelper.loadImage("/com/gui/img/CSI.png").getImage());
        setBounds(appBounds);
        setVisible(true);
    }

    private static class RoundedBorder implements Border {

        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    public static double roundDBL(double dollars) {
        int decimalPlace = 4;
        BigDecimal bd = new BigDecimal(dollars);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_UP);

        return (bd.doubleValue());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTable JtableRss;
    private javax.swing.JButton btnIndex;
    private static javax.swing.JButton btnRssFeed;
    private static javax.swing.JButton btnSelectPortf;
    private javax.swing.JButton btnStockChart;
    private static javax.swing.JButton btnacc;
    private static javax.swing.JButton btnbuy;
    private javax.swing.JButton btndown;
    private static javax.swing.JButton btneditPortf;
    private javax.swing.JButton btnup;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private static javax.swing.JComboBox cmb_hiddenSymbols;
    private static javax.swing.JComboBox cmb_market;
    private static javax.swing.JComboBox cmb_stocks;
    private static javax.swing.JComboBox cmxRssurls;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBoxBB;
    private javax.swing.JCheckBox jCheckBoxMAC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private static javax.swing.JMenu jMenu1;
    private static javax.swing.JMenu jMenu2;
    private static javax.swing.JMenu jMenu3;
    private static javax.swing.JMenu jMenuAbout;
    private static javax.swing.JMenu jMenuAcount;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private static javax.swing.JMenuItem jMenuItemAccountInvest;
    private static javax.swing.JMenuItem jMenuItemAllWeb;
    private static javax.swing.JMenuItem jMenuItemSelectPortWeb;
    private static javax.swing.JMenuItem jMenuItemSelectedPortfInvest;
    private static javax.swing.JMenu jMenuPortfolios;
    private static javax.swing.JMenu jMenuThemes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private static javax.swing.JTabbedPane jTabbedPane;
    public static javax.swing.JTabbedPane jTabbedPaneTables;
    private static javax.swing.JTable jTable1Orders;
    private static javax.swing.JTable jTable2Portfolio;
    private static javax.swing.JTable jTable3Operations;
    private static javax.swing.JMenu jmAccManagement;
    private javax.swing.JMenuItem jmExit;
    private static javax.swing.JMenu jmPortManagement;
    private static javax.swing.JMenuItem jmitAccLogin;
    private static javax.swing.JMenuItem jmitAccLogout;
    private static javax.swing.JMenuItem jmitAccMgCreate;
    private static javax.swing.JMenuItem jmitAccMgDelete;
    private static javax.swing.JMenuItem jmitAccMgEdit;
    private javax.swing.JMenuItem jmitPortBuy;
    private static javax.swing.JMenuItem jmitPortMgCreate;
    private static javax.swing.JMenuItem jmitPortMgEdit;
    private static javax.swing.JMenuItem jmitPortSelect;
    private javax.swing.JRadioButtonMenuItem jrbAcryl;
    private javax.swing.JRadioButtonMenuItem jrbAluminium;
    private javax.swing.JRadioButtonMenuItem jrbHiFi;
    private javax.swing.JRadioButtonMenuItem jrbLuna;
    private javax.swing.JRadioButtonMenuItem jrbMcWin;
    private javax.swing.JRadioButtonMenuItem jrbMint;
    private javax.swing.JRadioButtonMenuItem jrbNoire;
    private javax.swing.JRadioButtonMenuItem jrbSmart;
    private javax.swing.JRadioButtonMenuItem jrbTexture;
    private javax.swing.JTextField jtfIndexValue;
    // End of variables declaration//GEN-END:variables
}
