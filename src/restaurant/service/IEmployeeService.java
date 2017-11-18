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
    List<Employee> getEmployeeBySex(String sex);
}
