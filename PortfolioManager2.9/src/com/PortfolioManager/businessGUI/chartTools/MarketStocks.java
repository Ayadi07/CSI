/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.businessGUI.chartTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author Ahmed
 */
public class MarketStocks {

    private static Vector<String> Stocks;
    private static Vector<String> Symbols;

    public static Vector<String> getMarketStoks(String s) {
        Stocks = new Vector<String>();
        BufferedReader reader1 = null;
        String line1;
        try {
            File fileStocks = new File("markets\\" + s + "Stocks.txt");          
            reader1 = new BufferedReader(new FileReader(fileStocks.getCanonicalPath()));//without this the program won't find the file parsi.txt
            while ((line1 = reader1.readLine()) != null) {
                Stocks.add(line1);
        } 
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader1 != null) {
                    reader1.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return Stocks;
    }

    public static Vector<String> getMarketSymbols(String s) {
        Symbols = new Vector<String>();
        BufferedReader reader2 = null;
        String line2;
        try {
            File fileSymbols = new File("markets\\" + s + "Symbols.txt");
            reader2 = new BufferedReader(new FileReader(fileSymbols.getCanonicalPath()));//without this the program won't find the file parsi.txt
            while ((line2 = reader2.readLine()) != null) {
                Symbols.add(line2);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader2 != null) {
                    reader2.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return Symbols;
    }
}
