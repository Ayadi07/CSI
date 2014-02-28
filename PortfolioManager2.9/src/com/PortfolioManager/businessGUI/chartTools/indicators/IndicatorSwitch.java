/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.businessGUI.chartTools.indicators;

/**
 *
 * @author Ahmed
 */
public class IndicatorSwitch {

    public static void switchIndicator(int i) {
        switch (i) {
            case 1:
                MACrossover.setIndicator(true);
                break;
            case 2:
                BandsOfBolinger.setIndicator(true);
                break;
            default:System.exit(1);
        }
    }
}
