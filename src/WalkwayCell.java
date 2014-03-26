import java.awt.*;
import java.awt.geom.Rectangle2D;


public class WalkwayCell extends BoardCell {

    public WalkwayCell(int row, int column) {
        super(row, column);
    }

    public boolean isWalkway() {
        return true;
    }

    @Override
    public void draw(Graphics g) {
       // Rectangle2D.Double outline = new Rectangle2D.Double(getColumn()*20, getRow()*20, 20, 20);
//        g.setColor(Color.BLACK);
//      //  g.fill(outline);
//     g.drawRect(getColumn()*20, getRow()*20, 20, 20);
//       g.setColor(Color.YELLOW);
//       g.fillRect(getColumn()*20, getRow()*20, 20, 20);

        Graphics2D g2;
         g2 = (Graphics2D) g;
        float thickness = 2;
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(thickness));
        g2.drawRect(getColumn()*20, getRow()*20, 20, 20);
        g2.setStroke(oldStroke);


    }


    // future draw function override here
}
