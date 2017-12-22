package restaurant.service.pc;


import restaurant.communication.pc.PeerFactory;
import restaurant.service.core.*;
import restaurant.service.core.impl.InterModuleCommunication;
import restaurant.service.pc.client.ClientService;
import restaurant.service.pc.kitchen.KitchenService;
import restaurant.service.pc.management.ManagementService;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
    业务模块创建工厂，所有业务模块的实例创建都通过此类
 */
public class ServiceFactory {
    public static IEmployeeService getEmployeeService(){
        return new EmployeeService();
    }
    public static IDishService getDishService(){ return new DishService(); }
    /*
        餐桌id这里暂且这样设置，具体方案再定
     */
    public static IClientService getClientService() {
        String id = new SimpleDateFormat("HHmmssSS").format(new Date());
        return new ClientService(id, PeerFactory.getPeer(id, PeerFactory.WIN10_JDK8));
    }
    public static IKitchenService getKitchenService() {
        return new KitchenService(PeerFactory.getPeer(InterModuleCommunication.ModuleId.KITCHEN,
                PeerFactory.WIN10_JDK8));
    }
    public static IManagementService getManagementService(){
        return new ManagementService(PeerFactory.getPeer(InterModuleCommunication.ModuleId.MANAGEMENT,
                PeerFactory.WIN10_JDK8));
    }

}
