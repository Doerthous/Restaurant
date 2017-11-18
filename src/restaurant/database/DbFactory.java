package restaurant.database;

import restaurant.database.impl.SqlServerDb;

public class DbFactory {
    public enum DbType{
        SqlServer,
        Mysql,
        Oracle,
    };

    public static IDb getDb(DbType type){
        IDb db = null;
        switch (type){
            case SqlServer:{
                db = new SqlServerDb();
            } break;
            default:{
                throw new UnsupportedOperationException();
            }
        }
        return db;
    }
}
