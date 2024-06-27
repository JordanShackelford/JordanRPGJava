import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class JordanRPG extends JPanel implements ActionListener {
    private int[][] tileMap;
    private Timer timer;
    private BufferedImage dirtImage, waterImage, waterImage2, grassImage;
    private boolean useWater1 = true;
    private long lastToggleTime = 0;
    int mouseX = 0;
    int mouseY = 0;

    public JordanRPG() {
        tileMap = ChunkGenerator.generateChunk(); // Correctly initialize the instance variable
        try {
            dirtImage = ImageIO.read(getClass().getResource("res/dirt.jpg"));
            waterImage = ImageIO.read(getClass().getResource("res/water1.png"));
            waterImage2 = ImageIO.read(getClass().getResource("res/water2.png"));
            grassImage = ImageIO.read(getClass().getResource("res/grass.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer = new Timer(16, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'q') {
                    System.exit(0);
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                repaint(); // trigger paintComponent to be called again
            }
        });

        setFocusable(true);
        requestFocusInWindow(); // Request focus to ensure key events are captured
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastToggleTime >= 500) {
            useWater1 = !useWater1;
            lastToggleTime = currentTime;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawMap(g2d);

        // Draw the selection box
        int tileWidth = getWidth() / tileMap.length;
        int tileHeight = getHeight() / tileMap[0].length;

        int selectedTileX = (mouseX / tileWidth) * tileWidth;
        int selectedTileY = (mouseY / tileHeight) * tileHeight;

        g2d.setColor(Color.RED);
        g2d.drawRect(selectedTileX, selectedTileY, tileWidth, tileHeight);
    }

    public void drawMap(Graphics2D g2d) {
        int tileWidth = getWidth() / tileMap.length;
        int tileHeight = getHeight() / tileMap[0].length;

        for (int x_Index = 0; x_Index < tileMap.length; x_Index++) {
            for (int y_Index = 0; y_Index < tileMap[0].length; y_Index++) {
                int tileValue = tileMap[x_Index][y_Index];
                int x = tileWidth * x_Index;
                int y = tileHeight * y_Index;

                switch (tileValue) {
                    case 0:
                        g2d.drawImage(grassImage, x, y, tileWidth, tileHeight, null);
                        break;
                    case 1:
                        g2d.drawImage(useWater1 ? waterImage : waterImage2, x, y, tileWidth, tileHeight, null);
                        break;
                    case 2:
                        g2d.drawImage(dirtImage, x, y, tileWidth, tileHeight, null);
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Jordan RPG");
        JordanRPG jordanRPG = new JordanRPG();
        frame.add(jordanRPG);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
