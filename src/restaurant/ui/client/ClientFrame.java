package restaurant.ui.client;

import programmer.test.ui.component.SimpleFrame;
import restaurant.service.IClientService;
import restaurant.service.ServiceFactory;
import restaurant.service.vo.Dish;

import java.awt.*;

public class ClientFrame extends SimpleFrame {
    public ClientFrame(){
        initUICompoent();
        initServiceCompoent();
    }

    /*
        业务层(service)接口
     */
    private IClientService service;
    public void initServiceCompoent(){
        service = ServiceFactory.getClientService();
    }
    public java.util.List<Dish> getDishMenu(){
        return service.getDishMenu();
    }

    /*
        UI组件
     */
    private CardLayout card;
    private MainUI mainUI;
    private OrderUI orderUI;
    private ChatUI chatUI;
    private PayUI payUI;
    public void initUICompoent(){
        card = new CardLayout();
        mainUI = new MainUI(this);
        orderUI = new OrderUI(this);
        chatUI = new ChatUI(this);
        payUI = new PayUI(this);
        getContentPane().setLayout(card);
        add("Main", mainUI);
        add("Order", orderUI);
        add("Chat", chatUI);
        add("Pay", payUI);
    }
    public void main(){
        card.show(getContentPane(), "Main");
    }
    public void order(){
        card.show(getContentPane(), "Order");
    }
    public void callService(){

    }
    public void pay(){
        card.show(getContentPane(),"Pay");
    }
    public void chat(){
        card.show(getContentPane(), "Chat");
    }

    /*
        程序入口
     */
    public static void main(String[] args) {
        new ClientFrame().setVisible(true);
    }
}
