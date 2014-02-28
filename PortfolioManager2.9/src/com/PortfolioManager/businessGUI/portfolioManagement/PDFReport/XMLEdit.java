package com.PortfolioManager.businessGUI.portfolioManagement.PDFReport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public final class XMLEdit {

	public static void JRXMLEditor(long id) {
		try {

			FileInputStream fstream = new FileInputStream(
					"PDFReport\\AccountPortfolio.jrxml");

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			StringBuilder fileContent = new StringBuilder();

			String newLine = new String();
			int j = 1;
			while ((strLine = br.readLine()) != null) {
				if (j == 44) {
//					System.out.println(strLine);
					for (int i = 0; i < strLine.length(); i++) {
						newLine += strLine.charAt(i);
						if (strLine.charAt(i) == '=')
							break;
					}
					newLine += " " + id + "]]>";
					fileContent.append(newLine);
					fileContent.append("\n");
				} else {

					fileContent.append(strLine);
					fileContent.append("\n");
				}				
				j++;
			}
			FileWriter fstreamWrite = new FileWriter("PDFReport\\AccountPortfolio.jrxml");
			BufferedWriter out = new BufferedWriter(fstreamWrite);
			out.write(fileContent.toString());
			out.close();
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}