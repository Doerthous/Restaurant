package restaurant.database.tool;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class Tools {
    public static void main(String [] args) {
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL = "jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish";
        String userName = "ODuser";
        String userPwd = "1234567890";
        try {
            Class.forName(driverName);
            Connection conn = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println("连接数据库成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("连接失败");
        }
    }
}
