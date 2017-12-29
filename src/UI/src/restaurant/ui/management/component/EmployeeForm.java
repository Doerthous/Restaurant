package restaurant.ui.management.component;


import org.apache.commons.lang3.StringUtils;
import restaurant.ui.Constants;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.builder.JPanelBuilder;
import restaurant.ui.utils.GBC;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeForm extends JDialog implements ActionListener {
    public static class Data {
        public String name;
        //public String code;
        public String position;
        public String sex;
        public Integer salary;
        public String nativePlace;
        public String phone;
        public String password;
        public String url;
        public ImageIcon photo;

        public Data() {
        }

        public Data(String name, String position, String sex, Integer salary,
                    String nativePlace, String phone, String password, ImageIcon photo) {
            this.name = name;
            this.position = position;
            this.sex = sex;
            this.salary = salary;
            this.nativePlace = nativePlace;
            this.phone = phone;
            this.password = password;
            this.photo = photo;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "name='" + name + '\'' +
                    ", position='" + position + '\'' +
                    ", sex='" + sex + '\'' +
                    ", salary=" + salary +
                    ", nativePlace='" + nativePlace + '\'' +
                    ", phone='" + phone + '\'' +
                    ", password='" + password + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    private PicturePicker picturePicker;
    private JTextField name;
    private JTextField position;
    private JComboBox sex;
    private JTextField salary;
    private JTextField nativePlace;
    private JTextField phone;
    private JTextField password;
    private Data data;
    public EmployeeForm() {
        data = new Data();

        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500,300);
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
        position = new JTextField();
        sex = new JComboBox(new String[]{"男", "女"});
        salary = new JTextField();
        nativePlace = new JTextField();
        phone= new JTextField();
        password= new JTextField();
        c.add(JPanelBuilder.getInstance().opaque(false).layout(new BorderLayout())
                .add(JPanelBuilder.getInstance().opaque(false).layout(new GridLayout(7,1))
                        .add(JLabelBuilder.getInstance().text("姓名").foreground(Color.white).build())
                        .add(JLabelBuilder.getInstance().text("职位").foreground(Color.white).build())
                        .add(JLabelBuilder.getInstance().text("性别").foreground(Color.white).build())
                        .add(JLabelBuilder.getInstance().text("薪资").foreground(Color.white).build())
                        .add(JLabelBuilder.getInstance().text("籍贯").foreground(Color.white).build())
                        .add(JLabelBuilder.getInstance().text("电话").foreground(Color.white).build())
                        .add(JLabelBuilder.getInstance().text("密码").foreground(Color.white).build())
                        .preferredSize(new Dimension(80,0))
                        .build(),BorderLayout.WEST)
                .add(JPanelBuilder.getInstance().opaque(false).layout(new GridBagLayout())
                        .add(name, new GBC(0,0).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .add(position, new GBC(0,1).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .add(sex, new GBC(0,2).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .add(salary, new GBC(0,3).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .add(nativePlace, new GBC(0,4).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .add(phone, new GBC(0,5).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .add(password, new GBC(0,6).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .build(),BorderLayout.CENTER)
                .build());
        add(c);
    }
    public EmployeeForm(Data data){
        this();
        name.setText(data.name);
        position.setText(data.position);
        sex.setSelectedItem(data.sex);
        salary.setText(data.salary.toString());
        nativePlace.setText(data.nativePlace);
        phone.setText(data.phone);
        password.setText(data.password);
        picturePicker.setPicture(data.photo);
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
                    if(position.getText().length() == 0){
                        return;
                    }
                    if(salary.getText().length() != 0 && !StringUtils.isNumeric(salary.getText())){
                        return;
                    }
                    if(password.getText().length() == 0){
                        return;
                    }
                    data.name = name.getText();
                    data.position = position.getText();
                    data.sex = (String) sex.getSelectedItem();
                    data.salary = salary.getText().length() == 0 ? null : Integer.valueOf(salary.getText().length());
                    data.nativePlace = nativePlace.getText();
                    data.phone = phone.getText();
                    data.password = password.getText();
                    data.url = picturePicker.getUrl();
                    dispose();
                } break;
                case "取消": {
                    dispose();
                } break;
            }
        }
    }
}
