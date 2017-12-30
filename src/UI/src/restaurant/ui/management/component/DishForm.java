package restaurant.ui.management.component;

import restaurant.ui.Constants;
import restaurant.ui.component.PicturePicker;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.builder.JPanelBuilder;
import restaurant.ui.utils.GBC;
import restaurant.ui.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DishForm extends JDialog implements ActionListener {
    public static class Data {
        public String dishName;
        public Float dishPrice;
        public String dishType;
        public Boolean dishIsSaled;
        public String dishPicUrl;
        public ImageIcon dishPic;

        public Data() {
        }

        public Data(String dishName, Float dishPrice, String dishType, Boolean dishIsSaled, ImageIcon dishPic) {
            this.dishName = dishName;
            this.dishPrice = dishPrice;
            this.dishType = dishType;
            this.dishIsSaled = dishIsSaled;
            this.dishPic = dishPic;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "dishName='" + dishName + '\'' +
                    ", dishPrice=" + dishPrice +
                    ", dishType='" + dishType + '\'' +
                    ", dishIsSaled=" + dishIsSaled +
                    ", dishPicUrl='" + dishPicUrl + '\'' +
                    '}';
        }
    }

    private PicturePicker picturePicker;
    private JTextField name;
    private JTextField price;
    private JComboBox type;
    private JCheckBox isSaled;
    private Data data;
    public DishForm() {
        this(new String[]{}); // test
    }

    // for create
    public DishForm(String[] types){
        data = new Data();

        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500,300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Constants.Color.subtitle);

        // 图片
        picturePicker = new PicturePicker(new Dimension(200,160));
        add(picturePicker, BorderLayout.WEST);


        JPanel c = JPanelBuilder.getInstance().opaque(false).layout(new BorderLayout())
                .border(BorderFactory.createEmptyBorder(20,20,20,20)).build();
        // 按钮
        JPanel buttons = JPanelBuilder.getInstance().opaque(false)
                .layout(new GridLayout(1,2,10,0))
                .border(BorderFactory.createEmptyBorder(10,10,0,10))
                .build();
        buttons.add(JButtonBuilder.getInstance().text("确定")
                .background(Constants.Color.background).focusPainted(false)
                .listener(this).build());
        buttons.add(JButtonBuilder.getInstance().text("取消")
                .background(Constants.Color.background).focusPainted(false)
                .listener(this).build());
        c.add(buttons, BorderLayout.SOUTH);

        // 表单
        name = new JTextField();
        price = new JTextField();
        type = new JComboBox(types);
        isSaled = new JCheckBox();
        isSaled.setOpaque(false);
        c.add(JPanelBuilder.getInstance().opaque(false).layout(new BorderLayout())
                .add(JPanelBuilder.getInstance().opaque(false).layout(new GridLayout(4,1))
                        .add(JLabelBuilder.getInstance().text("菜品名称").foreground(Color.white).build())
                        .add(JLabelBuilder.getInstance().text("菜品价格").foreground(Color.white).build())
                        .add(JLabelBuilder.getInstance().text("菜品类型").foreground(Color.white).build())
                        .add(JLabelBuilder.getInstance().text("是否售卖").foreground(Color.white).build())
                        .preferredSize(new Dimension(80,0))
                        .build(),BorderLayout.WEST)
                .add(JPanelBuilder.getInstance().opaque(false).layout(new GridBagLayout())
                        .add(name, new GBC(0,0).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .add(price, new GBC(0,1).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .add(type, new GBC(0,2).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .add(isSaled, new GBC(0,3).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .build(),BorderLayout.CENTER)
                .build());
        add(c);
    }

    // for modify
    public DishForm(String[] types, Data data){
        this(types);
        name.setText(data.dishName);
        price.setText(data.dishPrice.toString());
        type.setSelectedItem(data.dishType);
        isSaled.setSelected(data.dishIsSaled);
        picturePicker.setPicture(data.dishPic);
    }


    public Data open(){
        setVisible(true);
        return data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            JButton button = (JButton)e.getSource();
            switch (button.getText()){
                case "确定":{
                    if(name.getText().length() == 0){
                        return;
                    }
                    if(price.getText().length() == 0){
                        return;
                    }
                    if(!StringUtils.isNumber(price.getText())){
                        return;
                    }
                    data.dishName = name.getText();
                    data.dishPrice = Float.valueOf(price.getText());
                    data.dishType = (String) type.getSelectedItem();
                    data.dishIsSaled = isSaled.isSelected();
                    data.dishPicUrl = picturePicker.getUrl();
                    dispose();
                } break;
                case "取消": {
                    data = null;
                    dispose();
                } break;
            }
        }
    }
}
