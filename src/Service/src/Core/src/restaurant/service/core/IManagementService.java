package restaurant.service.core;

import com.sun.org.apache.xpath.internal.operations.Bool;
import restaurant.service.core.vo.Dish;
import restaurant.service.core.vo.Employee;
import restaurant.service.core.vo.Table;

import java.util.List;
import java.util.Map;

public interface IManagementService {
    /*
        新增菜品
     */
    Boolean createDish(String name, Float price, String type, Boolean isSaled, String pictureUrl);
    Boolean modifiDish(String name, Float price, String type, Boolean isSaled, String pictureUrl);
    Boolean deleteDish(String name);
    List<Dish> getAllDish();
    List<Dish> getDishByType(String type);
    List<Dish> getDishByTypeSortByPrice(String type, Boolean asc);
    List<Dish> getDishByTypeSortBySaledCount(String type, Boolean asc);
    List<String> getDishTypes();
    Dish getDishByName(String name);
    /*
        职工
     */
    String createEmployee(String name, String position, Integer salary, String sex, String password,
                           String phone, String nativePlace, String photoUrl);
    Boolean modifiEmployee(String id, String name, String position, Integer salary, String sex,
                           String password, String phone, String nativePlace, String photoUrl);
    Boolean deleteEmployee(String id);
    List<Employee> getAllEmployee();
    List<Employee> getEmployeeByPosition(String position);
    List<Employee> getEmployeeByPositionAndSex(String position, String sex);
    List<String> getAllEmployeePositions();
    Employee getEmployeeByCode(String code);
    List<Employee> getOnlineWaiter();


    // table 相关
    String createTable(String type, Integer floor, Integer capacity);
    Boolean modifiTable(String id, String type, Integer floor, Integer capacity);
    Boolean deleteTable(String id);
    List<Table> getAllTable();
    Table getTableById(String id);
    List<String> getTableTypes();
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


    /*
        订单相关
     */
    Float getTotalConsumption(String tableId);
    Map<String, Integer> getOrderDetail(String tableId);


}
