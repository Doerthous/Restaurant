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

    public static IClientService getClientService() {
        IDb db = DbFactory.getDb(DbFactory.DbType.Mysql);
        //db.init(url, user, password);
        db.init("localhost", (short)3306, "restaurant", "root", "123456");
        return new ClientService(PeerFactory.getPeer(), db);
    }
    public static IKitchenService getKitchenService() {
        return new KitchenService(PeerFactory.getPeer());
    }
    public static IManagementService getManagementService(){
        IDb db = DbFactory.getDb(DbFactory.DbType.Mysql);
        //db.init(url, user, password);
        db.init("localhost", (short)3306, "restaurant", "root", "123456");
        return new ManagementService(PeerFactory.getPeer(), db);
    }

}
