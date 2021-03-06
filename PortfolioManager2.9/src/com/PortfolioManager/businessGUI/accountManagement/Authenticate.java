/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.businessGUI.accountManagement;

import com.PortfolioManager.domain.dao.IInvestorDAO;
import com.PortfolioManager.domain.impl.InvestorDAO;
import com.gui.mainGUI.GUI;
import com.gui.mainGUI.splash.ImageHelper;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

/**
 *
 * @author Ahmed
 */
public class Authenticate extends javax.swing.JFrame {

    /**
     * Creates new form Authenticate
     */
//    private static ImageIcon imageIcon = ImageHelper.loadImage("");
    private static Authenticate authenticate;

    public static Authenticate getMe() {
        if (authenticate == null) {
            authenticate = new Authenticate();

        }

        return authenticate;
    }

    private void showApp() {

        setIconImage(ImageHelper.loadImage("/com/gui/img/Key.png").getImage());
        setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
                / 2 - this.getSize().height / 2);

    }

    public static void reset() {
        tfPwd.setText("");
        jPasswordField1.setText("");
    }

    private Authenticate() {
        initComponents();
        showApp();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonLogin = new javax.swing.JButton();
        tfPwd = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Identification");
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(250, 220));
        setMinimumSize(new java.awt.Dimension(250, 220));
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Enter login and password")));

        jButtonLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/locked.png"))); // NOI18N
        jButtonLogin.setToolTipText("Login");
        jButtonLogin.setBorderPainted(false);
        jButtonLogin.setMaximumSize(new java.awt.Dimension(48, 48));
        jButtonLogin.setMinimumSize(new java.awt.Dimension(48, 48));
        jButtonLogin.setPreferredSize(new java.awt.Dimension(48, 48));
        jButtonLogin.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/login.png"))); // NOI18N
        jButtonLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonLoginMouseClicked(evt);
            }
        });
        jButtonLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonLoginKeyPressed(evt);
            }
        });

        tfPwd.setMaximumSize(new java.awt.Dimension(95, 20));
        tfPwd.setMinimumSize(new java.awt.Dimension(95, 20));
        tfPwd.setPreferredSize(new java.awt.Dimension(95, 20));

        jLabel1.setText("Password:");

        jLabel2.setText("Login:");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gui/img/loginQuit.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setMaximumSize(new java.awt.Dimension(48, 48));
        jButton2.setMinimumSize(new java.awt.Dimension(48, 48));
        jButton2.setPreferredSize(new java.awt.Dimension(48, 48));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jPasswordField1.setMaximumSize(new java.awt.Dimension(95, 20));
        jPasswordField1.setMinimumSize(new java.awt.Dimension(95, 20));
        jPasswordField1.setPreferredSize(new java.awt.Dimension(95, 20));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfPwd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonLoginMouseClicked
        if (tfPwd.getText().isEmpty() || jPasswordField1.getText().isEmpty()) {
        } else {
            getInvestor(tfPwd.getText(), jPasswordField1.getText());
        }
    }//GEN-LAST:event_jButtonLoginMouseClicked

    private void jButtonLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonLoginKeyPressed
        if (tfPwd.getText().isEmpty() || jPasswordField1.getText().isEmpty()) {
        } else {
            getInvestor(tfPwd.getText(), jPasswordField1.getText());
        }
    }//GEN-LAST:event_jButtonLoginKeyPressed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        reset();
        dispose();
    }//GEN-LAST:event_jButton2MouseClicked

    private void getInvestor(String l, String p) {
        IInvestorDAO iDao = new InvestorDAO();
        GUI.getMe().loggedInvestor = iDao.getInvestorById(l);
        if (GUI.getMe().loggedInvestor != null) {
            if (GUI.getMe().loggedInvestor.getPassword().equals(p)) {
                JOptionPane.showMessageDialog(this, "Welcome Mr:  "
                        + GUI.getMe().loggedInvestor.getLastName(), "Login Successful",
                        JOptionPane.INFORMATION_MESSAGE,ImageHelper.loadImage("/com/gui/img/login.png"));
                GUI.enabler(true);
                reset();
                GUI.getMe().logged = 0;

                if (GUI.getMe().getLoggedInvestor().getAccount().getPortfolios().size() != 0) {//charge orders
                    GUI.getMe().UpdateJTable(true, true, true, true);
                }
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Wrong login or password!",
                        "Login failed", JOptionPane.ERROR_MESSAGE,ImageHelper.loadImage("/com/gui/img/loginQuit.png"));
                GUI.getMe().loggedInvestor=null;
                reset();
            }
        }
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
            java.util.logging.Logger.getLogger(Authenticate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Authenticate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Authenticate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Authenticate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new Authenticate().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton jButton2;
    private static javax.swing.JButton jButtonLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private static javax.swing.JPasswordField jPasswordField1;
    private static javax.swing.JTextField tfPwd;
    // End of variables declaration//GEN-END:variables
}
