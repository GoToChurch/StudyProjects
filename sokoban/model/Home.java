package sokoban.model;

import java.awt.*;

public class Home extends GameObject {
    public Home(int x, int y) {
        super(x, y, 2, 2);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);

        int xc = getX();
        int yc = getY();
        int width = getWidth();
        int height = getHeight();

        g.drawOval(xc - width / 2, yc - height / 2, width, height);
    }
}
