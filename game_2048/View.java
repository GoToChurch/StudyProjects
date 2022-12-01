package game_2048;

import javax.swing.*;
import java.awt.*;

public class View extends JPanel {
    private static final Color BG_COLOR = new Color(0xbbada0);
    private static final String FONT_NAME = "Arial";
    private static final int TILE_SIZE = 96;
    private static final int TILE_MARGIN = 12;

    private Contoller contoller;

    boolean isGameWon = false;
    boolean isGameLost = false;

    public View(Contoller contoller) {
        setFocusable(true);
        this.contoller = contoller;
        addKeyListener(contoller);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BG_COLOR);
        g.fillRect(0,0, this.getSize().width, this.getSize().height);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                drawTile(g, contoller.getGameTiles()[i][j], i, j);
            }
        }

        g.drawString("Score" + contoller.getScore(), 140, 465);

        if (isGameWon) {
            JOptionPane.showMessageDialog(this, "You've won!");
        }
        else if (isGameLost) {
            JOptionPane.showMessageDialog(this, "You've lost!");
        }
    }

    private void drawTile(Graphics graphics, Tile tile, int x, int y) {
        Graphics2D g = ((Graphics2D) graphics);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int value = tile.value;
        int xOffSet = offsetCoors(x);
        int yOffSet = offsetCoors(y);
        g.setColor(tile.getTileColor());
        g.fillRoundRect(xOffSet, yOffSet, TILE_SIZE, TILE_SIZE, 8, 8);
        g.setColor(tile.getFontColor());
        final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
        final Font font = new Font(FONT_NAME, Font.BOLD, size);
        g.setFont(font);

        String s = String.valueOf(tile.value);
        final FontMetrics fm = getFontMetrics(font);

        final int w = fm.stringWidth(s);
        final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

        if (value !=0) {
            g.drawString(s, xOffSet + (TILE_SIZE - w) / 2, yOffSet + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);
        }
    }

    private static int offsetCoors(int arg) {
        return arg * (TILE_MARGIN + TILE_SIZE) + TILE_MARGIN;
    }
}
