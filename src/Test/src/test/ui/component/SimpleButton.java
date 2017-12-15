package test.ui.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SimpleButton extends JButton {
    Color enterColor;
    Color exitedColor;
    public SimpleButton(String text, Color enterColor, Color exitedColor, ActionListener actionListener) {
        super(text);
        this.enterColor = enterColor;
        this.exitedColor = exitedColor;
        addActionListener(actionListener);
        setFocusPainted(false);
        setBorderPainted(false);
        setBackground(exitedColor);
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setBackground(enterColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setBackground(exitedColor);
            }
        });
    }
}
