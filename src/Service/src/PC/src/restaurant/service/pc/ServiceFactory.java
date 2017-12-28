package restaurant.service.pc;


import restaurant.communication.pc.PeerFactory;
import restaurant.database.DbFactory;
import restaurant.database.IDb;
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
    static String url = "jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish";
    static String user = "ODuser";
    static String password = "1234567890";
    /*
        餐桌id这里暂且这样设置，具体方案再定
     */
    public static IClientService getClientService() {
        IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
        db.init(url, user, password);
        return new ClientService(PeerFactory.getPeer(), db);
    }
    public static IKitchenService getKitchenService() {
        return new KitchenService(PeerFactory.getPeer());
    }
    public static IManagementService getManagementService(){
        IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
        db.init(url, user, password);
        return new ManagementService(PeerFactory.getPeer(), db);
    }

}
