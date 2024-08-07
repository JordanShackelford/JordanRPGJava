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

    // Draw the enhanced selection box
    int tileWidth = getWidth() / tileMap.length;
    int tileHeight = getHeight() / tileMap[0].length;

    int selectedTileX = (mouseX / tileWidth) * tileWidth;
    int selectedTileY = (mouseY / tileHeight) * tileHeight;

    // Radial gradient fill
    RadialGradientPaint radialGradient = new RadialGradientPaint(
        new Point(selectedTileX + tileWidth / 2, selectedTileY + tileHeight / 2), 
        tileWidth / 2, 
        new float[]{0f, 1f}, 
        new Color[]{new Color(255, 255, 255, 100), new Color(255, 0, 0, 100)}
    );
    g2d.setPaint(radialGradient);
    g2d.fillRect(selectedTileX, selectedTileY, tileWidth, tileHeight);

    // Pulsating border effect
    long currentTime = System.currentTimeMillis();
    float pulsate = (float) (Math.sin(currentTime * 0.005) * 0.5 + 0.5);

    GradientPaint gradient = new GradientPaint(
        selectedTileX, selectedTileY, new Color(255, 255, 0, (int)(pulsate * 255)), 
        selectedTileX + tileWidth, selectedTileY + tileHeight, new Color(255, 0, 0, (int)(pulsate * 255))
    );
    g2d.setPaint(gradient);
    g2d.setStroke(new BasicStroke(5)); // Thicker border
    g2d.drawRect(selectedTileX, selectedTileY, tileWidth, tileHeight);

    // Optional shadow effect
    g2d.setColor(new Color(0, 0, 0, 50));
    g2d.fillRect(selectedTileX + 5, selectedTileY + 5, tileWidth, tileHeight);
}



public void drawMap(Graphics2D g2d) {
    int panelWidth = getWidth();
    int panelHeight = getHeight();
    int tileWidth = panelWidth / tileMap.length;
    int tileHeight = panelHeight / tileMap[0].length;

    // Adjusted dimensions to ensure full coverage
    int extraWidth = panelWidth % tileMap.length;
    int extraHeight = panelHeight % tileMap[0].length;

    for (int x_Index = 0; x_Index < tileMap.length; x_Index++) {
        for (int y_Index = 0; y_Index < tileMap[0].length; y_Index++) {
            int tileValue = tileMap[x_Index][y_Index];
            int x = tileWidth * x_Index;
            int y = tileHeight * y_Index;

            // Adjust tile width for the last column
            int currentTileWidth = tileWidth;
            if (x_Index == tileMap.length - 1) {
                currentTileWidth += extraWidth;
            }

            // Adjust tile height for the last row
            int currentTileHeight = tileHeight;
            if (y_Index == tileMap[0].length - 1) {
                currentTileHeight += extraHeight;
            }

            switch (tileValue) {
                case 0:
                    g2d.drawImage(grassImage, x, y, currentTileWidth, currentTileHeight, null);
                    break;
                case 1:
                    g2d.drawImage(useWater1 ? waterImage : waterImage2, x, y, currentTileWidth, currentTileHeight, null);
                    break;
                case 2:
                    g2d.drawImage(dirtImage, x, y, currentTileWidth, currentTileHeight, null);
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
