public class Screen {
    int offsetX;
    int offsetY;
    int numRows;
    int numColumns;
    int[] mouseCanvasCoords;
    int[] oldSelectionBoxCoords;
    int[] selectionBoxCoords;
    int tileX;
    int tileY;
    int notificationSpacing;
    int numOfInventorySlots;

    public Screen() {
        offsetX = 0;
        offsetY = 0;
        numRows = 15;
        numColumns = 15;
        mouseCanvasCoords = new int[]{0, 0};
        oldSelectionBoxCoords = new int[]{0, 0};
        selectionBoxCoords = new int[]{0, 0};
        tileX = 0;
        tileY = 0;
        notificationSpacing = 30;
        numOfInventorySlots = 4;
    }

    public int getNotificationX(int width) {
        return (int) (0.02 * width);
    }

    public int getNotificationY(int height) {
        return (int) (0.045 * height);
    }

    public void updateMouseCoords(int x, int y) {
        this.mouseCanvasCoords = new int[]{x, y};
    }

    public void updateSelectionBox(int x, int y) {
        this.oldSelectionBoxCoords = this.selectionBoxCoords;
        this.selectionBoxCoords = new int[]{x, y};
    }
}
