package restaurant.service.pc.management;

import restaurant.communication.core.IPeer;
import restaurant.database.IDb;
import restaurant.database.po.Seat;
import restaurant.service.core.vo.Dish;
import restaurant.service.pc.vo.PO2VO;

import java.util.ArrayList;
import java.util.List;

public class DishManager {
    private IDb db;
    // 缓存数据库的数据
    private List<restaurant.database.po.Dish> dishes;

    public DishManager(IDb db) {
        this.db = db;
    }


    public Boolean createDish(String name, Float price, String type, Boolean isSaled, String pictureUrl) {
        restaurant.database.po.Dish dish = PO2VO.newDishPo(name, name, price, type,isSaled, pictureUrl);
        //debug("New dish: " + dish.toString());
        if(db.insertDish(dish)){
            dishes.add(dish);
            return true;
        }
        return false;
    }
    public Boolean modifiDish(String name, Float price, String type, Boolean isSaled, String pictureUrl) {
        if(dishes == null){
            this.dishes = db.getAllDish();
        }
        restaurant.database.po.Dish targetDish = null;
        for(restaurant.database.po.Dish dish : this.dishes){
            if(dish.getName().equals(name)){
                targetDish = dish;
                break;
            }
        }
        restaurant.database.po.Dish dish = PO2VO.setDishPo(name, name, price, type,
                isSaled, pictureUrl, targetDish);
        //debug("Update dish: " + dish.toString());
        if(dish != null){
            return db.updateDish(dish);
        }
        return false;
    }
    public Boolean deleteDish(String name) {
        restaurant.database.po.Dish targetDish = PO2VO.newDishPo(name);
        //debug("Delete dish: " + targetDish.toString());
        if(db.deleteDish(targetDish)){
            getAllDish();
            return true;
        }
        return false;
    }
    public List<Dish> getAllDish() {
        this.dishes = db.getAllDish();
        List<Dish> dishes = new ArrayList<>();
        for(restaurant.database.po.Dish d: this.dishes){
            dishes.add(PO2VO.dish(d));
        }
        return dishes;
    }
    public List<Dish> getDishByType(String type) {
        if(this.dishes == null){
            getAllDish();
        }
        List<Dish> dishes = new ArrayList<>();
        for(restaurant.database.po.Dish d: this.dishes){
            if(type.equals("全部") || d.getType().equals(type)){
                dishes.add(PO2VO.dish(d));
            }
        }
        return dishes;
    }
    public List<Dish> getDishByTypeSortByPrice(String type, Boolean asc) {
        List<restaurant.database.po.Dish> dishes = db.getDishByPrice(asc);
        List<Dish> vdish = new ArrayList<>();
        for(restaurant.database.po.Dish dish : dishes){
            if(type.equals("全部") || dish.getType().equals(type)){
                vdish.add(PO2VO.dish(dish));
            }
        }
        return vdish;
    }
    public List<Dish> getDishByTypeSortBySaledCount(String type, Boolean asc) {
        List<restaurant.database.po.Dish> dishes = db.getDishBySales(asc);
        List<Dish> vdish = new ArrayList<>();
        for(restaurant.database.po.Dish dish : dishes){
            if(type.equals("全部") || dish.getType().equals(type)){
                vdish.add(PO2VO.dish(dish));
            }
        }
        return vdish;
    }
    public List<String> getDishTypes() {
        if(this.dishes == null){
            getAllDish();
        }
        List<String> types = new ArrayList<>();
        types.add("全部");
        for(restaurant.database.po.Dish dish: dishes){
            if(!types.contains(dish.getType())){
                types.add(dish.getType());
            }
        }
        return types;
    }
    public Dish getDishByName(String name) {
        List<restaurant.database.po.Dish> dishes = db.getDishByName(name);
        if(dishes.size() > 0) {
            restaurant.database.po.Dish dish = dishes.get(0);
            return PO2VO.dish(dish);
        }
        return null;
    }
}
