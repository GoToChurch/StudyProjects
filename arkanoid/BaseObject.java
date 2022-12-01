package arkanoid;

public abstract class BaseObject {
    protected double x;
    protected double y;
    protected double radius;

    public BaseObject(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public abstract void move();

    public abstract void draw(Canvas canvas);

    public void checkBorders(double minx, double maxx, double miny, double maxy) {
        if (x < minx) {
            x = minx;
        }
        if (x > maxx) {
            x = maxx;
        }
        if (y < miny) {
            y = miny;
        }
        if (y > maxy) {
            y = maxy;
        }
    }

    public boolean intersects(BaseObject obj) {
        double dx = getX() - obj.getX();
        double dy = getY() - obj.getY();
        double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double maxRadius = Math.max(getRadius(), obj.getRadius());
        return distance <= maxRadius;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
