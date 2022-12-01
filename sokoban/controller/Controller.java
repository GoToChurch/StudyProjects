package sokoban.controller;

import sokoban.model.Direction;
import sokoban.model.GameObjects;
import sokoban.model.Model;
import sokoban.view.View;

public class Controller implements EventListener {
    private Model model;
    private View view;

    public static void main(String[] args) {
        new Controller();
    }

    public Controller() {
        this.model = new Model();
        this.view = new View(this);

        view.init();
        model.restart();

        model.setEventListener(this);
        view.setEventListener(this);

    }

    public GameObjects getGameObjects() {
        return model.getGameObjects();
    }

    @Override
    public void move(Direction direction) {
        model.move(direction);
        view.update();
    }

    @Override
    public void restart() {
        model.restart();
        view.update();
    }

    @Override
    public void startNextLevel() {
        model.startNextLevel();
        view.update();
    }

    @Override
    public void levelCompleted(int level) {
        view.completed(level);
    }
}
