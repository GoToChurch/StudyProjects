package spacewar;

import arkanoid.Arkanoid;

public class SpaceShip extends BaseObject {
    private double dx = 0;
    private static int[][] matrix = {
            {0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0},
            {1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1},
    };

    public SpaceShip(double x, double y) {
        super(x, y, 3);
    }

    public void moveLeft() {
        this.dx = -1;
    }

    public void moveRight() {
        this.dx = 1;
    }

    @Override
    public void move() {
        setX(getX() + dx);
        checkBorders(radius, Space.game.getWidth() - radius + 1, 1, Space.game.getHeight() + 1);

    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawMatrix(x - radius + 1, y - radius + 1, matrix, 'M');
    }

    public void fire() {
        Space.game.getRockets().add(new Rcoket(x - 2, y ));
        Space.game.getRockets().add(new Rcoket(x + 2, y));
    }
}
