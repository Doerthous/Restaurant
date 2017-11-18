package restaurant.communication;

import restaurant.communication.IPeer;
import restaurant.communication.impl.Peer;
import restaurant.communication.impl.AutoPeer;

public class PeerFactory {
    public static final String T1 = "1";
    public static final String T2 = "2";
    public static IPeer newPeer(String type){
        switch (type){
            case "1":{
                return new Peer();
            }
            case "2":{
                return new AutoPeer();
            }
        }
        throw new UnsupportedOperationException();
    }
}
