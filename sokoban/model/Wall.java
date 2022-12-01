package sokoban.model;

import java.awt.*;

public class Wall extends CollisionObject {
    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        int xc = getX();
        int yc = getY();
        int width = getWidth();
        int height = getHeight();

        g.fillRect(xc - width / 2, yc - height / 2, width, height);
    }
}
