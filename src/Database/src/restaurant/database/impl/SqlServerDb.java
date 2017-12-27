package restaurant.database.impl;


import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.Parent;
import jdk.nashorn.internal.objects.NativeUint8Array;
import restaurant.database.IDb;
import restaurant.database.po.Dish;
import restaurant.database.po.Employee;
import restaurant.database.po.Seat;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.SimpleFormatter;

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
        String sql = "SELECT * FROM EMPLOYEE";
        return getEmployeeByColumn(sql);
    }

    @Override
    public Employee getEmployeeById(String id) {
        String sql = "SELECT * FROM EMPLOYEE WHERE 员工id = '"+ id +"'";
        List<Employee> emps = getEmployeeByColumn(sql);
        int count = emps.size();
        if (count == 0)return null;
        else return emps.get(0);
    }

    @Override
    public List<Employee> getEmployeeByName(String name, Boolean fuzzy) {
        String sql = "";
        if(fuzzy) sql = "SELECT  * FROM EMPLOYEE WHERE PATINDEX('%" + name + "%',姓名) > 0";
        else sql ="SELECT * FROM EMPLOYEE WHERE 姓名 = '"+ name +"'";
        return getEmployeeByColumn(sql);
    }

    @Override
    public List<Employee> getEmployeeByAgeRange(Integer inf, Integer sup) {return null;}

    @Override
    public List<Employee> getEmployeeBySex(Boolean isMale) {
        String sql = "";
        if(isMale)  sql = "SELECT * FROM EMPLOYEE WHERE 性别 = '男'";
        else sql= "SELECT * FROM EMPLOYEE WHERE 性别 = '女'";
        return getEmployeeByColumn(sql);
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        String sql = "SELECT * FROM EMPLOYEE WHERE 职位 ='" + position +"'";
        return getEmployeeByColumn(sql);
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
                    "SET 姓名=?,出生日期=?,性别=?,籍贯=?,职位=?,薪资=?,入职时间=?,联系方式=?,住址=?,密码=?,照片=? " +
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
            stmt.setBinaryStream(11,employee.getPhoto(),(int)employee.getPhoto().available());
            stmt.setString(12, employee.getId());
            int flag = stmt.executeUpdate();                //执行修改操作，返回影响的行数
            if (flag == 1) {
                result = true;
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Boolean insertEmployee(Employee employee) {
        Boolean result = false;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            String sql = "INSERT INTO EMPLOYEE VALUE(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,employee.getId());
            stmt.setString(2,employee.getName());
            stmt.setString(3,employee.getSex());
            stmt.setString(4,employee.getNativePlace());
            stmt.setString(5,employee.getPosition());
            stmt.setInt(6,employee.getSalary());
            stmt.setDate(7,new java.sql.Date(employee.getHiredate().getTime()));
            stmt.setString(8,employee.getContactWay());
            stmt.setString(9,employee.getAddress());
            stmt.setString(10,employee.getPassword());
            stmt.setDate(11,new java.sql.Date(employee.getBirthday().getTime()));
            stmt.setBinaryStream(12,employee.getPhoto(),(int)employee.getPhoto().available());
            int flag = stmt.executeUpdate();
            if (flag == 1)result = true;
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Boolean deleteEmployee(Employee employee) {
        Boolean result = false;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            String sql = "DELETE FROM MENU WHERE 员工id='"+ employee.getId()+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            int flag = stmt.executeUpdate();
            if (flag == 1)result = true;
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
        String sql = "SELECT * FROM MENU";
        return getDishByColumn(sql);
    }

    @Override
    public List<Dish> getDishByName(String name) {
        return null;
    }

    @Override
    public List<Dish> getDishByType(String type) {
        String sql = "SELECT * FROM MENU WHERE 品类='"+ type +"'";
        return getDishByColumn(sql);
    }

    @Override
    public List<Dish> getDishByPrice(Boolean bool) {
        String sql ="";
        if (bool)sql="SELECT * FROM MENU"+
                " ORDER BY 单价 ASC ";
        else sql = "SELECT * FROM MENU" +
                " ORDER BY 单价 DESC ";
        return getDishByColumn(sql);
    }


    @Override
    public List<Dish> getDishMenu() {
        String sql = "SELECT * FROM MENU WHERE 是否售卖 = '1'";
        return getDishByColumn(sql);
    }

    @Override
    public List<Dish> getDishBySales(Boolean bool) {
        String imdmonth = "";
        String month = "";
        String sql = "";
        long time = System.currentTimeMillis();
        Date currenttime = new Date(time);
        SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd");
        imdmonth = form.format(currenttime).substring(5,6);
        switch (imdmonth){
            case "01": month = "一月销售量";break;
            case "02": month = "二月销售量";break;
            case "03": month = "三月销售量";break;
            case "04": month = "四月销售量";break;
            case "05": month = "五月销售量";break;
            case "06": month = "六月销售量";break;
            case "07": month = "七月销售量";break;
            case "08": month = "八月销售量";break;
            case "09": month = "九月销售量";break;
            case "10": month = "十月销售量";break;
            case "11": month = "十一月销售量";break;
            default:month = "十二月销售量";break;
        }
        if (bool)sql= "SELECT * FROM MENU"+
                " ORDER BY "+ month +" ASC ";
        else sql = "SELECT * FROM MENU" +
                " ORDER BY "+ month +" DESC ";
        return getDishByColumn(sql);
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

            InputStream input = dish.getPicture();
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
    public Boolean deleteDish(Dish dish) {
        Boolean result = false;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            String sql = "DELETE FROM MENU WHERE 菜品id='"+ dish.getId()+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            int falg = stmt.executeUpdate();
            if (falg == 1) result = true;
            stmt.close();
            conn.close();
        } catch (SQLException e) {
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
                    "九月销售量=?,十月销售量=?,十一月销售量=?,十二月销售量=? "+"WHERE 菜品id=?";
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
            InputStream input = dish.getPicture();
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

    @Override
    public List<Seat> getAllSeat() {
        String sql = "SELECT * FROM SEATS";
        return getSeatByColumn(sql);
    }

    @Override
    public Seat getSeatById(String id) {
        String sql = "SELECT * FROM SEATS WHERE 餐桌id='"+ id +"'";
        List<Seat> sts= getSeatByColumn(sql);
        int count = sts.size();
        if(count == 0)return  null;
        else return sts.get(0);
    }

    @Override
    public List<Seat> getSeatByType(String type) {
        String sql = "SELECT * FROM SEATS WHERE 类型='"+ type +"'";
        return getSeatByColumn(sql);
    }

    @Override
    public List<Seat> getSeatByCapacity(int capacity) {
        String sql  = "SELECT * FROM SEATS WHERE 容量 ="+ capacity;
        return getSeatByColumn(sql);
    }

    @Override
    public Boolean insertSeat(Seat seat) {
        Boolean result = false;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            String sql = "INSERT INTO SEATS VALUES(?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,seat.getId());
            stmt.setString(2,seat.getType());
            stmt.setInt(3,seat.getFloor());
            stmt.setInt(4,seat.getCapacity());
            stmt.setString(5,seat.getStatus());
            stmt.setInt(6,seat.getUsedtimes());
            int flag = stmt.executeUpdate();
            if (flag == 1)result = true;
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Boolean deleteSeat(Seat seat) {
        Boolean result = false;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            String sql = "DELETE FROM SEATS WHERE 餐桌id = '"+ seat.getId() +"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            int flag = stmt.executeUpdate();
            if (flag == 1)result = true;
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Boolean updateSeat(Seat seat) {
        Boolean result = false;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            String sql = "UPDATE SEATS SET 类型=?, 楼层=?, 容量=?, 状态=?, 当月使用次数=? "+
                    "WHERE  餐桌id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,seat.getType());
            stmt.setInt(2,seat.getFloor());
            stmt.setInt(3,seat.getCapacity());
            stmt.setString(4,seat.getStatus());
            stmt.setInt(5,seat.getUsedtimes());
            stmt.setString(6,seat.getId());
            int flag = stmt.executeUpdate();
            if (flag == 1)result = true;
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Seat> getSeatByColumn(String sql){
        List<Seat> sts = new ArrayList<>();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            sta = conn.createStatement();
            rs = sta.executeQuery(sql);
            while (rs.next()){
                Seat st = new Seat();
                st.setId(rs.getString("餐桌id"));
                st.setType(rs.getString("类型"));
                st.setFloor(rs.getInt("楼层"));
                st.setCapacity(rs.getInt("容量"));
                st.setStatus(rs.getString("状态"));
                st.setUsedtimes(rs.getInt("当月使用次数"));
                sts.add(st);
            }
            rs.close();
            sta.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sts;
    }
    
    public List<Dish> getDishByColumn(String sql){
        List<Dish> dss = new ArrayList<>();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            sta = conn.createStatement();
            rs = sta.executeQuery(sql);
            while (rs.next()){
                Dish ds = new Dish();
                ds.setId(rs.getString("菜品id"));
                ds.setName(rs.getString("菜名"));
                ds.setPrice(rs.getFloat("单价"));
                ds.setType(rs.getString("品类"));
                ds.setSaled(rs.getBoolean("是否售卖"));
                ds.setPicture(rs.getBinaryStream("图片"));
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

    public List<Employee> getEmployeeByColumn(String sql){
        List<Employee> ems = new ArrayList();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            sta =conn.createStatement();
            rs =sta.executeQuery(sql);
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
                em.setPhoto(rs.getBinaryStream("照片"));
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
    /*private FileInputStream filetoInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }*/

    /*public File inputstreamtofile(InputStream ins) throws IOException {
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
    }*/

}
