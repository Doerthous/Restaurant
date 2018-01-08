package restaurant.communication.core.impl;

import java.io.Serializable;
import java.net.ConnectException;

public interface ISocketWrapper {
    /*
        写成类，或者提供一个实现
     */
    interface IAddress extends Serializable {
        String getIp();
        Integer getPort();
    }
    interface IData extends Serializable {
        IAddress getSourceAddress();
        IAddress getTargetAddress();
        Serializable getData();
    }
    interface IAction{
        void receive(IData data);
    }

    void sendByTcp(IData data) throws ConnectException;
    void sendByUdp(IData data);
    String listenByTcp(IAddress address, IAction action);
    String listenByUdp(IAddress address, IAction action);
    void killListener(String listenId);


}
