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

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement prepareStatement = connection.prepareStatement("insert into users(id, name, password) values(?,?,?)");

        prepareStatement.setString(1, user.getId());
        prepareStatement.setString(2, user.getName());
        prepareStatement.setString(3, user.getPassword());
        prepareStatement.executeUpdate();

        prepareStatement.close();
        connection.close();
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
        Connection connection = dataSource.getConnection();

        PreparedStatement prepareStatement = connection.prepareStatement("delete from USERS");
        prepareStatement.executeUpdate();

        prepareStatement.close();
        connection.close();
    }

    public int getCount() throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement prepareStatement = connection.prepareStatement("select count(*) from USERS");
        ResultSet rs = prepareStatement.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        prepareStatement.close();
        connection.close();

        return count;
    }
}
