package restaurant.database.impl.mapping;

import restaurant.database.impl.Wrapper;
import restaurant.database.po.Detail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailDMDeleteByOrderId implements Wrapper.DeleteMapping<Detail> {
    @Override
    public void mapping(Detail detail, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, detail.getOrderId());
    }
}
