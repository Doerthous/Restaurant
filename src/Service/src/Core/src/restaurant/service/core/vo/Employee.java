package restaurant.service.core.vo;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

public class Employee {
    private String name;
    private String code;
    private byte[] photo; // 引入ui层会不会提高耦合？
    private String position;
    private String phone;
    private String nativePlace;
    private String password;
    private String sex;
    private Integer salary;

    public Employee(String name, String code, String position, String sex,
                    byte[]  photo, String phone, String nativePlace,
                    String password, Integer salary) {
        this.name = name;
        this.code = code;
        this.photo = photo;
        this.position = position;
        this.phone = phone;
        this.sex = sex;
        this.nativePlace = nativePlace;
        this.password = password;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getPosition() {
        return position;
    }

    public String getPhone() {
        return phone;
    }

    public String getSex() {
        return sex;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public String getPassword() {
        return password;
    }

    public Integer getSalary() {
        return salary;
    }
}
