package restaurant.service.pc.management;

import restaurant.communication.core.IPeer;
import restaurant.database.IDb;
import restaurant.database.po.Seat;
import restaurant.service.core.vo.Dish;
import restaurant.service.pc.vo.PO2VO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DishManager {
    private IDb db;
    // 缓存数据库的数据
    private List<restaurant.database.po.Dish> dishes;

    public DishManager(IDb db) {
        this.db = db;
    }


    private static boolean isDigits(String str){
        return Pattern.compile("^\\d+$").matcher(str).find();
    }
    public String newDishId(){
        int max = 0;
        for(restaurant.database.po.Dish dish:dishes){
            if(isDigits(dish.getId())){
                int id = Integer.valueOf(dish.getId());
                if(id > max){
                    max = id;
                }
            }
        }
        String newId = String.valueOf(max+1);
        int l = 11 - newId.length();
        String prefix = "";
        for (int i = 0; i < l; ++i){
            prefix += "0";
        }
        return prefix+newId;
    }
    public Boolean createDish(String name, Float price, String type, Boolean isSaled, String pictureUrl) {
        restaurant.database.po.Dish dish = PO2VO.newDishPo(newDishId(), name, price, type,isSaled, pictureUrl);
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
            if(d.getType().equals(type)){
                dishes.add(PO2VO.dish(d));
            }
        }
        return dishes;
    }
    public List<Dish> getDishSortByPrice(Boolean asc) {
        List<restaurant.database.po.Dish> dishes = db.getDishByPrice(asc);
        List<Dish> vdish = new ArrayList<>();
        for(restaurant.database.po.Dish dish : dishes){
            vdish.add(PO2VO.dish(dish));
        }
        return vdish;
    }
    public List<Dish> getDishSortBySaledCount(Boolean asc) {
        List<restaurant.database.po.Dish> dishes = db.getDishBySales(asc);
        List<Dish> vdish = new ArrayList<>();
        for(restaurant.database.po.Dish dish : dishes){
            vdish.add(PO2VO.dish(dish));
        }
        return vdish;
    }
    public List<Dish> getDishByTypeSortByPrice(String type, Boolean asc) {
        List<restaurant.database.po.Dish> dishes = db.getDishByPrice(asc);
        List<Dish> vdish = new ArrayList<>();
        for(restaurant.database.po.Dish dish : dishes){
            if(dish.getType().equals(type)){
                vdish.add(PO2VO.dish(dish));
            }
        }
        return vdish;
    }
    public List<Dish> getDishByTypeSortBySaledCount(String type, Boolean asc) {
        List<restaurant.database.po.Dish> dishes = db.getDishBySales(asc);
        List<Dish> vdish = new ArrayList<>();
        for(restaurant.database.po.Dish dish : dishes){
            if(dish.getType().equals(type)){
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
