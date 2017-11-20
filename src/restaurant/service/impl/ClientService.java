package restaurant.service.impl;

import restaurant.communication.ICommandObserver;
import restaurant.communication.IData;
import restaurant.service.IClientService;
import restaurant.service.IDishService;
import restaurant.service.vo.Dish;

import java.util.List;
import java.util.Map;

public class ClientService implements IClientService, ICommandObserver {
    @Override
    public void update(IData data) {

    }


    @Override
    public List<Dish> getDishMenu() {
        return null;
    }

    @Override
    public void callService() {

    }

    @Override
    public List<String> getOnlineTableIds() {
        return null;
    }

    @Override
    public void sendMessage(String id, String message) {

    }

    @Override
    public String readMessage(String id) {
        return null;
    }

    @Override
    public void endAllSesion() {

    }

}
