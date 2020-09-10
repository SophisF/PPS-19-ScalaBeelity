package view.archive;

import java.awt.*;
import javax.swing.JFrame;

public class DisplayGraphics extends Canvas {

    public void paint(Graphics g) {
        //g.drawString("Hello",40,40);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 200));
        g.fillRect(130, 30, 100, 80);

        g.fillRect(130, 30, 100, 80);
        g.fillRect(130, 30, 100, 80);

        g.drawOval(30, 130, 50, 60);
        setForeground(Color.RED);
        g.fillOval(130, 130, 50, 60);
        g.drawArc(30, 200, 40, 50, 90, 60);
        g.fillArc(30, 130, 40, 50, 180, 40);

    }
}
