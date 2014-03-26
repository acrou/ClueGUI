import javax.swing.*;
import java.awt.*;

/**
 * Created by Marissa on 3/25/14.
 */
public class Tester extends JPanel {
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
       // g.setColor(Color.black);
        g.drawString("BLAH", 20, 20);

    }
    public static void main (String [] args){
        Tester t = new Tester ();
        JFrame j = new JFrame();
        Container c = j.getContentPane();
        JPanel panel = new JPanel();
        c.add(panel);
        j.setVisible(true);
        j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        j.add(t);
    }
}
