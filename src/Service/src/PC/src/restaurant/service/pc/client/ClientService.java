package restaurant.service.pc.client;



import restaurant.communication.core.ICommandObserver;
import restaurant.communication.core.IData;
import restaurant.communication.core.IPeer;
import restaurant.database.IDb;
import restaurant.database.po.Dish;
import restaurant.service.core.IClientService;
import restaurant.service.core.impl.InterModuleCommunication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

public class ClientService implements IClientService, ICommandObserver{
    private IPeer peer;
    private IDb db;
    private Chat chat;
    //private String tableId;
    private Boolean isLogined;
    private String failedReason;
    // 缓存
    private List<Dish> menu;
    private Map<String, Integer> order;

    public ClientService(IPeer peer, IDb db){
        this.peer = peer;
        this.db = db;
        peer.init();
        peer.start(new SimpleDateFormat("HHmmssSS").format(new Date())); // 临时id
        peer.addCommandObserver(this, InterModuleCommunication.CommandToClient.MANAGEMENT_OPEN_TABLE);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToClient.MANAGEMENT_CLOSE_TABLE);
        peer.addCommandObserver(this, InterModuleCommunication.CommandToClient.MANAGEMENT_TABLE_ONLINE_ACK);
        chat = new Chat(peer);
        tos = new ArrayList<>();
    }
    @Override
    public String getTableId(){ return peer.getId(); }

    @Override
    public Boolean login(String tableId) {
        peer.sendCommand(InterModuleCommunication.ModuleId.MANAGEMENT,
                InterModuleCommunication.CommandToManagement.CLIENT_TABLE_ONLINE,
                InterModuleCommunication.Data.MC.login(tableId));
        failedReason = "No response";
        while(failedReason.equals("No response")){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(isLogined){
            peer.start(tableId);
            chat.start();
        }
        return isLogined;
    }

    @Override
    public String getLoginFailedReason() {
        return failedReason;
    }

    @Override
    public List<IClientService.IDishInfo> getDishMenu(){
        List l = new ArrayList();
        menu = db.getDishMenu();
        order = new HashMap<>();
        for(Dish pd : menu){
            l.add(new DishInfo(pd.getName(), pd.getPrice(), pd.getPicture(), pd.getType()));
            order.put(pd.getName(), 0);
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
    public void sendOrder(Map<String, Integer> order, Float totalCost){
        // 过滤掉数量为0的菜品
        Map<String, Integer> o = new HashMap<>();
        for(String name: order.keySet()){
            int count = order.get(name) - this.order.get(name);
            if(count != 0){
                o.put(name, count);
            }
        }
        this.order = new HashMap<>();
        for(String name: order.keySet()){
            if(!order.get(name).equals(0)){
                this.order.put(name, order.get(name));
            }
        }
        peer.sendCommand(InterModuleCommunication.ModuleId.KITCHEN,
                InterModuleCommunication.CommandToKitchen.CLIENT_NEW_ORDER,
                InterModuleCommunication.Data.KC.sendOrder(o));
        peer.sendCommand(InterModuleCommunication.ModuleId.MANAGEMENT,
                InterModuleCommunication.CommandToManagement.CLIENT_NEW_ORDER,
                InterModuleCommunication.Data.MC.sendOrder(this.order, totalCost));
        this.order = order;
    }
    @Override
    public void addChatObserver(IChatObserver observer){
        chat.addChatObserver(observer);
    }
    @Override
    public void removeChatObserver(IChatObserver observer) {
        chat.removeChatObserver(observer);
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

    private List<ITableObserver> tos;
    @Override
    public void addTableObserver(ITableObserver observer) {
        tos.add(observer);
    }

    @Override
    public void removeTableObserver(ITableObserver observer) {
        if(tos.contains(observer)) {
            tos.remove(observer);
        }
    }

    @Override
    public void update(IData data) {
        String cmd = data.getCommand();
        if(InterModuleCommunication.CommandToClient.MANAGEMENT_OPEN_TABLE.equals(cmd)){
            for(ITableObserver to: tos){
                to.openTable();
            }
        } else if(InterModuleCommunication.CommandToClient.MANAGEMENT_CLOSE_TABLE.equals(cmd)){
            for(ITableObserver to: tos){
                to.closeTable();
            }
            for(String dish: order.keySet()){ // 清除缓存
                order.put(dish, 0);
            }
        } else if(InterModuleCommunication.CommandToClient.MANAGEMENT_TABLE_ONLINE_ACK.equals(cmd)){
            InterModuleCommunication.Data.MC mc = (InterModuleCommunication.Data.MC) data.getData();
            synchronized (this) {
                failedReason = mc.failedReason;
                isLogined = mc.isLogined;
            }
        }
    }


    /*

     */
    private class DishInfo implements IDishInfo{
        private String dishName;
        private Float price;
        private byte[] picture;
        private String type;

        public DishInfo(String dishName, Float price, InputStream picture, String type) {
            this.dishName = dishName;
            this.price = price;
            if(picture != null) {
                try {
                    int len = picture.available();
                    this.picture = new byte[len];
                    for (int rl = 0;
                         (rl = picture.read(this.picture, rl, len)) != -1;
                         len -= rl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        public byte[] getPicture() {
            return picture;
        }

        @Override
        public String getType() {
            return type;
        }
    }
}
