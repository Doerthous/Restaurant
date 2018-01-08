package restaurant.database.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Wrapper<PO> {
    public interface ResultMapping<PO> {
        PO mapping(ResultSet rs, int rowIndex) throws SQLException;
    }
    public interface SearchMapping<PO> {
        void mapping(PO po, PreparedStatement preparedStatement) throws SQLException;
    }
    public interface DeleteMapping<PO> extends SearchMapping<PO> {}
    public interface UpdateMapping<PO> extends DeleteMapping<PO> {}

    private String url;
    private String user;
    private String password;

    public Wrapper(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public List<PO> search(String sql, ResultMapping<PO> mapping){
        List<PO> l = new ArrayList<>();
        // do something
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            for(int i = 1; resultSet.next(); ++i){
                l.add(mapping.mapping(resultSet, i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return l;
    }
    public List<PO> search(String sql, PO po, SearchMapping<PO> sm, ResultMapping<PO> rm) {
        List<PO> l = new ArrayList<>();
        // do something
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            sm.mapping(po, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            for(int i = 1; resultSet.next(); ++i){
                l.add(rm.mapping(resultSet, i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return l;
    }
    public Integer delete(String sql, PO po, DeleteMapping<PO> mapping){
        Integer rows = 0;
        // do something
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            mapping.mapping(po, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rows;
    }
    public Integer update(String sql, PO po, UpdateMapping<PO> mapping){
        return delete(sql, po, mapping);
    }
}
