/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.n1tr0.flappyTube;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author fabian.ihle
 */
public class hud extends JLabel {

    private final int width = 30;
    private final int height = 80;
    Font hudFont = new Font("Castellar", Font.BOLD, 15);
    
    public hud() {
        super();
        init();
        this.setLocation(50, 10);
        this.setFont(hudFont);

    }

    public void init() {
        this.setBounds(50, 180, height, width);
        this.setForeground(Color.white);
    }
}
