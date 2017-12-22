package restaurant.service.core;

public interface IManagementService {
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
    interface IDishFinishObserver {
        void dishFinish(String dishName, String tableId);
    }
    void addDishFinishObserver(IDishFinishObserver observer);
}
