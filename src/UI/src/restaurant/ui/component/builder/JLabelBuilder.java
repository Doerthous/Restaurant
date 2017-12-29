package restaurant.ui.component.builder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseListener;

public class JLabelBuilder {
    private JLabel label;
    private JLabelBuilder(){
        label = new JLabel();
    }
    public JLabelBuilder text(String text){
        label.setText(text);
        return this;
    }
    public JLabelBuilder horizontalAlignment(int horizontalAlignment){
        label.setHorizontalAlignment(horizontalAlignment);
        return this;
    }
    public JLabelBuilder verticalAlignment(int verticalAlignment){
        label.setVerticalAlignment(verticalAlignment);
        return this;
    }
    public JLabelBuilder font(Font font){
        label.setFont(font);
        return this;
    }
    public JLabelBuilder foreground(Color foreground){
        label.setForeground(foreground);
        return this;
    }
    public JLabelBuilder mouseListener(MouseListener mouseListener){
        label.addMouseListener(mouseListener);
        return this;
    }
    public JLabelBuilder border(Border border){
        label.setBorder(border);
        return this;
    }
    public JLabelBuilder preferredSize(Dimension preferredSize){
        label.setPreferredSize(preferredSize);
        return this;
    }
    public JLabelBuilder opaque(boolean opaque){
        label.setOpaque(opaque);
        return this;
    }
    public JLabel build(){
        return label;
    }
    public static JLabelBuilder getInstance(){
        return new JLabelBuilder();
    }
}
