package spacewar;

public class Rcoket extends BaseObject {
    public Rcoket(double x, double y) {
        super(x, y, 1);
    }

    public void move() {
        y--;
    }

    public void draw(Canvas canvas) {
        canvas.setPoint(x, y, 'R');
    }
}
