/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.n1tr0.flappyTube;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

/**
 *
 * @author Fabi
 */
public class obstacles extends JRadioButton {

    private final int width = 50;
    private final int height = 70;
    private final ImageIcon Tube = new ImageIcon("src/resources/tube.png");

    public obstacles() {
        super();
        init();

    }

    private void init() {
        this.setBounds(height, width, height, width);
        this.setEnabled(false);
        this.setIcon(Tube);
        this.setDisabledIcon(Tube);
    }
}
