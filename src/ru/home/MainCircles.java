package ru.home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainCircles extends JFrame {
    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int INIT_BALLS_COUNT = 10;

    Sprite[] sprites = new Sprite[INIT_BALLS_COUNT];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainCircles();
            }
        });
    }

    private MainCircles() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        GameCanvas canvas = new GameCanvas(this);
        add(canvas, BorderLayout.CENTER);
        setTitle("Circles");
        initApplication();
        setVisible(true);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                onMouseClick(e);
            }
        });
    }

    private void initApplication() {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Ball();
        }
    }

    void onCanvasRepainted(GameCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime);
        render(canvas, g);
    }

    private void update(GameCanvas canvas, float deltaTime) {
        for (int i = 0; i < sprites.length; i++) {
            if (sprites[i] != null) {
                sprites[i].update(canvas, deltaTime);
            }
        }
    }

    private void render(GameCanvas canvas, Graphics g) {
        for (int i = 0; i < sprites.length; i++) {
            if (sprites[i] != null) {
                sprites[i].render(canvas, g);
            }
        }
    }

    /**
     * Обработать событие нажатия кнопки мыши
     * @param e
     */
    private void onMouseClick(MouseEvent e) {
        if (e.getButton() == 1) {
            onLeftButtonClick();
        } else if (e.getButton() == 3) {
            onRightButtonClick();
        }
    }

    /**
     * Обработать нажатие левой кнопки мыши
     * Добавить круг
     */
    private void onLeftButtonClick() {
        if (sprites[sprites.length-1] != null) {
            expandSprites(INIT_BALLS_COUNT);
        }
        int newSpriteIndex = getFirstNullSprite();
        if (newSpriteIndex >= 0) {
            sprites[newSpriteIndex] = new Ball();
        }
    }

    /**
     * Получить индекс первого пустого элемента массива кругов
     * @return int
     */
    private int getFirstNullSprite() {
        for (int i = 0; i < sprites.length; i++) {
            if (sprites[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Расширить массив кругов
     * @param expandSize Величина расширения массива
     */
    private void expandSprites(int expandSize) {
        Sprite[] newSprites = new Sprite[sprites.length + expandSize];
        for (int i = 0; i < sprites.length; i++) {
            newSprites[i] = sprites[i];
        }
        sprites = newSprites;
    }

    /**
     * Обработать нажатие правой кнопки мыши
     * Удалить круг
     */
    private void onRightButtonClick() {
        deleteSprite();
    }

    /**
     * Удалить из массива кругов последний круг
     */
    private void deleteSprite() {
        for (int i = sprites.length-1; i >= 0; i--) {
            if (sprites[i] != null) {
                sprites[i] = null;
                break;
            }
        }
    }
}
