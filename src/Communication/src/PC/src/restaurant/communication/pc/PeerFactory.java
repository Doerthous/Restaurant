package restaurant.communication.pc;

import restaurant.communication.core.IPeer;
import restaurant.communication.core.impl.ISocketWrapper;
import restaurant.communication.core.impl.Peer;

public class PeerFactory {
    public static IPeer getPeer(){
        return new Peer(new SocketWrapper(), new IPTools());
    }
}
