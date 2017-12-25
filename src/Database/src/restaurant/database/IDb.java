package restaurant.database;

import restaurant.database.po.Dish;
import restaurant.database.po.Employee;

import java.util.List;

public interface IDb {
    String serverIp = "192.168.155.1";
    void init(String url, String user, String password);
    /*
        员工表操作
     */
    // 查询操作
    List<Employee> getAllEmployee();
    List<Employee> getEmployeeByName(String name, Boolean fuzzy);
    List<Employee> getEmployeeByAgeRange(Integer inf, Integer sup);
    List<Employee> getEmployeeBySex(Boolean isMale);
    List<Employee> getEmployeeByPosition(String position); // 职位
    List<Employee> getEmployeeBySalaryRange(Integer inf, Integer sup);
    List<Employee> getEmployeeByHiredateRange(Integer inf, Integer sup);
    List<Employee> getEmployeeByNativePlace(String nativePlace, Boolean fuzzy); // 籍贯

    //增删操作
    //修改操作


    /*
        菜品表操作
     */
    //查询操作
    List<Dish> getAllDish();
    List<Dish> getDishByName(String name);
    List<Dish> getDishByType(String type);
    List<Dish> getDishByPriceRange(Float inf, Float sup);
    List<Dish> getDishMenu();

    //增删操作
    //修改操作

    /*
        座位表操作
    */
    //查询操作
    //增删操作
    //修改操作
    
}
