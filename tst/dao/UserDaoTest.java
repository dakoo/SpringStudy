package dao;

import domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;

public class UserDaoTest {

    private UserDao dao;

    @Before
    public void setUp() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml", UserDao.class);
        dao = context.getBean("userDao", UserDao.class);
        dao.deleteAll();
    }

    @Test
    public void testAddAndGet() throws Exception {
        User user = new User("id1", "name1", "passwd1");
        dao.add(user);
        User extractedUser = dao.get("id1");
        Assert.assertNotNull(extractedUser);
        Assert.assertEquals(user.getId(), extractedUser.getId());
        Assert.assertEquals(user.getName(), extractedUser.getName());
        Assert.assertEquals(user.getPassword(), extractedUser.getPassword());

    }

    @Test
    public void testCountAndDeleteAll() throws Exception {
        User user1 = new User("id1", "name1", "passwd1");
        User user2 = new User("id2", "name2", "passwd2");
        User user3 = new User("id3", "name3", "passwd3");
        Assert.assertEquals(0, dao.getCount());
        dao.add(user1);
        Assert.assertEquals(1, dao.getCount());
        dao.add(user2);
        Assert.assertEquals(2, dao.getCount());
        dao.add(user3);
        Assert.assertEquals(3, dao.getCount());
        dao.deleteAll();
        Assert.assertEquals(0, dao.getCount());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException {
        dao.get("id2");
    }
}
