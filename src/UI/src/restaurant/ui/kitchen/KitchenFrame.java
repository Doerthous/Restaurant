package restaurant.ui.kitchen;


import restaurant.service.core.IKitchenService;
import restaurant.ui.component.BaseFrame;

public class KitchenFrame extends BaseFrame {
    public KitchenFrame(IKitchenService service){
        this.service = service;
        initUICompoent();
    }

    /*
        业务层(service)接口
     */
    private IKitchenService service;
    public IKitchenService getService(){
        return service;
    }

    /*
        UI组件
     */
    private MainUI mainUI;
    public void initUICompoent(){
        mainUI = new MainUI(this);
        add("Main", mainUI);
    }

}
