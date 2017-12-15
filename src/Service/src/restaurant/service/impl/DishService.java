package restaurant.service.impl;

import restaurant.database.DbFactory;
import restaurant.database.IDb;
import restaurant.service.IDishService;
import restaurant.service.vo.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DishService implements IDishService {
    private IDb db;
    public DishService() {
        db = DbFactory.getDb(DbFactory.DbType.SqlServer);
        db.init("jdbc:sqlserver://"+IDb.serverIp+":1433;DatabaseName=OrderDish", "ODuser","1234567890");
    }

    @Override
    public List<Dish> getAllDish() {
        return null;
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

    @Override
    public List<Dish> getDishByCondition(List<Map<String, String>> condition) {
        return null;
    }

    @Override
    public Boolean addNewDish(Dish dish) {
        return null;
    }

    @Override
    public Boolean setDishOnSale(Dish dish) {
        return null;
    }

    @Override
    public Boolean changeDishType(Dish dish) {
        return null;
    }

    @Override
    public Boolean changeDishName(Dish dish) {
        return null;
    }

    @Override
    public Boolean changeDishPrice(Dish dish) {
        return null;
    }

    @Override
    public Boolean modifyDish(Dish dish) {
        return null;
    }

    @Override
    public Boolean deteleDish(Dish dish) {
        return null;
    }

    @Override
    public Boolean deteleDishes(List<Dish> dishes) {
        return null;
    }
}
