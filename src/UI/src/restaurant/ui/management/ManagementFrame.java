package restaurant.ui.management;

import restaurant.service.core.IManagementService;
import restaurant.service.pc.ServiceFactory;
import restaurant.ui.component.BaseFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ManagementFrame extends BaseFrame implements ActionListener {
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
    public void initUICompoent(){
        mainUI = new MainUI(this);
        dishManageUI = new DishManageUI(this);
        add("Main", mainUI);
        add("DishManage", dishManageUI);
    }


    /*
       程序入口
    */
    public static void main(String[] args) {
        new ManagementFrame(ServiceFactory.getManagementService()).open();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public void dishManage(){
        show("DishManage");
    }
    public void main(){
        show("Main");
    }
}
