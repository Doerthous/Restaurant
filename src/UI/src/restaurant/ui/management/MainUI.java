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

public class MainUI extends BasePanel3 implements IManagementService.ITableObserver, ActionListener {
    public static final String DISH_MANAGE = "菜品管理";
    public static final String EMPLOYEE_MANAGE = "员工管理";
    public static final String TABLE_MANAGE = "餐桌管理";
    public static final String DATA_ANALYZE = "数据统计";
    public static final String SYSTEM_SETTING = "系统设置";
    private ManagementFrame mf;

    public MainUI(ManagementFrame mf) {
        this.mf = mf;
        this.tablePanels = new ArrayList<>();
        PageLayout layout = new PageLayout();
        getContentLeft().setLayout(layout);
        PageButton pageButton = new PageButton(layout, getContentLeft());
        getFootLeft().add(pageButton);
        mf.getService().addTableObserver(this);

        // subtitle
        getSubtitleLeft().setLayout(new PageLayout().setSingleRow(true)
                .setHgap(0).setVgap(10).setPadding(new Insets(2,15,0,0)));
        getSubtitleLeft().add(createMenuButton(DISH_MANAGE));
        getSubtitleLeft().add(createMenuButton(EMPLOYEE_MANAGE));
        getSubtitleLeft().add(createMenuButton(TABLE_MANAGE));
        getSubtitleLeft().add(createMenuButton(DATA_ANALYZE));
        getSubtitleLeft().add(createMenuButton(SYSTEM_SETTING));

        load();
    }


    @Override
    public void online(String tableId) {

    }
    @Override
    public void dishFinish(String dishName, String tableId) {

    }
    @Override
    public void requestService(String tableId) {

    }
    @Override
    public void newOrder(String tableId) {
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
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
                }
            }
        }
    }



    /*
        创建菜单按钮，管理端主页面的四个转页按钮
     */
    private JButton createMenuButton(String text){
        return JButtonBuilder.getInstance().text(text)
                .listener(this).background(Constants.Color.background)
                .border(BorderFactory.createCompoundBorder(
                        ShadowBorder.newBuilder().buildSpecial(new Insets(0,0,2,0)),
                        BorderFactory.createEmptyBorder(15,15,15,15))
                ).build();
    }


    public void load(){
        loadTable();
    }
    private java.util.List<TablePanels> tablePanels;
    private void loadTable(){
        java.util.List<Table> tables = mf.getService().getAllTable();
        //
        getContentLeft().removeAll();
        for(Table table: tables) {
            TablePanels tps = new TablePanels(table.getId(), mf);
            tablePanels.add(tps);
            tps.setActionListener(e -> { // 点击“通知”按钮
                JPanel crt = getContentRightTop();
                crt.removeAll();
                crt.add(tps.getNotification());
                Utility.revalidate(crt);
            });
            getContentLeft().add(tps.getOt().addActionListener(e -> { // 点击table
                JPanel crb = getContentRightBottom();
                crb.removeAll();
                crb.add(tps.getTableInfo());
                Utility.revalidate(crb);
            }));
        }
        Utility.revalidate(getContentLeft());
    }
}
