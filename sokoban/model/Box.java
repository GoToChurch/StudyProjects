package sokoban.model;

import java.awt.*;

public class Box extends CollisionObject implements Movable {

    public Box(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        int xc = getX();
        int yc = getY();
        int width = getWidth();
        int height = getHeight();

        g.drawRect(xc - width / 2, yc - height / 2, width, height);
        g.drawLine(xc - width / 2, yc - height / 2, xc + width / 2, yc + height / 2);
        g.drawLine(xc + width / 2, yc + height / 2, xc - width / 2, yc - height / 2);
    }
}
