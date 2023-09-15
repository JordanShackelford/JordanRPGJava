import java.awt.*;
import java.awt.geom.*;
import javax.swing.JPanel;

public class MyGraphics {
    public static void drawSelectionBox(Graphics2D g2d, int mouseX, int mouseY, JPanel canvas, int tileWidth, int tileHeight) {
        int tileX = (mouseX / tileWidth) * tileWidth;
        int tileY = (mouseY / tileHeight) * tileHeight;

        long t = System.currentTimeMillis();
        float f = 2000;
        float tN = (t % f) / f;
        float pF = (float) Math.sin(tN * Math.PI);

        // Gradient setup
        GradientPaint gp = new GradientPaint(tileX, tileY, Color.RED, tileX + tileWidth, tileY + tileHeight, Color.BLUE);
        g2d.setPaint(gp);

        // Line setup with thicker stroke
        g2d.setStroke(new BasicStroke(8 + 10 * pF, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        float[] dashPattern = {16 + 10 * pF, (16 + 10 * pF) * (1 + 0.2f * (float) Math.sin(t / 500.0))};
        g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f));

        // Draw the box
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(tileX, tileY, tileWidth, tileHeight, 10, 10);
        g2d.draw(roundedRectangle);

        // Draw circle
        Ellipse2D circle = new Ellipse2D.Float(tileX + tileWidth / 4, tileY + tileHeight / 4, tileWidth / 2, tileHeight / 2);
        g2d.draw(circle);
    }
}
