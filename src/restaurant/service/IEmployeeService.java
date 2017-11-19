package restaurant.service;

import restaurant.service.vo.Employee;

import java.util.List;

/*
    UI层与Service层的接口
 */

/*
    员工管理接口
 */
public interface IEmployeeService {
    List<Employee> getAllEmployee();
    List<Employee> getEmployeeByName(String name, Boolean fuzzy);
    List<Employee> getEmployeeByAgeRange(Integer inf, Integer sup);
    List<Employee> getEmployeeBySex(String sex);
    List<Employee> getEmployeeByPosition(String position); // 职位
    List<Employee> getEmployeeBySalaryRange(Integer inf, Integer sup);
    List<Employee> getEmployeeByHiredateRange(Integer inf, Integer sup);
    List<Employee> getEmployeeByNativePlace(String nativePlace, Boolean fuzzy); // 籍贯
}
