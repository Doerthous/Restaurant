package restaurant.service.pc.kitchen;


import restaurant.communication.core.ICommandObserver;
import restaurant.communication.core.IData;
import restaurant.communication.core.IPeer;
import restaurant.service.core.IKitchenService;
import restaurant.service.core.impl.InterModuleCommunication;

import java.util.HashMap;
import java.util.Map;

public class KitchenService implements IKitchenService, ICommandObserver {
    private IPeer peer;
    private IKitchenService.IOrderDataObserver orderDataObserver;

    public KitchenService(IPeer peer){
        this.peer = peer;
        peer.start();
        peer.addCommandObserver(this, InterModuleCommunication.CommandToKitchen.CLIENT_NEW_ORDER);
    }
    @Override
    public void addOrderDataObserver(IOrderDataObserver orderDataObserver) {
        this.orderDataObserver = orderDataObserver;
    }

    @Override
    public void dishFinish(String dishName, String tableId) {
        peer.sendCommand(InterModuleCommunication.ModuleId.MANAGEMENT,
                InterModuleCommunication.CommandToManagement.KITCHEN_DISH_FINISHED,
                InterModuleCommunication.Data.MK.dishFinish(dishName, tableId));
    }

    @Override
    public void update(IData data) {
        if(data.getCommand().equals(InterModuleCommunication.CommandToKitchen.CLIENT_NEW_ORDER)){
            Map<String, Integer> order = (Map<String, Integer>) data.getData();
            OrderData od = new OrderData(data.getFromId());
            for(String name : order.keySet()){
                od.add(name, order.get(name));
            }
            orderDataObserver.newOrder(od);
        }
    }

    class OrderData implements IOrderData{
        private String tableId;
        private Map<String, Integer> order;

        public OrderData(String tableId){
            this.tableId = tableId;
            order = new HashMap<>();
        }
        public void add(String dishName, Integer count){
            order.putIfAbsent(dishName, count);
        }

        @Override
        public String getTableId() {
            return tableId;
        }

        @Override
        public Map<String, Integer> getOrder() {
            return order;
        }
    }
}
