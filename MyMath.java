import java.awt.Rectangle;
import javax.swing.JPanel;

public class MyMath {

    public static int[] calculateCanvasCoordsFromWindowCoords(int x, int y, JPanel canvas) {
        Rectangle r = canvas.getBounds();
        int c = (int) java.lang.Math.round(((double) (x - r.x) / (double) (r.width)) * canvas.getWidth());
        int d = (int) java.lang.Math.round(((double) (y - r.y) / (double) (r.height)) * canvas.getHeight());
        return new int[]{c, d};
    }
}
