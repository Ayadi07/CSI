/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.businessGUI.portfolioManagement.gui;

import com.PortfolioManager.domain.dao.IAccountDAO;
import com.PortfolioManager.domain.dao.IPortfolioDAO;
import com.PortfolioManager.domain.entities.Account;
import com.PortfolioManager.domain.entities.PortfolioOperation;
import com.PortfolioManager.domain.entities.Portfolio;
import com.PortfolioManager.domain.impl.AccountDAO;
import com.PortfolioManager.domain.impl.PortfolioDAO;
import com.PortfolioManager.businessGUI.portfolioManagement.performance.data.PortfolioPerformanceUtilities;
import com.gui.mainGUI.GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Ahmed
 */
public class PortfolioEdition extends javax.swing.JFrame {

    private static PortfolioEdition portfolioSelection;
    private static MaskFormatter moneyMask;
    private static IPortfolioDAO portfolioDAO;
    private static IAccountDAO iAccountDAO;
//    private static  IOperation
    private static Portfolio portfolio;
    private static Account account;
    private static Color c1;
    private static Color c2;
    private static Color c3;
    private static boolean nameError;
    private static boolean moneyError;

    public static PortfolioEdition getMe() {
        if (portfolioSelection == null) {
            portfolioSelection = new PortfolioEdition();
        }
        reset();
        return portfolioSelection;
    }

    /**
     * Creates new form PortfolioCreation
     */
    private PortfolioEdition() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
                / 2 - this.getSize().height / 2);
        buttonGroup1.add(jrbCredit);
        buttonGroup1.add(jrbDebit);
        init();
        portfolioDAO = new PortfolioDAO();
        iAccountDAO = new AccountDAO();
        c1 = jtfPortName.getBackground();
        c2 = jtfPortName.getForeground();
        c3 = jtfMoneyTransfer.getBackground();
        //
        jtfMoneyTransfer.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabel(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabel(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabel(e);
            }

            private void updateLabel(DocumentEvent e) {

                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        double value = moneyReader(jtfMoneyTransfer.getText());
                        double portLiq = portfolio.getLiquidMoney();
                        double accLiqui = account.getMoney();
                        double diff;
                        if (jrbCredit.isSelected()) {
                            if (value <= accLiqui) {
                                diff = accLiqui - value;
                                jtfAccountMoney.setText(moneyWriter(diff));
                                diff = portLiq + value;
                                jtfLiquidMoney.setText(moneyWriter(diff));
                                jtfLiquidMoney.setBackground(c3);
                                moneyError = false;
                            } else {
                                jtfLiquidMoney.setBackground(new Color(255, 0, 0));
                                moneyError = true;
                            }
                        } else if (jrbDebit.isSelected()) {
                            if (value <= portfolio.getLiquidMoney()) {
                                diff = accLiqui + value;
                                jtfAccountMoney.setText(moneyWriter(diff));
                                diff = portLiq - value;
                                jtfLiquidMoney.setText(moneyWriter(diff));
                                jtfLiquidMoney.setBackground(c3);
                                moneyError = false;
                            } else {
                                jtfLiquidMoney.setBackground(new Color(255, 0, 0));
                                moneyError = true;
                            }
                        }
                    }
                });
            }
        });
        //
    }

    public static void reset() {
        jtfLiquidMoney.setText("");
        jtfPortName.setText("");
        jtfPortName.setBackground(c1);
        jtfPortName.setForeground(c2);
        jtfLiquidMoney.setBackground(c3);
        nameError = false;
        moneyError = false;
        account = GUI.getMe().getLoggedInvestor().getAccount();
        portfolio = GUI.getMe().getLoggedInvestor().getAccount().getPortfolio();
        jtfAccountMoney.setText(account.getMoney() + "");
        jtfLiquidMoney.setText(portfolio.getLiquidMoney() + "");
    }

    public static void init() {

        reset();
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
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtfPortName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtfLiquidMoney = new javax.swing.JTextField();
        jtfAccountMoney = new javax.swing.JTextField();
        jtfAccountMoney.setEditable(false);
        jLabel4 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        btnCommit = new javax.swing.JButton();
        btnQuit = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        try {             moneyMask = new MaskFormatter("###,###.##");             moneyMask.setPlaceholderCharacter('$');         } catch (ParseException e) {             e.printStackTrace();         }
        jtfMoneyTransfer =  new javax.swing.JFormattedTextField(moneyMask);
        jLabel7 = new javax.swing.JLabel();
        jrbCredit = new javax.swing.JRadioButton();
        jrbDebit = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Portfolio Edition");
        setAlwaysOnTop(true);
        setMinimumSize(new java.awt.Dimension(305, 344));
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Edit Your Portfolio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 11))); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(285, 322));
        jPanel1.setMinimumSize(new java.awt.Dimension(285, 322));

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jLabel2.setText("Portfolio Name:");

        jtfPortName.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jtfPortName.setMaximumSize(new java.awt.Dimension(218, 20));
        jtfPortName.setMinimumSize(new java.awt.Dimension(218, 20));
        jtfPortName.setPreferredSize(new java.awt.Dimension(218, 20));
        jtfPortName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfPortNameFocusLost(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Liquid Money:");

        jtfLiquidMoney.setEditable(false);
        jtfLiquidMoney.setBackground(new java.awt.Color(255, 255, 255));
        jtfLiquidMoney.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jtfLiquidMoney.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtfLiquidMoney.setMaximumSize(new java.awt.Dimension(100, 26));
        jtfLiquidMoney.setMinimumSize(new java.awt.Dimension(100, 26));
        jtfLiquidMoney.setPreferredSize(new java.awt.Dimension(100, 26));

        jtfAccountMoney.setEditable(false);
        jtfAccountMoney.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jtfAccountMoney.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtfAccountMoney.setMaximumSize(new java.awt.Dimension(100, 26));
        jtfAccountMoney.setMinimumSize(new java.awt.Dimension(100, 26));
        jtfAccountMoney.setPreferredSize(new java.awt.Dimension(100, 26));

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Account Balance:");

        btnReset.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/refresh.png"))); // NOI18N
        btnReset.setMnemonic('r');
        btnReset.setToolTipText("Reset");
        btnReset.setMaximumSize(new java.awt.Dimension(67, 23));
        btnReset.setMinimumSize(new java.awt.Dimension(67, 23));
        btnReset.setPreferredSize(new java.awt.Dimension(67, 23));
        btnReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResetMouseClicked(evt);
            }
        });

        btnCommit.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnCommit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/yes.png"))); // NOI18N
        btnCommit.setMnemonic('e');
        btnCommit.setToolTipText("Edit");
        btnCommit.setMaximumSize(new java.awt.Dimension(67, 23));
        btnCommit.setMinimumSize(new java.awt.Dimension(67, 23));
        btnCommit.setPreferredSize(new java.awt.Dimension(67, 23));
        btnCommit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCommitMouseClicked(evt);
            }
        });
        btnCommit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCommitKeyPressed(evt);
            }
        });

        btnQuit.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnQuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/exit_delete.png"))); // NOI18N
        btnQuit.setMnemonic('q');
        btnQuit.setToolTipText("Quit");
        btnQuit.setMaximumSize(new java.awt.Dimension(67, 23));
        btnQuit.setMinimumSize(new java.awt.Dimension(67, 23));
        btnQuit.setPreferredSize(new java.awt.Dimension(67, 23));
        btnQuit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnQuitMouseClicked(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("$");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("$");

        jtfMoneyTransfer.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jtfMoneyTransfer.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtfMoneyTransfer.setMaximumSize(new java.awt.Dimension(100, 26));
        jtfMoneyTransfer.setMinimumSize(new java.awt.Dimension(100, 26));
        jtfMoneyTransfer.setPreferredSize(new java.awt.Dimension(100, 26));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("$");

        jrbCredit.setMnemonic('d');
        jrbCredit.setText("Diposit");
        jrbCredit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jrbCreditItemStateChanged(evt);
            }
        });

        jrbDebit.setMnemonic('w');
        jrbDebit.setText("Withdraw");
        jrbDebit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jrbDebitItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jtfPortName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jrbCredit)
                            .addComponent(jtfMoneyTransfer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jrbDebit)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtfLiquidMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtfAccountMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnCommit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)))
                        .addComponent(btnQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator3)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfPortName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jrbCredit))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfLiquidMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jtfMoneyTransfer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbDebit)
                .addGap(9, 9, 9)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfAccountMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCommit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseClicked
        reset();
    }//GEN-LAST:event_btnResetMouseClicked

    private void btnCommitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCommitKeyPressed
        if (moneyError || nameError) {
        } else {
            if (!jtfPortName.getText().isEmpty()) {
                portfolio.setName(jtfPortName.getText());
            }
            portfolio.setLiquidMoney(moneyReader(jtfLiquidMoney.getText()));
            PortfolioOperation operation = new PortfolioOperation();
            operation.setOperationDate(new Date());
            if (jrbCredit.isSelected()) {
                account.debit(moneyReader(jtfMoneyTransfer.getText()));
                operation.setAmount(moneyReader(jtfMoneyTransfer.getText()));
            } else if (jrbDebit.isSelected()) {
                account.credit(moneyReader(jtfMoneyTransfer.getText()));
                operation.setAmount(-moneyReader(jtfMoneyTransfer.getText()));

            }
            PortfolioPerformanceUtilities.getMe().manageOperations(portfolio,operation);
            iAccountDAO.update(account);
            JOptionPane.showMessageDialog(this, "Done!", "Successfull Editing", JOptionPane.INFORMATION_MESSAGE);
            GUI.getMe().UpdateJTable(true, true, true, false);
            reset();
            this.setAlwaysOnTop(false);
            this.setVisible(false);
        }

    }//GEN-LAST:event_btnCommitKeyPressed
    private void btnCommitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCommitMouseClicked
        if (moneyError || nameError) {
        } else {
            if (!jtfPortName.getText().isEmpty()) {
                portfolio.setName(jtfPortName.getText());
            }
            portfolio.setLiquidMoney(moneyReader(jtfLiquidMoney.getText()));
            PortfolioOperation operation = new PortfolioOperation();
            operation.setOperationDate(new Date());
            if (jrbCredit.isSelected()) {
                account.debit(moneyReader(jtfMoneyTransfer.getText()));
                operation.setAmount(moneyReader(jtfMoneyTransfer.getText()));
            } else if (jrbDebit.isSelected()) {
                account.credit(moneyReader(jtfMoneyTransfer.getText()));
                operation.setAmount(-moneyReader(jtfMoneyTransfer.getText()));

            }
            PortfolioPerformanceUtilities.getMe().manageOperations(portfolio,operation);
            iAccountDAO.update(account);
            JOptionPane.showMessageDialog(this, "Done!", "Successfull Editing", JOptionPane.INFORMATION_MESSAGE);
            GUI.getMe().UpdateJTable(true, true, true, false);
            reset();
            this.setAlwaysOnTop(false);
            this.setVisible(false);
        }
    }//GEN-LAST:event_btnCommitMouseClicked

    public double moneyReader(String moneyString) {
        StringTokenizer st = new StringTokenizer(moneyString, ",");
        if (moneyString.contains("$")) {
            return 0.0;
        } else {
            double money = Double.parseDouble(st.nextToken()) * 1000;
            money += Double.parseDouble(st.nextToken());
            return money;
        }
    }

    public double roundDBL(double dollars) {
        int decimalPlace = 2;
        BigDecimal bd = new BigDecimal(dollars);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_UP);

        return (bd.doubleValue());
    }

    public String moneyWriter(double moneyValue) {
        String money = "";
        int millions = (int) moneyValue / 1000;
        money += millions + ",";
        double dollars = moneyValue - millions * 1000;
        dollars = roundDBL(dollars);
        if (dollars < 10) {
            money += "00" + dollars;
        } else if (dollars < 100) {
            money += "0" + dollars;
        } else {
            money += "" + dollars;
        }
        return money;
    }
    private void btnQuitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQuitMouseClicked
        reset();
        setVisible(false);
    }//GEN-LAST:event_btnQuitMouseClicked

    private void jtfPortNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfPortNameFocusLost
        if (!jtfPortName.getText().isEmpty()) {
            if (portfolioDAO.getPortfolioByName(jtfPortName.getText().toString(), account.getId()) != null) {
                jtfPortName.setBackground(new Color(255, 0, 0));
                jtfPortName.setForeground(new Color(0, 124, 120));
                nameError = true;
            } else {
                jtfPortName.setBackground(c1);
                jtfPortName.setForeground(c2);
                nameError = false;
            }
        }
    }//GEN-LAST:event_jtfPortNameFocusLost

    private void jrbCreditItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jrbCreditItemStateChanged
        if (jrbCredit.isSelected()) {
            double value = moneyReader(jtfMoneyTransfer.getText());
            double portLiq = portfolio.getLiquidMoney();
            double accLiqui = account.getMoney();
            double diff;
            if (value <= accLiqui) {
                diff = accLiqui - value;
                jtfAccountMoney.setText(moneyWriter(diff));
                diff = portLiq + value;
                jtfLiquidMoney.setText(moneyWriter(diff));
                jtfLiquidMoney.setBackground(c3);
                moneyError = false;
            } else {
                jtfLiquidMoney.setBackground(new Color(255, 0, 0));
                moneyError = true;
            }
        }
    }//GEN-LAST:event_jrbCreditItemStateChanged

    private void jrbDebitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jrbDebitItemStateChanged
        if (jrbDebit.isSelected()) {
            double value = moneyReader(jtfMoneyTransfer.getText());
            double portLiq = portfolio.getLiquidMoney();
            double accLiqui = account.getMoney();
            double diff;
            if (value <= portLiq) {
                diff = accLiqui + value;
                jtfAccountMoney.setText(moneyWriter(diff));
                diff = portLiq - value;
                jtfLiquidMoney.setText(moneyWriter(diff));
                jtfLiquidMoney.setBackground(c3);
                moneyError = false;
            } else {
                jtfLiquidMoney.setBackground(new Color(255, 0, 0));
                moneyError = true;
            }
        }
    }//GEN-LAST:event_jrbDebitItemStateChanged

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
            java.util.logging.Logger.getLogger(PortfolioEdition.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PortfolioEdition.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PortfolioEdition.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PortfolioEdition.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PortfolioEdition().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton btnCommit;
    private static javax.swing.JButton btnQuit;
    private static javax.swing.JButton btnReset;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JRadioButton jrbCredit;
    private javax.swing.JRadioButton jrbDebit;
    private static javax.swing.JTextField jtfAccountMoney;
    private static javax.swing.JTextField jtfLiquidMoney;
    private static javax.swing.JTextField jtfMoneyTransfer;
    private static javax.swing.JTextField jtfPortName;
    // End of variables declaration//GEN-END:variables
}
