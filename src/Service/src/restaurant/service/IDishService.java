package restaurant.service;

import restaurant.service.vo.Dish;

import java.util.List;
import java.util.Map;

/*
    UI层与Service层的接口
 */

/*
    菜品管理接口
 */
public interface IDishService {
    List<Dish> getAllDish();
    List<Dish> getDishMenu(); // 获取菜单
    List<Dish> getDishByCondition(List<Map<String, String>> condition);

    Boolean addNewDish(Dish dish);
    Boolean setDishOnSale(Dish dish);
    Boolean changeDishType(Dish dish);
    Boolean changeDishName(Dish dish);
    Boolean changeDishPrice(Dish dish);
    Boolean modifyDish(Dish dish);
    Boolean deteleDish(Dish dish);
    Boolean deteleDishes(List<Dish> dishes);
}
