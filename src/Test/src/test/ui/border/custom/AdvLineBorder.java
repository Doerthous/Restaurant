package test.ui.border.custom;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class AdvLineBorder extends AbstractBorder {

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;

            Color oldColor = g2d.getColor();
            g2d.setColor(Color.black);

            g2d.drawLine(x, y,x+width, y+height);

            g2d.setColor(oldColor);
        }
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0,0,0,0);
    }
}
