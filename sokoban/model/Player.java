package sokoban.model;

import java.awt.*;

public class Player extends CollisionObject implements Movable {

    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);

        int xc = getX();
        int yc = getY();
        int width = getWidth();
        int height = getHeight();

        g.fillOval(xc - width / 2, yc - height / 2, width, height);
    }
}

