package restaurant.service;

import restaurant.service.vo.Dish;

import java.util.List;

public interface IClientService {
    List<Dish> getDishMenu(); // 获取菜单
    void callService(); // 呼叫服务
    List<String> getOnlineTableIds(); // 获取在线餐桌的Id
    void sendMessage(String id, String message);
    String readMessage(String id);
    void endAllSesion();
}
