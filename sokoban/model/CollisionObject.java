package sokoban.model;

public abstract class CollisionObject extends GameObject {

    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {
        int newX = x;
        int newY = y;

        switch (direction) {
            case UP -> newY -= Model.FIELD_CELL_SIZE;
            case DOWN -> newY += Model.FIELD_CELL_SIZE;
            case LEFT -> newX -= Model.FIELD_CELL_SIZE;
            case RIGHT -> newX += Model.FIELD_CELL_SIZE;
        }

        return gameObject.x == newX && gameObject.y == newY;
    }
}
