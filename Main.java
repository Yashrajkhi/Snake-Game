package com.snakegame;

import javax.swing.SwingUtilities;

/**
 * Entry point for the Snake Game application.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            frame.setVisible(true);
        });
    }
}
