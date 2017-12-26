package restaurant.database.impl;


import javafx.scene.Parent;
import jdk.nashorn.internal.objects.NativeUint8Array;
import restaurant.database.IDb;
import restaurant.database.po.Dish;
import restaurant.database.po.Employee;

import java.io.*;
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
                em.setBirthday(rs.getDate("出生日期"));
                em.setSex(rs.getString("性别"));
                em.setNativePlace(rs.getString("籍贯"));
                em.setPosition(rs.getString("职位"));
                em.setSalary(rs.getInt("薪资"));
                em.setHiredate(rs.getDate("入职时间"));
                em.setContactWay(rs.getString("联系方式"));
                em.setAddress(rs.getString("住址"));
                em.setPassword(rs.getString("密码"));
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
    public Employee getEmployeeById(String id) {
        Employee em = null;
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try{
            conn = DriverManager.getConnection(url,user,password);
            sta = conn.createStatement();
            rs = sta.executeQuery("SELECT * FROM EMPLOYEE WHERE 员工id = '"+ id +"'");
            while(rs.next()){
                em = new Employee();
                em.setId(rs.getString("员工id"));
                em.setName(rs.getString("姓名"));
                em.setBirthday(rs.getDate("出生日期"));
                em.setSex(rs.getString("性别"));
                em.setNativePlace(rs.getString("籍贯"));
                em.setPosition(rs.getString("职位"));
                em.setSalary(rs.getInt("薪资"));
                em.setHiredate(rs.getDate("入职时间"));
                em.setContactWay(rs.getString("联系方式"));
                em.setAddress(rs.getString("住址"));
                em.setPassword(rs.getString("密码"));
            }
            rs.close();
            sta.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return em;
    }

    @Override
    public List<Employee> getEmployeeByName(String name, Boolean fuzzy) {
        List<Employee> ems = new ArrayList<>();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try{
            conn = DriverManager.getConnection(url,user,password);
            sta = conn.createStatement();
            if(fuzzy) rs = sta.executeQuery("SELECT  * FROM EMPLOYEE WHERE PATINDEX('%" + name + "%',姓名) > 0");
             else rs = sta.executeQuery("SELECT * FROM EMPLOYEE WHERE 姓名 = '"+ name +"'");
            while(rs.next()){
                Employee em = new Employee();
                em.setId(rs.getString("员工id"));
                em.setName(rs.getString("姓名"));
                em.setBirthday(rs.getDate("出生日期"));
                em.setSex(rs.getString("性别"));
                em.setNativePlace(rs.getString("籍贯"));
                em.setPosition(rs.getString("职位"));
                em.setSalary(rs.getInt("薪资"));
                em.setHiredate(rs.getDate("入职时间"));
                em.setContactWay(rs.getString("联系方式"));
                em.setAddress(rs.getString("住址"));
                em.setPassword(rs.getString("密码"));
                ems.add(em);
            }
            rs.close();
            sta.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ems;
    }

    @Override
    public List<Employee> getEmployeeByAgeRange(Integer inf, Integer sup) {return null;}

    @Override
    public List<Employee> getEmployeeBySex(Boolean isMale) {
        List<Employee> ems = new ArrayList<>();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try{
            conn = DriverManager.getConnection(url,user,password);
            sta = conn.createStatement();
            if(isMale)  rs = sta.executeQuery("SELECT * FROM EMPLOYEE WHERE 性别 = '男'");
            else rs= sta.executeQuery("SELECT * FROM EMPLOYEE WHERE 性别 = '女'");
            while(rs.next())
            {
                Employee em = new Employee();
                em.setId(rs.getString("员工id"));
                em.setName(rs.getString("姓名"));
                em.setBirthday(rs.getDate("出生日期"));
                em.setSex(rs.getString("性别"));
                em.setNativePlace(rs.getString("籍贯"));
                em.setPosition(rs.getString("职位"));
                em.setSalary(rs.getInt("薪资"));
                em.setHiredate(rs.getDate("入职时间"));
                em.setContactWay(rs.getString("联系方式"));
                em.setAddress(rs.getString("住址"));
                em.setPassword(rs.getString("密码"));
                ems.add(em);
            }
            rs.close();
            sta.close();
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return ems;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> ems = new ArrayList<>();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try{
            conn = DriverManager.getConnection(url,user,password);
            sta = conn.createStatement();
            rs = sta.executeQuery("SELECT * FROM EMPLOYEE WHERE 职位 ='" + position +"'");
            while(rs.next()){
                Employee em  =  new Employee();
                em.setId(rs.getString("员工id"));
                em.setName(rs.getString("姓名"));
                em.setBirthday(rs.getDate("出生日期"));
                em.setSex(rs.getString("性别"));
                em.setPosition(rs.getString("职位"));
                em.setSalary(rs.getInt("薪资"));
                em.setHiredate(rs.getDate("入职时间"));
                em.setNativePlace(rs.getString("籍贯"));
                em.setContactWay(rs.getString("联系方式"));
                em.setAddress(rs.getString("住址"));
                em.setPassword(rs.getString("密码"));
                ems.add(em);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return ems;
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

    @Override
    public Boolean updateEmployee(Employee employee) {
        Boolean result = false;
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "UPDATE EMPLOYEE " +
                    "SET 姓名=?,出生日期=?,性别=?,籍贯=?,职位=?,薪资=?,入职时间=?,联系方式=?,住址=?,密码=? " +
                    "WHERE 员工id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, employee.getName());
            stmt.setDate(2, new java.sql.Date(employee.getBirthday().getTime()));
            stmt.setString(3, employee.getSex());
            stmt.setString(4, employee.getNativePlace());
            stmt.setString(5, employee.getPosition());
            stmt.setInt(6, employee.getSalary());
            stmt.setDate(7, new java.sql.Date(employee.getHiredate().getTime()));
            stmt.setString(8, employee.getContactWay());
            stmt.setString(9, employee.getAddress());
            stmt.setString(10, employee.getPassword());
            stmt.setString(11, employee.getId());
            int flag = stmt.executeUpdate();                //执行修改操作，返回影响的行数
            if (flag == 1) {
                result = true;
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    /*

     */
    @Override
    public List<Dish> getAllDish() {
        List<Dish> dss = new ArrayList<>();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            sta = conn.createStatement();
            rs = sta.executeQuery("SELECT * FROM MENU");
            while (rs.next()){
                Dish ds = new Dish();
                ds.setId(rs.getString("菜品id"));
                ds.setName(rs.getString("菜名"));
                //ds.set
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                ds.setType(rs.getString("品类"));
                ds.setSaled(rs.getBoolean("是否售卖"));
                ds.setPicture(inputstreamtofile(rs.getBinaryStream("图片")));
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dss;
    }

    @Override
    public Boolean insertDish(Dish dish) {
        Boolean result = false;
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try{
            conn = DriverManager.getConnection(url,user,password);
            String sql = "INSERT INTO MENU(菜品id,菜名,单价,品类,是否售卖,图片) VALUES(?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,dish.getId());
            stmt.setString(2,dish.getName());
            stmt.setFloat(3,dish.getPrice());
            stmt.setString(4,dish.getType());
            stmt.setBoolean(5,dish.getSaled());

            File file = dish.getPicture();
            FileInputStream input = new FileInputStream(dish.getPicture());
            stmt.setBinaryStream(6,input,(int)input.available());
            int flag = stmt.executeUpdate();
            if (flag ==1) result = true;
            stmt.close();
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Boolean updateDish(Dish dish) {
        Boolean result = false;
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try{
            conn = DriverManager.getConnection(url,user,password);
            String sql = "UPDATE MENU"+
                    "SET 菜名=?,单价=?,品类=?, 是否售卖=?,图片=?,一月销售量=?,二月销售量=?,"+
                    "三月销售量=?,四月销售量=?,五月销售量=?,六月销售量=?,七月销售量=?,八月销售量=?,"+
                    "九月销售量=?,十月销售量=?,十一月销售量=?,十二月销售量=?,"+"WHERE 菜品id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,dish.getName());
            stmt.setFloat(2,dish.getPrice());
            stmt.setString(3,dish.getType());
            stmt.setBoolean(4,dish.getSaled());
            stmt.setInt(6,dish.getSaledCount1());
            stmt.setInt(7,dish.getSaledCount2());
            stmt.setInt(8,dish.getSaledCount3());
            stmt.setInt(9,dish.getSaledCount4());
            stmt.setInt(10,dish.getSaledCount5());
            stmt.setInt(11,dish.getSaledCount6());
            stmt.setInt(12,dish.getSaledCount7());
            stmt.setInt(13,dish.getSaledCount8());
            stmt.setInt(14,dish.getSaledCount9());
            stmt.setInt(15,dish.getSaledCount10());
            stmt.setInt(16,dish.getSaledCount11());
            stmt.setInt(17,dish.getSaledCount12());
            stmt.setString(18,dish.getId());
            FileInputStream input = new FileInputStream(dish.getPicture());
            stmt.setBinaryStream(5,input,(int)input.available());
            int flag = stmt.executeUpdate();
            if (flag == 1)  result = true;
            stmt.close();
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private FileInputStream filetoInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public File inputstreamtofile(InputStream ins) throws IOException {
        File file = File.createTempFile("temp","txt");
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return  file;
    }

}
