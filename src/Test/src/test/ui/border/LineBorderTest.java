package test.ui.border;

import javax.swing.*;
import java.awt.*;

public class LineBorderTest {
    public static void main(String[] args) {
        // 创建窗口
        JFrame jf = new JFrame("测试窗口");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setSize(300, 200);
        jf.setLocationRelativeTo(null);

        JButton b = new JButton("fsdf");
        b.setBorder(BorderFactory.createLineBorder(Color.red));

        jf.add(b);
        jf.setVisible(true);
    }
}
