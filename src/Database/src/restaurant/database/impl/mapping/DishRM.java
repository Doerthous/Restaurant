package restaurant.database.impl.mapping;

import restaurant.database.impl.Wrapper;
import restaurant.database.po.Dish;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DishRM implements Wrapper.ResultMapping<Dish> {
    @Override
    public Dish mapping(ResultSet rs, int rowIndex) throws SQLException {
        Dish dish = new Dish();
        dish.setId(rs.getString("菜品id"));
        dish.setName(rs.getString("菜名"));
        dish.setPrice(rs.getFloat("单价"));
        dish.setType(rs.getString("品类"));
        dish.setSaled(rs.getBoolean("是否售卖"));
        dish.setPicture(rs.getBinaryStream("图片"));
        dish.setSaledCount1(rs.getInt("一月销售量"));
        dish.setSaledCount2(rs.getInt("二月销售量"));
        dish.setSaledCount3(rs.getInt("三月销售量"));
        dish.setSaledCount4(rs.getInt("四月销售量"));
        dish.setSaledCount5(rs.getInt("五月销售量"));
        dish.setSaledCount6(rs.getInt("六月销售量"));
        dish.setSaledCount7(rs.getInt("七月销售量"));
        dish.setSaledCount8(rs.getInt("八月销售量"));
        dish.setSaledCount9(rs.getInt("九月销售量"));
        dish.setSaledCount10(rs.getInt("十月销售量"));
        dish.setSaledCount11(rs.getInt("十一月销售量"));
        dish.setSaledCount12(rs.getInt("十二月销售量"));
        return dish;
    }
}
