package ru.home;

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {

    MainCircles listener;
    long lastFrameTime;
    long startTime;
    Background background;

    GameCanvas(MainCircles listener) {
        this.listener = listener;
        this.startTime = System.nanoTime();
        lastFrameTime = System.nanoTime();
        background = new Background();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //60 frames per second
        long currentTime = System.nanoTime();
        this.setBackground(background.getBackgroundColor(currentTime-startTime));
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = currentTime;
        listener.onCanvasRepainted(this, g, deltaTime);
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    public int getLeft() { return 0; }
    public int getRight() { return getWidth() - 1; }
    public int getTop() { return 0; }
    public int getBottom() { return getHeight() - 1; }

}
