import java.awt.*;

public class WalkwayCell extends BoardCell {

    public WalkwayCell(int row, int column) {
        super(row, column);
    }

    public boolean isWalkway() {
        return true;
    }

    @Override
    public void draw(Graphics g, Board b, int x, int y) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 20, 20);
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 20, 20);
    }
    // future draw function override here
}
