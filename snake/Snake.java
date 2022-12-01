package snake;


import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public Snake(int x, int y) {
        this.sections = new ArrayList<>();
        this.isAlive = true;
        sections.add(new SnakeSection(x, y));
    }

    public List<SnakeSection> getSections() {
        return sections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public void move() {
        if (!isAlive) {
            return;
        }
        switch (direction) {
            case UP -> move(-1, 0);
            case DOWN -> move(1, 0);
            case RIGHT -> move(0, 1);
            case LEFT -> move(0, -1);
        }
    }

    public void move(int x, int y) {
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX() + x, head.getY() + y);

        checkBorders(head);
        checkBody(head);
        if (!isAlive()) {
            return;
        }

        Mouse mouse = Room.game.getMouse();
        if (head.getX() == mouse.getX() && head.getY() == mouse.getY()) {
            sections.add(0, head);
            Room.game.eatMouse();
        }
        else {
            sections.add(0, head);
            sections.remove(sections.size() - 1);
        }
    }

    public void checkBorders(SnakeSection head) {
        if (head.getY() < 0 || head.getY() > Room.game.getHeight() || head.getX() < 0 || head.getX() > Room.game.getWidth()) {
            isAlive = false;
        }
    }

    public void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }
}
