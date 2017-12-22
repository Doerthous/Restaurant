package restaurant.communication.v1.impl;

import restaurant.communication.v1.ICommandObserver;
import restaurant.communication.v1.IData;
import restaurant.communication.v1.IPeer;
import restaurant.communication.v1.PeerFactory;
import restaurant.communication.v1.ip.IpManager;
import restaurant.communication.v1.tools.SocketWrapper;

import java.util.*;

import static java.lang.Thread.sleep;

public class AutoPeer implements IPeer, ICommandObserver {
    /*private static final String CMD_IDENTIFY = "autopeer identify";
    private static final String CMD_IDENTIFY_RESPONSE = "autopeer identify response";
    private static final String CMD_ONLINE_PEER_CONFIRM = "autopeer online peer confirm";
    private static final String CMD_ONLINE_PEER_CONFIRM_ACK = "autopeer online peer confirm ack";*/
    private int broadcast_port = 1445;
    //private Map<Integer, List<ICommandObserver>> observers;
    private Peer p;
    private Set<String> others;
    private boolean hasIdentified;

    public AutoPeer() {
        IpManager.init();
        hasIdentified = false;
        others = new HashSet<>();
        //observers = new HashMap<>();
        p = (Peer) PeerFactory.newPeer(PeerFactory.T1);
        addCommandObserver(this, CMD_ONLINE_PEER_CONFIRM);
        addCommandObserver(this, CMD_IDENTIFY);
        addCommandObserver(this, CMD_IDENTIFY_RESPONSE);
        addCommandObserver(this, CMD_ONLINE_PEER_CONFIRM_ACK);
    }

    @Override
    public String getId() {
        return p.getId();
    }
    @Override
    public void setId(String id) {
        p.setId(id);
    }

    @Override
    public void send(IData data) {
        if (!data.getToId().equals(BROADCAST_ADDR)) {
            p.send(data);
        } else { // 如果是广播则用udp
            SocketWrapper.sendByUdp(data, data.getToId(), broadcast_port);
        }
    }
    @Override
    public void listen() {
        p.listen();
        SocketWrapper.listenByUdp(broadcast_port, p);
        new Thread(()->{
            try { // 广播确认自己的ip
                String oId = p.getId();
                while(oId.equals(p.getId())){
                    sendCommand(BROADCAST_ADDR, CMD_IDENTIFY);
                    sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendCommand("localhost", CMD_INIT_FINISHED); // 通知上层，初始化完成
        }).start();
    }
    @Override
    public void stop() {
        sendCommand(BROADCAST_ADDR, CMD_EXIT);
        p.stop();
    }
    @Override
    public boolean isRunning() {
        return p.isRunning();
    }


    @Override
    public void sendCommand(String id, Integer command) {
        sendCommand(id, command, new Byte[0]);
    }

    @Override
    public void sendCommand(String id, Integer command, Byte[] data) {
        send(new Data(p.getId(), id, command, new Date(), data));
    }

    @Override
    public void addCommandObserver(ICommandObserver observer, Integer command) {
        p.addCommandObserver(observer, command);
        // l = observers.getOrDefault(command, new ArrayList<>());
        //l.add(observer);
        //observers.putIfAbsent(command, l);
    }

    @Override
    public void removeCommandObserver(ICommandObserver observer, Integer command) {
        p.removeCommandObserver(observer, command);
        //List l = observers.getOrDefault(command, new ArrayList<>());
        //if(l.size() > 0){
         //   l.remove(observer);
        //}
    }

    @Override
    public List<String> getAllPeersId() {
        List<String> l = new ArrayList<>();
        l.addAll(p.getAllPeersId());
        l.addAll(new ArrayList<>(others));
        return new ArrayList<>(new HashSet<>(l));
    }

    @Override
    public void sendMessage(String id, String message) {
        if (!id.equals(BROADCAST_ADDR)) {
            p.sendMessage(id, message);
        } else { // 暂不支持用广播传递消息
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public List<IData> getSessionById(String id) {
        return p.getSessionById(id);
    }

    @Override
    public void endSessionWith(String id) {
        p.endSessionWith(id);
    }

    @Override
    public void endAllSessions() {
        p.endAllSessions();
    }


    @Override
    public void update(IData data) {
        switch (data.getCommand()) {
            case CMD_IDENTIFY_RESPONSE: {
                setId(data.getToId());
                /*System.out.println("I'm " + p.getId());*/
                removeCommandObserver(this, CMD_IDENTIFY_RESPONSE);
                sendCommand(BROADCAST_ADDR, CMD_ONLINE_PEER_CONFIRM);
                hasIdentified = true;
            } break;
            case CMD_ONLINE_PEER_CONFIRM_ACK:{
                if(hasIdentified) {
                    if(data.getFromId().length() > 0) {
                        others.add(data.getFromId());
                    }
                }
            } break;
            case CMD_IDENTIFY:{
                if (!IpManager.isLocalIp(data.getFromId())) { // 不允许自己向自己确认身份
                    sendCommand(data.getFromId(), CMD_IDENTIFY_RESPONSE);
                }
            } break;
            case CMD_ONLINE_PEER_CONFIRM:{
                while(!hasIdentified){
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                sendCommand(data.getFromId(), CMD_ONLINE_PEER_CONFIRM_ACK);
            } break;
        }
    }
}
