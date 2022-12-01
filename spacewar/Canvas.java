package spacewar;

public class Canvas {
    private int width;
    private int height;
    private char[][] matrix = new char[height + 2][width + 2];

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setPoint(double x, double y, char c) {
        int first = (int) Math.round(y);
        int second = (int) Math.round(x);

        if (second >= 0 && second < matrix[0].length && first >= 0 && first < matrix.length) {
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
        this.matrix = new char[height + 2][width + 2];
    }

    public void print() {
        for (char[] m : matrix) {
            System.out.println(m);
        }
        System.out.println();
        System.out.println();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char[][] getMatrix() {
        return matrix;
    }
}
