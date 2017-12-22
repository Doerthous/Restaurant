package doerthous.network;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static java.lang.Thread.sleep;

public class IP {
    public static void main(String[] args) throws IOException {
        printAllIpv4();
        bindAllIpv4WithPort(44444);

        new Socket("192.168.155.33", 000);
        InetSocketAddress i = new InetSocketAddress("192.168.155.33", 10000);
        InetAddress ie = InetAddress.getByName("192.168.155.33");


    }

    public static void printAllIp() throws UnknownHostException, SocketException {
        System.out.println("Host:\t" + InetAddress.getLocalHost() + "\n");
        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        Enumeration<InetAddress> addresses;
        while (en.hasMoreElements()) {
            NetworkInterface networkinterface = en.nextElement();
            System.out.println(networkinterface.getName());
            addresses = networkinterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                System.out.println("\t"
                        + addresses.nextElement().getHostAddress() + "");
            }
        }
    }

    public static void printAllIp2(){
        for(String ip : getAllLocalHostIP()){
            System.out.println(ip);
        }
    }
    public static String getLocalHostName() {
        String hostName;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostName = addr.getHostName();
        } catch (Exception ex) {
            hostName = "";
        }
        return hostName;
    }
    public static String[] getAllLocalHostIP() {
        String[] ret = null;
        try {
            String hostName = getLocalHostName();
            if (hostName.length() > 0) {
                InetAddress[] addrs = InetAddress.getAllByName(hostName);
                if (addrs.length > 0) {
                    ret = new String[addrs.length];
                    for (int i = 0; i < addrs.length; i++) {
                        ret[i] = addrs[i].getHostAddress();
                    }
                }
            }

        } catch (Exception ex) {
            ret = null;
        }
        return ret;
    }

    public static void printAllIpv4(){
        List<String> ipv4s = getAllIpv4();
        for(String ipv4 : ipv4s){
            System.out.println(ipv4);
        }
    }
    public static List<String> getAllIpv4(){
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
    public static void bindAllIpv4WithPort(int port) {
        try {
            List<String> ipv4s = getAllIpv4();
            for (String ipv4 : ipv4s) {
                ServerSocket ss = new ServerSocket();
                ss.bind(new InetSocketAddress(ipv4, port));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean isHostConnectable(String host, int port) {
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
}

