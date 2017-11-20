package programmer.test.ui.component;

import restaurant.ui.ColorConstants;

import javax.swing.*;
import java.awt.*;

public class OrderDishCard extends JPanel {
    public OrderDishCard(int width, int height, String dishName, String dishPrice, String dishPictrue) {
        int x = width;
        int y = height;
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setBorder(BorderFactory.createLineBorder(Color.black));

        // 创建组件
        JLabel name = new JLabel(dishName);
        JLabel price = new JLabel(dishPrice);
        JLabel pictrue = new JLabel(dishPictrue);
        JButton dec = new JButton("-");
        JLabel count = new JLabel("0", JLabel.CENTER);
        JButton inc = new JButton("+");

        pictrue.setBorder(BorderFactory.createLineBorder(Color.black));

        // 添加组件到内容面板
        add(name);
        add(price);
        add(pictrue);
        add(dec);
        add(count);
        add(inc);

        //
        SpringLayout.Constraints panelCons = layout.getConstraints(this);
        panelCons.setConstraint(SpringLayout.EAST, Spring.constant(x));
        panelCons.setConstraint(SpringLayout.SOUTH, Spring.constant(y));

        SpringLayout.Constraints nameCons = layout.getConstraints(name);
        nameCons.setX(Spring.constant(5));
        nameCons.setY(Spring.constant(5));

        SpringLayout.Constraints priceCons = layout.getConstraints(price);
        priceCons.setY(Spring.constant(5));
        priceCons.setConstraint(SpringLayout.EAST,
                Spring.sum(Spring.constant(x), Spring.minus(Spring.constant(5)))); // x-5

        SpringLayout.Constraints decCons = layout.getConstraints(dec);
        decCons.setX(Spring.constant(5));
        decCons.setConstraint(SpringLayout.EAST,
                Spring.sum(Spring.constant(5), Spring.scale(Spring.constant(x), 0.25f))); // x/4+5
        decCons.setConstraint(SpringLayout.SOUTH,
                Spring.sum(Spring.constant(y), Spring.minus(Spring.constant(5)))); // y-5

        SpringLayout.Constraints incCons = layout.getConstraints(inc);
        incCons.setConstraint(SpringLayout.EAST,
                Spring.sum(Spring.constant(x), Spring.minus(Spring.constant(5)))); // x-5
        incCons.setConstraint(SpringLayout.WEST,
                Spring.sum(Spring.minus(Spring.constant(5)), Spring.scale(Spring.constant(x), 0.75f))); // x-5-(x/4)
        incCons.setConstraint(SpringLayout.SOUTH,
                Spring.sum(Spring.constant(y), Spring.minus(Spring.constant(5)))); // y-5

        SpringLayout.Constraints picCons = layout.getConstraints(pictrue);
        picCons.setX(Spring.constant(5));
        picCons.setY(nameCons.getConstraint(SpringLayout.SOUTH));
        picCons.setConstraint(SpringLayout.EAST,
                Spring.sum(Spring.constant(x), Spring.minus(Spring.constant(5)))); // x-5
        picCons.setConstraint(SpringLayout.SOUTH,
                Spring.sum(decCons.getConstraint(SpringLayout.NORTH),Spring.minus(Spring.constant(5))));

        SpringLayout.Constraints countCons = layout.getConstraints(count);
        countCons.setX(Spring.constant(5));
        countCons.setConstraint(SpringLayout.NORTH,
                Spring.sum(picCons.getConstraint(SpringLayout.SOUTH), Spring.constant(5)));
        countCons.setConstraint(SpringLayout.WEST, decCons.getConstraint(SpringLayout.EAST));
        countCons.setConstraint(SpringLayout.EAST, incCons.getConstraint(SpringLayout.WEST));
        countCons.setConstraint(SpringLayout.SOUTH,
                Spring.sum(Spring.constant(y), Spring.minus(Spring.constant(5)))); // y-5

        //
        inc.addActionListener(e->{
            count.setText(String.valueOf(Integer.valueOf(count.getText().toString())+1));
        });
        inc.setFocusPainted(false);
        dec.addActionListener(e->{
            Integer c = Integer.valueOf(count.getText().toString());
            if(c > 0){
                count.setText(String.valueOf(c-1));
            }
        });
        dec.setFocusPainted(false);
        count.setBorder(BorderFactory.createLineBorder(Color.black));

        setBackground(ColorConstants.title);
        setBorder(ShadowBorder.newBuilder().shadowSize(5).center().build());
    }
}
