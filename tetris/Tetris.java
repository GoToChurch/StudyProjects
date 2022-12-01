package tetris;

import java.awt.event.KeyEvent;

public class Tetris {
    private Field field;
    private Figure figure;
    static Tetris game;
    private boolean isGameOver;



    public Tetris(int width, int height) {
        field = new Field(width, height);
        figure = null;
    }

    public void run() throws InterruptedException {
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        isGameOver = false;
        figure = FigureFactory.createRandomFigure(field.getWidth() / 2, 0);

        while (!isGameOver) {
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                if (event.getKeyChar() == 'q') {
                    return;
                }
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> figure.left();
                    case KeyEvent.VK_RIGHT -> figure.right();
                    case KeyEvent.VK_SPACE -> figure.downMaximum();
                    case 12-> figure.rotate();
                }

                step();
                field.print();
                Thread.sleep(300);
            }
        }

        System.out.println("Game over :(");
    }

    public void step() {
        figure.down();
        if(!figure.isCurrentPositionAvailable()) {
            figure.up();
            figure.landed();
            isGameOver = figure.getY() <= 1;
            field.removeFullLines();
            figure = FigureFactory.createRandomFigure(field.getWidth() / 2, 0);
        }
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public static void main(String[] args) throws InterruptedException {
        game = new Tetris(10, 10);
        game.run();
    }
}
