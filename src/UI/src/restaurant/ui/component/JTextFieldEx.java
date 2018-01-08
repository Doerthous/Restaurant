package restaurant.ui.component;

import restaurant.ui.component.builder.JLabelBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class JTextFieldEx extends RectangleCard {
    private JTextField jtf;
    public JTextFieldEx(String tip, ActionListener listener) {
        setLayout(new BorderLayout(10,0));
        add(JLabelBuilder.getInstance().text(tip).build(), BorderLayout.WEST);
        jtf = new JTextField();
        jtf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                listener.actionPerformed(new ActionEvent(this, 1, ""));
            }
        });
        add(jtf, BorderLayout.CENTER);
    }

    public String getText(){
        return jtf.getText();
    }
}
