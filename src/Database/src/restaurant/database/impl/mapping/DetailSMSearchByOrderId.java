package restaurant.database.impl.mapping;

import restaurant.database.impl.Wrapper;
import restaurant.database.po.Detail;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DetailSMSearchByOrderId implements Wrapper.SearchMapping<Detail> {
    @Override
    public void mapping(Detail detail, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, detail.getOrderId());
    }
}
