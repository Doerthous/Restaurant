package restaurant.service.core;

import com.sun.org.apache.xpath.internal.operations.Bool;
import restaurant.service.core.vo.Dish;

import java.util.List;
import java.util.Map;

public interface IManagementService {
/*    int DISH_FINISH = 0;
    int CUSTOMER_REQUEST = 1;*/

    /*
        通知服务员传菜
     */
    void dishFinish(String dishName, String tableId, String waiterId);
    /*
        通知服务员接待
     */
    void customerCall(String tableId, String waiterId);
    /*
        开台
     */
    void openTable(String tableId, Integer customerCount);
    /*
        关台
     */
    void closeTable(String tableId);
    /*
        新增菜品
     */
    boolean createDish(String name, Float price, String type, Boolean isSaled, String pictureUrl);
    /*
        删除菜品
     */
    boolean deleteDish(String name);
    /*
        获取所有菜品
     */
    List<Dish> getAllDish();
    /*
        根据类型获取菜品
     */
    List<Dish> getDishByType(String type);
    /*
        获取菜品类型
     */
    List<String> getDishTypes();
    /*
        餐桌管理
     */
    interface ITableInfo {
        enum State {BUSY, FREE};
        String getTableId();
        State getTableState();
        // 以下方法只有在餐桌使用时才有效
        Integer getCustomerCount();
        Float getTotalCost();
        String getOrderId(); //
        Map<String, Integer> getOrder();
    }
    ITableInfo getTableInfo(String tableId);
    /*
        通知UI层
     */
    interface ITableObserver {
        void online(String tableId);
        void dishFinish(String dishName, String tableId);
        void requestService(String tableId);
        void newOrder(String tableId);
    }
    void addTableObserver(ITableObserver observer);
    void removeTableObserver(ITableObserver observer);
}
