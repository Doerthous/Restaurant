package restaurant.service.impl;

import restaurant.database.DbFactory;
import restaurant.database.IDb;
import restaurant.service.IEmployeeService;
import restaurant.service.vo.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YMS on 2017/11/18.
 */
public class EmployeeService implements IEmployeeService {
    private IDb db;
    public EmployeeService() {
        db = DbFactory.getDb(DbFactory.DbType.SqlServer);
    }
    @Override
    public List<Employee> getAllEmployee() {
        db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
        List<restaurant.database.po.Employee> pems = db.getAllEmployee();
        List<Employee> vems = new ArrayList<>();
        for(restaurant.database.po.Employee pem : pems){
            vems.add(new Employee(pem.getId(), pem.getName(), pem.getAge(), pem.getSex(), pem.getNativePlace(),
                    pem.getPosition(), pem.getSalary(), pem.getHiredate(), pem.getContactWay(), pem.getAddress()));
        }
        return vems;
    }

    @Override
    public List<Employee> getEmployeeBySex(String sex) {
        List<restaurant.database.po.Employee> pems = db.getAllEmployee();
        List<Employee> vems = new ArrayList<>();
        for(restaurant.database.po.Employee pem : pems){
            if(pem.getSex().equals(sex)) {
                vems.add(new Employee(pem.getId(), pem.getName(), pem.getAge(), pem.getSex(), pem.getNativePlace(),
                        pem.getPosition(), pem.getSalary(), pem.getHiredate(), pem.getContactWay(), pem.getAddress()));
            }
        }
        return vems;
    }
}
