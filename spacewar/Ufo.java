package spacewar;

public class Ufo extends BaseObject {
    private static int[][] matrix = {
            {0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0},
            {1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1},
    };

    public Ufo(double x, double y) {
        super(x, y, 3);
    }

    public void move() {
        double dx = Math.random() * 2 -1;
        double dy = Math.random() * 2 -1;
        x += dx;
        if (y + dy <= Space.game.getHeight() / 2) {
            y += dy;
        }
        checkBorders(radius, Space.game.getWidth() - radius + 1, radius - 1, Space.game.getHeight() / 2 - 1);

        if (Math.random() <= 0.1) {
            fire();
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawMatrix(x - radius + 1, y - radius + 1, matrix, 'U');
    }

    public void fire() {
        Space.game.getBombs().add(new Bomb(x, y+3));
    }
}
