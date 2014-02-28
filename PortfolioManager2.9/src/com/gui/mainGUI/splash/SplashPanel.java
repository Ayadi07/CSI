/*
 * Copyright 2006 MH-Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */

package com.gui.mainGUI.splash;


import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @author Michael Hagen
 */


public class SplashPanel extends JPanel {
    private ImageIcon splashImage = ImageHelper.loadImage("/com/gui/img/CSISplash.png");
    private Dimension size = new Dimension(splashImage.getIconWidth(), splashImage.getIconHeight());

    public SplashPanel() {
        super();
        setForeground(new Color(233, 115, 103));
        setFont(new Font("Serif", Font.PLAIN, 28));
    }

    public Dimension getPreferredSize() {
        return size;
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        splashImage.paintIcon(this, g, 0, 0);
        
        
        
        
        
    }

}
