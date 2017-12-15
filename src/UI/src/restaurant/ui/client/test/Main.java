package restaurant.ui.client.test;

import restaurant.service.IClientService;
import restaurant.service.ServiceFactory;
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
