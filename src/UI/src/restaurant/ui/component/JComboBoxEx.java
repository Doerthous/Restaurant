package restaurant.ui.component;

import restaurant.ui.Constants;
import restaurant.ui.component.builder.JLabelBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class JComboBoxEx extends RectangleCard {
    public static final String ITEM_SELETED = "ITEM_SELETED";
    private JComboBox jcb;
    private java.util.List<ActionListener> listeners;
    public JComboBoxEx(String tip, String[] data) {
        listeners = new ArrayList<>();
        setLayout(new BorderLayout(10,0));
        add(JLabelBuilder.getInstance().text(tip).build(), BorderLayout.WEST);
        jcb = new JComboBox(data);
        jcb.addItemListener(e->{
            if(e.getStateChange() == ItemEvent.SELECTED){
                for(ActionListener listener: listeners){
                    listener.actionPerformed(new ActionEvent(this, 1, ITEM_SELETED));
                }
            }
        });
        jcb.setBackground(Constants.Color.title);
        add(jcb, BorderLayout.CENTER);
    }

    public JComboBoxEx addActionListener(ActionListener listener){
        listeners.add(listener);
        return this;
    }
    public Object getSelectedItem(){
        return jcb.getSelectedItem();
    }
}
