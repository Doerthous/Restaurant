package restaurant.ui.management;

import restaurant.service.core.IManagementService;
import restaurant.service.pc.ServiceFactory;
import restaurant.ui.component.BaseFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ManagementFrame extends BaseFrame implements ActionListener {
    public static final int ACTION_FIRST = 1;
    public static final int TABLE_CREATE = 1;
    public static final int TABLE_DELETE = 2;
    public static final int ACTION_LAST = 2;
    public ManagementFrame(IManagementService service){
        this.service = service;
        initUICompoent();
    }

    /*
        业务层(service)接口
     */
    private IManagementService service;
    public IManagementService getService(){
        return service;
    }

    /*
        UI组件
     */
    private MainUI mainUI;
    private DishManageUI dishManageUI;
    private EmployeeManageUI employeeManageUI;
    private TableManageUI tableManageUI;
    private OrderManagerUI orderManagerUI;
    public void initUICompoent(){
        mainUI = new MainUI(this);
        dishManageUI = new DishManageUI(this);
        employeeManageUI = new EmployeeManageUI(this);
        tableManageUI = new TableManageUI(this);
        orderManagerUI = new OrderManagerUI(this);
        add("Main", mainUI);
        add("DishManage", dishManageUI);
        add("EmployeeManage", employeeManageUI);
        add("TableManage", tableManageUI);
        add("OrderManage", orderManagerUI);
    }


    /*
       程序入口
    */
    public static void main(String[] args) {
        new ManagementFrame(ServiceFactory.getManagementService()).open();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof TableManageUI){
            switch (e.getID()){
                case TableManageUI.TABLE_CREATE:{
                    mainUI.actionPerformed(new ActionEvent(this, TABLE_CREATE, e.getActionCommand()));
                } break;
                case TableManageUI.TABLE_DELETE: {
                    mainUI.actionPerformed(new ActionEvent(this, TABLE_DELETE, e.getActionCommand()));
                } break;
            }
        }
    }



    public void main(){
        show("Main");
    }
    public void dishManage(){
        show("DishManage");
    }
    public void employeeManage(){
        show("EmployeeManage");
    }
    public void tableManage() {
        show("TableManage");
    }
    public void orderManage() { show("OrderManage"); }

}
