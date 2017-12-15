package restaurant.service;

import restaurant.service.impl.DishService;
import restaurant.service.impl.client.ClientService;
import restaurant.service.impl.EmployeeService;
import restaurant.service.impl.kitchen.KitchenService;

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
        return new ClientService(new Date().toString());
    }
    public static IKitchenService getKitchenService() { return new KitchenService(); }


}
