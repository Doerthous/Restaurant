package restaurant.ui.client;


import restaurant.service.core.IClientService;
import restaurant.ui.component.BaseFrame;

public class ClientFrame extends BaseFrame {
    public ClientFrame(IClientService service){
        this.service = service;
        initUICompoent();
    }

    /*
        业务层(service)接口
     */
    private IClientService service;
    public IClientService getService(){
        return service;
    }

    /*
        UI组件
     */
    private MainUI mainUI;
    private OrderUI orderUI;
    private ChatUI chatUI;
    private PayUI payUI;
    public void initUICompoent(){
        mainUI = new MainUI(this);
        orderUI = new OrderUI(this);
        chatUI = new ChatUI(this);
        payUI = new PayUI(this);
        add("Main", mainUI);
        add("Order", orderUI);
        add("Chat", chatUI);
        add("Pay", payUI);
    }
    public void main(){
        show("Main");
    }
    public void order(){
        show("Order");
    }
    public void callService(){

    }
    public void pay(){
        show("Pay");
    }
    public void chat(){
        show( "Chat");
    }


}
