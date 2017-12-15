package test.ui.layout;

import javax.swing.*;
import java.awt.*;

public class CardLayoutTest {
    public static void main(String[] args) {
        // 创建窗口
        JFrame jf = new JFrame("测试窗口");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setSize(300, 200);
        jf.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // 初始化方法
        CardLayout layout = new CardLayout();
        JPanel clp = new JPanel(layout);
        JPanel rp = new JPanel();
        rp.setBackground(Color.red);
        clp.add("Red", rp);
        JPanel gp = new JPanel();
        gp.setBackground(Color.green);
        clp.add("Green", gp);
        panel.add("Center", clp);

        JPanel btns = new JPanel();
        btns.setPreferredSize(new Dimension(50, 0));
        JButton r = new JButton("Red");
        r.addActionListener(e->{layout.show(clp, "Red");}); // 使用
        btns.add(r);
        JButton g = new JButton("Green");
        g.addActionListener(e->{layout.show(clp, "Green");});
        btns.add(g);
        panel.add("East", btns);

        jf.add(panel);
        jf.setVisible(true);
    }
}
