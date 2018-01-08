package restaurant.ui.management;

import restaurant.service.core.IManagementService;
import restaurant.service.core.vo.Table;
import restaurant.ui.Constants;
import restaurant.ui.component.BasePanel3;
import restaurant.ui.component.TimeCard;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.PageButton;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.component.thirdpart.ShadowBorder;
import restaurant.ui.management.component.TablePanels;
import restaurant.ui.utils.Utility;
import tutorial.advance.Anima2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainUI extends BasePanel3 implements ActionListener {
    public static final String DISH_MANAGE = "菜品管理";
    public static final String EMPLOYEE_MANAGE = "员工管理";
    public static final String TABLE_MANAGE = "餐桌管理";
    public static final String ORDER_MANAGE = "订单管理";
    public static final String DATA_ANALYZE = "数据统计";
    public static final String SYSTEM_SETTING = "系统设置";
    private ManagementFrame mf;

    public MainUI(ManagementFrame mf) {
        this.mf = mf;
        this.tablePanels = new ArrayList<>();

        // subtitle
        addSubtitleLeftButton(DISH_MANAGE, this);
        addSubtitleLeftButton(EMPLOYEE_MANAGE, this);
        addSubtitleLeftButton(TABLE_MANAGE, this);
        addSubtitleLeftButton(ORDER_MANAGE, this);

        // content
        PageLayout layout = new PageLayout();
        getContentLeft().setLayout(layout);
        PageButton pageButton = new PageButton(layout, getContentLeft());

        // foot
        getFootLeft().add(pageButton);
        addFootRight(new TimeCard());

        // data
        initUIData();
        loadTableUI();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source instanceof JButton){
            JButton button = (JButton)e.getSource();
            switch (button.getText()){
                case DISH_MANAGE:{
                    mf.dishManage();
                } break;
                case EMPLOYEE_MANAGE: {
                    mf.employeeManage();
                } break;
                case TABLE_MANAGE: {
                    mf.tableManage();
                } break;
                case ORDER_MANAGE: {
                    mf.orderManage();
                } break;
            }
        } else if(source instanceof ManagementFrame){
            switch (e.getID()){
                case ManagementFrame.TABLE_CREATE:{
                    Table table = mf.getService().getTableById(e.getActionCommand());
                    addTableToUIData(table);
                    loadTableUI();
                } break;
                case ManagementFrame.TABLE_DELETE: {
                    Table table = mf.getService().getTableById(e.getActionCommand());
                    removeTableFromUIData(table);
                    loadTableUI();
                }
            }
        }
    }

    private java.util.List<TablePanels> tablePanels;
    private void initUIData(){ // 初始化内部数据
        java.util.List<Table> tables = mf.getService().getAllTable();
        for(Table table: tables) {
            addTableToUIData(table);
        }
    }
    private void addTableToUIData(Table table){
        TablePanels tps = new TablePanels(table, mf);
        tps.setActionListener(e -> { // 点击“通知”按钮
            JPanel crt = getContentRightTop();
            crt.removeAll();
            crt.add(tps.getNotification());
            Utility.revalidate(crt);
        });
        tps.getOt().addActionListener(e -> { // 点击table
            JPanel crb = getContentRightBottom();
            crb.removeAll();
            crb.add(tps.getTableInfo());
            Utility.revalidate(crb);
            getContentRightTop().removeAll();
            Utility.revalidate(getContentRightTop());
        });
        tablePanels.add(tps);
    }
    private void removeTableFromUIData(Table table){
        String targetId = table.getId();
        for(TablePanels tps: tablePanels) {
            if(tps.getTableId().equals(targetId)){
                tablePanels.remove(tps);
                break;
            }
        }
    }
    private void loadTableUI(){
        getContentLeft().removeAll();
        for(TablePanels tps: tablePanels) {
            getContentLeft().add(tps.getOt());
        }
        Utility.revalidate(getContentLeft());
    }
}
