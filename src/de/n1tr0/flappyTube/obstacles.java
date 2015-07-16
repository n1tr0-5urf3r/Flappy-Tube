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
    public int randYtop;
    public int randYbot;
    public int randXbot;
    public int randXtop;

    public obstacles() {
        super();
        init();

    }

    private void init() {
        this.setBounds(30, 190, width, height);
        this.setEnabled(false);
        this.setIcon(Tube);
        this.setDisabledIcon(Tube);
        
    }
    
        public void randYtop() {
        randYtop = (int) (Math.random() * 30) + 10;
    }

    public void randYbot() {
        randYbot = (int) (Math.random() * 40) + 160;
    }
    
    public int randXbot() {
        randXbot = (int) (Math.random() * 50) + 80;
        return randXbot;
    }
    
    public int randXtop() {
        randXbot = (int) (Math.random() * 50) + 80;
        return randXbot;
    }
    

}
