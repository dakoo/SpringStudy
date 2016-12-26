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
    public void testDIUsingGenericXmlApplicationContext() throws ClassNotFoundException, SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("dao/applicationContext.xml");
        UserDao dao1 = context.getBean("userDao", UserDao.class);
        UserDao dao2 = context.getBean("userDao", UserDao.class);
        Assert.assertEquals(dao1, dao2);
    }

    @Test
    public void testDIUsingClassPathXMLApplicationContext() throws ClassNotFoundException, SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml", DaoFactory.class);
        UserDao dao1 = context.getBean("userDao", UserDao.class);
        UserDao dao2 = context.getBean("userDao", UserDao.class);
        Assert.assertEquals(dao1, dao2);
    }
}
