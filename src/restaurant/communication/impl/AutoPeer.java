package restaurant.communication.impl;

import restaurant.communication.ICommandObserver;
import restaurant.communication.IData;
import restaurant.communication.IPeer;
import restaurant.communication.PeerFactory;
import restaurant.communication.ip.IpManager;
import restaurant.communication.tools.SocketSender;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.*;

public class AutoPeer implements IPeer, ICommandObserver {
    private static final int BROADCAST_PORT = 1445;
    private Map<Integer, List<ICommandObserver>> observers;
    private IPeer p;
    private Set<String> others;
    private boolean hasIdentified;

    public AutoPeer() {
        IpManager.init();
        hasIdentified = false;
        others = new HashSet<>();
        observers = new HashMap<>();
        p = PeerFactory.newPeer(PeerFactory.T1);
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
        try {
            if (!data.getToId().equals(BROADCAST_ADDR)) {
                p.send(data);
            } else { // 如果是广播则用udp
                SocketSender.sendByUdp(data, data.getToId(), BROADCAST_PORT);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void listen() {
        new Thread(()->{
            p.listen();
            new Thread(() -> { // response
                try {
                    byte[] buffer = new byte[65535];
                    DatagramSocket me = new DatagramSocket(BROADCAST_PORT);
                    DatagramPacket packet = new DatagramPacket(buffer , buffer.length);
                    while(p.isRunning()){
                        me.receive(packet);
                        ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
                        ObjectInputStream ois = new ObjectInputStream(bais);
                        IData data = (IData) ois.readObject();;
                    /*System.out.println(ip + " at port " + packet.getPort( ) +
                            " says " + new String(packet.getData()));*/
                        ois.close();
                        dataProcess(new Data(packet.getAddress().toString().substring(1),
                                data.getToId(), data.getCommand(), data.getDate(), data.getData()));
                    }
                    if(me != null){
                        me.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            try {
                String oId = p.getId();
                while(oId.equals(p.getId())){
                    sendCommand(BROADCAST_ADDR, CMD_IDENTIFY);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendCommand("localhost", CMD_INIT_FINISHED);
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

    public void dataProcess(IData data){
        switch (data.getCommand()){
            case CMD_IDENTIFY:{
                if (!IpManager.isLocalIp(data.getFromId())) { // 不允许自己向自己确认身份
                    sendCommand(data.getFromId(), CMD_IDENTIFY_RESPONSE);
                }
            } break;
            case CMD_ONLINE_PEER_CONFIRM:{
                sendCommand(data.getFromId(), CMD_ONLINE_PEER_CONFIRM_ACK);
            }
        }
        List observers = this.observers.getOrDefault(data.getCommand(), new ArrayList<>());
        for(int i = 0; i < observers.size(); ++i){
            ICommandObserver observer = (ICommandObserver) observers.get(i);
            observer.update(data);
        }
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
        List l = observers.getOrDefault(command, new ArrayList<>());
        l.add(observer);
        observers.putIfAbsent(command, l);
    }

    @Override
    public void removeCommandObserver(ICommandObserver observer, Integer command) {
        p.removeCommandObserver(observer, command);
        List l = observers.getOrDefault(command, new ArrayList<>());
        if(l.size() > 0){
            l.remove(observer);
        }
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
        }
    }
}
