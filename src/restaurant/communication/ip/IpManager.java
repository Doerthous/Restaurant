package restaurant.communication.ip;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpManager {
    private static List<String> localIps = new ArrayList<>();
    public static void init(){
        Enumeration allNetInterfaces = null;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (java.net.SocketException e) {
            e.printStackTrace();
        }
        InetAddress ip;
        while (allNetInterfaces.hasMoreElements()){
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()){
                ip = (InetAddress) addresses.nextElement();
                if (ip != null && ip instanceof Inet4Address){
                    localIps.add(ip.getHostAddress());
                }
            }
        }
    }
    public static boolean isLocalIp(String ip){
        return localIps.contains(ip);
    }
}