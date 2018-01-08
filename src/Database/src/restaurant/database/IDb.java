package restaurant.database;

import restaurant.database.po.*;

import java.io.File;
import java.util.List;

public interface IDb {
    String serverIp = "192.168.155.1";
    void init(String url, String user, String password);
    void init(String host, Short port, String database, String user, String password);
    /*
        员工表操作
     */
    // 查询操作
    List<Employee> getAllEmployee();
    Employee getEmployeeById(String id);
    List<Employee> getEmployeeByName(String name, Boolean fuzzy);
    List<Employee> getEmployeeByAgeRange(Integer inf, Integer sup);
    List<Employee> getEmployeeBySex(Boolean isMale);
    List<Employee> getEmployeeByPosition(String position); // 职位
    List<Employee> getEmployeeBySalaryRange(Integer inf, Integer sup);
    List<Employee> getEmployeeByHiredateRange(Integer inf, Integer sup);
    List<Employee> getEmployeeByNativePlace(String nativePlace, Boolean fuzzy); // 籍贯
    Boolean updateEmployee(Employee employee);

    //增删操作
    Boolean insertEmployee(Employee employee);
    Boolean deleteEmployee(Employee employee);
    //修改操作


    /*
        菜品表操作
     */
    //查询操作
    List<Dish> getAllDish();
    List<Dish> getDishByName(String name);
    List<Dish> getDishByType(String type);
    List<Dish> getDishByPrice(Boolean bool);
    List<Dish> getDishMenu();
    List<Dish> getDishBySales(Boolean bool);

    //增删操作
    Boolean insertDish(Dish dish);
    Boolean deleteDish(Dish dish);
    //修改操作
    Boolean updateDish(Dish dish);
    /*
        座位表操作
    */
    //查询操作
    List<Seat> getAllSeat();
    Seat getSeatById(String id);
    List<Seat> getSeatByType(String type);
    List<Seat> getSeatByCapacity(int capacity);
    //增删操作
    Boolean insertSeat(Seat seat);
    Boolean deleteSeat(Seat seat);
    //修改操作
    Boolean updateSeat(Seat seat);

    /*
        订单表操作
    */
    //查询操作
    //增删操作
    Boolean insertOrder(Order order);
    Boolean deleteOrder(Order order);
    List<Order> getAllOrder();
    List<Order> getOrderByTableId(String  tableId);
    Order getOrderById(String orderId);
    //修改操作




    /*
        订单详情表操作
    */
    //查询操作
    //增删操作
    Boolean insertDetail(Detail detail);
    Boolean deleteDetail(Detail detail);
    List<Detail> getDetailByOrderId(String id);
    //修改操作
}
