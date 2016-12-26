package dao;

import domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {

    @Test
    public void testDI() throws ClassNotFoundException, SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao1 = context.getBean("userDao", UserDao.class);
        UserDao dao2 = context.getBean("userDao", UserDao.class);
        Assert.assertEquals(dao1, dao2);
    }

    @Test
    public void testUserDaoConnectionCounting() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);
        CountingConnectionMaker countingConnectionMaker = context.getBean("connectionMaker", CountingConnectionMaker.class);
        Assert.assertEquals(0, countingConnectionMaker.getCounter());
        User user = new User();
        user.setId("1");
        user.setName("name");
        user.setPassword("passwd");
        dao.add(user);
        Assert.assertEquals(1, countingConnectionMaker.getCounter());
        User gotUser = dao.get("1");
        Assert.assertEquals(2, countingConnectionMaker.getCounter());
    }

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
}
