package restaurant.ui.management.component;

import restaurant.ui.Constants;
import restaurant.ui.component.RectangleCard;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.builder.JPanelBuilder;
import restaurant.ui.utils.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class OrderCard extends RectangleCard implements ActionListener, MouseListener {
    public static final int ACTION_FIRST = 1;
    public static final int ACTION_DELETE = 1;
    public static final int ACTION_CLICK = 2;
    public static final int ACTION_LAST = 2;
    private ActionListener listener;
    private String orderId;

    public OrderCard() {
        this("订单号","餐桌号","日期",null);
    }
    public OrderCard(String orderId, String tableId, String date, ActionListener listener) {
        this.orderId = orderId;

        setLayout(new GridBagLayout());
        add(JLabelBuilder.getInstance().text(orderId).mouseListener(this).build(),
                new GBC(0,0).setWeight(2,1));
        add(JLabelBuilder.getInstance().text(tableId).mouseListener(this).build(),
                new GBC(1,0).setWeight(2,1));
        add(JLabelBuilder.getInstance().text(date).mouseListener(this).build(),
                new GBC(2,0).setWeight(2,1));
        JPanel button = JPanelBuilder.getInstance().layout(new BorderLayout()).opaque(false).build();
        button.add(JButtonBuilder.getInstance().text("删除").background(Constants.Color.title)
                        .margin(new Insets(2,10,2,10)).listener(this).build(),
                BorderLayout.EAST);
        add(button, new GBC(3,0));
        addMouseListener(this);
        setActionListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.actionPerformed(new ActionEvent(this, ACTION_DELETE, ""));
    }

    private void setActionListener(ActionListener listener){
        this.listener = listener;
    }
    public String getOrderId(){
        return orderId;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        listener.actionPerformed(new ActionEvent(this, ACTION_CLICK, ""));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
