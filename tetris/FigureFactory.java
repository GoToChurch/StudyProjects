package tetris;

import java.util.Random;

public class FigureFactory {
    public static final int[][][] BRICKS = {
            {{1, 1, 0}, {0, 1, 1}, {0, 0, 0}},
            {{1, 0, 0}, {1, 1, 0}, {0, 1, 0}},
            {{0, 1, 0}, {0, 1, 0}, {0, 1, 0}},
            {{1, 1, 0}, {1, 1, 0}, {0, 0, 0}},
            {{1, 1, 1}, {0, 1, 0}, {0, 0, 0}},
            {{1, 1, 1}, {1, 1, 1}, {0, 0, 0}}
    };

    public static Figure createRandomFigure(int x, int y) {
        Random random = new Random();
        return new Figure(x, y, BRICKS[random.nextInt(6)]);
    }
}
