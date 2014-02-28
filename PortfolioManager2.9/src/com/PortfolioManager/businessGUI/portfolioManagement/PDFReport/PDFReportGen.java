/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.businessGUI.portfolioManagement.PDFReport;

import com.gui.mainGUI.GUI;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * 
 * @author Ahmed
 */
public final class PDFReportGen {

	public static void getReport(long id) {
		Connection connection = SingletonConnection.getConnexion();
		XMLEdit.JRXMLEditor(id);
		try {

			Map parameters = new HashMap();
			parameters.put("Titre", "Titre");
			JasperDesign jasperDesign = JRXmlLoader
					.load("PDFReport\\AccountPortfolio.jrxml");
			JasperReport jasperReport = JasperCompileManager
					.compileReport(jasperDesign);                       
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameters, connection);
//			s
                        JasperExportManager.exportReportToPdfFile(jasperPrint,
					"PDFReport\\"+GUI.getMe().getLoggedInvestor().getLastName()+"_AccountID"+GUI.getMe().getLoggedInvestor().getAccount().getId()+"_Report"+id+".pdf");
			File file=new File("PDFReport\\"+GUI.getMe().getLoggedInvestor().getLastName()+"_AccountID"+GUI.getMe().getLoggedInvestor().getAccount().getId()+"_Report"+id+".pdf");
			try {
				open(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JRException e) {

			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}
	
	public static void open(File document) throws IOException {
	    Desktop dt = Desktop.getDesktop();
	    dt.open(document);
	}
        public static void main(String[] args) {
        getReport(1l);
    }
}