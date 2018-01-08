package restaurant.communication.core.impl;

import restaurant.communication.core.ICommandObserver;
import restaurant.communication.core.IData;
import restaurant.communication.core.IPeer;
import restaurant.communication.core.utils.Tools;
import restaurant.communication.core.utils.Debug;
import restaurant.communication.core.utils.MapOperation;

import java.io.Serializable;
import java.net.ConnectException;
import java.util.*;

import static java.lang.Thread.sleep;

public class Peer implements IPeer, ISocketWrapper.IAction {
    private static final String WHERE_IS_ID = Peer.class.getName()+"[WII]";
    private static final String WHERE_IS_ID_ACK = Peer.class.getName()+"[WIIA]";
    private static final String ID_ONLINE = Peer.class.getName()+"[ION]";
    private static final String ID_OFFLINE = Peer.class.getName()+"[IOFF]";
    private static final String WRONG_ID = Peer.class.getName()+"[WI]";
    private static final IData INIT_COMPLETED = new restaurant.communication.core.impl.Data(null,null,CMD_INIT_COMPLETED,null);
    private static final IData ID_IS_ALREADY_IN_USED = new restaurant.communication.core.impl.Data(null,null,CMD_ID_IS_ALREADY_IN_USED,null);
    private static String BROADCAST_ADDR = "255.255.255.255";
    private static int BROADCAST_PORT = 14444;
    private ISocketWrapper socketWrapper;
    private IIPTools ipTools;
    private LANIpIdentify lanIpIdentify;
    private String tcpListenerId;
    private String udpListenerId;
    private String id;
    private String ip;
    private Integer port;
    private Map<String, String> id2ip;
    private Map<String, Integer> id2port;
    private Map<String, List<ICommandObserver>> observers;
    private Debug debug;

    public Peer(ISocketWrapper socketWrapper, IIPTools ipTools){
        this.socketWrapper = socketWrapper;
        this.ipTools = ipTools;
        this.id = null;//"";
        this.isPause = true;
        this.ip = "";
        id2ip = new HashMap<>();
        id2port = new HashMap<>();
        observers = new HashMap<>();
        debug = new Debug(getClass());
        debug.on();
    }

    @Override
    public void init() {
        // indentify ip
        lanIpIdentify = new LANIpIdentify(socketWrapper, BROADCAST_PORT, ipTools);
        ip = lanIpIdentify.getIp();
        BROADCAST_ADDR = Tools.getIpv4BroadcastAddress(ip, ipTools.getIpv4SubnetMask(ip));

        // setup socket
        port = Tools.getFreePort(ip,BROADCAST_PORT+1000);
        udpListenerId = socketWrapper.listenByUdp(
                SocketData.packAddress(ip, BROADCAST_PORT), this);
        tcpListenerId = socketWrapper.listenByTcp(
                SocketData.packAddress(ip, port), this);
        debug("listen by tcp: ["+ip+":"+port+"]");
        debug("listen by udp: ["+ip+":"+BROADCAST_PORT+"]");

        // notify observer init completed
        updateCommandObserver(CMD_INIT_COMPLETED, INIT_COMPLETED);
    }

    @Override
    public void start(String id) {
        if(id != null && !id.equals(this.id)) {
            continueMessageHandle();
            sendCommand(BROADCAST_ID, ID_ONLINE,
                    new Bundle()
                    .putString("old id", this.id)
                    .putString("new id", id)
                    .putString("ip", ip)
                    .putInteger("port", port));
            this.id = id;
        }
    }

    @Override
    public void stop() {
        sendCommand(BROADCAST_ID, ID_OFFLINE, null);
        socketWrapper.killListener(tcpListenerId);
        socketWrapper.killListener(udpListenerId);
        lanIpIdentify.stop();
    }
    /*
        暂停消息处理
     */
    private boolean isPause;
    private void pauseMessageHandle(){
        isPause = true;
    }
    private void continueMessageHandle() {
        isPause = false;
    }

    @Override
    public String getId() {
        return id;
    }
    @Override
    public void sendCommand(String id, String command, Object data) {
        new Thread(()->{
            if(!isPause && whereIsId(id)) {
                IData idata = new restaurant.communication.core.impl.Data(this.id, id, command, data);
                if (id.equals(BROADCAST_ID)) {
                    ISocketWrapper.IData socketData = SocketData.packData(ip, port,
                            BROADCAST_ADDR, BROADCAST_PORT, idata);
                    debug.println(new AddressDebug(this.id, ip, port).toString() + ": send to " +
                            new AddressDebug(id, BROADCAST_ADDR, BROADCAST_PORT) + " by udp");
                    socketWrapper.sendByUdp(socketData);
                } else {
                    ISocketWrapper.IData socketData = SocketData.packData(ip, port,
                            id2ip.get(id), id2port.get(id), idata);
                    debug.println(new AddressDebug(this.id, ip, port).toString() + ": send to " +
                            new AddressDebug(id, id2ip.get(id), id2port.get(id)) + " by tcp");
                    try {
                        socketWrapper.sendByTcp(socketData);
                    } catch (ConnectException e) { // 不在线
                        debug(id + " is not online");
                        updateCommandObserver(CMD_ID_IS_NOT_ONLINE,
                                new restaurant.communication.core.impl.Data(this.id, id,
                                        CMD_ID_IS_NOT_ONLINE, null));
                    }
                }
            }
        }).start();
    }
    @Override
    public void addCommandObserver(ICommandObserver observer, String command) {
        //List l = observers.getOrDefault(command, new ArrayList<>());
        List l = (List)MapOperation.getOrDefault(observers, command, new ArrayList<>());
        l.add(observer);
        //observers.putIfAbsent(command, l);
        MapOperation.putIfAbsent(observers, command, l);
    }
    @Override
    public void removeCommandObserver(ICommandObserver observer, String command) {
        //List l = observers.getOrDefault(command, new ArrayList<>());
        List l = (List)MapOperation.getOrDefault(observers, command, new ArrayList<>());
        l.remove(observer);
        //observers.putIfAbsent(command, l);
        MapOperation.putIfAbsent(observers, command, l);
    }

    @Override
    public void receive(ISocketWrapper.IData data) {
        if(data.getData() instanceof IData) {
            IData idata = (IData) data.getData();
            if(idata.getToId().equals(id) || idata.getToId().equals(IPeer.BROADCAST_ID)) { // 只接受自己id和广播
                // 如未暂停则进行消息处理
                if (!isPause) {
                    // 处理内部消息
                    String cmd = idata.getCommand();
                    if (WHERE_IS_ID.equals(cmd)) {
                        whereIsIdMessage(idata);
                        return;
                    }
                    if (WHERE_IS_ID_ACK.equals(cmd)) {
                        whereIsIdAckMessage(idata);
                        return;
                    }
                    if (ID_ONLINE.equals(cmd)) {
                        idChangeMessage(idata);
                        return;
                    }
                    if (ID_OFFLINE.equals(cmd)) {
                        idOfflineMessage(idata.getFromId());
                        return;
                    }
                    if (WRONG_ID.equals(cmd)) {
                        wrongIdMessage(idata);
                        return;
                    }
                    // 非内部消息传给监听者处理
                    updateObservers(idata);
                }
            } else { // 通知发送方错误id
                sendCommand(idata.getFromId(), WRONG_ID, idata);
                debug(idata.getFromId() +" send message with wrong id["+idata.getToId()+"] to me");
            }
        }
    }
    private void updateObservers(IData data){
        updateCommandObserver(data.getCommand(), data);
    }
    private void updateCommandObserver(String command, IData data){
        List<ICommandObserver> l = (List<ICommandObserver>)
                MapOperation.getOrDefault(observers, command, new ArrayList<>());
        for (ICommandObserver observer : l) {
            observer.update(data);
        }
    }

    private class AddressDebug {
        public String id;
        public String ip;
        public Integer port;

        public AddressDebug(String id, String ip, Integer port) {
            this.id = id;
            this.ip = ip;
            this.port = port;
        }

        @Override
        public String toString() {
            return id+"["+ip+", "+port+"]";
        }
    }
    private void debug(String message){
        debug.println(new AddressDebug(id, ip, port).toString() +": "+message);
    }
    private boolean whereIsId(String id){
        int count = 10;
        if(!id.equals(BROADCAST_ID) && !id2ip.keySet().contains(id)){
            Bundle bundle = new Bundle()
                    .putString("source ip", ip)
                    .putInteger("source tcp port", port)
                    .putString("target id", id);
            while(!id2ip.keySet().contains(id) && count > 0){
                sendCommand(BROADCAST_ID, WHERE_IS_ID, bundle);
                debug("where is " + id + "[" + BROADCAST_ID + "," + BROADCAST_ADDR+"]");
                try {
                    sleep(1000);
                    --count;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(count == 0){
                updateCommandObserver(CMD_ID_IS_NOT_ONLINE,
                        new restaurant.communication.core.impl.Data(this.id, id,
                                CMD_ID_IS_NOT_ONLINE, null));
                return false;
            }
        }
        return true;
    }
    private void idChangeMessage(IData data){
        Bundle d = (Bundle) data.getData();
        String oldId = d.getString("old id");
        String newId = d.getString("new id");
        String ip = d.getString("ip");
        Integer port = d.getInteger("port");
        // 如果远程id与自己id相同，则id冲突，暂停Peer，直到重设id
        if(newId.equals(id)){
            if(!this.ip.equals(ip) && !this.port.equals(port)) {
                pauseMessageHandle();
                updateCommandObserver(CMD_ID_IS_ALREADY_IN_USED, ID_IS_ALREADY_IN_USED);
            }
        } else {
            // 通知同id者
            if (id2ip.keySet().contains(newId)) {
                String sIp = id2ip.get(newId);
                Integer sPort = id2port.get(newId);
                if(!sIp.equals(ip) && !sPort.equals(port)) {
                    sendCommand(newId, ID_ONLINE, data.getData());
                }
            }
            // 更新信息
            // 添加新信息
            id2ip.put(newId, ip);
            id2port.put(newId, port);
            idOfflineMessage(oldId);
        }
    }
    private void whereIsIdMessage(IData data){
        if(data.getData() instanceof Bundle) {
            Bundle bundle = (Bundle) data.getData();
            String targetId = bundle.getString("target id");
            if (id.equals(targetId)) {
                String sourceIp = bundle.getString("source ip");
                Integer sourceTcpPort = bundle.getInteger("source tcp port");
                String sourceId = data.getFromId();
                MapOperation.putIfAbsent(id2ip, sourceId, sourceIp);
                MapOperation.putIfAbsent(id2port, sourceId, sourceTcpPort);
                bundle = new Bundle()
                        .putString("source ip", ip)
                        .putInteger("source tcp port", port)
                        .putString("target id", sourceId);
                sendCommand(sourceId, WHERE_IS_ID_ACK, bundle);
                debug(new AddressDebug(sourceId, sourceIp, sourceTcpPort).toString() +
                        " ask for my address");
            }
        }
    }
    private void whereIsIdAckMessage(IData data){
        if(data.getData() instanceof Bundle) {
            Bundle bundle = (Bundle) data.getData();
            String targetId = bundle.getString("target id");
            if (id.equals(targetId)) {
                String sourceIp = bundle.getString("source ip");
                Integer sourceTcpPort = bundle.getInteger("source tcp port");
                String sourceId = data.getFromId();
                MapOperation.putIfAbsent(id2ip, sourceId, sourceIp);
                MapOperation.putIfAbsent(id2port, sourceId, sourceTcpPort);
                debug(new AddressDebug(sourceId, sourceIp, sourceTcpPort).toString() +
                        " is there");
            }
        }
    }
    private void idOfflineMessage(String offlineId){
        removeId(offlineId);
        debug(offlineId+" offline");
    }
    private void wrongIdMessage(IData data){
        if(data.getData() instanceof IData) {
            IData idata = (IData) data.getData();
            // 发消息给错误id
            debug("wrong message to " + data.getFromId() + ", expect id: " +idata.getToId());
            removeId(idata.getToId());
            // 消息重发
            sendCommand(idata.getToId(), idata.getCommand(), idata.getData());
        }
    }
    private void removeId(String id){
        if(id2ip.keySet().contains(id)){
            id2ip.remove(id);
        }
        if(id2port.keySet().contains(id)){
            id2port.remove(id);
        }
    }


    /*
        确认ip
     */
    public static class Data implements Serializable {
        public String uuid;
        public List<ISocketWrapper.IAddress> ipps;

        public Data(String uuid, List<ISocketWrapper.IAddress> ipps) {
            this.uuid = uuid;
            this.ipps = ipps;
        }
    }
    private class LANIpIdentify {

        private class BL implements ISocketWrapper.IAction {
            @Override
            public void receive(ISocketWrapper.IData data) {
                if(data.getData() instanceof Data) {
                    Data d = (Data) data.getData();
                    synchronized (uuid) {
                        if (!d.uuid.equals(uuid)) {
                            if (broadcastListenerId.equals("unknown")) {
                                debug.println("receive broadcast");
                                for (ISocketWrapper.IAddress ipp : d.ipps) {
                                    if (ipp.getIp().equals(data.getSourceAddress().getIp())) {
                                        try {
                                            sw.sendByTcp(SocketData.packData("", 0,
                                                    ipp.getIp(), ipp.getPort(), ipp.getIp()));
                                        } catch (ConnectException e) {
                                            debug.println("bad address while confirming address: ["+
                                            ipp.getIp()+":"+ipp.getPort()+"]");
                                        }
                                        debug.println(ipp.getIp() + " " + ipp.getPort());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        private class BLAfter implements ISocketWrapper.IAction {
            private String broadcastIp;

            public BLAfter() {
                this.broadcastIp = Tools.getIpv4BroadcastAddress(ip, ipTool.getIpv4SubnetMask(ip));
            }

            @Override
            public void receive(ISocketWrapper.IData data) {
                if(data.getData() instanceof Data) {
                    Data d = (Data) data.getData();
                    debug.println("new one coming: ");
                    debug.println("\t my broadcast ip " + broadcastIp);
                    debug.println("\t target ip " + data.getTargetAddress().getIp());
                    for (ISocketWrapper.IAddress ipp : d.ipps) {
                        if (ipp.getIp().equals(data.getSourceAddress().getIp())) {
                            try {
                                sw.sendByTcp(SocketData.packData("", 0,
                                        ipp.getIp(), ipp.getPort(), ipp.getIp()));
                            } catch (ConnectException e) {
                                debug.println("bad address while confirming address: ["+
                                        ipp.getIp()+":"+ipp.getPort()+"]");
                            }
                            debug.println(ipp.getIp() + " " + ipp.getPort());
                            break;
                        }
                    }
                }
            }
        }
        private class SL implements ISocketWrapper.IAction {
            private List<String> ips = new ArrayList<>();
            @Override
            public void receive(ISocketWrapper.IData data) {
                synchronized (uuid) {
                    synchronized (ips) {
                        s.stop();
                        t.stop();
                        u.stop();
                        ips.add(data.getData().toString());
                        ip = ips.get(0);
                        if (ips.size() > 1) {
                            return;
                        }
                        debug.println("my ip in LAN: " + ip);
                        broadcastListenerId = sw.listenByUdp(SocketData.packAddress(ip, broadcastPort), new BLAfter());
                        debug.println("re-listen on " + ip + ":" + broadcastPort);
                        debug.println("LAN Ip identify finish");
                    }
                }
            }
        }
        private class ListenOnAllIpv4ByUdp {
            private ISocketWrapper sw;
            private List<String> udpListeners;
            private int port;
            private ISocketWrapper.IAction action;
            public ListenOnAllIpv4ByUdp(int port, ISocketWrapper.IAction action, ISocketWrapper sw) {
                this.port = port;
                this.sw = sw;
                udpListeners = new ArrayList<>();
                this.action = action;
            }
            public void start() {
                List<String> ipv4s = ipTool.getAllIpv4();
                for (String ipv4 : ipv4s) {
                    debug.println("listen on "+ipv4+":"+port+" by udp");
                    udpListeners.add(sw.listenByUdp(SocketData.packAddress(ipv4, port), action));
                }
            }
            public void stop(){
                for(String l : udpListeners){
                    sw.killListener(l);
                }
            }
        }
        private class ListenOnAllIpv4ByTcp {
            private ISocketWrapper sw;
            private List<String> tcpListeners;
            private List<ISocketWrapper.IAddress> ipPortPairs;
            private ISocketWrapper.IAction action;
            public ListenOnAllIpv4ByTcp(ISocketWrapper.IAction action, ISocketWrapper sw) {
                this.sw = sw;
                tcpListeners = new ArrayList<>();
                ipPortPairs = new ArrayList<>();
                this.action = action;
            }
            public void start() {
                List<String> ipv4s = ipTool.getAllIpv4();
                int port = 14445;
                for (String ipv4 : ipv4s) {
                    port = Tools.getFreePort(ipv4, port);
                    debug.println("listen on "+ipv4+":"+port+" by tcp");
                    tcpListeners.add(sw.listenByTcp(SocketData.packAddress(ipv4, port), action));
                    ipPortPairs.add(SocketData.packAddress(ipv4, port));
                    ++port;
                }
            }
            public void stop(){
                for(String l : tcpListeners){
                    sw.killListener(l);
                }
            }
            public List<ISocketWrapper.IAddress> getIpPortPairs(){
                return ipPortPairs;
            }
        }
        private class SendToAllIpv4ByUdp {
            private int port;
            private ISocketWrapper sw;
            private boolean running;
            private Serializable data;
            public SendToAllIpv4ByUdp(int port, Serializable data, ISocketWrapper sw){
                this.port = port;
                this.sw = sw;
                this.data = data;
            }
            public void start(){
                List<String> ipv4s = ipTool.getAllIpv4();
                running = true;
                while(running) {
                    for (String ipv4 : ipv4s) {
                        String ba = Tools.getIpv4BroadcastAddress(ipv4, ipTool.getIpv4SubnetMask(ipv4));
                        debug.println("send to " + ba + ":" + port + " by udp " +
                                "using " + ipv4);

                        sw.sendByUdp(SocketData.packData("", 0,
                                ba, port, data));
                    }
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            public void stop(){
                running = false;
            }
        }

        private Debug debug;
        private ListenOnAllIpv4ByTcp t;
        private ListenOnAllIpv4ByUdp u;
        private SendToAllIpv4ByUdp s;
        private ISocketWrapper sw;
        private IIPTools ipTool;
        private int broadcastPort;
        private String ip;
        private String uuid;
        private String broadcastListenerId;
        public LANIpIdentify(ISocketWrapper sw, int broadcastPort, IIPTools ipTool) {
            this.debug = new Debug(getClass());
            debug.on();
            this.sw = sw;
            this.ipTool = ipTool;
            this.broadcastPort = broadcastPort;
            this.ip = UUID.randomUUID().toString() + new Date().toString();
            this.uuid = ip;
            this.broadcastListenerId = "unknown";
            debug.println("LAN Ip identify begin");
            t = new ListenOnAllIpv4ByTcp(new SL(), sw);
            t.start();
            u = new ListenOnAllIpv4ByUdp(broadcastPort, new BL(), sw);
            u.start();
            s = new SendToAllIpv4ByUdp(broadcastPort, new Data(ip, t.getIpPortPairs()), sw);
            s.start();
        }

        public void stop(){
            sw.killListener(broadcastListenerId);
        }

        /*
            阻塞到确认为止
         */
        public String getIp() {
            while(broadcastListenerId.equals("unknown")){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return ip;
        }
    }
}


