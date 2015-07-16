/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.n1tr0.flappyTube;

import javax.swing.JRadioButton;

/**
 *
 * @author Fabi
 */
public class player extends JRadioButton {

    private boolean alive;
    private final int width = 70;
    private final int height = 50;

    public player() {
        super();
        init();
        this.setLocation(50, 100);

    }

    private void init() {
        alive = true;
        this.setBounds(height, width, height, width);
    }

    public boolean getStatus() {
        return this.alive;
    }

    public void setStatus(boolean status) {
        this.alive = status;
    }

    public void jumpDown(int X, int Y) {
        this.setLocation((this.getX() + X), (this.getY() + Y));
    }

    public void jumpUp(int X, int Y) {
        this.setLocation(this.getX() + X, this.getY() - Y);
    }

    public void fallDown(int X, int Y) {
        this.setLocation((this.getX() + X), (this.getY() + Y));
    }

}
