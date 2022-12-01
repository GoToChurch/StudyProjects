package game_2048;

public class MoveEfficiency implements Comparable<MoveEfficiency> {
    private int numberOfEmptyTiles;
    private int score;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency o) {
        int emptyTilesDifferency = this.numberOfEmptyTiles - o.numberOfEmptyTiles;
        if (emptyTilesDifferency == 0) {
            int scoreDifferency = this.score - o.score;
            if (scoreDifferency == 0) {
                return 0;
            }
            else {
                return scoreDifferency;
            }
        }
        else {
            return emptyTilesDifferency;
        }
    }
}
