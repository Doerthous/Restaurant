package restaurant.service.pc.management;

import restaurant.communication.core.ICommandObserver;
import restaurant.communication.core.IData;
import restaurant.communication.core.IPeer;
import restaurant.database.IDb;
import restaurant.database.po.Detail;
import restaurant.database.po.Order;
import restaurant.service.core.IManagementService;
import restaurant.service.core.impl.InterModuleCommunication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderManager implements ICommandObserver {
    private IDb db;
    // table的订单缓存
    private Map<String, InterModuleCommunication.Data.MC.Order> orders;

    public OrderManager(IPeer peer, IDb db) {
        this.db = db;
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.CLIENT_NEW_ORDER);
        orders = new HashMap<>();
    }

    public Boolean createOrder(String tableId){
        InterModuleCommunication.Data.MC.Order order = orders.get(tableId);
        Boolean result = true;
        if(order != null) {
            String orderId = generateOrderId(tableId);
            if (db.insertOrder(new Order(orderId, new Date(), tableId, order.totalCost))) {
                for (String dish : order.orderDetail.keySet()) {
                    if (!db.insertDetial(new Detail(orderId, dish, order.orderDetail.get(dish)))) {
                        //debug("订单插入失败");
                        result = false;
                    }
                }
            } else {
                //debug("订单插入失败");
                result = false;
            }
        }
        return result;
    }
    public Map<String, Integer> getOrderDetail(String tableId){
        if(orders.keySet().contains(tableId)){
            return orders.get(tableId).orderDetail;
        }
        return new HashMap<>();
    }
    public Float getTotalConsumption(String tableId){
        if(orders.keySet().contains(tableId)){
            return orders.get(tableId).totalCost;
        }
        return 0f;
    }


    private static String generateOrderId(String tableId){
        return tableId+"-"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    @Override
    public void update(IData data) {
        String cmd = data.getCommand();
        if(InterModuleCommunication.CommandToManagement.CLIENT_NEW_ORDER.equals(cmd)) {
            // 能发出此条消息，说明table登陆成功，即fromId等于tableId
            orders.put(data.getFromId(), ((InterModuleCommunication.Data.MC) data.getData()).order);
        }
    }
}
