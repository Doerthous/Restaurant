package restaurant.communication.v1.tools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Port {
    public static boolean isLoclePortUsing(int port){
        boolean flag = true;
        try {
            flag = isPortUsing("localhost", port);
        } catch (Exception e) {
        }
        return flag;
    }
    public static boolean isPortUsing(String host,int port) throws UnknownHostException {
        boolean flag = false;
        InetAddress theAddress = InetAddress.getByName(host);
        try {
            Socket socket = new Socket(theAddress,port);
            socket.close();
            flag = true;
        } catch (IOException e) {

        }
        return flag;
    }
    public static int getFreePort(int begin){
        while(begin < 65535){
            if(!isLoclePortUsing(begin)){
                return begin;
            }
            ++begin;
        }
        return -1;
    }
}
