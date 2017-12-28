package restaurant.ui.component;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class PictureLabel extends JPanel {
    private ImageIcon ii;
    public PictureLabel(int width, int height, String path) {
        setPreferredSize(new Dimension(width, height));
        //
        ii = new ImageIcon(path);
    }
    public PictureLabel(ImageIcon picture) {
        ii = picture;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(ii != null) {
            g.drawImage(ii.getImage(), 0, 0, getWidth(), getHeight(), ii.getImageObserver());
        }
    }
}
