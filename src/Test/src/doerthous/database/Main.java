package doerthous.database;

import java.io.*;
import java.sql.*;

public class Main {
    private final static String DRIVE_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String url = "jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish";
    static String user = "ODuser";
    static String password = "1234567890";

    static class Insert {
        public static void main(String[] args) {
            Connection conn = null;
            Statement sta = null;
            ResultSet rs = null;
            try{
                conn = DriverManager.getConnection(url,user,password);
                String sql = "INSERT INTO P(p) VALUES(?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                InputStream input = new FileInputStream(new File("D:\\Doerthous\\Pictures\\Saved Pictures\\SyeyiF8.jpg"));
                int len = input.available();
                stmt.setBinaryStream(1,input,len);
                int flag = stmt.executeUpdate();
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
        }
    }
    static class Select {
        public static void main(String[] args) {
            Connection conn = null;
            Statement sta = null;
            ResultSet rs = null;
            try{
                conn = DriverManager.getConnection(url,user,password);
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM P");
                byte[] cache = new byte[10240];
                while(rs.next()) {
                    InputStream is = rs.getBinaryStream("p");
                    int len = is.available();
                    int total = 0;
                    while(len != 0) {
                        total += is.read(cache);
                        len = is.available();
                    }
                    System.out.println(total);
                }
                stmt.close();
                conn.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
