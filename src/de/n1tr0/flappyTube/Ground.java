/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.n1tr0.flappyTube;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Fabi
 */
public class Ground extends JLabel{
    
    private final int width = 130;
    private final int height = 700;
    private final ImageIcon Ground = new ImageIcon("src/resources/ground.png");

    public Ground() {
        super();
        init();

    }

    private void init() {
        this.setBounds(height, width, height, width);
        this.setEnabled(false);
        this.setDisabledIcon(Ground);
    }
    
}
