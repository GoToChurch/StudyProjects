package game_2048;

import java.util.*;

public class Model {
    private final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    public int score;
    public int maxTile;
    private final Stack<Tile[][]> previousStates = new Stack<>();
    private final Stack<Integer> previousScores = new Stack<>();
    private boolean isSaveNeeded = true;

    public Model() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        resetGame();
    }

    public List<Tile> getEmptyTiles() {
        List<Tile> emptyTiles = new ArrayList<>();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                Tile tile = gameTiles[i][j];
                if (tile.isEmpty()) {
                    emptyTiles.add(tile);
                }
            }
        }

        return emptyTiles;
    }

    public void addTile() {
        List<Tile> emptyTiles = getEmptyTiles();
        if (!emptyTiles.isEmpty()) {
            int index = (int) (Math.random() * emptyTiles.size()) % emptyTiles.size();
            Tile emptyTile = emptyTiles.get(index);
            emptyTile.value = Math.random() < 0.9 ? 2 : 4;
        }
    }

    public void resetGame() {
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }

    public boolean canMove() {
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].isEmpty()) {
                    return true;
                }
                else if (j < FIELD_WIDTH - 1 && gameTiles[j][i].value == gameTiles[j+1][i].value) {
                    return true;
                }
                else if (i < FIELD_WIDTH - 1 && gameTiles[j][i].value == gameTiles[j][i+1].value) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean result = false;
        int insertPostion = 0;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (!tiles[i].isEmpty()) {
                if (i != insertPostion) {
                    tiles[insertPostion] = tiles[i];
                    tiles[i] = new Tile();
                    result = true;
                }
                insertPostion++;

            }
        }

        return result;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean result = false;
        LinkedList<Tile> tilesList = new LinkedList<>();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (tiles[i].isEmpty()) {
                continue;
            }

            if (i < FIELD_WIDTH - 1 && tiles[i].value == tiles[i + 1].value) {
                int updatedValue = tiles[i].value * 2;
                if (updatedValue > maxTile) {
                    maxTile = updatedValue;
                }
                score += updatedValue;
                tilesList.addLast(new Tile(updatedValue));
                tiles[i + 1].value = 0;
                result = true;
            } else {
                tilesList.addLast(new Tile(tiles[i].value));
            }
            tiles[i].value = 0;
        }

        for (int i = 0; i < tilesList.size(); i++) {
            tiles[i] = tilesList.get(i);
        }
        return result;
    }

    public void left() {
        boolean needed = false;
        if (isSaveNeeded) {
            saveState(gameTiles);
        }

        for (Tile[] tiles : gameTiles) {
            boolean moved = compressTiles(tiles);
            boolean merged = mergeTiles(tiles);
            if (merged) {
                compressTiles(tiles);
            }
            if (moved || merged) {
                needed = true;
            }
        }

        if (needed) {
            addTile();
        }
        else {
            return;
        }
    }

    public void down() {
        saveState(gameTiles);
        rotateClockwise();
        left();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }

    public void right() {
        saveState(gameTiles);
        rotateClockwise();
        rotateClockwise();
        left();
        rotateClockwise();
        rotateClockwise();
    }

    public void up() {
        saveState(gameTiles);
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        left();
        rotateClockwise();
    }

    public void randomMove() {
        Random random = new Random();
        int move = random.nextInt(4);
        switch (move) {
            case 0 -> left();
            case 1 -> right();
            case 2 -> up();
            case 3 -> down();
        }
    }

    private void rotateClockwise() {
        Tile[][] rotated = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                rotated[j][FIELD_WIDTH-(i+1)] = gameTiles[i][j];
            }
        }
        gameTiles = rotated;
    }

    private void saveState(Tile[][] tiles) {
        Tile [][] newTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                newTiles[i][j] = new Tile(tiles[i][j].value);
            }
        }
        previousStates.push(newTiles);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    public void rollback() {
        try {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        } catch (EmptyStackException e) {
            e.printStackTrace();
        }

    }

    public boolean hasBoardChanged() {
        Tile[][] lastStackTiles = previousStates.peek();

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].value != lastStackTiles[i][j].value) {
                    return true;
                }
            }
        }

        return false;
    }

    public MoveEfficiency getMoveEfficiency(Move move) {
        MoveEfficiency moveEfficiency = new MoveEfficiency(-1, 0, move);
        move.move();
        if (hasBoardChanged()) {
            moveEfficiency = new MoveEfficiency(getEmptyTilesCount(), score, move);
        }
        rollback();
        return moveEfficiency;
    }

    public void autoMove() {
        PriorityQueue<MoveEfficiency> moves = new PriorityQueue<>(4, Collections.reverseOrder());
        moves.offer(getMoveEfficiency(this::left));
        moves.offer(getMoveEfficiency(this::right));
        moves.offer(getMoveEfficiency(this::down));
        moves.offer(getMoveEfficiency(this::up));
        moves.peek().getMove().move();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public int getEmptyTilesCount() {
        return getEmptyTiles().size();
    }

}
