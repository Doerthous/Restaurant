package restaurant.ui.client;

import restaurant.ui.component.thirdpart.ShadowBorder;
import restaurant.ui.ColorConstants;
import restaurant.ui.component.BasePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainUI extends BasePanel {
    private ClientFrame cf;

    public MainUI(ClientFrame cf) {
        super();
        hideFoot();
        hideSubTitle();
        this.cf = cf;

        JLabel l = new JLabel("餐桌号："+cf.getService().getTableId(), JLabel.CENTER);
        l.setPreferredSize(new Dimension(Constants.ContentEastWidth,0));
        getContent().add("East", l);
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.add(createMainButton("点餐", e->{ cf.order(); }));
        p.add(createMainButton("聊天", e->{ cf.chat(); }));
        p.add(createMainButton("呼叫服务", e->{ cf.requestService(); }));
        p.add(createMainButton("结账", e->{ cf.pay(); }));
        getContent().add("Center", p);
    }

    private JButton createMainButton(String name, ActionListener listener){
        JButton b = new JButton(name);
        b.setPreferredSize(new Dimension(Constants.MainButtonSize,Constants.MainButtonSize));
        b.addActionListener(listener);
        b.setBorder(ShadowBorder.newBuilder().shadowSize(5).build());
        b.setBackground(ColorConstants.title);
        return b;
    }
}
