package restaurant.communication.tools;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class SocketSender {
    public static void sendByTcp(Object object, String ip, int port) throws Exception{
        Socket other;
        ObjectOutputStream out;
        other = new Socket(ip, port);
        out = new ObjectOutputStream(other.getOutputStream());
        out.writeObject(object);
        out.close();
        other.close();
    }
    public static void sendByUdp(Object object, String ip, int port) throws Exception{
            DatagramSocket other;
            DatagramPacket packet;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] buf = baos.toByteArray();
            other = new DatagramSocket();
            other.setBroadcast(true); //有没有没啥不同
            packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), port);
            other.send(packet);
            baos.close();
            oos.close();
            other.close();
    }
}
