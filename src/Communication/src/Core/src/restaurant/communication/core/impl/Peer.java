package restaurant.communication.core.impl;

import restaurant.communication.core.ICommandObserver;
import restaurant.communication.core.IData;
import restaurant.communication.core.IPeer;
import restaurant.communication.core.utils.Tools;
import restaurant.communication.core.utils.Debug;
import restaurant.communication.core.utils.MapOperation;

import java.io.Serializable;
import java.util.*;

import static java.lang.Thread.sleep;

public class Peer implements IPeer, ISocketWrapper.IAction {
    //private static final String WHO_ARE_HERE = "PEER_CMD_WAH";
    //private static final String I_AM_HERE = "PEER_CMD_IAH";
    private static final String WHERE_IS_ID = "PEER_CMD_WII";
    private static final String WHERE_IS_ID_ACK = "PEER_CMD_WIIA";
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

    public Peer(String id, ISocketWrapper socketWrapper, IIPTools ipTools){
        this.socketWrapper = socketWrapper;
        this.ipTools = ipTools;
        this.id = id;
        this.ip = UUID.randomUUID().toString();
        id2ip = new HashMap<>();
        id2port = new HashMap<>();
        observers = new HashMap<>();
        debug = new Debug(getClass());
        debug.on();
    }


    @Override
    public void start() {
        // indentify ip
        lanIpIdentify = new LANIpIdentify(socketWrapper, BROADCAST_PORT, ipTools);
        while(lanIpIdentify.getIp() == null){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ip = lanIpIdentify.getIp();
        BROADCAST_ADDR = Tools.getIpv4BroadcastAddress(ip, ipTools.getIpv4SubnetMask(ip));
        
        // setup socket
        port = Tools.getFreePort(ip,BROADCAST_PORT+1000);
        udpListenerId = socketWrapper.listenByUdp(
                new SocketData.Address(ip, BROADCAST_PORT), this);
        tcpListenerId = socketWrapper.listenByTcp(
                new SocketData.Address(ip, port), this);
        debug("tcp: ["+ip+":"+port+"]");
        debug("udp: ["+ip+":"+BROADCAST_PORT+"]");
    }
    @Override
    public void stop() {
        socketWrapper.killListener(tcpListenerId);
        socketWrapper.killListener(udpListenerId);
        lanIpIdentify.stop();
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
        whereIsId(id);
        IData idata = new restaurant.communication.core.impl.Data(this.id, id, command, data);
        if(id.equals(BROADCAST_ID)){
            ISocketWrapper.IData socketData = packData(ip, port,
                    BROADCAST_ADDR, BROADCAST_PORT, idata);
            debug.println(new AddressDebug(this.id, ip, port).toString() + ": send to " +
                    new AddressDebug(id, BROADCAST_ADDR, BROADCAST_PORT) +" by udp");
            socketWrapper.sendByUdp(socketData);
        } else {
            ISocketWrapper.IData socketData = packData(ip, port,
                    id2ip.get(id), id2port.get(id), idata);
            debug.println(new AddressDebug(this.id, ip, port).toString() + ": send to " +
                    new AddressDebug(id, id2ip.get(id), id2port.get(id)) +" by tcp");
            socketWrapper.sendByTcp(socketData);
        }
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
            switch (idata.getCommand()) {
                /*case WHO_ARE_HERE: {
                    if (!idata.getFromId().equals(id)) {
                        //id2ip.putIfAbsent(idata.getFromId(), data.getSourceIp());
                        //id2port.putIfAbsent(idata.getFromId(), data.getSourcePort());
                        MapOperation.putIfAbsent(id2ip, idata.getFromId(), data.getSourceAddress().getIp());
                        MapOperation.putIfAbsent(id2port, idata.getFromId(), data.getSourceAddress().getPort());
                        sendCommand(idata.getFromId(), I_AM_HERE, id);
                        debug(new AddressDebug(idata.getFromId(),
                                data.getSourceAddress().getIp(),
                                data.getSourceAddress().getPort()).toString() + " ask who are here");
                    }
                }
                break;
                case I_AM_HERE: {
                    if (!idata.getFromId().equals(id)) {
                        //id2ip.putIfAbsent(idata.getFromId(), data.getSourceIp());
                        //id2port.putIfAbsent(idata.getFromId(), data.getSourcePort());
                        MapOperation.putIfAbsent(id2ip, idata.getFromId(), data.getSourceAddress().getIp());
                        MapOperation.putIfAbsent(id2port, idata.getFromId(), data.getSourceAddress().getPort());
                        debug(new AddressDebug(idata.getFromId(),
                                data.getSourceAddress().getIp(),
                                data.getSourceAddress().getPort()).toString() + " said he/she is here");
                    }
                }
                break;*/
                case WHERE_IS_ID: {
                    if(idata.getData() instanceof AskIdData) {
                        AskIdData aid = (AskIdData) idata.getData();
                        if (id.equals(aid.targetId)) {
                            //id2ip.putIfAbsent(idata.getFromId(), data.getSourceIp());
                            //id2port.putIfAbsent(idata.getFromId(), data.getSourcePort());
                            MapOperation.putIfAbsent(id2ip, idata.getFromId(), data.getSourceAddress().getIp());
                            MapOperation.putIfAbsent(id2port, idata.getFromId(), aid.sourceTcpPort);
                            sendCommand(idata.getFromId(), WHERE_IS_ID_ACK, new AskIdData(idata.getFromId(), port));
                            debug(new AddressDebug(idata.getFromId(),
                                    data.getSourceAddress().getIp(),
                                    aid.sourceTcpPort).toString() + " ask for my address");
                        }
                    }
                }
                break;
                case WHERE_IS_ID_ACK: {
                    if(idata.getData() instanceof AskIdData) {
                        AskIdData aid = (AskIdData) idata.getData();
                        if(id.equals(aid.targetId)) {
                            //id2ip.putIfAbsent(idata.getFromId(), data.getSourceIp());
                            //id2port.putIfAbsent(idata.getFromId(), data.getSourcePort());
                            MapOperation.putIfAbsent(id2ip, idata.getFromId(), data.getSourceAddress().getIp());
                            MapOperation.putIfAbsent(id2port, idata.getFromId(), aid.sourceTcpPort);
                            debug(new AddressDebug(idata.getFromId(),
                                    data.getSourceAddress().getIp(),
                                    aid.sourceTcpPort).toString() + " is there");
                        }
                    }
                }
                break;
            }
            //List<ICommandObserver> l = observers.getOrDefault(idata.getCommand(), new ArrayList<>());
            List<ICommandObserver> l = (List<ICommandObserver>)
                    MapOperation.getOrDefault(observers, idata.getCommand(), new ArrayList<>());
            for (ICommandObserver observer : l) {
                observer.update(idata);
            }
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
    public static class AskIdData implements Serializable {
        public String targetId;
        public Integer sourceTcpPort;

        public AskIdData(String targetId, Integer sourceTcpPort) {
            this.targetId = targetId;
            this.sourceTcpPort = sourceTcpPort;
        }
    }
    private void whereIsId(String id){
        while(!id.equals(BROADCAST_ID) && !id2ip.keySet().contains(id)){
            sendCommand(BROADCAST_ID, WHERE_IS_ID, new AskIdData(id, port));
            debug("where is " + id + "[" + BROADCAST_ID + "," + BROADCAST_ADDR+"]");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Data implements Serializable {
        public String uuid;
        public List<SocketData.Address> ipps;

        public Data(String uuid, List<SocketData.Address> ipps) {
            this.uuid = uuid;
            this.ipps = ipps;
        }
    }
    public static class SocketData implements ISocketWrapper.IData {
        public static class Address implements ISocketWrapper.IAddress {
            private String i;
            private Integer p;

            public Address(String i, Integer p) {
                this.i = i;
                this.p = p;
            }

            @Override
            public String getIp() {
                return i;
            }

            @Override
            public Integer getPort() {
                return p;
            }
        }

        private String si;
        private Integer sp;
        private String ti;
        private Integer tp;
        private Serializable d;

        public SocketData(String si, Integer sp, String ti, Integer tp, Serializable d) {
            this.si = si;
            this.sp = sp;
            this.ti = ti;
            this.tp = tp;
            this.d = d;
        }

        @Override
        public ISocketWrapper.IAddress getSourceAddress() {
            return new Address(si, sp);
        }

        @Override
        public ISocketWrapper.IAddress getTargetAddress() {
            return new Address(ti, tp);
        }

        @Override
        public Serializable getData() {
            return d;
        }
    }
    public static ISocketWrapper.IData packData(String sourceIp, Integer sourcePort,
                                                String targetIp, Integer targetPort, Serializable data) {
        return new SocketData(sourceIp, sourcePort, targetIp, targetPort, data);
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
                                for (SocketData.Address ipp : d.ipps) {
                                    if (ipp.getIp().equals(data.getSourceAddress().getIp())) {
                                        sw.sendByTcp(new SocketData("", 0, ipp.getIp(), ipp.getPort(), ipp.getIp()));
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
                    for (SocketData.Address ipp : d.ipps) {
                        if (ipp.getIp().equals(data.getSourceAddress().getIp())) {
                            sw.sendByTcp(new SocketData("", 0, ipp.getIp(), ipp.getPort(), ipp.getIp()));
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
                        broadcastListenerId = sw.listenByUdp(new SocketData.Address(ip, broadcastPort), new BLAfter());
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
                    udpListeners.add(sw.listenByUdp(new SocketData.Address(ipv4, port), action));
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
            private List<SocketData.Address> ipPortPairs;
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
                    tcpListeners.add(sw.listenByTcp(new SocketData.Address(ipv4, port), action));
                    ipPortPairs.add(new SocketData.Address(ipv4, port));
                    ++port;
                }
            }
            public void stop(){
                for(String l : tcpListeners){
                    sw.killListener(l);
                }
            }
            public List<SocketData.Address> getIpPortPairs(){
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

                        sw.sendByUdp(new SocketData("", 0, ba, port, data));
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
            没确认ip则返回null
         */
        public String getIp() {
            if(broadcastListenerId.equals("unknown")){
                return null;
            }
            return ip;
        }
    }
}


