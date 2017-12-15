package restaurant.communication.v2.lowlever;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketWrapper {
    public interface IData extends Serializable {
        String getSourceIp();
        void setSourceIp(String sourceIp);
        Integer getSourcePort();
        void setSourcePort(int sourcePort);
        String getTargetIp();
        void setTargetIp(String targetIp);
        Integer getTargetPort();
        void setTargetPort(int targetPort);
        Object getData();
        void setData(Object data);
    }
    public static class Data implements IData {
        public String sourceIp;
        public Integer sourcePort;
        public String targetIp;
        public Integer targetPort;
        public Object data;

        public Data(String sourceIp, Integer sourcePort, String targetIp, Integer targetPort, Object data) {
            this.sourceIp = sourceIp;
            this.sourcePort = sourcePort;
            this.targetIp = targetIp;
            this.targetPort = targetPort;
            this.data = data;
        }

        @Override
        public String getSourceIp() {
            return sourceIp;
        }
        @Override
        public void setSourceIp(String sourceIp) {
            this.sourceIp = sourceIp;
        }
        @Override
        public Integer getSourcePort() {
            return sourcePort;
        }
        @Override
        public void setSourcePort(int sourcePort) {
            this.sourcePort = sourcePort;
        }
        @Override
        public String getTargetIp() {
            return targetIp;
        }
        @Override
        public void setTargetIp(String targetIp) {
            this.targetIp = targetIp;
        }
        @Override
        public Integer getTargetPort() {
            return targetPort;
        }
        @Override
        public void setTargetPort(int targetPort) {
            this.targetPort = targetPort;
        }
        @Override
        public Object getData() {
            return data;
        }
        @Override
        public void setData(Object data) {
            this.data = data;
        }
    }
    public interface IAction{
        void receive(IData data);
    }

    private abstract class Listener {
        protected IAction action;
        protected boolean isRunning;

        public Listener(int port, IAction action){
            isRunning = false;
            this.action = action;
            initSocket(port);
        }
        protected abstract void initSocket(int port);
        public abstract void listen();
        public abstract void stop();
    }
    private class ListenByTcp extends Listener {
        private ServerSocket server;
        public ListenByTcp(int port, IAction action) {
            super(port, action);
        }
        @Override
        protected void initSocket(int port) {
            try {
                server = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void listen(){
            if(isRunning == false) {
                isRunning = true;
                new Thread(() -> {
                    while (isRunning) {
                        Socket client;
                        try {
                            client = server.accept();
                            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                            IData data = (IData) in.readObject();
                            in.close();
                            client.close();
                            data.setSourceIp(client.getInetAddress().toString().substring(1));
                            new Thread(()->action.receive(data)).start();
                        } catch (SocketException e){
                            if(!e.getMessage().equalsIgnoreCase("socket closed")){
                                e.printStackTrace();
                            }
                        } catch(EOFException e){

                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
        @Override
        public void stop(){
            if(isRunning == true) {
                isRunning = false;
                if (server != null) {
                    try {
                        server.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private class ListenByUdp extends Listener {
        private MulticastSocket server;
        public ListenByUdp(int port, IAction action) {
            super(port, action);
        }
        @Override
        protected void initSocket(int port){
            try {
                server = new MulticastSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void listen(){
            if(isRunning == false) {
                isRunning = true;
                new Thread(() -> {
                    while (isRunning) {
                        try {
                            byte[] buffer = new byte[65535];
                            DatagramPacket packet = new DatagramPacket(buffer , buffer.length);
                            while(true){
                                server.receive(packet);
                                ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
                                ObjectInputStream ois = new ObjectInputStream(bais);
                                IData data = (Data) ois.readObject();
                                data.setSourceIp(packet.getAddress().toString().substring(1));
                                new Thread(()->action.receive(data)).start();
                            }
                        } catch (IOException e) {
                            if(!e.getMessage().equals("socket closed")) {
                                e.printStackTrace();
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
        @Override
        public void stop(){
            if(isRunning == true) {
                isRunning = false;
                if (server != null) {
                    server.close();
                }
            }
        }
    }

    private Map<String, Listener> listners;
    public SocketWrapper(){
        listners = new HashMap<>();
    }
    public void sendByTcp(IData data){
        Socket other = null;
        ObjectOutputStream out = null;
        try {
            other = new Socket(data.getTargetIp(), data.getTargetPort());
            out = new ObjectOutputStream(other.getOutputStream());
            out.writeObject(data);
            out.writeObject(null);
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
    public void sendByUdp(IData data){
        MulticastSocket other = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(data);
            oos.writeObject(null);
            byte[] buf = baos.toByteArray();
            other = new MulticastSocket();
            other.setBroadcast(true); //有没有没啥不同
            DatagramPacket packet = new DatagramPacket(buf, buf.length,
                    InetAddress.getByName(data.getTargetIp()), data.getTargetPort());
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
    public String listenByTcp(int port, IAction action){
        String listenId = UUID.randomUUID().toString();
        Listener listener = new ListenByTcp(port, action);
        listners.putIfAbsent(listenId, listener);
        listener.listen();
        return listenId;
    }
    public String listenByUdp(int port, IAction action){
        String listenId = UUID.randomUUID().toString();
        Listener listener = new ListenByUdp(port, action);
        listners.putIfAbsent(listenId, listener);
        listener.listen();
        return listenId;
    }
    public void stopListener(String listenerId){
        Listener listener = listners.getOrDefault(listenerId, null);
        if(listener != null){
            listener.stop();
        }
    }
    public IData packData(String sourceIp, Integer sourcePort,
                          String targetIp, Integer targetPort, Object data){
        return new Data(sourceIp, sourcePort, targetIp, targetPort, data);
    }
}
