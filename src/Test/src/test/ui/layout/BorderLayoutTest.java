package test.ui.layout;

import javax.swing.*;
import java.awt.*;

public class BorderLayoutTest {
    public static void main(String[] args) {
        // 创建窗口
        JFrame jf = new JFrame("测试窗口");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setSize(300, 200);
        jf.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // FlowLayout
        panel.add("East", new JButton("East"));
        //panel.add("West",new JButton("West"));
        panel.add("South", new JButton("South"));
        JButton north = new JButton("North");
        north.setPreferredSize(new Dimension(0, 100)); // South和North的宽默认铺满窗口，所以设置无效
        panel.add("North", north);
        panel.add("Center", new JButton("Center"));

        jf.add(panel);
        jf.setVisible(true);
    }
}
