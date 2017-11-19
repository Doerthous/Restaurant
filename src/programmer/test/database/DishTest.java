package programmer.test.database;

import restaurant.database.DbFactory;
import restaurant.database.IDb;
import restaurant.database.po.Dish;
import restaurant.database.po.Employee;

import java.util.List;

public class DishTest {
    public static void main(String[] args) {
        IDb db = DbFactory.getDb(DbFactory.DbType.SqlServer);
        db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
        List<Dish> dss = db.getDishMenu();
        for(Dish ds : dss){
            System.out.println(ds.toString());
        }
    }
}
