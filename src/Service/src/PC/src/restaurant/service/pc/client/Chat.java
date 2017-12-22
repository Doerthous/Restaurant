package restaurant.service.pc.client;


import restaurant.communication.core.ICommandObserver;
import restaurant.communication.core.IData;
import restaurant.communication.core.IPeer;
import restaurant.communication.core.utils.Debug;
import restaurant.service.core.IClientService;

import javax.swing.*;
import java.util.*;
import java.util.List;

import static java.lang.Thread.sleep;

public class Chat implements ICommandObserver {
    @Override
    public void update(IData data) {
        if(!data.getFromId().equals(getTableId())) {
            switch (data.getCommand()) {
                case ONLINE_TABLE_CONFIRM: {
                    debug(data.getFromId() +" ask who is online?");
                    debug("I'am online");
                    peer.sendCommand(data.getFromId(), ONLINE_TABLE_CONFIRM_ACK, null);
                    id2session.putIfAbsent(data.getFromId(), new ArrayList<>());
                    for(IClientService.IChatObserver observer : observers){
                        observer.online(data.getFromId());
                    }
                }
                break;
                case ONLINE_TABLE_CONFIRM_ACK: {
                    debug(data.getFromId() + " said he/she is online");
                    id2session.putIfAbsent(data.getFromId(), new ArrayList<>());
                    for(IClientService.IChatObserver observer : observers){
                        observer.online(data.getFromId());
                    }
                }
                break;
                case CHAT: {
                    List l = id2session.getOrDefault(data.getFromId(), new ArrayList<>());
                    l.add(data.getData());
                    for(IClientService.IChatObserver observer : observers){
                        observer.newMessage(data.getFromId());
                    }
                    debug("new message from " + data.getFromId());
                } break;
                case ONLINE: {

                } break;
                case OFFLINE: {

                } break;
            }
        }
    }

    public static class ChatData implements IClientService.IChatData {
        private String time;
        private String tableId;
        private String message;

        public ChatData(String time, String tableId, String message) {
            this.time = time;
            this.tableId = tableId;
            this.message = message;
        }

        @Override
        public String getTime() {
            return time;
        }

        @Override
        public String getTableId() {
            return tableId;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
    private static final String ONLINE_TABLE_CONFIRM = "CHAT_CMD_OTC";
    private static final String ONLINE_TABLE_CONFIRM_ACK = "CHAT_CMD_OTCA";
    private static final String CHAT = "CHAT_CMD_C";
    private static final String ONLINE = "CHAT_CMD_O";
    private static final String OFFLINE = "CHAT_CMD_OF";
    private Map<String, List<IClientService.IChatData>> id2session;
    private IPeer peer;
    private List<IClientService.IChatObserver> observers;
    private Debug debug;
    public Chat(IPeer peer){
        id2session = new HashMap<>();
        observers = new ArrayList<>();
        this.peer = peer;
        this.peer.addCommandObserver(this, ONLINE_TABLE_CONFIRM);
        this.peer.addCommandObserver(this, ONLINE_TABLE_CONFIRM_ACK);
        this.peer.addCommandObserver(this, CHAT);
        debug = new Debug(getClass());
        debug.on();
        // online table confirm
        new Thread(()->{
            int cnt = 5;
            for(;cnt > 0; --cnt){
                peer.sendCommand(IPeer.BROADCAST_ID, ONLINE_TABLE_CONFIRM, null);
                debug("who is online?");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public String getTableId(){
        return peer.getId();
    }
    public List<String> getOnlineTableIds(){
        return new ArrayList<>(id2session.keySet());
    }
    public void sendMessage(String tableId, String message){
        if(tableId.length() == 0){
            JOptionPane.showMessageDialog(null,
                    "请选择联系人",
                    "提示",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        List l = id2session.getOrDefault(tableId, new ArrayList<>());
        l.add(new ChatData(new Date().toString(), getTableId(), message));
        peer.sendCommand(tableId, CHAT, new ChatData(new Date().toString(), getTableId(), message));
    }
    public List<IClientService.IChatData> getSessionWith(String tableId){
        return id2session.getOrDefault(tableId, new ArrayList<>());
    }
    public void addChatObserver(IClientService.IChatObserver observer){
        observers.add(observer);
    }
    public void endAllSesion(){
        for(String id : id2session.keySet()){
            id2session.put(id, new ArrayList<>());
        }
    }
    
    private void debug(String message){
        debug.println(getTableId()+": "+message);
    }
}
