package yms.component;

import restaurant.ui.ColorConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by YMS on 2017/12/27.
 */
public class MinSetMeatCard extends JPanel {
    public MinSetMeatCard(int width, int height,
                    String dishName, String price, ActionListener delete)
    {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        setBackground(ColorConstants.title);
        JLabel dishname = new JLabel(dishName,JLabel.CENTER);
        dishname.setBackground(ColorConstants.background);
        dishname.setPreferredSize(new Dimension(width/3,height));
        JLabel dishprice = new JLabel(price,JLabel.CENTER);
        dishprice.setBackground(ColorConstants.background);

        JButton jb = new JButton("删除");
        jb.addActionListener(delete);
        jb.setBackground(ColorConstants.background);
        jb.setPreferredSize(new Dimension(width/3,height));
        add("West",dishname);
        add("Center",dishprice);
        add("East",jb);
    }
}
