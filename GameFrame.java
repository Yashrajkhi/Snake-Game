package com.snakegame;

import javax.swing.JFrame;

/**
 * The main application window for the Snake Game.
 */
public class GameFrame extends JFrame {

    public GameFrame() {
        GamePanel panel = new GamePanel();

        this.setTitle("🐍 Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null); // Center on screen
    }
}
