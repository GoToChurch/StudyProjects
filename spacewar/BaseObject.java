package spacewar;

public class BaseObject {
    protected double x;
    protected double y;
    protected double radius;
    protected boolean isAlive;

    public BaseObject(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.isAlive = true;
    }

    public void draw(Canvas canvas) {

    }

    public void move() {

    }

    public void die() {
        this.isAlive = false;
    }

    public boolean isIntersect(BaseObject object) {
        double dx = getX() - object.getX();
        double dy = getY() - object.getY();
        double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double maxRadius = Math.max(getRadius(), object.getRadius());
        return distance <= maxRadius;
    }

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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
