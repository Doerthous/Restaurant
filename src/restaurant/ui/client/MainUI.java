package restaurant.ui.client;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JPanel {
    private ClientFrame cf;

    public MainUI(ClientFrame cf) {
        this.cf = cf;
        setBackground(Color.pink);
        add(new JLabel("餐桌号：xx"));
        JButton order = new JButton("点餐");
        order.addActionListener(e->{
            cf.order();
        });
        add(new JButton("套餐推荐"));
        JButton pay = new JButton("结账");
        pay.addActionListener(e->{
            cf.pay();
        });
        JButton callService = new JButton("呼叫服务");
        callService.addActionListener(e->{
            cf.callService();
        });
        JButton chat = new JButton("聊天");
        chat.addActionListener(e->{
            cf.chat();
        });
        add(order);
        add(chat);
        add(callService);
        add(pay);
    }
}
