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
import javax.swing.JLabel;

/**
 *
 * @author Fabi
 */
public class main extends javax.swing.JFrame implements ActionListener, KeyListener {

    // Player
    private final player Player = new player();

    // Borders/obstacles
    private final obstacles Ground = new obstacles();
    public obstacles[] obstacleTop = new obstacles[5];
    public obstacles[] obstacleBot = new obstacles[5];
    private final obstacles levelEnd = new obstacles();

    // Graphics
    private final Ground Ground_pic = new Ground();
    private final hud levelHud = new hud();
    private final hud debugHud = new hud();
    private final Ground zomg = new Ground();

    // Icons
    private final ImageIcon TubeInv = new ImageIcon("src/resources/tubeinv.png");
    private final ImageIcon Test = new ImageIcon("src/resources/player.png");
    private final ImageIcon zomg_png = new ImageIcon("src/resources/zomg.png");

    private boolean isAlive;

    public main() {
        initComponents();

        setContentPane(new JLabel(new ImageIcon("src/resources/background.png")));

        // Initialize the player
        getContentPane().add(Player);
        Player.setStatus(true);
        Player.setFocusable(true);
        Player.addKeyListener(this);

        // Inititalize Objects
        // Ground
        getContentPane().add(Ground);
        Ground.setLocation(30, 205);

        // Player
        Player.setLocation(30, 120);

        // Textures
        getContentPane().add(Ground_pic);
        Ground_pic.setLocation(0, 222);
        getContentPane().add(zomg);
        zomg.setText(".");

        zomg.setEnabled(false);
        zomg.setDisabledIcon(zomg_png);
        zomg.setSize(50, 30);
        zomg.setVisible(false);

        // Level end
        getContentPane().add(levelEnd);
        levelEnd.setLocation(630, 150);
        //levelEnd.setDisabledIcon(Test);

        // Hud
        getContentPane().add(levelHud);
        levelHud.setText("Level " + Player.getLevel());
        getContentPane().add(debugHud);
        debugHud.setBounds(500, 40, 200, 80);
        debugHud.setText("Debugging");

        // The Player will fall continously down from the beginning if he doesn't do anything
        generateObstacle();
        checkGround();

        // Start checking for each tube
        for (int i = 0; i < obstacleTop.length; i++) {
            System.out.println(i);
            checkPlayerDieded(i);
        }
        checkLevelSuccess();
        PlayerFalling();

    }

    public void generateObstacle() {

        for (int i = 0; i < 5; i++) {
            // new Obstacle in Array
            obstacleBot[i] = new obstacles();
            // Add new Obstacle to pane
            getContentPane().add(obstacleBot[i]);
            // Generate random distances between obstacles
            obstacleBot[i].randYbot();
            obstacleBot[i].randXbot();
            // Set obstacle to random position
            obstacleBot[i].setLocation(150 + obstacleBot[i].randXbot() * i, obstacleBot[i].randYbot);
        }

        for (int i = 0; i < 5; i++) {
            obstacleTop[i] = new obstacles();
            getContentPane().add(obstacleTop[i]);
            obstacleTop[i].randYtop();
            obstacleTop[i].randXtop();
            obstacleTop[i].setLocation(150 + obstacleTop[i].randXtop() * i, obstacleTop[i].randYtop);
            obstacleTop[i].setDisabledIcon(TubeInv);
        }
    }

    public void removeObstacle() {
        for (int i = 0; i < 5; i++) {
            getContentPane().remove(obstacleTop[i]);
            debugHud.setText("Removed " + i);
        }

        for (int i = 0; i < 5; i++) {
            getContentPane().remove(obstacleBot[i]);
            debugHud.setText("Removed " + i);
        }
    }

    public void checkGround() {

        Thread ground = new Thread() {

            public void run() {
                while (Player.getStatus()) {
                    System.out.println(Player.getStatus());
                    if (Player.getY() >= Ground.getY()) {
                        // Player dieded
                        System.out.println("dieded");
                        levelHud.setText("Dieded!");
                        Player.setStatus(false);
                        zomg.setLocation(Player.getX(), Player.getY() - Player.getHeight());
                        zomg.setVisible(true);
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

    private void checkLevelSuccess() {
        Thread level = new Thread() {
            public void run() {
                while (Player.getStatus()) {
                    System.out.println(Player.getStatus());
                    if (Player.getX() + Player.getWidth() >= levelEnd.getX()) {
                        Player.addLevel(1);
                        levelHud.setText("Level " + Player.getLevel());
                        System.out.println("Level " + Player.getLevel() + " complete !");
                        Player.resetLocation();
                        removeObstacle();
                        generateObstacle();

                    }
                }
            }
        };
        level.start();
    }

    private void checkPlayerDieded(int i) {
        // Location of obstacle is in bottom left corner!

        Thread PlayerDieded = new Thread() {
            public void run() {
                while (Player.getStatus()) {
                    System.out.println(Player.getStatus());
                    //System.out.println("Obstacle X: " + obstacleBot[0].getX() + " Y: " + obstacleBot[0].getY());
                    //System.out.println("Player X: " + Player.getX() + " Y: " + Player.getY());
                    if (Player.getX() >= obstacleBot[i].getX() && Player.getX() <= obstacleBot[i].getX() + obstacleBot[i].getWidth() && Player.getY() + Player.getHeight() >= obstacleBot[i].getY()) {
                        Player.setStatus(false);
                        System.out.println("Dieded!");
                        levelHud.setText("Dieded!");
                        zomg.setLocation(Player.getX(), Player.getY() - Player.getHeight());
                        zomg.setVisible(true);
                    }
                    if (Player.getX() >= obstacleTop[i].getX() && Player.getX() <= obstacleTop[i].getX() + obstacleTop[i].getWidth() && Player.getY() >= obstacleTop[i].getY() && Player.getY() <= (obstacleTop[i].getY() + obstacleTop[i].getHeight())) {
                        Player.setStatus(false);
                        System.out.println("Dieded!");
                        levelHud.setText("Dieded!");
                        zomg.setLocation(Player.getX(), Player.getY() + Player.getHeight());
                        zomg.setVisible(true);
                    }

                }
            }
        };
        PlayerDieded.start();

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
                            Player.setEnabled(false);
                            Player.jumpUp(8, 13);
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        Player.setEnabled(true);
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
        setResizable(false);

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
