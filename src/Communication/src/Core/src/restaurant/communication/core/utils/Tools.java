package restaurant.communication.core.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Tools {
    /*
        获取广播地址
     */
    public static String getIpv4BroadcastAddress(String ipv4, String subnet) {
        String[] ips = ipv4.split("\\.");
        String[] subnets = subnet.split("\\.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ips.length; i++) {
            ips[i] = String.valueOf((~Integer.parseInt(subnets[i]))
                    | (Integer.parseInt(ips[i])));
            sb.append(turnToStr(Integer.parseInt(ips[i])));
            if (i != (ips.length - 1))
                sb.append(".");
        }
        return turnToIp(sb.toString());
    }
    private static String turnToStr(int num) {
        String str = "";
        str = Integer.toBinaryString(num);
        int len = 8 - str.length();
        for (int i = 0; i < len; i++) {
            str = "0" + str;
        }
        if (len < 0)
            str = str.substring(24, 32);
        return str;
    }
    private static String turnToIp(String str) {
        String[] ips = str.split("\\.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ips.length; i++) {
            sb.append(turnToInt(ips[i]));
            sb.append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    private static int turnToInt(String str) {
        int total = 0;
        int top = str.length();
        for (int i = 0; i < str.length(); i++) {
            String h = String.valueOf(str.charAt(i));
            top--;
            total += ((int) Math.pow(2, top)) * (Integer.parseInt(h));
        }
        return total;
    }

    /*
        端口
     */
    public static boolean isPortUsing(String host,int port) {
        boolean flag = false;
        try {
            InetAddress theAddress = InetAddress.getByName(host);
            Socket socket = new Socket(theAddress,port);
            socket.close();
            flag = true;
        } catch (IOException e) {

        }
        return flag;
    }
    public static int getFreePort(String ip, int begin){
        while(begin < 65535){
            if(!isPortUsing(ip, begin)){
                return begin;
            }
            ++begin;
        }
        return -1;
    }
}
