package restaurant.database;

import restaurant.database.impl.MysqlDb;
import restaurant.database.impl.SqlServerDb;

public class DbFactory {
    public enum DbType{
        SqlServer,
        Mysql,
        Oracle,
    };

    public static IDb getDb(DbType type){
        IDb db;
        switch (type){
            case SqlServer:{
                db = new SqlServerDb();
            } break;
            case Mysql: {
                db = new MysqlDb();
            } break;
            default:{
                throw new UnsupportedOperationException();
            }
        }
        return db;
    }
}
