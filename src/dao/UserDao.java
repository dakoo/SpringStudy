package dao;

import domain.User;

import java.sql.*;

public class UserDao {
    public void add(User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/USERS", "root", "Mika0116");
        PreparedStatement prepareStatement = connection.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        prepareStatement.setString(1, user.getId());
        prepareStatement.setString(2, user.getName());
        prepareStatement.setString(3, user.getPassword());
        prepareStatement.executeUpdate();
        prepareStatement.close();
        connection.close();
    }
    public User get(String id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/USERS", "root", "Mika0116");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE ID = ?");
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        User user = new User();
        while (rs.next()) {
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }
        preparedStatement.close();
        connection.close();
        return user;
    }
}
