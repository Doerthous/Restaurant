package restaurant.ui.management;

import restaurant.service.core.IManagementService;
import restaurant.ui.Constants;
import restaurant.ui.component.BasePanel3;
import restaurant.ui.component.builder.JButtonBuilder;
import restaurant.ui.component.PageButton;
import restaurant.ui.component.layout.PageLayout;
import restaurant.ui.component.thirdpart.ShadowBorder;
import restaurant.ui.management.component.BusyTableInfo;
import restaurant.ui.management.component.FreeTableInfo;
import restaurant.ui.management.component.OnlineTable;
import restaurant.ui.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI extends BasePanel3 implements IManagementService.ITableObserver, ActionListener {
    public static final String DISH_MANAGE = "菜品管理";
    public static final String EMPLOYEE_MANAGE = "员工管理";
    public static final String DATA_ANALYZE = "数据统计";
    public static final String SYSTEM_SETTING = "系统设置";
    private ManagementFrame mf;

    public MainUI(ManagementFrame mf) {
        this.mf = mf;
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
        getSubtitleLeft().add(createMenuButton(DATA_ANALYZE));
        getSubtitleLeft().add(createMenuButton(SYSTEM_SETTING));
    }

    @Override
    public void online(String tableId) {
        //
        getContentLeft().add(new OnlineTable(tableId).addActionListener(this));
        Utility.revalidate(getContentLeft());
    }
    @Override
    public void dishFinish(String dishName, String tableId) {
        //
    }
    @Override
    public void requestService(String tableId) {
        //
    }
    @Override
    public void newOrder(String tableId) {
        loadTableInfo(tableId);
    }

    private void loadTableInfo(String tableId){
        getContentRightBottom().removeAll();
        IManagementService.ITableInfo ti = mf.getService().getTableInfo(tableId);
        if(ti != null) {
            if (ti.getTableState().equals(IManagementService.ITableInfo.State.FREE)) {
                getContentRightBottom().add(new FreeTableInfo(tableId, this));
            } else {
                getContentRightBottom().add(new BusyTableInfo(tableId,
                        ti.getCustomerCount(), ti.getTotalCost(), this));
            }
        }
        Utility.revalidate(getContentRightBottom());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof OnlineTable){
            OnlineTable ot = (OnlineTable) e.getSource();
            loadTableInfo(ot.getTableId());
        } else if(e.getSource() instanceof JButton){
            JButton button = (JButton)e.getSource();
            switch (button.getText()){
                case DISH_MANAGE:{
                    mf.dishManage();
                } break;
                case EMPLOYEE_MANAGE: {
                    mf.employeeManage();
                } break;
            }
        } else if(e.getSource() instanceof FreeTableInfo){
            FreeTableInfo fti = ((FreeTableInfo)e.getSource());
            Integer cc = fti.getCustomerCount();
            String tableId = fti.getTableId();
            if(cc != null) {
                mf.getService().openTable(tableId, cc);
                loadTableInfo(tableId);
            }
        } else if(e.getSource() instanceof BusyTableInfo){
            BusyTableInfo bti = (BusyTableInfo)e.getSource();
            String cmd = e.getActionCommand();
            switch (cmd){
                case BusyTableInfo.SEND_WAITER:{
                    String waiterId = "";
                    //mf.getService().customerCall(bti.getTableId(), waiterId);
                } break;
                case BusyTableInfo.PRINT_ORDER:{
                    //
                } break;
                case BusyTableInfo.PAY: {
                    //
                    mf.getService().closeTable(bti.getTableId());
                    loadTableInfo(bti.getTableId());
                } break;
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
}
