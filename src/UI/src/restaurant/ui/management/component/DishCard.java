package restaurant.ui.management.component;

import restaurant.ui.client.Constants;
import restaurant.ui.component.PicturePanel;
import restaurant.ui.component.thirdpart.ShadowBorder;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DishCard extends JPanel implements ActionListener {
    public static final String MODIFY = "修改";
    public static final String DELETE = "删除";
    private JLabel name;
    private JLabel price;
    private PicturePanel picture;
    private ActionListener listener;

    public DishCard(String dishName, Float dishPrice, ImageIcon dishPicture) {
        this(200,200,dishName,dishPrice,dishPicture);
    }

    public DishCard(int width, int height, String dishName, Float dishPrice, ImageIcon dishPicture) {
        // 设置样式
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                ShadowBorder.newBuilder().shadowSize(2).build(),
                BorderFactory.createEmptyBorder(20,20,20,20)
        ));
        setBackground(Constants.Color.title);

        // 创建组件
        JPanel jPanel1  = new JPanel(new BorderLayout());
        JPanel jPanel2  = new JPanel(new GridLayout(1,2));
        name = new JLabel(dishName);
        price = new JLabel(dishPrice.toString());
        picture = new PicturePanel(dishPicture);
        JButton modify = new JButton(MODIFY);
        JButton delete = new JButton(DELETE);

        // 设置组件样式
        picture.setBorder(BorderFactory.createLineBorder(Color.black));
        jPanel1.setOpaque(false);
        jPanel2.setOpaque(false);
        modify.setBackground(Constants.Color.title);
        delete.setBackground(Constants.Color.title);

        // 添加组件到内容面板
        jPanel1.add("West", name);
        jPanel1.add("East", price);
        jPanel2.add(modify);
        jPanel2.add(delete);
        add("North",jPanel1);
        add("Center", picture);
        add("South", jPanel2);

        // 设置组件事件
        modify.addActionListener(this);
        modify.setFocusPainted(false);
        delete.addActionListener(this);
        delete.setFocusPainted(false);
    }


    public DishCard setActionListener(ActionListener listener){
        this.listener = listener;
        return this;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(listener != null && e.getSource() instanceof JButton){
            String ac = ((JButton)e.getSource()).getText();
            listener.actionPerformed(new ActionEvent(this,1,ac));
        }
    }
    public String getDishName(){
        return name.getText();
    }
    public DishCard setDishName(String name){
        this.name.setText(name);
        return this;
    }
    public DishCard setDishPrice(Float price){
        this.price.setText(price.toString());
        return this;
    }
    public DishCard setDishPicture(ImageIcon picture){
        this.picture.setPicture(picture);
        return this;
    }
}