package restaurant.ui.component;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JButtonBuilder {
    private JButton button;
    private JButtonBuilder(){
        button = new JButton();
    }
    public JButtonBuilder text(String text){
        button.setText(text);
        return this;
    }
    public JButtonBuilder listener(ActionListener listener){
        button.addActionListener(listener);
        return this;
    }
    public JButtonBuilder background(Color background){
        button.setBackground(background);
        return this;
    }
    public JButtonBuilder foreground(Color foreground){
        button.setForeground(foreground);
        return this;
    }
    public JButtonBuilder font(Font font){
        button.setFont(font);
        return this;
    }
    public JButtonBuilder opaque(Boolean opaque){
        button.setOpaque(opaque);
        return this;
    }
    public JButtonBuilder focusPainted(Boolean focusPainted){
        button.setFocusPainted(focusPainted);
        return this;
    }
    public JButtonBuilder borderPainted(Boolean borderPainted){
        button.setBorderPainted(borderPainted);
        return this;
    }
    public JButtonBuilder contentAreaFilled(Boolean contentAreaFilled){
        button.setContentAreaFilled(contentAreaFilled);
        return this;
    }
    public JButtonBuilder actionCommand(String actionCommand){
        button.setActionCommand(actionCommand);
        return this;
    }
    public JButtonBuilder preferredSize(Dimension dimension){
        button.setPreferredSize(dimension);
        return this;
    }
    public JButtonBuilder mouseListener(MouseListener listener){
        button.addMouseListener(listener);
        return this;
    }
    public JButtonBuilder enteredColor(Color background){
        mouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                button.setBackground(background);
            }
        });
        return this;
    }
    public JButtonBuilder exitedColor(Color background){
        mouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                button.setBackground(background);
            }
        });
        return this;
    }
    public JButtonBuilder border(Border border){
        button.setBorder(border);
        return this;
    }
    public JButtonBuilder icon(Icon icon){
        button.setIcon(icon);
        return this;
    }
    public JButton build(){
        return button;
    }

    public static JButtonBuilder getInstance(){
        return new JButtonBuilder();
    }

}
