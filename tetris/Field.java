package tetris;

import java.util.Arrays;

public class Field {
    private int width;
    private int height;
    private int[][] matrix;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new int[height][width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void print() {
        int[][] canvas = new int[height][width];

        for (int i = 0; i <height ; i++) {
            for (int j = 0; j < width; j++) {
                canvas[i][j] = matrix[i][j];
            }
        }

        int left = Tetris.game.getFigure().getX();
        int top = Tetris.game.getFigure().getY();
        int[][] brickMatrix = Tetris.game.getFigure().getMatrix();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (top + i >= height || left + j >= height) {
                    continue;
                }
                if (brickMatrix[i][j] == 1) {
                    canvas[top + i][left + j] = 2;
                }
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------");

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = matrix[i][j];
                if (index == 0) {
                    System.out.print(" . ");
                }
                else if (index == 1 || index == 2) {
                    System.out.print(" X ");
                }
                else {
                    System.out.print("???");
                }
            }
            System.out.println('\n');
        }
        System.out.println();
        System.out.println();
    }

    public void removeFullLines() {
        int[] fullLine = new int[height];
        int[][] newMatrix = new int[height][width];
        int count = 0;

        for (int i = 0; i < height; i++) {
            fullLine[i] = 1;
        }

        for (int i = 0; i < height; i++) {
            if (!Arrays.equals(matrix[i], fullLine)) {
                newMatrix[count] = matrix[i];
                count++;
            }
        }

        for (int i = count; i < height; i++) {
            newMatrix[count] = new int[height];
        }

        matrix = newMatrix;
    }

    public Integer getValue(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return matrix[y][x];
        }

        return null;
    }

    public void setVakue(int x, int y, int value) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            matrix[y][x] = value;
        }
    }

}
