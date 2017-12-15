package restaurant.communication.v2.test;

import restaurant.communication.v2.lowlever.Port;
import restaurant.communication.v2.lowlever.SocketWrapper;

import java.util.UUID;

import static java.lang.Thread.sleep;

public class IPIdentify implements SocketWrapper.IAction, Runnable {
    private static final String SELF_IDENTIFY_ACK = "Self Identify Ack";
    private static final String BROADCAST_ADDR = "255.255.255.255";
    private static final int BROADCAST_PORT = 14444;
    private String ip;
    private int port;
    private SocketWrapper socketWrapper;
    String tcpListenerId;
    String udpListenerId;

    public IPIdentify(){

    }

    @Override
    public void receive(SocketWrapper.IData data) {
        String msg = (String) data.getData();
        if(msg != null) {
            if (!msg.equals(ip) && !msg.equals(SELF_IDENTIFY_ACK)) {
                String sourceIp = data.getSourceIp();
                int sourcePort = data.getSourcePort();
                data = socketWrapper.packData(ip, port, sourceIp, sourcePort, SELF_IDENTIFY_ACK);
                socketWrapper.sendByTcp(data);
            } else if (msg.equals(SELF_IDENTIFY_ACK)) {
                ip = data.getTargetIp();
                System.out.println("Identified: " + ip + ", " + port);
                socketWrapper.stopListener(tcpListenerId);
            }
        }
    }

    @Override
    public void run(){
        this.ip = UUID.randomUUID().toString();
        this.port = Port.getFreePort(14445);
        this.socketWrapper = new SocketWrapper();
        tcpListenerId = socketWrapper.listenByTcp(port, this);
        udpListenerId = socketWrapper.listenByUdp(BROADCAST_PORT, this);
        String oIp = ip;
        while(ip.equals(oIp)){
            SocketWrapper.IData data = socketWrapper.packData(ip, port, BROADCAST_ADDR, BROADCAST_PORT,
                    ip);
            socketWrapper.sendByUdp(data);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getIp(){
        return ip;
    }

    public void stop(){
        socketWrapper.stopListener(udpListenerId);
    }
}
