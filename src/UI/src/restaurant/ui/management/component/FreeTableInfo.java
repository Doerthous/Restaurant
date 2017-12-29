package restaurant.ui.management.component;

import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FreeTableInfo extends JPanel implements ActionListener {
    private ActionListener listener;
    private JTextField jtf;
    private String tableId;
    public FreeTableInfo(String tableId, ActionListener listener) {
        this.tableId = tableId;
        setLayout(new FlowLayout());
        setOpaque(false);
        add(JLabelBuilder.getInstance().text("餐桌号："+tableId).build());
        add(JLabelBuilder.getInstance().text("顾客数：").build());
        jtf = new JTextField();
        jtf.setPreferredSize(new Dimension(50,20));
        add(jtf);
        add(JButtonBuilder.getInstance().text("开台").listener(this).build());
        this.listener = listener;
    }
    public Integer getCustomerCount(){
        String text = jtf.getText();
        if(Utility.isNumeric(text)){
            return Integer.valueOf(text);
        }
        return null;
    }
    public String getTableId(){
        return tableId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.actionPerformed(new ActionEvent(this, 1, ""));
    }
}