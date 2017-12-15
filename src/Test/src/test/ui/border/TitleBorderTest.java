package test.ui.border;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TitleBorderTest {
    public static void main(String[] args) {
        // 创建窗口
        JFrame jf = new JFrame("测试窗口");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setSize(300, 200);
        jf.setLocationRelativeTo(null);
        jf.getContentPane().setLayout(new FlowLayout());

        JButton b = new JButton("fsdf");
        b.setBorder(BorderFactory.createTitledBorder("1"));
        JButton b1 = new JButton("fsdf");
        b1.setBorder(BorderFactory.createTitledBorder("2"));
        JButton b2 = new JButton("dfsdf");
        b2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.red), "3"));
        JButton b3 = new JButton("dfsdf");
        b3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.red), "3",TitledBorder.CENTER,TitledBorder.BOTTOM));

        jf.add(b);
        jf.add(b1);
        jf.add(b2);
        jf.add(b3);
        jf.setVisible(true);
    }
}
