package restaurant.ui.component;

import restaurant.ui.component.builder.JLabelBuilder;

import javax.swing.*;
import java.awt.*;

public class JTextFieldEx extends RectangleCard {
    private JTextField jtf;
    public JTextFieldEx(String tip) {
        setLayout(new BorderLayout(10,0));
        add(JLabelBuilder.getInstance().text(tip).build(), BorderLayout.WEST);
        jtf = new JTextField();
        add(jtf, BorderLayout.CENTER);
    }

    public String getText(){
        return jtf.getText();
    }
}
