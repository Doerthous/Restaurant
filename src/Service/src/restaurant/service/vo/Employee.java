package restaurant.service.vo;

import java.util.Date;

/**
 * Created by YMS on 2017/11/18.
 */
public class Employee extends restaurant.database.po.Employee {
    public Employee(String id, String name, Integer age, String sex, String nativePlace, String position,
                    Integer salary, Date hiredate, String contactWay, String address) {
        super(id, name, age, sex, nativePlace, position, salary, hiredate, contactWay, address);
    }

    public Employee() {
    }
}
