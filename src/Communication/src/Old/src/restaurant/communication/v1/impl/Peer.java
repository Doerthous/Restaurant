package restaurant.communication.v1.impl;


import restaurant.communication.v1.ICommandObserver;
import restaurant.communication.v1.IData;
import restaurant.communication.v1.IPeer;
import restaurant.communication.v1.tools.SocketWrapper;

import java.util.*;

public class Peer implements IPeer, SocketWrapper.Action, ICommandObserver {
    private int port = 1444;
    private String id;
    private boolean running;
    private Map<String, List<IData>> sessionData;
    private Map<Integer, List<ICommandObserver>> observers;

    public Peer() {
        this.id = "";
        this.running = true;
        sessionData = new HashMap<>();
        observers = new HashMap<>();
        addCommandObserver(this, CMD_CHAT);
    }

    @Override
    public String getId(){
        return this.id;
    }
    @Override
    public void setId(String id) {
        this.id = id;
    }


    // Thread
    @Override
    public void send(IData data) {
        SocketWrapper.sendByTcp(data, data.getToId(), port);
    }
    @Override
    public void listen() {
        SocketWrapper.listenByTcp(port, this);
    }
    @Override
    public void stop() {
        running = false;
        sendCommand("localhost", CMD_EXIT);
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

    // Command
    @Override
    public void sendCommand(String id, Integer command) {
        sendCommand(id, command, new Byte[0]);
    }
    @Override
    public void sendCommand(String id, Integer command, Byte[] data) {
        send(new Data(this.id, id, command, new Date(), data));
    }
    public void addCommandObserver(ICommandObserver observer, Integer command) {
        List l = observers.getOrDefault(command, new ArrayList<>());
        l.add(observer);
        observers.putIfAbsent(command, l);
    }
    public void removeCommandObserver(ICommandObserver observer, Integer command) {
        List l = observers.getOrDefault(command, new ArrayList<>());
        if(l.size() > 0){
            l.remove(observer);
        }
    }


    // Chat
    @Override
    public List<String> getAllPeersId() {
        return new ArrayList(sessionData.keySet());
    }
    @Override
    public void sendMessage(String id, String message) {
        byte[] b = message.getBytes();
        Byte[] B = new Byte[b.length];
        for(int i = 0; i < b.length; ++i){
            B[i] = b[i];
        }
        IData data = new Data(this.id, id, CMD_CHAT, new Date(), B);
        List l = sessionData.getOrDefault(data.getFromId(), new ArrayList<>());
        l.add(data);
        sessionData.putIfAbsent(data.getFromId(), l);
        send(data);
    }
    @Override
    public List<IData> getSessionById(String id) {
        List from = new ArrayList<>();
        if(!id.equals(this.id)) { // 排除自己给自己传消息时的重复
            from = sessionData.getOrDefault(id, new ArrayList<>());
        }
        List<IData> to = sessionData.getOrDefault(this.id, new ArrayList<>());
        List<IData> session = new ArrayList<>();
        session.addAll(from);
        for(int i = 0; i < to.size(); ++i){
            if(to.get(i).getToId().equals(id)){
                session.add(to.get(i));
            }
        }
        return session;
    }
    @Override
    public void endSessionWith(String id) {
        sessionData.remove(id);
    }
    @Override
    public void endAllSessions() {
        sessionData = new HashMap<>();
    }


    @Override
    public void update(IData data) {
        if(data.getCommand().equals(CMD_CHAT)){
            List l = sessionData.getOrDefault(data.getFromId(), new ArrayList<>());
            l.add(data);
            sessionData.putIfAbsent(data.getFromId(), l);
        }
    }
}
