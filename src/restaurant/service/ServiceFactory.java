package restaurant.service;

import restaurant.service.impl.EmployeeService;

/**
 * Created by YMS on 2017/11/18.
 */
public class ServiceFactory {
    public static IEmployeeService getEmployeeService(){
        return new EmployeeService();
    }
}