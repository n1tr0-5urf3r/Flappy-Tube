/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.n1tr0.flappyTube;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Fabi
 */
public class main extends javax.swing.JFrame implements ActionListener, KeyListener {

    private final player Player = new player();
    private final obstacles Ground = new obstacles();
    public obstacles[] obstacle = new obstacles[100];
    public obstacles[] obstacle2 = new obstacles[100];
    private final Ground Ground_pic = new Ground();
    public int zufallszahl;
    public int randYtop;
    public int randYbot;


    private final ImageIcon TubeInv = new ImageIcon("src/resources/tubeinv.png");

    private boolean isAlive;

    public main() {
        initComponents();

        // Initialize the player
        getContentPane().add(Player);
        Player.setStatus(true);
        Player.setFocusable(true);
        Player.addKeyListener(this);

        // The Player will fall continously down from the beginning if he doesn't do anything
        generateObstacle();
        checkGround();
        PlayerFalling();

        // Inititalize Borders
        getContentPane().add(Ground);
        Ground.setLocation(30, 190);
        getContentPane().add(Ground_pic);
        Ground_pic.setLocation(0, 222);
    }

    public void generateObstacle() {

        Thread generatingTop = new Thread() {
            public void run() {
                while (Player.getStatus()) {
                    for (int i = 0; i < 10; i++) {
                        zufallszahl=(int)(Math.random() *3000)+2000; 
                        randYtop();
                        obstacle2[i] = new obstacles();
                        getContentPane().add(obstacle2[i]);
                        obstacle2[i].setLocation(600, randYtop);
                        obstacle2[i].setDisabledIcon(TubeInv);
                        moveObstacleTop(i);
                        try {
                            Thread.sleep(zufallszahl);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }
        };
        generatingTop.start();

        Thread generatingBot = new Thread() {
            public void run() {
                while (Player.getStatus()) {
                    for (int i = 0; i < 10; i++) {
                        zufallszahl=(int)(Math.random() *3000)+2000;
                        randYbot();
                        obstacle[i] = new obstacles();
                        getContentPane().add(obstacle[i]);
                        obstacle[i].setLocation(700, randYbot);
                        moveObstacleBot(i);
                        try {
                            Thread.sleep(zufallszahl);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }
        };
        generatingBot.start();
    }

    public void moveObstacleBot(int i) {
        Thread moveObstacle = new Thread() {
            public void run() {
                while (Player.getStatus()) {
                    for (int j = 0; j < 100; j++) {
                        obstacle[i].setLocation(obstacle[i].getX() - 10, obstacle[i].getY());
                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        };
        moveObstacle.start();
    }
    
    public void moveObstacleTop(int i) {
        Thread moveObstacle = new Thread() {
            public void run() {
                while (Player.getStatus()) {
                    for (int j = 0; j < 7; j++) {
                        obstacle2[i].setLocation(obstacle2[i].getX() - 10, obstacle2[i].getY());
                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        };
        moveObstacle.start();
    }

    public void checkGround() {
        Thread ground = new Thread() {
            @Override
            public void run() {
                while (Player.getStatus()) {
                    System.out.println(Player.getStatus());
                    if (Player.getY() >= Ground.getY()) {
                        // Player dieded
                        System.out.println("dieded");
                        Player.setStatus(false);

                    }
                }
            }
        };
        ground.start();
    }

    public void PlayerFalling() {
        Thread falling = new Thread() {
            public void run() {
                while (Player.getStatus()) {
                    System.out.println(Player.getStatus());
                    Player.fallDown(1, 2);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        falling.start();
    }
    
    private void randYtop(){
        randYtop=(int)(Math.random() *30)+60;
    }
    
    private void randYbot(){
        randYbot=(int)(Math.random() *40)+170;
    }

    public void keyTyped(KeyEvent e) {
        // Invoked when a key has been typed.
    }

    public void keyPressed(KeyEvent e) {
        // Invoked when a key has been pressed.
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (Player.getStatus()) {
                Thread move = new Thread() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 3; i++) {
                            Player.jumpUp(8, 8);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        for (int i = 0; i < 3; i++) {
                            Player.jumpDown(2, 3);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                };
                move.start();
            }
        }

    }

    public void keyReleased(KeyEvent e) {
        // Invoked when a key has been released.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(700, 360));
        setPreferredSize(new java.awt.Dimension(700, 360));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
