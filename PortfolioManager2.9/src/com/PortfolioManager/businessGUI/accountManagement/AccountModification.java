package com.PortfolioManager.businessGUI.accountManagement;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.PortfolioManager.domain.dao.IConsultant;
import com.PortfolioManager.domain.dao.IInvestorDAO;
import com.PortfolioManager.domain.entities.Consultant;
import com.PortfolioManager.domain.entities.Investor;
import com.PortfolioManager.domain.impl.ConsultantDAO;
import com.PortfolioManager.domain.impl.InvestorDAO;
import com.gui.mainGUI.GUI;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

/**
 * 
 * @author Ahmed
 */
public class AccountModification extends javax.swing.JFrame {

	/**
	 * Creates new form AccountModification
	 */
	private static AccountModification accountModification;

	private AccountModification() {
		initComponents();
		Investor i = GUI.getMe().getLoggedInvestor();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
		// upload Consultants from database and select logged investor
		// consultant
		cbxConsultant.removeAll();
		IConsultant iConsultant = new ConsultantDAO();
		List<Consultant> list = iConsultant.getAllConsultant();
		for (Consultant c : list) {
			cbxConsultant.addItem(c.getName());
			if (c.getName().equals(i.getName())) {
				cbxConsultant.setSelectedItem(c.getName());
			}
		}
		// upload Consultants from database and select logged investor
		// consultant
		tfName.setText(i.getName());
		tfLastName.setText(i.getLastName());
		tfEmail.setText(i.getEmail());
		tfMobile.setText(i.getMobile());
		tfLogin.setText(i.getLogin());
		tfLogin.setEditable(false);
	}

	public static AccountModification getMe() {
		if (accountModification == null) {
			accountModification = new AccountModification();
		}
		return accountModification;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanelAccountcreation = new javax.swing.JPanel();
		jLabelname = new javax.swing.JLabel();
		jLabelLast = new javax.swing.JLabel();
		jLabelemail = new javax.swing.JLabel();
		jLabelmobile = new javax.swing.JLabel();
		jLabelLogin = new javax.swing.JLabel();
		jLabelPwd = new javax.swing.JLabel();
		jSeparator4 = new javax.swing.JSeparator();
		tfName = new javax.swing.JTextField();
		tfLastName = new javax.swing.JTextField();
		tfEmail = new javax.swing.JTextField();

		// /phone number mask
		try {
			mobileMask = new MaskFormatter("+###-##-###-###");
			mobileMask.setPlaceholderCharacter(' ');
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// /phone number mask==>>
		tfMobile = new javax.swing.JFormattedTextField(mobileMask);// <<==
		tfLogin = new javax.swing.JTextField();
		jButtonModify = new javax.swing.JButton();
		jButtonReset = new javax.swing.JButton();
		jButtonQuit = new javax.swing.JButton();
		Jpassword = new javax.swing.JPasswordField();
		jSeparator1 = new javax.swing.JSeparator();
		jLabel1 = new javax.swing.JLabel();
		Format format = new Format() {

			@Override
			public Object parseObject(String source, ParsePosition pos) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public StringBuffer format(Object obj, StringBuffer toAppendTo,
					FieldPosition pos) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		tfMoney = new JFormattedTextField(format);
		btnMinus = new javax.swing.JButton();
		btnPlus = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		radioConsultant = new javax.swing.JRadioButton();
		cbxConsultant = new javax.swing.JComboBox();
		jLabel3 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Modify your account");
		setResizable(false);

		jPanelAccountcreation.setBorder(javax.swing.BorderFactory
				.createTitledBorder(null, "Account Creation",
						javax.swing.border.TitledBorder.CENTER,
						javax.swing.border.TitledBorder.DEFAULT_POSITION));
		jPanelAccountcreation.setMaximumSize(new java.awt.Dimension(300, 305));
		jPanelAccountcreation.setMinimumSize(new java.awt.Dimension(300, 305));

		jLabelname.setText("Name:");

		jLabelLast.setText("Last Name:");

		jLabelemail.setText("Email:");

		jLabelmobile.setText("Mobile:");

		jLabelLogin.setText("Login:");

		jLabelPwd.setText("Password:");

		tfName.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfNamejTextField1ActionPerformed(evt);
			}
		});

		tfEmail.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tfEmailjTextField3ActionPerformed(evt);
			}
		});

		jButtonModify.setText("Modify");
		jButtonModify.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonModifyjButton1ActionPerformed(evt);
			}
		});

		jButtonReset.setText("Reset");
		jButtonReset.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonResetActionPerformed(evt);
			}
		});

		jButtonQuit.setText("Quit");
		jButtonQuit.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jButtonQuitMouseClicked(evt);
			}
		});

		Jpassword.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JpasswordActionPerformed(evt);
			}
		});

		jLabel1.setText("Debit Acount: ");

		tfMoney.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		tfMoney.setText(Double.toString(GUI.getMe().loggedInvestor.getAccount()
				.getMoney()));
		tfMoney.setMaximumSize(new java.awt.Dimension(90, 20));
		tfMoney.setMinimumSize(new java.awt.Dimension(90, 20));
		tfMoney.setPreferredSize(new java.awt.Dimension(90, 20));
		tfMoney.setEditable(false);
		btnMinus.setText("-");
		btnMinus.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				btnMinusMouseClicked(evt);
			}
		});

		btnPlus.setText("+");
		btnPlus.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				btnPlusMouseClicked(evt);
			}
		});

		jLabel2.setText("$");

		radioConsultant.setText("Consultant");
		radioConsultant
				.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						radioConsultantStateChanged(evt);
					}
				});
		jLabel3.setText("(5000  ->  500.000)");

		javax.swing.GroupLayout jPanelAccountcreationLayout = new javax.swing.GroupLayout(
				jPanelAccountcreation);
		jPanelAccountcreation.setLayout(jPanelAccountcreationLayout);
		jPanelAccountcreationLayout
				.setHorizontalGroup(jPanelAccountcreationLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanelAccountcreationLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanelAccountcreationLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jSeparator4)
														.addGroup(
																jPanelAccountcreationLayout
																		.createSequentialGroup()
																		.addComponent(
																				jButtonModify)
																		.addGap(38,
																				38,
																				38)
																		.addComponent(
																				jButtonQuit,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				63,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				37,
																				Short.MAX_VALUE)
																		.addComponent(
																				jButtonReset))
														.addComponent(
																jSeparator1)
														.addGroup(
																jPanelAccountcreationLayout
																		.createSequentialGroup()
																		.addComponent(
																				radioConsultant)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				cbxConsultant,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				152,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jPanelAccountcreationLayout
																		.createSequentialGroup()
																		.addGroup(
																				jPanelAccountcreationLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLabelname)
																						.addGroup(
																								jPanelAccountcreationLayout
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.TRAILING,
																												false)
																										.addGroup(
																												jPanelAccountcreationLayout
																														.createSequentialGroup()
																														.addComponent(
																																jLabelPwd)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																Jpassword,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																100,
																																javax.swing.GroupLayout.PREFERRED_SIZE))
																										.addGroup(
																												jPanelAccountcreationLayout
																														.createSequentialGroup()
																														.addComponent(
																																jLabelLogin)
																														.addGap(64,
																																64,
																																64)
																														.addComponent(
																																tfLogin,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																100,
																																javax.swing.GroupLayout.PREFERRED_SIZE)))
																						.addGroup(
																								jPanelAccountcreationLayout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel1)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												btnMinus)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												tfMoney,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jLabel2)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												btnPlus))
																						.addGroup(
																								jPanelAccountcreationLayout
																										.createSequentialGroup()
																										.addGroup(
																												jPanelAccountcreationLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addGroup(
																																jPanelAccountcreationLayout
																																		.createParallelGroup(
																																				javax.swing.GroupLayout.Alignment.LEADING)
																																		.addGroup(
																																				jPanelAccountcreationLayout
																																						.createSequentialGroup()
																																						.addComponent(
																																								jLabelmobile)
																																						.addGap(30,
																																								30,
																																								30))
																																		.addGroup(
																																				javax.swing.GroupLayout.Alignment.TRAILING,
																																				jPanelAccountcreationLayout
																																						.createSequentialGroup()
																																						.addComponent(
																																								jLabelLast)
																																						.addPreferredGap(
																																								javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
																														.addGroup(
																																jPanelAccountcreationLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				jLabelemail)
																																		.addGap(36,
																																				36,
																																				36)))
																										.addGroup(
																												jPanelAccountcreationLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addComponent(
																																tfEmail,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																160,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addGroup(
																																jPanelAccountcreationLayout
																																		.createParallelGroup(
																																				javax.swing.GroupLayout.Alignment.TRAILING,
																																				false)
																																		.addComponent(
																																				tfName,
																																				javax.swing.GroupLayout.Alignment.LEADING,
																																				javax.swing.GroupLayout.DEFAULT_SIZE,
																																				120,
																																				Short.MAX_VALUE)
																																		.addComponent(
																																				tfLastName,
																																				javax.swing.GroupLayout.Alignment.LEADING)
																																		.addComponent(
																																				tfMobile)))))
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)))
										.addContainerGap())
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanelAccountcreationLayout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(jLabel3)
										.addGap(84, 84, 84)));
		jPanelAccountcreationLayout
				.setVerticalGroup(jPanelAccountcreationLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanelAccountcreationLayout
										.createSequentialGroup()
										.addGroup(
												jPanelAccountcreationLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jLabelname)
														.addComponent(
																tfName,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanelAccountcreationLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jLabelLast)
														.addComponent(
																tfLastName,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanelAccountcreationLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jLabelemail)
														.addComponent(
																tfEmail,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanelAccountcreationLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																tfMobile,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jLabelmobile))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jSeparator4,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(2, 2, 2)
										.addGroup(
												jPanelAccountcreationLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(btnMinus)
														.addComponent(
																tfMoney,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(btnPlus)
														.addComponent(jLabel2))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel3)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												9, Short.MAX_VALUE)
										.addGroup(
												jPanelAccountcreationLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																cbxConsultant,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																radioConsultant))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jSeparator1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanelAccountcreationLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																tfLogin,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jLabelLogin))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanelAccountcreationLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																Jpassword,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jLabelPwd,
																javax.swing.GroupLayout.Alignment.TRAILING))
										.addGap(18, 18, 18)
										.addGroup(
												jPanelAccountcreationLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jButtonModify)
														.addComponent(
																jButtonQuit)
														.addComponent(
																jButtonReset))
										.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanelAccountcreation, javax.swing.GroupLayout.PREFERRED_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE)
						.addComponent(jPanelAccountcreation,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void tfNamejTextField1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tfNamejTextField1ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_tfNamejTextField1ActionPerformed

	private void tfEmailjTextField3ActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tfEmailjTextField3ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_tfEmailjTextField3ActionPerformed

	private void jButtonModifyjButton1ActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonModifyjButton1ActionPerformed
		Investor investor = GUI.getMe().getLoggedInvestor();
		IInvestorDAO iInvestorDAO = new InvestorDAO();

		if (Jpassword.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Password required!",
					"Infilled required Information!!",
					JOptionPane.ERROR_MESSAGE);
		} else if (Jpassword.getText().length() <= 4) {
			JOptionPane.showMessageDialog(this,
					"Password must be longer than 4 characters!",
					"Infilled required Information!!",
					JOptionPane.ERROR_MESSAGE);
		} else if (tfEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Please insert your email address!",
					"Infilled required Information!!",
					JOptionPane.ERROR_MESSAGE);
		} else if (!validate(tfEmail.getText())) {
			JOptionPane.showMessageDialog(this,
					"Please enter a valid email address!",
					"Infilled required Information!!",
					JOptionPane.ERROR_MESSAGE);
		} else {
			investor.setName(tfName.getText());
			investor.setLastName(tfLastName.getText());
			investor.setEmail(tfEmail.getText());

			// to delete - characters in the formattedJtextfield
			StringTokenizer st = new StringTokenizer(tfMobile.getText(), "-");
			String mobilePhone = st.nextToken() + st.nextToken()
					+ st.nextToken();
			// to delete - characters in the formattedJtextfield

			investor.setMobile(mobilePhone);
			investor.setPassword(Jpassword.getText());
			investor.getAccount().setMoney(Float.parseFloat(tfMoney.getText()));
			Consultant consultant;
			if (radioConsultant.isSelected()) {
				IConsultant iconsultant = new ConsultantDAO();
				consultant = iconsultant.getConsultanttById(cbxConsultant
						.getSelectedItem().toString());
				investor.setConsultant(consultant);
			}
			iInvestorDAO.update(investor);
			GUI.getMe().loggedInvestor = investor;
			JOptionPane.showMessageDialog(this, "Account Information Updated!",
					"Modification Done", JOptionPane.INFORMATION_MESSAGE);
//			GUI.getMe().jTableAccountUpdater();
                        AccountModification.getMe().setAlwaysOnTop(false);
			this.setVisible(false);
		}
	}// GEN-LAST:event_jButtonModifyjButton1ActionPerformed

	// email input control
	public boolean validate(final String email) {

		// email pattern to control email input
		String emailWatcher = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		// email pattern to control email input

		Pattern pattern = Pattern.compile(emailWatcher);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	// email input control
	private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonResetActionPerformed
		reset();
	}// GEN-LAST:event_jButtonResetActionPerformed

	private void jButtonQuitMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonQuitMouseClicked

		this.reset();// if not, when you quit and then reenter the account
						// interface
		// you'll find the text you edited : not nice ;)
		this.setVisible(false);
	}// GEN-LAST:event_jButtonQuitMouseClicked

	private void JpasswordActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_JpasswordActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_JpasswordActionPerformed

	private void btnMinusMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnMinusMouseClicked
		Float m;
		try {
			m = Float.parseFloat(tfMoney.getText());
			if (m < GUI.getMe().loggedInvestor.getAccount().getMoney()) {
				tfMoney.setText(""
						+ GUI.getMe().loggedInvestor.getAccount().getMoney());
			}
			if (m >= 5100) {
				m = m - 100;
			}
			tfMoney.setText("" + m);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this,
					"Enter a correct number please");
		}

	}// GEN-LAST:event_btnMinusMouseClicked

	private void btnPlusMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnPlusMouseClicked
		Float m;
		try {
			m = Float.parseFloat(tfMoney.getText());
			if (m > 500000) {
				tfMoney.setText("500000");
			}
			if (m <= 499900) {
				m = m + 100;
			}
			tfMoney.setText("" + m);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this,
					"Enter a correct number please");
		}
	}// GEN-LAST:event_btnPlusMouseClicked

	private void radioConsultantStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_radioConsultantStateChanged
		if (radioConsultant.isSelected())
			cbxConsultant.enable();
		else
			cbxConsultant.disable();
	}// GEN-LAST:event_radioConsultantStateChanged

	private void reset() {
		this.tfName.setText("");
		this.tfLastName.setText("");
		this.tfEmail.setText("");
		this.tfMobile.setText("");
		this.tfMoney.setText(""
				+ GUI.getMe().loggedInvestor.getAccount().getMoney());
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(
					AccountModification.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(
					AccountModification.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(
					AccountModification.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(
					AccountModification.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new AccountModification().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPasswordField Jpassword;
	private javax.swing.JButton btnMinus;
	private javax.swing.JButton btnPlus;
	private javax.swing.JComboBox cbxConsultant;
	private javax.swing.JButton jButtonModify;
	private javax.swing.JButton jButtonQuit;
	private javax.swing.JButton jButtonReset;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabelLast;
	private javax.swing.JLabel jLabelLogin;
	private javax.swing.JLabel jLabelPwd;
	private javax.swing.JLabel jLabelemail;
	private javax.swing.JLabel jLabelmobile;
	private javax.swing.JLabel jLabelname;
	private javax.swing.JPanel jPanelAccountcreation;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator4;
	private javax.swing.JRadioButton radioConsultant;
	private javax.swing.JTextField tfEmail;
	private javax.swing.JTextField tfLastName;
	private javax.swing.JTextField tfLogin;
	private MaskFormatter mobileMask;
	private javax.swing.JFormattedTextField tfMobile;
	private javax.swing.JFormattedTextField tfMoney;
	private javax.swing.JTextField tfName;
	// End of variables declaration//GEN-END:variables
}
