package dao;

import domain.User;
import java.sql.SQLException;

/**
 * Created by hochulshin on 12/6/16.
 */
public class UserDaoTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDao dao = new UserDao();

        User user = new User();
        user.setId("whilteship");
        user.setName("hochul Shin");
        user.setPassword("married");

        dao.add(user);

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + ":suceess");

    }
}
