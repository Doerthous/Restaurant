package restaurant.database.impl;

import restaurant.database.IDb;
import restaurant.database.po.Dish;
import restaurant.database.po.Employee;

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


    /*

     */
    @Override
    public List<Dish> getAllDish() {
        return null;
    }

    @Override
    public List<Dish> getDishByName(String name) {
        return null;
    }

    @Override
    public List<Dish> getDishByType(String type) {
        return null;
    }

    @Override
    public List<Dish> getDishByPriceRange(Float inf, Float sup) {
        return null;
    }

    @Override
    public List<Dish> getDishMenu() {
        List<Dish> dss = new ArrayList();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            sta =conn.createStatement();
            rs =sta.executeQuery("SELECT * FROM MENU WHERE 是否售卖 = '1'");
            while(rs.next()){
                Dish ds = new Dish();
                ds.setId(rs.getString("菜品id"));
                ds.setName(rs.getString("菜名"));
                ds.setPrice(rs.getFloat("单价"));
                ds.setSaled(rs.getBoolean("是否售卖"));
                ds.setSaledCount1(rs.getInt("一月销售量"));
                ds.setSaledCount2(rs.getInt("二月销售量"));
                ds.setSaledCount3(rs.getInt("三月销售量"));
                ds.setSaledCount4(rs.getInt("四月销售量"));
                ds.setSaledCount5(rs.getInt("五月销售量"));
                ds.setSaledCount6(rs.getInt("六月销售量"));
                ds.setSaledCount7(rs.getInt("七月销售量"));
                ds.setSaledCount8(rs.getInt("八月销售量"));
                ds.setSaledCount9(rs.getInt("九月销售量"));
                ds.setSaledCount10(rs.getInt("十月销售量"));
                ds.setSaledCount11(rs.getInt("十一月销售量"));
                ds.setSaledCount12(rs.getInt("十二月销售量"));
                dss.add(ds);
            }
            rs.close();
            sta.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dss;
    }
}
