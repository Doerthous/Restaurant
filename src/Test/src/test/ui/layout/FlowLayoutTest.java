package test.ui.layout;

import javax.swing.*;
import java.awt.*;

public class FlowLayoutTest {
    public static void main(String[] args) {
        // 创建窗口
        JFrame jf = new JFrame("测试窗口");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setSize(300, 200);
        jf.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0)); // FlowLayout
        panel.add(new JButton("000000000"));
        panel.add(new JButton("000000000"));
        panel.add(new JButton("000000000"));
        panel.add(new JButton("000000000"));
        panel.add(new JButton("000000000"));
        panel.add(new JButton("000000000"));
        panel.add(new JButton("000000000"));
        panel.add(new JButton("000000000"));
        panel.add(new JButton("000000000"));

        jf.add(panel);
        jf.setVisible(true);
    }
}
