package restaurant.ui.client;

import javax.swing.*;
import java.awt.*;

public class PayUI extends JPanel {
    private ClientFrame cf;
    public PayUI(ClientFrame cf) {
        this.cf = cf;
        setBackground(Color.blue);
        JButton ret = new JButton("返回");
        ret.addActionListener(e->{
            cf.main();
        });
        add(ret);
    }
}
