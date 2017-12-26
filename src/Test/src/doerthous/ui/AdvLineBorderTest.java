package doerthous.ui;

import restaurant.ui.component.border.AdvLineBorder;

import javax.swing.*;
import java.awt.*;

public class AdvLineBorderTest {
    public static void main(String[] args) {
        AFrameForJPanelTest f = new AFrameForJPanelTest();
        f.setLayout(new FlowLayout());
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200,200));
        panel.setBackground(Color.green);
        panel.setBorder(new AdvLineBorder().setLeft(1).setRight(10).setTop(5).setBottom(50)
                .setLeftColor(Color.red).setRightColor(Color.gray).setTopColor(Color.pink)
                .setBottomColor(Color.blue));
        f.add(panel);
        f.open();
    }
}
