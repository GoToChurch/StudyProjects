package snake;

import java.awt.event.KeyEvent;

public class Room {
    private int width;
    private int height;
    private Mouse mouse;
    private Snake snake;
    public static Room game;


    public static void main(String[] args) throws InterruptedException {
        Snake snake = new Snake(25, 25);
        game = new Room(50, 50, snake);
        snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();
    }

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        game = this;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void run() throws InterruptedException {
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        while (snake.isAlive()) {
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                if (event.getKeyChar() == 'q') {
                    return;
                }
                if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                    snake.setDirection(SnakeDirection.LEFT);
                }
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                    snake.setDirection(SnakeDirection.RIGHT);
                }
                else if (event.getKeyCode() == KeyEvent.VK_UP) {
                    snake.setDirection(SnakeDirection.UP);
                }
                else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                    snake.setDirection(SnakeDirection.DOWN);
                }
            }
            snake.move();
            print();
            sleep();
        }
        System.out.println("GAME OVER :(");
    }

    public void print() {
        int[][] matrix = new int[getWidth()][getHeight()];
        for (SnakeSection snakeSection: snake.getSections()) {
            matrix[snakeSection.getX()][snakeSection.getY()] = 1;
        }
        matrix[snake.getX()][snake.getY()] = snake.isAlive() ? 2 : 4;
        matrix[mouse.getX()][mouse.getY()] = 3;

        String[] symbols = {" . ", " x ", " X ", " ^_^ ", " * "};
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                System.out.print(symbols[matrix[x][y]]);
            }
            System.out.println();

        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void createMouse () {
        this.mouse = new Mouse((int) (Math.random() * getWidth()), (int) (Math.random() * getHeight()));
    }

    public void eatMouse() {
        createMouse();
    }

    void sleep() throws InterruptedException {
        int sleep = 500;
        int size = snake.getSections().size();
        if (size >= 15) {
            sleep = 200;
        }
        else {
            for (int i = 0; i < size; i++) {
                sleep -= 20;
            }
        }
        Thread.sleep(sleep);
    }
}
