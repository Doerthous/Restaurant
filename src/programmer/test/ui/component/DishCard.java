package programmer.test.ui.component;

import javax.swing.*;
import java.awt.*;

public class DishCard extends JPanel {
    public DishCard(int width, int height, String dishName, String dishPrice, String dishPictrue) {
        int x = width;
        int y = height;
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setBorder(BorderFactory.createLineBorder(Color.black));
        
        // 创建组件
        JLabel name = new JLabel(dishName);
        JLabel price = new JLabel(dishPrice);
        JButton modify = new JButton("修改");
        JButton delte = new JButton("删除");
        JLabel pictrue = new JLabel(dishPictrue);
        pictrue.setBorder(BorderFactory.createLineBorder(Color.black));

        // 添加组件到内容面板
        add(name);
        add(price);
        add(modify);
        add(delte);
        add(pictrue);

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

        SpringLayout.Constraints modifyCons = layout.getConstraints(modify);
        modifyCons.setX(Spring.constant(5));
        modifyCons.setConstraint(SpringLayout.EAST,
                Spring.scale(Spring.constant(x), 0.5f)); // x/2
        modifyCons.setConstraint(SpringLayout.SOUTH,
                Spring.sum(Spring.constant(y), Spring.minus(Spring.constant(5)))); // y-5

        SpringLayout.Constraints delteCons = layout.getConstraints(delte);
        delteCons.setConstraint(SpringLayout.EAST,
                Spring.sum(Spring.constant(x), Spring.minus(Spring.constant(5)))); // x-5
        delteCons.setConstraint(SpringLayout.WEST, modifyCons.getConstraint(SpringLayout.EAST));
        delteCons.setConstraint(SpringLayout.SOUTH,
                Spring.sum(Spring.constant(y), Spring.minus(Spring.constant(5)))); // y-5

        SpringLayout.Constraints picCons = layout.getConstraints(pictrue);
        picCons.setX(Spring.constant(5));
        picCons.setY(nameCons.getConstraint(SpringLayout.SOUTH));
        picCons.setConstraint(SpringLayout.EAST,
                Spring.sum(Spring.constant(x), Spring.minus(Spring.constant(5)))); // x-5
        picCons.setConstraint(SpringLayout.SOUTH,
                Spring.sum(modifyCons.getConstraint(SpringLayout.NORTH),Spring.minus(Spring.constant(5))));
    }
}
