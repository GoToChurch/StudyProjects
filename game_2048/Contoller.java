package game_2048;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Contoller extends KeyAdapter {
    private Model model;
    private View view;
    private final int WINNING_TILE = 2048;

    public Contoller(Model model) {
        this.model = model;
        this.view = new View(this);
    }

    public Tile[][] getGameTiles() {
        return model.getGameTiles();
    }

    public int getScore() {
        return model.score;
    }

    public void resetGame() {
        model.score = 0;
        view.isGameWon = false;
        view.isGameLost = false;
        model.resetGame();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            resetGame();
        }
        if (!model.canMove()) {
            view.isGameLost = true;
        }

        if (!view.isGameLost && !view.isGameWon) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> model.up();
                case KeyEvent.VK_RIGHT -> model.down();
                case KeyEvent.VK_UP -> model.left();
                case KeyEvent.VK_DOWN -> model.right();
                case KeyEvent.VK_Z -> model.rollback();
                case KeyEvent.VK_R -> model.randomMove();
                case KeyEvent.VK_A -> model.autoMove();
            }
        }

        if (model.maxTile == WINNING_TILE) {
            view.isGameWon = true;
        }

        view.repaint();
    }

    public View getView() {
        return view;
    }

}
