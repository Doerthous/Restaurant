package restaurant.ui.client;


import restaurant.service.core.IClientService;
import restaurant.service.pc.ServiceFactory;
import restaurant.ui.component.BaseFrame;

import javax.swing.*;

public class ClientFrame extends BaseFrame implements IClientService.ITableObserver {
    public ClientFrame(IClientService service){
        this.service = service;
        service.addTableObserver(this);
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
        LoginFrame lf = new LoginFrame(e -> {
            if(e.getSource() instanceof LoginFrame) {
                LoginFrame lp = (LoginFrame)e.getSource();
                System.out.println(lp.getText());
                if(service.login(lp.getText())){
                    lp.dispose();
                } else {
                    lp.setText("桌号不存在");
                }
            }
        });
        lf.setVisible(true);
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
    public void requestService(){
        service.requestService();
    }
    public void pay(){
        show("Pay");
    }
    public void chat(){
        show( "Chat");
    }



    /*
        程序入口
     */
    public static void main(String[] args) {
        new ClientFrame(ServiceFactory.getClientService()).open();
    }

    @Override
    public void openTable() {
        mainUI.start();
        orderUI.start();
        payUI.start();
        chatUI.start();
    }

    @Override
    public void closeTable() {
        mainUI.stop();
        orderUI.stop();
        payUI.stop();
        chatUI.stop();
        main();
    }
}
