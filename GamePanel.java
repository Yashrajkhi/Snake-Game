package com.snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The core game panel — handles rendering, input, and the game loop.
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener {

    // Grid & display settings
    public static final int CELL_SIZE   = 24;
    public static final int GRID_WIDTH  = 25;
    public static final int GRID_HEIGHT = 25;
    public static final int PANEL_W     = CELL_SIZE * GRID_WIDTH;
    public static final int PANEL_H     = CELL_SIZE * GRID_HEIGHT;

    // Game speed (ms per tick — lower = faster)
    private static final int INITIAL_DELAY = 130;
    private static final int MIN_DELAY     = 60;

    // Colors
    private static final Color BG_COLOR         = new Color(15, 17, 26);
    private static final Color GRID_COLOR        = new Color(30, 34, 48);
    private static final Color SNAKE_HEAD_COLOR  = new Color(80, 220, 120);
    private static final Color SNAKE_BODY_COLOR  = new Color(50, 170, 90);
    private static final Color FOOD_COLOR        = new Color(255, 90, 90);
    private static final Color FOOD_GLOW_COLOR   = new Color(255, 90, 90, 60);
    private static final Color SCORE_COLOR       = new Color(200, 200, 220);
    private static final Color OVERLAY_COLOR     = new Color(0, 0, 0, 160);
    private static final Color TITLE_COLOR       = new Color(80, 220, 120);
    private static final Color SUBTITLE_COLOR    = new Color(180, 180, 200);

    private Snake snake;
    private Food food;
    private Timer timer;
    private int score;
    private int highScore;
    private boolean running;
    private boolean gameOver;
    private int currentDelay;

    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        setBackground(BG_COLOR);
        setFocusable(true);
        addKeyListener(this);
        highScore = 0;
        initGame();
    }

    private void initGame() {
        snake = new Snake(GRID_WIDTH / 2, GRID_HEIGHT / 2);
        food  = new Food(GRID_WIDTH, GRID_HEIGHT, snake.getBody());
        score = 0;
        currentDelay = INITIAL_DELAY;
        gameOver = false;
        running  = true;

        if (timer != null) timer.stop();
        timer = new Timer(currentDelay, this);
        timer.start();
    }

    // ── Game Loop ────────────────────────────────────────────────────────────

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            boolean ate = snake.getHead().equals(food.getPosition());

            // Move before checking so head already advanced
            snake.move(ate);

            if (ate) {
                score += 10;
                if (score > highScore) highScore = score;
                food.respawn(GRID_WIDTH, GRID_HEIGHT, snake.getBody());
                speedUp();
            }

            if (isWallCollision() || snake.hasSelfCollision()) {
                running  = false;
                gameOver = true;
            }
        }
        repaint();
    }

    private boolean isWallCollision() {
        Point head = snake.getHead();
        return head.x < 0 || head.x >= GRID_WIDTH
            || head.y < 0 || head.y >= GRID_HEIGHT;
    }

    private void speedUp() {
        currentDelay = Math.max(MIN_DELAY, currentDelay - 2);
        timer.setDelay(currentDelay);
    }

    // ── Rendering ────────────────────────────────────────────────────────────

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawGrid(g2);
        drawFood(g2);
        drawSnake(g2);
        drawHUD(g2);

        if (gameOver) drawGameOver(g2);
        if (!running && !gameOver) drawStartScreen(g2);
    }

    private void drawGrid(Graphics2D g) {
        g.setColor(GRID_COLOR);
        for (int x = 0; x <= PANEL_W; x += CELL_SIZE) {
            g.drawLine(x, 0, x, PANEL_H);
        }
        for (int y = 0; y <= PANEL_H; y += CELL_SIZE) {
            g.drawLine(0, y, PANEL_W, y);
        }
    }

    private void drawFood(Graphics2D g) {
        Point p = food.getPosition();
        int px = p.x * CELL_SIZE;
        int py = p.y * CELL_SIZE;
        int pad = 3;

        // Glow halo
        g.setColor(FOOD_GLOW_COLOR);
        g.fillOval(px - pad, py - pad, CELL_SIZE + pad * 2, CELL_SIZE + pad * 2);

        // Food dot
        g.setColor(FOOD_COLOR);
        g.fillOval(px + pad, py + pad, CELL_SIZE - pad * 2, CELL_SIZE - pad * 2);
    }

    private void drawSnake(Graphics2D g) {
        java.util.LinkedList<java.awt.Point> body = snake.getBody();
        for (int i = 0; i < body.size(); i++) {
            Point p = body.get(i);
            int px = p.x * CELL_SIZE + 1;
            int py = p.y * CELL_SIZE + 1;
            int size = CELL_SIZE - 2;

            if (i == 0) {
                // Head — slightly larger and brighter
                g.setColor(SNAKE_HEAD_COLOR);
                g.fillRoundRect(px - 1, py - 1, size + 2, size + 2, 8, 8);

                // Eyes
                drawEyes(g, p, size);
            } else {
                // Fade body color slightly toward tail
                float ratio = 1f - (float) i / body.size() * 0.5f;
                Color c = interpolateColor(SNAKE_BODY_COLOR, BG_COLOR, 1f - ratio);
                g.setColor(c);
                g.fillRoundRect(px, py, size, size, 6, 6);
            }
        }
    }

    private void drawEyes(Graphics2D g, Point head, int size) {
        g.setColor(Color.BLACK);
        Direction dir = snake.getDirection();
        int eyeSize = 4;
        int ex1, ey1, ex2, ey2;
        int px = head.x * CELL_SIZE;
        int py = head.y * CELL_SIZE;

        switch (dir) {
            case RIGHT -> { ex1 = px + size - 6; ey1 = py + 4;        ex2 = px + size - 6; ey2 = py + size - 8; }
            case LEFT  -> { ex1 = px + 4;         ey1 = py + 4;        ex2 = px + 4;         ey2 = py + size - 8; }
            case UP    -> { ex1 = px + 4;          ey1 = py + 4;        ex2 = px + size - 8;  ey2 = py + 4;        }
            default    -> { ex1 = px + 4;          ey1 = py + size - 6; ex2 = px + size - 8;  ey2 = py + size - 6; }
        }
        g.fillOval(ex1, ey1, eyeSize, eyeSize);
        g.fillOval(ex2, ey2, eyeSize, eyeSize);
    }

    private Color interpolateColor(Color a, Color b, float t) {
        t = Math.max(0, Math.min(1, t));
        int r = (int) (a.getRed()   * (1 - t) + b.getRed()   * t);
        int gr = (int) (a.getGreen() * (1 - t) + b.getGreen() * t);
        int bl = (int) (a.getBlue()  * (1 - t) + b.getBlue()  * t);
        return new Color(r, gr, bl);
    }

    private void drawHUD(Graphics2D g) {
        g.setColor(SCORE_COLOR);
        g.setFont(new Font("Monospaced", Font.BOLD, 14));
        g.drawString("Score: " + score, 8, 18);
        g.drawString("Best: " + highScore, PANEL_W - 100, 18);
    }

    private void drawGameOver(Graphics2D g) {
        // Dim overlay
        g.setColor(OVERLAY_COLOR);
        g.fillRect(0, 0, PANEL_W, PANEL_H);

        g.setFont(new Font("Monospaced", Font.BOLD, 42));
        g.setColor(FOOD_COLOR);
        drawCenteredString(g, "GAME OVER", PANEL_H / 2 - 40);

        g.setFont(new Font("Monospaced", Font.PLAIN, 18));
        g.setColor(SCORE_COLOR);
        drawCenteredString(g, "Score: " + score + "  |  Best: " + highScore, PANEL_H / 2 + 10);

        g.setFont(new Font("Monospaced", Font.PLAIN, 14));
        g.setColor(SUBTITLE_COLOR);
        drawCenteredString(g, "Press ENTER to play again", PANEL_H / 2 + 50);
    }

    private void drawStartScreen(Graphics2D g) {
        g.setColor(OVERLAY_COLOR);
        g.fillRect(0, 0, PANEL_W, PANEL_H);

        g.setFont(new Font("Monospaced", Font.BOLD, 48));
        g.setColor(TITLE_COLOR);
        drawCenteredString(g, "SNAKE", PANEL_H / 2 - 40);

        g.setFont(new Font("Monospaced", Font.PLAIN, 16));
        g.setColor(SUBTITLE_COLOR);
        drawCenteredString(g, "Press ENTER to start", PANEL_H / 2 + 20);
        drawCenteredString(g, "Use Arrow Keys or WASD", PANEL_H / 2 + 50);
    }

    private void drawCenteredString(Graphics2D g, String s, int y) {
        FontMetrics fm = g.getFontMetrics();
        int x = (PANEL_W - fm.stringWidth(s)) / 2;
        g.drawString(s, x, y);
    }

    // ── Input ─────────────────────────────────────────────────────────────────

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (gameOver && key == KeyEvent.VK_ENTER) {
            initGame();
            return;
        }

        switch (key) {
            case KeyEvent.VK_UP,    KeyEvent.VK_W -> snake.setDirection(Direction.UP);
            case KeyEvent.VK_DOWN,  KeyEvent.VK_S -> snake.setDirection(Direction.DOWN);
            case KeyEvent.VK_LEFT,  KeyEvent.VK_A -> snake.setDirection(Direction.LEFT);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> snake.setDirection(Direction.RIGHT);
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
