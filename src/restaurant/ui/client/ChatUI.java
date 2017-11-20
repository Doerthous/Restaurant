package restaurant.ui.client;

import javax.swing.*;
import java.awt.*;

public class ChatUI extends JPanel {
    private ClientFrame cf;
    public ChatUI(ClientFrame cf, LayoutManager layout) {
        this(cf);
        setLayout(layout);
    }

    public ChatUI(ClientFrame cf) {
        this.cf = cf;
        setBackground(Color.green);
        JButton ret = new JButton("返回");
        ret.addActionListener(e->{
            cf.main();
        });
        add(ret);
    }
}
