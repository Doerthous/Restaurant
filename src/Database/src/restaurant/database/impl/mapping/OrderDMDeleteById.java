package restaurant.database.impl.mapping;

import restaurant.database.impl.Wrapper;
import restaurant.database.po.Order;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDMDeleteById  implements Wrapper.DeleteMapping<Order>  {
    @Override
    public void mapping(Order order, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, order.getId());
    }
}
