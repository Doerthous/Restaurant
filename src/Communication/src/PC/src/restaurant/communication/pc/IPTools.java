package restaurant.communication.pc;



import restaurant.communication.core.impl.IIPTools;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IPTools implements IIPTools {
    public List<String> getAllIpv4(){
        List<String> ipv4s = new ArrayList<>();
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
                    ipv4s.add(ip.getHostAddress());
                }
            }
        }
        return ipv4s;
    }
    public String getIpv4SubnetMask(String ipv4) {
        String mask = null;
        try {
            InetAddress ip = InetAddress.getByName(ipv4);
            NetworkInterface ni = NetworkInterface.getByInetAddress(ip);
            List<InterfaceAddress> list = ni.getInterfaceAddresses();
            if (list.size() > 0) {
                int maskLen = list.get(0).getNetworkPrefixLength(); //子网掩码的二进制1的个数
                StringBuilder maskStr = new StringBuilder();
                int[] maskIp = new int[4];
                for (int i=0; i<maskIp.length; i++) {
                    maskIp[i] = (maskLen >= 8) ? 255 : (maskLen > 0 ? (maskLen & 0xff) : 0);
                    maskLen -= 8;
                    maskStr.append(maskIp[i]);
                    if (i < maskIp.length-1) {maskStr.append(".");}
                }
                mask = maskStr.toString();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } finally {
            return mask;
        }
    }
}
