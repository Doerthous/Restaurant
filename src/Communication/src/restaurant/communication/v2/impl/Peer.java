package restaurant.communication.v2.impl;

import restaurant.communication.v2.ICommandObserver;
import restaurant.communication.v2.IData;
import restaurant.communication.v2.IPeer;
import restaurant.communication.v2.lowlever.Port;
import restaurant.communication.v2.lowlever.SocketWrapper;

import java.util.*;

import static java.lang.Thread.sleep;

public class Peer implements IPeer, SocketWrapper.IAction {
    private static final String SELF_IDENTIFY = "PEER_CMD_SI";
    private static final String SELF_IDENTIFY_ACK = "PEER_CMD_SIA";
    private static final String ID_IS_WHO = "PEER_CMD_IIW";
    private static final String ID_IS_WHO_ACK = "PEER_CMD_IIWA";
    private static final String BROADCAST_ADDR = "255.255.255.255";
    public static final String BROADCAST_ID = BROADCAST_ADDR;
    private static final int BROADCAST_PORT = 14444;
    private SocketWrapper socketWrapper;
    private String tcpListenerId;
    private String udpListennerId;
    private String id;
    private String ip;
    private Integer port;
    private Map<String, String> id2ip;
    private Map<String, Integer> id2port;
    private Map<String, List<ICommandObserver>> observers;

    public Peer(String id){
        socketWrapper = new SocketWrapper();
        this.id = id;
        this.ip = UUID.randomUUID().toString();
        id2ip = new HashMap<>();
        id2port = new HashMap<>();
        observers = new HashMap<>();
    }


    @Override
    public void start() {
        // setup socket
        port = Port.getFreePort(14445);
        tcpListenerId = socketWrapper.listenByTcp(port, this);
        udpListennerId = socketWrapper.listenByUdp(BROADCAST_PORT, this);

        // indentify ip
        String oIp = ip;
        while(ip.equals(oIp)){
            sendCommand(BROADCAST_ID, SELF_IDENTIFY, ip);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void stop() {
        socketWrapper.stopListener(tcpListenerId);
        socketWrapper.stopListener(udpListennerId);
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
    public void sendCommand(String id, String command, Object data) {
        whoIs(id);
        IData idata = new Data(this.id, id, command, data);
        if(id.equals(BROADCAST_ID)){
            SocketWrapper.IData socketData = socketWrapper.packData(ip, port,
                    BROADCAST_ADDR, BROADCAST_PORT, idata);
            socketWrapper.sendByUdp(socketData);
        } else {
            SocketWrapper.IData socketData = socketWrapper.packData(ip, port,
                    id2ip.get(id), id2port.get(id), idata);
            socketWrapper.sendByTcp(socketData);
        }
    }
    @Override
    public void addCommandObserver(ICommandObserver observer, String command) {
        List l = observers.getOrDefault(command, new ArrayList<>());
        l.add(observer);
        observers.putIfAbsent(command, l);
    }
    @Override
    public void removeCommandObserver(ICommandObserver observer, String command) {
        List l = observers.getOrDefault(command, new ArrayList<>());
        l.remove(observer);
        observers.putIfAbsent(command, l);
    }


    @Override
    public void receive(SocketWrapper.IData data) {
        IData idata = (IData)data.getData();
        switch (idata.getCommand()){
            case SELF_IDENTIFY: {
                String msg = (String) idata.getData();
                if (!msg.equals(ip)) {
                    id2ip.putIfAbsent(idata.getFromId(), data.getSourceIp());
                    id2port.putIfAbsent(idata.getFromId(), data.getSourcePort());
                    sendCommand(idata.getFromId(), SELF_IDENTIFY_ACK, data.getSourceIp());
                }
            } break;
            case SELF_IDENTIFY_ACK: {
                ip = data.getTargetIp();
                id2ip.putIfAbsent(idata.getFromId(), data.getSourceIp());
                id2port.putIfAbsent(idata.getFromId(), data.getSourcePort());
                System.out.println("Identified: " + ip + ", " + port);
            } break;
            case ID_IS_WHO: {
                if(id.equals(idata.getData())){
                    id2ip.putIfAbsent(idata.getFromId(), data.getSourceIp());
                    id2port.putIfAbsent(idata.getFromId(), data.getSourcePort());
                    sendCommand(idata.getFromId(), ID_IS_WHO_ACK, null);
                    System.out.println(idata.getFromId() + " ask me");
                }
            }
            case ID_IS_WHO_ACK: {
                id2ip.putIfAbsent(idata.getFromId(), data.getSourceIp());
                id2port.putIfAbsent(idata.getFromId(), data.getSourcePort());
                System.out.println(idata.getFromId() + " is "
                        + data.getSourceIp() + ":" + data.getSourcePort());
            }
        }
        List<ICommandObserver> l = observers.getOrDefault(idata.getCommand(), new ArrayList<>());
        for(ICommandObserver observer : l){
            observer.update(idata);
        }
    }

    private void whoIs(String id){
        while(!id.equals(BROADCAST_ID) && !id2ip.keySet().contains(id)){
            sendCommand(BROADCAST_ID, ID_IS_WHO, id);
            System.out.println("who is " + id);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
