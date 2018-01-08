package restaurant.database.impl.mapping;

import restaurant.database.impl.Wrapper;
import restaurant.database.po.Detail;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailRM implements Wrapper.ResultMapping<Detail> {
    @Override
    public Detail mapping(ResultSet rs, int rowIndex) throws SQLException {
        Detail detail = new Detail();
        detail.setOrderId(rs.getString("订单id"));
        detail.setDishId(rs.getString("菜品id"));
        detail.setAmount(rs.getInt("菜品数量"));
        return detail;
    }
}
