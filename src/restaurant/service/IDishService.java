package restaurant.service;

import restaurant.service.vo.Dish;

import java.util.List;

public interface IDishService {
    List<Dish> getDishMenu();
}
