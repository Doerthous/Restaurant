package restaurant.dataset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDataBase {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        //一开始必须填一个已经存在的数据库
        String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf-8";
        Connection conn = DriverManager.getConnection(url, "root", "123456");
        Statement stat = conn.createStatement();

        //创建数据库hello
        int flag = stat.executeUpdate("CREATE DATABASE Restaurant");
        System.out.println(flag);

        //打开创建的数据库
        stat.close();
        conn.close();
    }
}
