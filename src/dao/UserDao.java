package dao;

import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private DataSource dataSource;

    interface StatementStrategy {
        PreparedStatement makePreparedStatement(Connection connection) throws SQLException;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        StatementStrategy stmt = (c) -> {
            PreparedStatement prepareStatement = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
            prepareStatement.setString(1, user.getId());
            prepareStatement.setString(2, user.getName());
            prepareStatement.setString(3, user.getPassword());
            return prepareStatement;
        };
        jdbcContextWithStatementStrategy(stmt);

    }

    public User get(String id) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE ID = ?");
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        User user = null;
        if(rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        preparedStatement.close();
        connection.close();

        if(user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return user;
    }

    public void deleteAll() throws SQLException {
        StatementStrategy stmt = (c) -> c.prepareStatement("delete from USERS");
        jdbcContextWithStatementStrategy(stmt);
    }

    public int getCount() throws SQLException {
        Connection connection = null;
        PreparedStatement prepareStatement = null;
        ResultSet rs = null;

        try {
            connection = dataSource.getConnection();
            prepareStatement = connection.prepareStatement("select count(*) from USERS");
            rs = prepareStatement.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            prepareStatement.close();
            connection.close();
            return count;
        } catch(SQLException e) {
            throw e;
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch(SQLException e) {

                }
            }
            if(prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException e) {

                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection connection = null;
        PreparedStatement prepareStatement = null;

        try {
            connection = dataSource.getConnection();
            prepareStatement = stmt.makePreparedStatement(connection) ;
            prepareStatement.executeUpdate();
        } catch(SQLException e){
            throw e;
        } finally {
            if (prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
    }
}
