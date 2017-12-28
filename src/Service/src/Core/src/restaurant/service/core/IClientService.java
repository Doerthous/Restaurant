package restaurant.service.core;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/*
    Client模块，业务层（service）向视图层（UI）暴露的接口
 */
public interface IClientService {
    /*
        一条聊天记录
     */
    interface IChatData extends Serializable {
        /*
            发送时间
         */
        String getTime();
        /*
            发送方Id
         */
        String getTableId();
        /*
            聊天内容
         */
        String getMessage();
    }
    /*
        聊天监听器
        当有关于聊天的动态的事件发生时，会调用已注册在IClientService中的监听器的相应函数
        注册方法：addChatObserver
     */
    interface IChatObserver {
        /*
            新消息事件，表明餐桌桌号为tableId的餐桌客户端传来了一条新消息
         */
        void newMessage(String tableId);
        /*
            下线事件，表明餐桌桌号为tableId的餐桌客户端下线
         */
        void offline(String tableId);
        /*
            上线事件，表明餐桌桌号为tableId的餐桌客户端上线
         */
        void online(String tableId);
    }
    /*
        菜品信息
     */
    interface IDishInfo {
        /*
            菜品名称
         */
        String getDishName();
        /*
            菜品价格
         */
        Float getPrice();
        /*
            菜品图片
         */
        byte[] getPicture();
        /*
            菜品类型
         */
        String getType();
    }
    /*

     */
    interface ITableObserver {
        void openTable();
        void closeTable();
    }

    /*
        获取餐桌号
     */
    String getTableId();
    /*
        登陆
     */
    Boolean login(String tableId);
    String getLoginFailedReason();
    /*
        获取菜单
     */
    List<IDishInfo> getDishMenu();
    /*
        获取菜品类型（菜品分类）
     */
    List<String> getDishType();
    /*
        获取当前在线的餐桌桌号
     */
    List<String> getOnlineTableIds();
    /*
        给餐桌桌号为tableId的餐桌客户端发消息
     */
    void sendMessage(String tableId, String message);
    /*
        获取与餐桌桌号为tableId的餐桌客户端的聊天记录
     */
    List<IChatData> getSessionWith(String tableId);
    /*
        发送订单
     */
    void sendOrder(Map<String, Integer> order, Float totalCost);
    /*
        聊天事件监听注册/移除函数
     */
    void addChatObserver(IChatObserver observer);
    void removeChatObserver(IChatObserver observer);
    /*
        清空当前餐桌的所有聊天记录
     */
    void endAllSesion();
    /*
        呼叫服务
     */
    void requestService();
    /*

     */
    void addTableObserver(ITableObserver observer);
    void removeTableObserver(ITableObserver observer);
}
