package restaurant.ui.management.component;

import restaurant.ui.client.Constants;
import restaurant.ui.component.PicturePanel;
import restaurant.ui.component.RectangleCard;
import restaurant.ui.component.thirdpart.ShadowBorder;
import restaurant.ui.utils.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableCard extends RectangleCard implements ActionListener {
    public static final String MODIFY = "修改";
    public static final String DELETE = "删除";
    private JLabel jlId;
    private JLabel jlType;
    private JLabel jlFloor;
    private JLabel jlCapaciity;
    private ActionListener listener;

    public TableCard(String tableId, String tableType, Integer tableFloor, Integer tableCapacity) {
        this(150,150,tableId,tableType,tableFloor,tableCapacity);
    }

    public TableCard(int width, int height, String tableId, String tableType,
                     Integer tableFloor, Integer tableCapacity) {
        // 设置样式
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                ShadowBorder.newBuilder().shadowSize(2).build(),
                BorderFactory.createEmptyBorder(20,20,20,20)
        ));
        setBackground(Constants.Color.title);

        // 创建组件
        JLabel jLabel = new JLabel("餐桌号：");
        JLabel jLabel1 = new JLabel("类型：");
        JLabel jLabel2 = new JLabel("楼层：");
        JLabel jLabel3 = new JLabel("容量：");
        jlId = new JLabel(tableId);
        jlType = new JLabel(tableType);
        jlFloor = new JLabel(tableFloor.toString());
        jlCapaciity = new JLabel(tableCapacity.toString());
        JPanel jPanel = new JPanel(new GridLayout(1,2,4,0));
        JButton modify = new JButton(MODIFY);
        JButton delete = new JButton(DELETE);

        // 设置组件样式
        jPanel.setOpaque(false);
        modify.setBackground(Constants.Color.title);
        modify.setMargin(new Insets(0,0,0,0));
        delete.setBackground(Constants.Color.title);
        delete.setMargin(new Insets(0,0,0,0));

        // 添加组件到内容面板
        jPanel.add(modify);
        jPanel.add(delete);
        add(jLabel, new GBC(0,0).setFill(GBC.BOTH));
        add(jlId, new GBC(1,0).setFill(GBC.BOTH).setWeight(1,1));
        add(jLabel1, new GBC(0,1).setFill(GBC.BOTH));
        add(jlType, new GBC(1,1).setFill(GBC.BOTH).setWeight(1,1));
        add(jLabel2, new GBC(0,2).setFill(GBC.BOTH));
        add(jlFloor, new GBC(1,2).setFill(GBC.BOTH).setWeight(1,1));
        add(jLabel3, new GBC(0,3).setFill(GBC.BOTH));
        add(jlCapaciity, new GBC(1,3).setFill(GBC.BOTH).setWeight(1,1));
        add(jPanel, new GBC(0,4,2,1)
                .setInsets(10,0,0,0)
                .setFill(GBC.BOTH).setWeight(1,1));


        // 设置组件事件
        modify.addActionListener(this);
        modify.setFocusPainted(false);
        delete.addActionListener(this);
        delete.setFocusPainted(false);
    }


    public TableCard setActionListener(ActionListener listener){
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
    public String getTableId(){
        return jlId.getText();
    }
    public TableCard setTableType(String type){
        this.jlType.setText(type);
        return this;
    }
    public TableCard setTableFloor(Integer floor){
        this.jlFloor.setText(floor.toString());
        return this;
    }
    public TableCard setTableCapacity(Integer capacity){
        this.jlCapaciity.setText(capacity.toString());
        return this;
    }
}
