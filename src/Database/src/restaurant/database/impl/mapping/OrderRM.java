package restaurant.database.impl.mapping;

import restaurant.database.impl.Wrapper;
import restaurant.database.po.Dish;
import restaurant.database.po.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRM implements Wrapper.ResultMapping<Order> {
    @Override
    public Order mapping(ResultSet rs, int rowIndex) throws SQLException {
        Order order = new Order();
        order.setId(rs.getString("订单id"));
        order.setSeatId(rs.getString("桌号"));
        order.setDate(rs.getDate("日期时间"));
        order.setExpend(rs.getFloat("消费金额"));
        return order;
    }
}
