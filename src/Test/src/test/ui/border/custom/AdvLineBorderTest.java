package test.ui.border.custom;

import javax.swing.*;

public class AdvLineBorderTest {
    public static void main(String[] args) {
        // 创建窗口
        JFrame jf = new JFrame("测试窗口");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setSize(300, 200);
        jf.setLocationRelativeTo(null);


        JButton b = new JButton("fsdf");
        b.setBorder(new AdvLineBorder());

        jf.add(b);
        jf.setVisible(true);
    }
}
