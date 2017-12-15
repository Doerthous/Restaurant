package restaurant.communication.v1.tools;

import restaurant.communication.v1.IData;
import restaurant.communication.v1.impl.Data;

import java.io.*;
import java.net.*;

import static java.lang.System.exit;

public class SocketWrapper {
    public static void sendByTcp(Object object, String ip, int port){
        Socket other = null;
        ObjectOutputStream out = null;
        try {
            other = new Socket(ip, port);
            out = new ObjectOutputStream(other.getOutputStream());
            out.writeObject(object);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(out != null) {
                    out.close();
                } if(other != null) {
                    other.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void sendByUdp(Object object, String ip, int port) {
        DatagramSocket other = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] buf = baos.toByteArray();
            other = new DatagramSocket();
            other.setBroadcast(true); //有没有没啥不同
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), port);
            other.send(packet);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(baos != null) {
                    baos.close();
                }
                if(oos != null){
                    oos.close();
                }
                if(other != null) {
                    other.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface Action{
        void receive(Object data);
        boolean isRunning();
    }
    public static void listenByTcp(int port, Action action){
        new Thread(()->{
            ServerSocket me = null;
            try {
                me = new ServerSocket(port);
                while(action.isRunning()) {
                    Socket other;
                    try {
                        other = me.accept();
                        ObjectInputStream in = new ObjectInputStream(other.getInputStream());
                        Object data = in.readObject();
                        in.close();
                        other.close();
                        action.receive(data);
                    } catch (Exception e) {
                        System.out.println("Server accept error: " + e);
                    }
                }
            } catch (Exception e) {
                System.out.println("Chat server init failed：" + e);
                exit(0);
            } finally {
                if(me != null){
                    try {
                        me.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public static void listenByUdp(int port, Action action){
        new Thread(() -> { // response
            DatagramSocket me = null;
            try {
                byte[] buffer = new byte[65535];
                me = new DatagramSocket(port);
                DatagramPacket packet = new DatagramPacket(buffer , buffer.length);
                while(action.isRunning()){
                    me.receive(packet);
                    ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    IData data = (IData) ois.readObject();
                    ois.close();
                    action.receive(new Data(packet.getAddress().toString().substring(1),
                            data.getToId(), data.getCommand(), data.getDate(), data.getData()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(me != null){
                    me.close();
                }
            }
        }).start(); // 监听udp广播
    }

}
