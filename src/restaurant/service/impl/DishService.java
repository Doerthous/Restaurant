package restaurant.service.impl;

import restaurant.database.DbFactory;
import restaurant.database.IDb;
import restaurant.service.IDishService;
import restaurant.service.vo.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishService implements IDishService {
    private IDb db;
    public DishService() {
        db = DbFactory.getDb(DbFactory.DbType.SqlServer);
        db.init("jdbc:sqlserver://192.168.155.1:1433;DatabaseName=OrderDish", "ODuser","1234567890");
    }

    @Override
    public List<Dish> getDishMenu() {
        List<restaurant.database.po.Dish> pdss = db.getDishMenu();
        List<Dish> vdss = new ArrayList<>();
        for(restaurant.database.po.Dish pds : pdss){
            Dish vds = new Dish();
            vds.setName(pds.getName());
            vds.setPrice(pds.getPrice().toString());
            vdss.add(vds);
        }
        return vdss;
    }
}
