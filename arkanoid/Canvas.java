package arkanoid;

public class Canvas {
    private int width;
    private int height;
    private char[][] matrix;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new char[height+2][width+2];
    }

    public void setPoint(double x, double y, char c) {
        int first = (int) Math.round(y);
        int second = (int) Math.round(x);

        if (!(x < 0) || !(y < 0) || !(y >= matrix.length) || !(x >= matrix[0].length)) {
            matrix[first][second] = c;
        }
    }

    public void drawMatrix(double x, double y, int[][] matrix, char c) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    setPoint(x+j, y+i, c);
                }
            }
        }
    }

    public void clear() {
        this.matrix = new char[getHeight()+2][getWidth()+2];
    }

    public void print() {
        for (char[] m : matrix) {
            System.out.println(m);
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public char[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(char[][] matrix) {
        this.matrix = matrix;
    }
}
