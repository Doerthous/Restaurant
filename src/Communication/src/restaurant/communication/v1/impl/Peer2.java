package restaurant.communication.v1.impl;

import restaurant.communication.v1.ICommandObserver;
import restaurant.communication.v1.IData;
import restaurant.communication.v1.IPeer;
import restaurant.communication.v1.tools.Port;
import restaurant.communication.v1.tools.SocketWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Peer2 implements IPeer, SocketWrapper.Action, ICommandObserver {
    private static final String LOCALHOST = "localhost";
    private static final String BROADCAST_ADDR = "255.255.255.255";
    private static final int BROADCAST_PORT = 1444;
    private String id;
    private String ip;
    private int port;
    private Map<String, Integer> id2port;
    private Map<String, String> id2ip;
    private boolean running;
    private Map<String, List<IData>> sessionData;
    private Map<Integer, List<ICommandObserver>> observers;

    public Peer2(String id){
        this.id = id;
        this.ip = "unkonwn";
        port = Port.getFreePort(1445);
        id2port = new HashMap<>();
        id2ip = new HashMap<>();
        this.running = true;
        sessionData = new HashMap<>();
        observers = new HashMap<>();
        addCommandObserver(this, CMD_CHAT);
        addCommandObserver(this, CMD_ONLINE_PEER_CONFIRM);
        addCommandObserver(this, CMD_IDENTIFY);
        addCommandObserver(this, CMD_IDENTIFY_RESPONSE);
        addCommandObserver(this, CMD_ONLINE_PEER_CONFIRM_ACK);
    }

    @Override
    public void update(IData data) {

    }

    @Override
    public void send(IData data) {
        if (data.getToId().equals(BROADCAST_ADDR)) { // 如果是广播则用udp
            SocketWrapper.sendByUdp(data, BROADCAST_ADDR, BROADCAST_PORT);
        } else {
            SocketWrapper.sendByTcp(data, id2ip.get(data.getToId()), id2port.get(data.getToId()));
        }
    }

    @Override
    public void listen() {
        SocketWrapper.listenByTcp(port, this);
        SocketWrapper.listenByUdp(BROADCAST_PORT, this);
        new Thread(()->{
            try { // 广播确认自己的ip
                String oIp = ip;
                while(oIp.equals(ip)){
                    sendCommand(BROADCAST_ADDR, CMD_IDENTIFY);
                    sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // sendCommand("localhost", CMD_INIT_FINISHED); // 通知上层，初始化完成
        }).start();
    }

    @Override
    public void stop() {
        running = false;
        //sendCommand(id, CMD_EXIT);
        //sendCommand(BROADCAST_ADDR, CMD_EXIT);
    }

    @Override
    public void receive(Object data) {
        List observers = this.observers.getOrDefault(((IData)data).getCommand(), new ArrayList<>());
        for(int i = 0; i < observers.size(); ++i){
            ICommandObserver observer = (ICommandObserver) observers.get(i);
            observer.update((IData)data);
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void sendCommand(String id, Integer command) {

    }

    @Override
    public void sendCommand(String id, Integer command, Byte[] data) {

    }

    @Override
    public void addCommandObserver(ICommandObserver observer, Integer command) {
        List l = observers.getOrDefault(command, new ArrayList<>());
        l.add(observer);
        observers.putIfAbsent(command, l);
    }

    @Override
    public void removeCommandObserver(ICommandObserver observer, Integer command) {
        List l = observers.getOrDefault(command, new ArrayList<>());
        if(l.size() > 0){
            l.remove(observer);
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public List<String> getAllPeersId() {
        return new ArrayList<>(id2port.keySet());
    }

    @Override
    public void sendMessage(String id, String message) {

    }

    @Override
    public List<IData> getSessionById(String id) {
        return null;
    }

    @Override
    public void endSessionWith(String id) {
        sessionData.remove(id);
    }

    @Override
    public void endAllSessions() {
        sessionData = new HashMap<>();
    }
}
