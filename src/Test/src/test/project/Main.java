package test.project;

import restaurant.ui.client.ClientFrame;
import restaurant.service.impl.client.ClientService;

public class Main {
    public static void main(String[] args) {
        for(String arg : args) {
            System.out.println(arg);
        }
        if(args[0].equals("-t")){
            ClientFrame cf = new ClientFrame(new ClientService(args[1]));
            cf.open();
        } else if("c".equals(args[0])){

        } else {

        }
    }
}
