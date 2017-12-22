package restaurant.service.pc.client;



import restaurant.communication.core.IPeer;
import restaurant.service.core.IClientService;
import restaurant.service.core.impl.InterModuleCommunication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientService implements IClientService{
    private IPeer peer;
    private Chat chat;
    private String tableId;

    public ClientService(String tableId, IPeer peer){
        this.tableId = tableId;
        this.peer = peer;
        peer.start();
        chat = new Chat(peer);
    }
    @Override
    public String getTableId(){ return tableId; }
    @Override
    public List<IClientService.IDishInfo> getDishMenu(){
        List l = new ArrayList();
        l.add(new DishInfo("酱爆肉", 12f, "", "荤菜"));
        l.add(new DishInfo("京酱肉丝", 10f, "", "荤菜"));
        l.add(new DishInfo("炒时蔬", 9f, "", "素菜"));
        l.add(new DishInfo("紫菜蛋花汤", 9f, "", "汤类"));
        return l;
    }
    @Override
    public List<String> getDishType(){
        List l = new ArrayList();
        l.add("全部");
        l.add("荤菜");
        l.add("素菜");
        l.add("汤类");
        l.add("甜品");
        l.add("特色菜");
        return l;
    }
    @Override
    public List<String> getOnlineTableIds(){
        return chat.getOnlineTableIds();
    }
    @Override
    public void sendMessage(String tableId, String message){
        chat.sendMessage(tableId, message);
    }
    @Override
    public List<IChatData> getSessionWith(String tableId){
        return chat.getSessionWith(tableId);
    }
    @Override
    public void sendOrder(Map<String, Integer> order){
        peer.sendCommand(InterModuleCommunication.ModuleId.KITCHEN,
                InterModuleCommunication.CommandToKitchen.CLIENT_NEW_ORDER, order);
    }
    @Override
    public void addChatObserver(IChatObserver observer){
        chat.addChatObserver(observer);
    }
    @Override
    public void endAllSesion() {
        chat.endAllSesion();
    }

    @Override
    public void requestService() {
        peer.sendCommand(InterModuleCommunication.ModuleId.MANAGEMENT,
                InterModuleCommunication.CommandToManagement.CLIENT_REQUEST_SERVICE,
                null);
    }


    /*

     */
    private class DishInfo implements IDishInfo{
        private String dishName;
        private Float price;
        private String pictrue;
        private String type;

        public DishInfo(String dishName, Float price, String pictrue, String type) {
            this.dishName = dishName;
            this.price = price;
            this.pictrue = pictrue;
            this.type = type;
        }

        @Override
        public String getDishName() {
            return dishName;
        }

        @Override
        public Float getPrice() {
            return price;
        }

        @Override
        public String getPictrue() {
            return pictrue;
        }

        @Override
        public String getType() {
            return type;
        }
    }
}
