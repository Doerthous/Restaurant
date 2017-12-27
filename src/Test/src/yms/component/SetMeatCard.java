package yms.component;
import yms.ColorConstants;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by YMS on 2017/12/25.
 */
public class SetMeatCard extends JPanel {
    public SetMeatCard(int width, int height,
                       String dishName, String price, String url)
    {
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
        JPanel south = new JPanel(new BorderLayout());
        south.setOpaque(false);
        south.setPreferredSize(new Dimension(width,height/10));
        JCheckBox checkBox =new JCheckBox("设为套餐");
        checkBox.setOpaque(false);
        checkBox.addActionListener(e -> {
            if(checkBox.isSelected())System.out.println("设为套餐");
        });
        south.add("East",checkBox);
        add("South",south);
    }
}































































































