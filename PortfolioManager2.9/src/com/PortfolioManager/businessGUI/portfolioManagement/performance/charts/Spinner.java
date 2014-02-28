/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PortfolioManager.businessGUI.portfolioManagement.performance.charts;

/**
 *
 * @author Ahmed
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.jfree.chart.plot.PiePlot3D;

class Spinner extends Timer
        implements ActionListener
{

        private PiePlot3D plot;
        private int angle;

        Spinner(PiePlot3D pieplot3d)
        {
                super(100, null);
                angle = 270;
                plot = pieplot3d;
                addActionListener(this);
        }

        public void actionPerformed(ActionEvent actionevent)
        {
                plot.setStartAngle(angle);
                angle = angle + 1;
                if (angle == 360)
                        angle = 0;
        }
}