package restaurant.ui.kitchen;


import restaurant.service.pc.ServiceFactory;

public class Main {
    /*
       程序入口
    */
    public static void main(String[] args) {
        new KitchenFrame(ServiceFactory.getKitchenService()).open();
    }
}
