package restaurant.ui.management;

import restaurant.service.core.IManagementService;
import restaurant.service.core.vo.Table;
import restaurant.ui.Constants;
import restaurant.ui.component.BasePanel3;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.PageButton;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.component.thirdpart.ShadowBorder;
import restaurant.ui.management.component.TablePanels;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainUI extends BasePanel3 implements ActionListener {
    public static final String DISH_MANAGE = "菜品管理";
    public static final String EMPLOYEE_MANAGE = "员工管理";
    public static final String TABLE_MANAGE = "餐桌管理";
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

        // content
        PageLayout layout = new PageLayout();
        getContentLeft().setLayout(layout);
        PageButton pageButton = new PageButton(layout, getContentLeft());
        getFootLeft().add(pageButton);


        initUIData();
        loadUI();
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
            }
        } else if(source instanceof ManagementFrame){
            switch (e.getID()){
                case ManagementFrame.TABLE_CREATE:{
                    loadToUIData(e.getActionCommand());
                    loadUI();
                } break;
            }
        }
    }

    private java.util.List<TablePanels> tablePanels;
    private void initUIData(){ // 初始化内部数据
        java.util.List<Table> tables = mf.getService().getAllTable();
        for(Table table: tables) {
            loadToUIData(table.getId());
        }
    }
    private void loadUI(){ // 装到视图
        getContentLeft().removeAll();
        for(TablePanels tps: tablePanels) {
            getContentLeft().add(tps.getOt());
        }
        Utility.revalidate(getContentLeft());
    }
    private void loadToUIData(String tableId){ // 装到内部数据
        TablePanels tps = new TablePanels(tableId, mf);
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
}
