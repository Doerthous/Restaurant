package restaurant.ui.management.component;

import restaurant.ui.Constants;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.builder.JLabelBuilder;
import restaurant.ui.component.builder.JPanelBuilder;
import restaurant.ui.utils.GBC;
import restaurant.ui.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableForm extends JDialog implements ActionListener {
    public static class Data {
        public String tableType;
        public Integer tableFloor;
        public Integer tableCapacity;

        public Data() {
        }

        public Data(String tableType, Integer tableFloor, Integer tableCapacity) {
            this.tableType = tableType;
            this.tableFloor = tableFloor;
            this.tableCapacity = tableCapacity;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "tableTable='" + tableType + '\'' +
                    ", tableFloor=" + tableFloor +
                    ", tableCapacity=" + tableCapacity +
                    '}';
        }
    }

    private JComboBox jcbType;
    private JTextField jtfFloor;
    private JTextField jtfCapacity;
    private Data data;
    public TableForm() {
        this(new String[]{}); // test
    }

    // for create
    public TableForm(String[] types){
        data = new Data();

        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(220,300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Constants.Color.subtitle);

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
        jcbType = new JComboBox(types);
        jtfFloor = new JTextField();
        jtfCapacity = new JTextField();
        c.add(JPanelBuilder.getInstance().opaque(false).layout(new BorderLayout(10,0))
                .add(JPanelBuilder.getInstance().opaque(false).layout(new GridLayout(3,1))
                        .add(JLabelBuilder.getInstance().text("餐桌类型").foreground(Color.white).build())
                        .add(JLabelBuilder.getInstance().text("餐桌楼层").foreground(Color.white).build())
                        .add(JLabelBuilder.getInstance().text("餐桌容量").foreground(Color.white).build())
                        .build(),BorderLayout.WEST)
                .add(JPanelBuilder.getInstance().opaque(false).layout(new GridBagLayout())
                        .add(jcbType, new GBC(0,0).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .add(jtfFloor, new GBC(0,1).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .add(jtfCapacity, new GBC(0,2).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER))
                        .build(),BorderLayout.CENTER)
                .build());
        add(c);
    }

    // for modify
    public TableForm(String[] types, Data data){
        this(types);
        jcbType.setSelectedItem(data.tableType);
        jtfFloor.setText(data.tableFloor.toString());
        jtfCapacity.setText(data.tableCapacity.toString());
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
                    if(jtfFloor.getText().length() == 0){
                        return;
                    }
                    if(!StringUtils.isNumber(jtfFloor.getText())){
                        return;
                    }
                    if(jtfCapacity.getText().length() == 0){
                        return;
                    }
                    if(!StringUtils.isNumber(jtfCapacity.getText())){
                        return;
                    }
                    data.tableType = (String) jcbType.getSelectedItem();
                    data.tableFloor = Integer.valueOf(jtfFloor.getText());
                    data.tableCapacity = Integer.valueOf(jtfCapacity.getText());
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
