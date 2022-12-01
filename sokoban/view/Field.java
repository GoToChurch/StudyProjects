package sokoban.view;

import sokoban.controller.EventListener;
import sokoban.model.Direction;
import sokoban.model.GameObject;
import sokoban.model.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Field extends JPanel {
    private View view;
    private EventListener eventListener;

    public Field(View view) {
        this.view = view;

        addKeyListener(new KeyHandler());
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        GameObjects gameObjects = view.getGameObjects();
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (GameObject gameObject : gameObjects.getAll()) {
            gameObject.draw(g);
        }
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    eventListener.move(Direction.LEFT);
                }
                case KeyEvent.VK_RIGHT -> {
                    eventListener.move(Direction.RIGHT);
                }
                case KeyEvent.VK_UP -> {
                    eventListener.move(Direction.UP);
                }
                case KeyEvent.VK_DOWN -> {
                    eventListener.move(Direction.DOWN);
                }
                case KeyEvent.VK_R -> {
                    eventListener.restart();
                }
            }
        }
    }
}
