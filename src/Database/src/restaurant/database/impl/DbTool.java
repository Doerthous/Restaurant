package restaurant.database.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbTool {
    enum Driver {
        SQLSERVER,
        MYSQL,
    };
    public static Connection getSqlServerConnection(String host, Short port, String database,
                                             String user, String password) throws SQLException {
        String url = "jdbc:sqlserver://" +
                host + ":" + port + ";" +
                "DatabaseName=" + database;
        return DriverManager.getConnection(url, user, password);
    }
    public static Connection getMysqlConnection(String host, Short port, String database,
                                    String user, String password) throws SQLException {
        String url = "jdbc:mysql://" + host + ":"+port + "/" + database + "?" +
                "useUnicode=true&characterEncoding=UTF8&useSSL=false";
        return DriverManager.getConnection(url, user, password);
    }
}

