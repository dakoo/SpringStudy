package dao;

import domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;

public class UserDaoTest {

    @Test
    public void testDIUsingGenericXmlApplicationContext() throws ClassNotFoundException, SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("dao/applicationContext.xml");
        UserDao dao1 = context.getBean("userDao", UserDao.class);
        UserDao dao2 = context.getBean("userDao", UserDao.class);
        Assert.assertEquals(dao1, dao2);
    }

    @Test
    public void testDIUsingClassPathXMLApplicationContext() throws ClassNotFoundException, SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml", UserDao.class);
        UserDao dao1 = context.getBean("userDao", UserDao.class);
        UserDao dao2 = context.getBean("userDao", UserDao.class);
        Assert.assertEquals(dao1, dao2);
    }

    @Test
    public void testAddAndGet() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml", UserDao.class);
        UserDao dao = context.getBean("userDao", UserDao.class);
        User extractedUser = dao.get("id1");
        Assert.assertNotNull(extractedUser);
        User user = new User("id1", "name1", "passwd1");
        dao.add(user);
        extractedUser = dao.get("id1");
        Assert.assertNotNull(extractedUser);
        Assert.assertEquals(user.getId(), extractedUser.getId());
        Assert.assertEquals(user.getName(), extractedUser.getName());
        Assert.assertEquals(user.getPassword(), extractedUser.getPassword());

    }

    @Test
    public void testCountAndDeleteAll() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml", UserDao.class);
        UserDao dao = context.getBean("userDao", UserDao.class);
        User user1 = new User("id1", "name1", "passwd1");
        User user2 = new User("id2", "name2", "passwd2");
        User user3 = new User("id3", "name3", "passwd3");

        dao.deleteAll();
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
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml", UserDao.class);
        UserDao dao = context.getBean("userDao", UserDao.class);
        dao.deleteAll();
        dao.get("id2");
    }
}
