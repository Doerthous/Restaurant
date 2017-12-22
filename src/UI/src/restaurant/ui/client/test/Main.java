package restaurant.ui.client.test;


import restaurant.service.core.IClientService;
import restaurant.service.pc.ServiceFactory;
import restaurant.ui.client.ClientFrame;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            IClientService cs1 = ServiceFactory.getClientService();
            ClientFrame cf1 = new ClientFrame(cs1);
            cf1.open();
        }).start();
        sleep(1000);
        new Thread(()->{
            IClientService cs1 = ServiceFactory.getClientService();
            ClientFrame cf1 = new ClientFrame(cs1);
            cf1.open();
        }).start();
    }
}
