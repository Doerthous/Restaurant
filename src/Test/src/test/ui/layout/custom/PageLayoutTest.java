package test.ui.layout.custom;

import doerthous.ui.AFrameForJPanelTest;
import restaurant.ui.component.JButtonBuilder;
import restaurant.ui.component.layout.PageLayout;

import javax.swing.*;
import java.awt.*;

public class PageLayoutTest {
    private static int bCnt = 0;
    private static void incBCnt(){
        ++bCnt;
    }
    public static void main(String[] args) {
        AFrameForJPanelTest f = new AFrameForJPanelTest();
        JPanel panel = new JPanel(new BorderLayout());
        PageLayout ml = new PageLayout().setSingleRow(true);
        JPanel layout = new JPanel(ml);
        layout.add(new JButton("f"));
        layout.add(new JButton("f2"));
        panel.add("Center", layout);
        JPanel btns = new JPanel();
        JLabel page = new JLabel("0");
        btns.add(page);
        btns.add(JButtonBuilder.getInstance().text("Add")
                .listener(e->{
                    layout.add(new JButton(bCnt+""));
                    incBCnt();
                    layout.revalidate();
                }).build());
        btns.add(JButtonBuilder.getInstance().text("Prior").listener(e->{
            ml.prior(layout);
            page.setText(String.valueOf(ml.getCurrentPage(layout)+"/"+ml.getPageCount(layout)));
        }).build());
        btns.add(JButtonBuilder.getInstance().text("Next").listener(e->{
            ml.next(layout);
            page.setText(String.valueOf(ml.getCurrentPage(layout)+"/"+ml.getPageCount(layout)));
        }).build());
        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(30,30));
        btns.add(tf);
        JButton to = new JButton("To");
        to.addActionListener(e->{
            tf.getText();
            ml.to(layout, Integer.valueOf(tf.getText()));
            page.setText(String.valueOf(ml.getCurrentPage(layout)+"/"+ml.getPageCount(layout)));
        });
        btns.add(to);
        btns.setPreferredSize(new Dimension(0,30));
        panel.add("South", btns);
        f.add(panel);
        f.open();
    }
}
