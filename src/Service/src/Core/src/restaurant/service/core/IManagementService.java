package restaurant.service.core;

public interface IManagementService {
    int DISH_FINISH = 0;
    int CUSTOMER_REQUEST = 1;

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
    void tableStart(String tableId);
    /*
        通知UI层
     */
    interface IObserver {
        void dishFinish(String dishName, String tableId);
        void requestService(String tableId);
    }
    void addObserver(IObserver observer);
}
