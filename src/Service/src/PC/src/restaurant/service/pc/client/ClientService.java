package restaurant.service.pc.client;



import restaurant.communication.core.IPeer;
import restaurant.database.IDb;
import restaurant.database.po.Dish;
import restaurant.service.core.IClientService;
import restaurant.service.core.impl.InterModuleCommunication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientService implements IClientService{
    private IPeer peer;
    private IDb db;
    private Chat chat;
    private String tableId;
    // 缓存
    private List<Dish> menu;

    public ClientService(String tableId, IPeer peer, IDb db){
        this.tableId = tableId;
        this.peer = peer;
        this.db = db;
        peer.init();
        peer.start(tableId);
        chat = new Chat(peer);
    }
    @Override
    public String getTableId(){ return tableId; }
    @Override
    public List<IClientService.IDishInfo> getDishMenu(){
        List l = new ArrayList();
        menu = db.getDishMenu();
        for(Dish pd : menu){
            l.add(new DishInfo(pd.getName(), pd.getPrice(), "", pd.getType()));
        }
        return l;
    }
    @Override
    public List<String> getDishType(){
        if(menu == null){
            menu = db.getDishMenu();
        }
        List l = new ArrayList();
        l.add("全部");
        for(Dish pd: menu){
            String type = pd.getType()
                    .replace("\n","")
                    .replace("\r","")
                    .replace(" ","");
            if(!l.contains(type)){
                l.add(type);
            }
        }
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
        // 过滤掉数量为0的菜品
        Map<String, Integer> o = new HashMap<>();
        for(String name: order.keySet()){
            if(!order.get(name).equals(0)){
                o.put(name, order.get(name));
            }
        }
        peer.sendCommand(InterModuleCommunication.ModuleId.KITCHEN,
                InterModuleCommunication.CommandToKitchen.CLIENT_NEW_ORDER, o);
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
