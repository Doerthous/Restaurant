package restaurant.ui.management.component;

import restaurant.ui.client.Constants;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.PicturePanel;
import restaurant.ui.component.thirdpart.ShadowBorder;
import restaurant.ui.utils.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeCard extends JPanel implements ActionListener {
    public static final String MODIFY = "修改";
    public static final String DELETE = "删除";
    private ActionListener listener;
    private JCheckBox jcb;
    private JLabel jlName;
    private JLabel jlCode;
    private PicturePanel ppPhoto;
    private JLabel jlPosition;
    private JLabel jlSex;
    private JLabel jlPhone;
    public EmployeeCard(String name, String code, String position, ImageIcon photo, String sex, String phone) {
        this(300,200, name, code, position, photo, sex, phone);
    }

    public EmployeeCard(int width, int height, String name,
                        String code, String position, ImageIcon photo, String sex, String phone) {
        // 设置样式
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                ShadowBorder.newBuilder().shadowSize(2).build(),
                BorderFactory.createEmptyBorder(20,20,20,20)
        ));
        setBackground(Constants.Color.title);

        // 创建组件
        JPanel panel = new JPanel(new GridBagLayout());
        jlName = new JLabel(name);
        jlCode = JLabelBuilder.getInstance().text(code).build();
        ppPhoto = new PicturePanel(photo);
        jlPosition = JLabelBuilder.getInstance().text(position).build();
        jlSex = JLabelBuilder.getInstance().text(sex).build();
        jlPhone = JLabelBuilder.getInstance().text(phone).build();

        JButton modify = new JButton(MODIFY);
        JButton delete = new JButton(DELETE);
        JPanel nc = new JPanel(new BorderLayout());
        jcb = new JCheckBox();

        // 设置组件样式
        panel.setOpaque(false);
        ppPhoto.setBorder(BorderFactory.createLineBorder(Color.black));
        modify.setBackground(Constants.Color.title);
        delete.setBackground(Constants.Color.title);
        nc.setOpaque(false);
        jcb.setBackground(Constants.Color.title);
        modify.setFocusPainted(false);
        delete.setFocusPainted(false);

        // 添加组件到内容面板
        nc.add(jcb, BorderLayout.EAST);
        nc.add(jlName, BorderLayout.CENTER);
        panel.add(ppPhoto, new GBC(0,0,1,6)
                .setInsets(0,0,0,10)
                .setFill(GBC.BOTH).setWeight(20,20));
        panel.add(nc, new GBC(1,0,2,1)
                .setInsets(0,0,10,0).setFill(GBC.HORIZONTAL));
        panel.add(JLabelBuilder.getInstance().text("性别：").build(), new GBC(1,1)
                .setFill(GBC.VERTICAL).setWeight(0,1));
        panel.add(jlSex, new GBC(2,1)
                .setWeight(1,1).setAnchor(GBC.WEST));
        panel.add(JLabelBuilder.getInstance().text("工号：").build(), new GBC(1,2)
                .setFill(GBC.VERTICAL).setWeight(0,1));
        panel.add(jlCode, new GBC(2,2)
                .setWeight(1,1).setAnchor(GBC.WEST));
        panel.add(JLabelBuilder.getInstance().text("职位：").build(), new GBC(1,3)
                .setFill(GBC.VERTICAL).setWeight(0,1));
        panel.add(jlPosition, new GBC(2,3)
                .setWeight(1,1).setAnchor(GBC.WEST));
        panel.add(JLabelBuilder.getInstance().text("手机：").build(), new GBC(1,4)
                .setFill(GBC.VERTICAL).setWeight(0,1));
        panel.add(jlPhone, new GBC(2,4)
                .setWeight(1,1).setAnchor(GBC.WEST));
        panel.add(modify, new GBC(1,5)
                .setInsets(10,0,0,0).setAnchor(GBC.WEST));
        panel.add(delete, new GBC(2,5)
                .setInsets(10,0,0,0).setAnchor(GBC.EAST));
        add(panel);

        // 设置组件事件
        modify.addActionListener(this);
        delete.addActionListener(this);
    }


    public EmployeeCard setActionListener(ActionListener listener){
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
    public String getName(){
        return jlName.getText();
    }
    public String getCode(){
        return jlCode.getText();
    }
    public Boolean isChecked(){
        return jcb.isSelected();
    }

    public EmployeeCard setEmployeeName(String name) {
        jlName.setText(name);
        return this;
    }

    public EmployeeCard setEmployeePhoto(ImageIcon photo) {
        this.ppPhoto.setPicture(photo);
        return this;
    }

    public EmployeeCard setEmployeePosition(String position) {
        jlPosition.setText(position);
        return this;
    }

    public EmployeeCard setEmployeeSex(String sex) {
        jlSex.setText(sex);
        return this;
    }

    public EmployeeCard setEmployeePhone(String phone) {
        jlPhone.setText(phone);
        return this;
    }
}
