package restaurant.database.impl;

public class MysqlDb extends SqlServerDb {
    private final static String DRIVE_NAME = "com.mysql.jdbc.Driver";


    @Override
    public void init(String host, Short port, String database, String user, String password) {
        String url = "jdbc:mysql://" + host + ":"+port + "/" + database + "?" +
                "useUnicode=true&characterEncoding=UTF8&useSSL=false";
        super.init(url, user, password);
        /*this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;*/
        try {
            Class.forName(DRIVE_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

