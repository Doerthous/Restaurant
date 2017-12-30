package restaurant.ui.component;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class PicturePanel extends JPanel {
    private ImageIcon ii;
    public PicturePanel(String path){
        ii = new ImageIcon(path);
    }
    public PicturePanel(ImageIcon picture) {
        ii = picture;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(ii != null) {
            g.drawImage(ii.getImage(), 0, 0, getWidth(), getHeight(), ii.getImageObserver());
        }
    }
    public PicturePanel setPicture(ImageIcon picture){
        ii = picture;
        return this;
    }
}
