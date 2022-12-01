package sokoban.model;

import atmEmulator.ATM;
import sokoban.controller.EventListener;

import java.nio.file.Path;

public class Model {
    public static final int FIELD_CELL_SIZE = 20;
    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(Path.of("C:\\Users\\Admin\\IdeaProjects\\Trying\\src\\sokoban\\res\\levels.txt"));

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        //currentLevel++;
        restart();
    }

    public void move(Direction direction) {
        Player player = gameObjects.getPlayer();
        if (checkWallColision(player, direction)) {
            return;
        }

        if(checkBoxCollisionAndMoveIfAvailable(direction)) {
            return;
        }

        int dx = direction == Direction.LEFT ? -FIELD_CELL_SIZE : (direction == Direction.RIGHT ? FIELD_CELL_SIZE : 0);
        int dy = direction == Direction.UP ? - FIELD_CELL_SIZE : (direction == Direction.DOWN ? FIELD_CELL_SIZE : 0);
        player.move(dx, dy);

        checkCompletion();
    }

    public boolean checkWallColision(CollisionObject gameObject, Direction direction) {
        for (Wall wall : gameObjects.getWalls()) {
            if (gameObject.isCollision(wall, direction)) {
                return true;
            }
        }

        return false;
    }

    public boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
        for (Box box : gameObjects.getBoxes()) {
            if (gameObjects.getPlayer().isCollision(box, direction)) {
                if (checkWallColision(box, direction)) {
                    return true;
                }
                for (Box box2 : gameObjects.getBoxes()) {
                    if (box.isCollision(box2, direction)) {
                        return true;
                    }
                }
                int dx = direction == Direction.LEFT ? -FIELD_CELL_SIZE : (direction == Direction.RIGHT ? FIELD_CELL_SIZE : 0);
                int dy = direction == Direction.UP ? - FIELD_CELL_SIZE : (direction == Direction.DOWN ? FIELD_CELL_SIZE : 0);
                box.move(dx, dy);
            }
        }

        return false;
    }

    public void checkCompletion() {
        int fullHomeNumber = 0;

        for (Box box : gameObjects.getBoxes()) {
            for (Home home : gameObjects.getHomes()) {
                if (box.x == home.x && box.y == home.y) {
                    fullHomeNumber++;
                }
            }
        }

        if(fullHomeNumber == gameObjects.getHomes().size()) {
            eventListener.levelCompleted(currentLevel);
        }
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }
}
