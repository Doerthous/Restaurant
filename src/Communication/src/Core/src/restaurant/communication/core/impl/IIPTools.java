package restaurant.communication.core.impl;

import java.util.List;

public interface IIPTools {
    List<String> getAllIpv4();
    String getIpv4SubnetMask(String ipv4);
}
