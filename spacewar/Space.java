package spacewar;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Space {
    private int width;
    private int height;
    private SpaceShip ship;
    private List<Ufo> ufos = new ArrayList<>();
    private List<Rcoket> rockets = new ArrayList<>();
    private List<Bomb> bombs = new ArrayList<>();
    public static Space game;

    public static void main(String[] args) {
        game = new Space(20, 20);
        game.setShip(new SpaceShip(10, 18));
        game.run();
    }

    public Space(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void run() {
        Canvas canvas = new Canvas(width, height);
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        while (ship.isAlive()) {
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                    ship.moveLeft();
                }
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                    ship.moveRight();
                }
                else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                    ship.fire();
                }
            }

            moveAllItems();

            checkBombs();
            checkRockets();
            removeDead();

            createUfo();

            canvas.clear();
            draw(canvas);
            canvas.print();

            Space.sleep(300);
        }
        System.out.println("Game over :(");
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < width + 2; i++) {
            for (int j = 0; j < height + 2; j++) {
                canvas.setPoint(i, j, '.');
            }
        }

        for (int i = 0; i < width + 2; i++) {
            canvas.setPoint(i, 0, '-');
            canvas.setPoint(i, height + 1, '-');
        }

        for (int i = 0; i < height + 2; i++) {
            canvas.setPoint(0, i, '|');
            canvas.setPoint(width + 1, i, '|');
        }

        for (BaseObject object : getAllItems()) {
            object.draw(canvas);
        }
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<BaseObject> getAllItems() {
        List<BaseObject> allItems = new ArrayList<>();
        allItems.add(ship);
        allItems.addAll(ufos);
        allItems.addAll(rockets);
        allItems.addAll(bombs);

        return allItems;
    }

    public void moveAllItems() {
        List<BaseObject> allItems = getAllItems();

        for (BaseObject object : allItems) {
            object.move();
        }
    }

    public void createUfo() {
        if(ufos.isEmpty() && Math.random() <= 0.1) {
            ufos.add(new Ufo(Math.random() * width, Math.random() * height / 2));
        }
    }

    public void checkRockets() {
        for (Rcoket rocket : rockets) {
            if (rocket.y < 0) {
                rocket.die();
                continue;
            }
            for (Ufo ufo : ufos) {
                if (rocket.isIntersect(ufo)) {
                    rocket.die();
                    ufo.die();
                }
            }
        }
    }

    public void checkBombs() {
        for (Bomb bomb : bombs) {
            if (bomb.y > height) {
                bomb.die();
                continue;
            }
            if (bomb.isIntersect(ship)) {
                bomb.die();
                ship.die();
            }
        }
    }

    public void removeDead() {
        for (BaseObject object : getAllItems()) {
            if (!object.isAlive()) {
                if (object instanceof Ufo) {
                    ufos.remove(object);
                }
                else if (object instanceof Rcoket) {
                    rockets.remove(object);
                }
                else {
                    bombs.remove(object);
                }
            }

        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SpaceShip getShip() {
        return ship;
    }

    public void setShip(SpaceShip ship) {
        this.ship = ship;
    }

    public List<Ufo> getUfos() {
        return ufos;
    }

    public List<Rcoket> getRockets() {
        return rockets;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }
}
