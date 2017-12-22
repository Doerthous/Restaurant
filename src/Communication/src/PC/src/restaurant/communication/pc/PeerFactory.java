package restaurant.communication.pc;

import restaurant.communication.core.IPeer;
import restaurant.communication.core.impl.ISocketWrapper;
import restaurant.communication.core.impl.Peer;

public class PeerFactory {
    public static final int WIN10_JDK8 = 0;
    public static final int ANDROIND6_JDK8 = 1;
    public static IPeer getPeer(String id, int type){
        switch (type){
            case WIN10_JDK8:{
                ISocketWrapper sw = new SocketWrapper();
                return new Peer(id, sw, new IPTools());
            }
        }
        return null;
    }
}
