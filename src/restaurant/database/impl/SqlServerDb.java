package restaurant.database.impl;

import restaurant.database.IDb;
import restaurant.database.tabobj.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlServerDb implements IDb {
    private final static String DRIVE_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String url;
    private String user;
    private String password;
    @Override
    public void init(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        try {
            Class.forName(DRIVE_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> ems = new ArrayList();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            sta =conn.createStatement();
            rs =sta.executeQuery("SELECT * FROM EMPLOYEE");
            while(rs.next()){
                Employee em = new Employee();
                em.setId(rs.getString("员工id"));
                em.setName(rs.getString("姓名"));
                em.setAge(rs.getInt("年龄"));
                em.setSex(rs.getString("性别"));
                em.setNativePlace(rs.getString("籍贯"));
                em.setSalary(rs.getInt("薪资"));
                em.setHiredate(rs.getDate("入职时间"));
                em.setContactWay(rs.getString("联系方式"));
                em.setAddress(rs.getString("住址"));
                ems.add(em);
            }
            rs.close();
            sta.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ems;
    }

    @Override
    public List<Employee> getEmployeeByName(String name, Boolean fuzzy) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByAgeRange(Integer inf, Integer sup) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeBySex(Boolean isMale) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeBySalaryRange(Integer inf, Integer sup) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByHiredateRange(Integer inf, Integer sup) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByNativePlace(String nativePlace, Boolean fuzzy) {
        return null;
    }
}
