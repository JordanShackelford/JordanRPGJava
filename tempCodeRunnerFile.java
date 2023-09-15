import javax.swing.*;
import java.awt.*;

public class JordanRPG {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Map");
        GameMap gameMap = new GameMap();
        gameMap.generateChunk();
        frame.add(gameMap);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static class GameMap extends JPanel {
        private int[][] tileMap;
        private int tileWidth = 32;
        private int tileHeight = 32;
        private int offsetX = 0;
        private int offsetY = 0;
        private int numColumns = 10;
        private int numRows = 10;

        public void generateChunk() {
            int c = 10;
            tileMap = new int[c][c];
            for (int r = 0; r < c; r++) {
                for (int j = 0; j < c; j++) {
                    tileMap[r][j] = (int) (Math.random() * 3);
                }
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (tileMap == null) return;

            for (int x_Index = 0; x_Index < numColumns; x_Index++) {
                for (int y_Index = 0; y_Index < numRows; y_Index++) {
                    int tileValue = tileMap[x_Index][y_Index];

                    if (tileValue < 0 || tileValue > 2) continue;

                    int x = tileWidth * x_Index + offsetX;
                    int y = tileHeight * y_Index + offsetY;

                    switch (tileValue) {
                        case 0:
                            g.setColor(new Color(0, 128, 0));
                            break;
                        case 1:
                            g.setColor(new Color(0, 0, 128));
                            break;
                        case 2:
                            g.setColor(new Color(139, 69, 19));
                            break;
                    }
                    g.fillRect(x, y, tileWidth, tileHeight);
                }
            }
        }
    }
}
