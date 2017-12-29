package restaurant.ui.management.component;

import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JLabelBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BusyTableInfo extends JPanel implements ActionListener {
    public static final String SEND_WAITER = "指派服务员";
    public static final String PRINT_ORDER = "打印订单";
    public static final String PAY = "结账";
    private ActionListener listener;
    private String tableId;
    public BusyTableInfo(String tableId, Integer customerCount,
                         Float totalCost, ActionListener listener) {
        this.listener = listener;
        this.tableId = tableId;

        setLayout(new FlowLayout());
        setOpaque(false);
        add(JLabelBuilder.getInstance().text("餐桌号："+tableId).build());
        add(JButtonBuilder.getInstance().text(SEND_WAITER).listener(this).build());
        add(JLabelBuilder.getInstance().text("顾客数："+customerCount).build());
        add(JButtonBuilder.getInstance().text(PRINT_ORDER).listener(this).build());
        add(JLabelBuilder.getInstance().text("总消费："+totalCost).build());
        add(JButtonBuilder.getInstance().text(PAY).listener(this).build());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton) {
            listener.actionPerformed(new ActionEvent(this,
                    1, ((JButton)e.getSource()).getText()));
        }
    }

    public String getTableId(){
        return tableId;
    }
}
