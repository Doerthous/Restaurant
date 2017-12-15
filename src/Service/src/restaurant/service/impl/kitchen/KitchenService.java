package restaurant.service.impl.kitchen;

import restaurant.communication.v2.ICommandObserver;
import restaurant.communication.v2.IData;
import restaurant.communication.v2.IPeer;
import restaurant.communication.v2.impl.Peer;
import restaurant.service.IKitchenService;
import restaurant.service.impl.ModuleCommand;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class KitchenService implements IKitchenService, ICommandObserver {
    private IPeer peer;
    private IOrderDataObserver orderDataObserver;

    public KitchenService(){
        peer = new Peer("kitchen");
        peer.start();
        peer.addCommandObserver(this, ModuleCommand.CLIENT_TO_KITCHEN_NEW_ORDER);
        peer.addCommandObserver(this, ModuleCommand.CLIENT_TO_KITCHEN_WHO_IS_KITCHEN);
    }
    @Override
    public void addOrderDataObserver(IOrderDataObserver orderDataObserver) {
        this.orderDataObserver = orderDataObserver;
    }

    @Override
    public void update(IData data) {
        switch (data.getCommand()){
            case ModuleCommand.CLIENT_TO_KITCHEN_NEW_ORDER:{
                Map<String, Integer> order = (Map<String, Integer>) data.getData();
                OrderData od = new OrderData(data.getFromId());
                for(String name : order.keySet()){
                    od.add(name, order.get(name));
                }
                orderDataObserver.newOrder(od);
            } break;
            case ModuleCommand.CLIENT_TO_KITCHEN_WHO_IS_KITCHEN: {
                peer.sendCommand(data.getFromId(),
                        ModuleCommand.KITCHEN_TO_CLIENT_WHO_IS_KITCHEN_ACK, null);
            }
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
