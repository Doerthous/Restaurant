package yms.component;

import yms.ColorConstants;
import restaurant.ui.component.builder.JButtonBuilder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by YMS on 2017/12/24.
 */
public class DishCard extends JPanel {
    public DishCard(int width, int height,
                    String dishName, String price, String url,
                    ActionListener modify, ActionListener delete) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        setBackground(ColorConstants.title);
        //north
        JPanel north = new JPanel(new BorderLayout());
        north.setOpaque(false);
        north.setPreferredSize(new Dimension(width,height/5));
        JLabel name = new JLabel(dishName,JLabel.CENTER);
        name.setPreferredSize(new Dimension(width/2,0));
        JLabel dishprice = new JLabel(price,JLabel.CENTER);
        dishprice.setPreferredSize(new Dimension(width/2,0));
        north.add("West",name);
        north.add("East",dishprice);
        add("North", north);
        //center
        JPanel center = new JPanel();
        center.setOpaque(false);
        Border border = BorderFactory.createLineBorder(Color.black);
        center.setBorder(border);
        add("Center",  center);
        //south
        JPanel south = new JPanel(new GridLayout(1,2));
        south.setOpaque(false);
        south.add(JButtonBuilder.getInstance().text("修改").listener(modify)
                .preferredSize(new Dimension(width/3,height/5)).
                        background(ColorConstants.title).build());
        south.add(JButtonBuilder.getInstance().text("删除").listener(delete)
                .preferredSize(new Dimension(width/3,height/5))
                .background(ColorConstants.title).build());
        add("South", south);
    }
}

