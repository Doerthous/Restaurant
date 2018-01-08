package restaurant.service.pc.management;

import restaurant.communication.core.ICommandObserver;
import restaurant.communication.core.IData;
import restaurant.communication.core.IPeer;
import restaurant.database.IDb;
import restaurant.service.core.IManagementService;
import restaurant.service.core.impl.InterModuleCommunication;
import restaurant.service.core.vo.Order;
import restaurant.service.pc.vo.PO2VO;

import java.text.SimpleDateFormat;
import java.util.*;

public class OrderManager implements ICommandObserver {
    private IDb db;
    // table的订单缓存
    private Map<String, InterModuleCommunication.Data.MC.Order> orders;
    private List<restaurant.database.po.Order> orders2;
    // table消息监听器
    private List<IManagementService.ITableObserver> tableObservers;

    public OrderManager(IPeer peer, IDb db) {
        this.db = db;
        peer.addCommandObserver(this, InterModuleCommunication.CommandToManagement.CLIENT_NEW_ORDER);
        orders = new HashMap<>();
        tableObservers = new ArrayList<>();
    }

    // 数据库操作
    private static String newOrderId(String tableId){
        return tableId+"-"+new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
    }
    public Boolean createOrder(String tableId){
        InterModuleCommunication.Data.MC.Order order = orders.get(tableId);
        Boolean result = true;
        if(order != null) {
            String orderId = newOrderId(tableId);
            if (db.insertOrder(PO2VO.newOrderPo(orderId, tableId, new Date(), order.totalCost))) {
                for (String dish : order.orderDetail.keySet()) {
                    if (!db.insertDetail(PO2VO.newDetailPo(orderId, dish, order.orderDetail.get(dish)))) {
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
    public Boolean deleteOrder(String orderId) {
        db.deleteDetail(PO2VO.newDetailPo(orderId));
        return db.deleteOrder(PO2VO.newOrderPo(orderId));
    }
    public List<Order> getAllOrder() {
        this.orders2 = db.getAllOrder();
        List<Order> vos = new ArrayList<>();
        for(restaurant.database.po.Order order: this.orders2){
            vos.add(PO2VO.order(order));
        }
        return vos;
    }
    public Order getOrderById(String id) {
        restaurant.database.po.Order pOrder = db.getOrderById(id);
        List<restaurant.database.po.Detail> details = db.getDetailByOrderId(id);
        Map<String, Integer> d = new HashMap<>();
        for(restaurant.database.po.Detail detail: details){
            d.put(detail.getDishId(), detail.getAmount());
        }
        Order vOrder = PO2VO.order(pOrder);
        vOrder.setDetail(d);
        return vOrder;
    }
    public List<Order> getOrderByTableId(String tableId) {
        if(orders2 == null){
            getAllOrder();
        }
        List<Order> orders = new ArrayList<>();
        for(restaurant.database.po.Order order: orders2){
            if(order.getSeatId().equals(tableId)){
                orders.add(PO2VO.order(order));
            }
        }
        return orders;
    }
    public List<Order> getOrderByDateRange(Date begin, Date end) {
        if(orders2 == null){
            getAllOrder();
        }
        List<Order> orders = new ArrayList<>();
        for(restaurant.database.po.Order order: orders2){
            if(order.getDate().compareTo(begin) >= 0 &&
                    order.getDate().compareTo(end) <= 0){
                orders.add(PO2VO.order(order));
            }
        }
        return orders;
    }
    public List<Order> getOrderByTableIdAndDateRange(String tableId, Date begin, Date end) {
        if(orders2 == null){
            getAllOrder();
        }
        List<Order> orders = new ArrayList<>();
        for(restaurant.database.po.Order order: orders2){
            if(order.getSeatId().equals(tableId) &&
                    order.getDate().compareTo(begin) >= 0 &&
                    order.getDate().compareTo(end) <= 0){
                orders.add(PO2VO.order(order));
            }
        }
        return orders;
    }

    // 通讯
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
    public void addTableObserver(IManagementService.ITableObserver observer){
        tableObservers.add(observer);
    }
    public void removeTableObserver(IManagementService.ITableObserver observer){
        if(tableObservers.contains(observer)){
            tableObservers.remove(observer);
        }
    }
    @Override
    public void update(IData data) {
        String cmd = data.getCommand();
        if(InterModuleCommunication.CommandToManagement.CLIENT_NEW_ORDER.equals(cmd)) {
            // 能发出此条消息，说明table登陆成功，即fromId等于tableId
            orders.put(data.getFromId(), ((InterModuleCommunication.Data.MC) data.getData()).order);
            String tableId = data.getFromId();
            for(IManagementService.ITableObserver tableObserver : tableObservers) {
                tableObserver.newOrder(tableId);
            }
            return;
        }
    }
}
